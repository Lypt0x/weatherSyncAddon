package dev.lypt0x.weathersync.util;

/**
 * Credits to https://github.com/buelowp/sunset/blob/master/src/sunset.h
 */
public class Sunset {

    private static final double SUNSET_OFFICIAL = 90.833,
            SUNSET_NAUTICAL = 102.917,
            SUNSET_CIVIL = 96.067,
            SUNSET_ASTRONOMICAL = 108.167;

    private double latitude;
    private double longitude;
    private double timezoneOffset;
    private double julianDate;

    private int year;
    private int month;
    private int day;

    public Sunset() {}

    public Sunset(double latitude, double longitude, double timezoneOffset) {
        this.setPosition(latitude, longitude, timezoneOffset);
    }

    public void setPosition(double latitude, double longitude, double timezoneOffset) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.setTimezoneOffset(timezoneOffset);
    }

    public void setTimezoneOffset(double timezoneOffset) {
        if (timezoneOffset >= -12.0 && timezoneOffset <= 14.0) {
            this.timezoneOffset = timezoneOffset;
        } else
            this.timezoneOffset = 0.0;
    }

    public double setCurrentDate(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.julianDate = calcJD(year, month, day);
        return this.julianDate;
    }

    public double calcNauticalSunrise() {
        return calcCustomSunrise(SUNSET_NAUTICAL);
    }

    public double calcNauticalSunset() {
        return calcCustomSunset(SUNSET_NAUTICAL);
    }

    public double calcCivilSunrise() {
        return calcCustomSunrise(SUNSET_CIVIL);
    }

    public double calcCivilSunset() {
        return calcCustomSunset(SUNSET_CIVIL);
    }

    public double calcAstronomicalSunrise() {
        return calcCustomSunrise(SUNSET_ASTRONOMICAL);
    }

    public double calcAstronomicalSunset() {
        return calcCustomSunset(SUNSET_ASTRONOMICAL);
    }

    public double calcOfficialSunrise() {
        return calcCustomSunrise(SUNSET_OFFICIAL);
    }

    public double calcOfficialSunset() {
        return calcCustomSunset(SUNSET_OFFICIAL);
    }

    public int moonPhase(long fromEpoch) {
        int moonEpoch = 614100;
        long phase = (fromEpoch - moonEpoch) % 2551443;
        int res = (int) (Math.floor(phase / (24.0 * 3600)) + 1);

        if (res == 30)
            return 0;

        return res;
    }

    public int moonPhase() {
        return moonPhase(System.currentTimeMillis());
    }

    public double calcCustomSunrise(double angle) {
        return calcAbsSunrise(angle) + (60 * timezoneOffset);
    }

    public double calcCustomSunset(double angle) {
        return calcAbsSunset(angle) + (60 * timezoneOffset);
    }

    private double degreesToRadians(double degrees) {
        return degrees * Math.PI / 180;
    }

    private double radiansToDegrees(double radians) {
        return radians * 180 / Math.PI;
    }

    private double calcMeanObliquityOfEcliptic(double julianDate) {
        double seconds = 21.448 - julianDate * (46.8150 + julianDate * (0.00059 - julianDate * (0.001813)));
        return 23.0 + (26.0 + (seconds / 60.0)) / 60.0;
    }

    private double calcObliquityCorrection(double t) {
        double e0 = calcMeanObliquityOfEcliptic(t);
        double omega = 125.04 - 1934.136 * t;

        return e0 + 0.00256 * Math.cos(degreesToRadians(omega));               // in degrees
    }

    private double calcGeomMeanLongSun(double julianDate) {
        return (280.46646 + julianDate * (36000.76983 + julianDate * 0.0003032)) % 360;
    }

    private double calcEccentricityEarthOrbit(double julianDate) {
        return 0.016708634 - julianDate * (0.000042037 + 0.0000001267 * julianDate);
    }

    private double calcGeomMeanAnomalySun(double julianDate) {
        return 357.52911 + julianDate * (35999.05029 - 0.0001537 * julianDate);
    }

    private double calcEquationOfTime(double julianDate) {
        double epsilon = calcObliquityCorrection(julianDate);
        double l0 = calcGeomMeanLongSun(julianDate);
        double e = calcEccentricityEarthOrbit(julianDate);
        double m = calcGeomMeanAnomalySun(julianDate);
        double y = Math.tan(degreesToRadians(epsilon)/2.0);

        y *= y;

        double sin2l0 = Math.sin(2.0 * degreesToRadians(l0));
        double sinm   = Math.sin(degreesToRadians(m));
        double cos2l0 = Math.cos(2.0 * degreesToRadians(l0));
        double sin4l0 = Math.sin(4.0 * degreesToRadians(l0));
        double sin2m  = Math.sin(2.0 * degreesToRadians(m));
        double Etime = y * sin2l0 - 2.0 * e * sinm + 4.0 * e * y * sinm * cos2l0 - 0.5 * y * y * sin4l0 - 1.25 * e * e * sin2m;
        return radiansToDegrees(Etime)*4.0;	// in minutes of time
    }

    private double calcTimeJulianCent(double julianDate) {
        return (julianDate - 2451545.0) / 36525.0;
    }

    private double calcSunTrueLong(double julianDate) {
        double l0 = calcGeomMeanLongSun(julianDate);
        double c = calcSunEqOfCenter(julianDate);
        return l0 + c;
    }

    private double calcSunApparentLong(double julianDate) {
        double o = calcSunTrueLong(julianDate);
        double omega = 125.04 - 1934.136 * julianDate;
        return o - 0.00569 - 0.00478 * Math.sin(degreesToRadians(omega));
    }

    private double calcSunDeclination(double julianDate) {
        double e = calcObliquityCorrection(julianDate);
        double lambda = calcSunApparentLong(julianDate);

        double sint = Math.sin(degreesToRadians(e)) * Math.sin(degreesToRadians(lambda));
        return radiansToDegrees(Math.asin(sint));           // in degrees
    }

    private double calcHourAngleSunrise(double latitude, double solarDec, double offset) {
        double latRad = degreesToRadians(latitude);
        double sdRad  = degreesToRadians(solarDec);

        return (Math.acos(Math.cos(degreesToRadians(offset))/(Math.cos(latRad)*Math.cos(sdRad))-Math.tan(latRad) * Math.tan(sdRad)));              // in radians
    }

    private double calcHourAngleSunset(double latitude, double solarDec, double offset) {
        double latRad = degreesToRadians(latitude);
        double sdRad  = degreesToRadians(solarDec);
        double HA = (Math.acos(Math.cos(degreesToRadians(offset))/(Math.cos(latRad)*Math.cos(sdRad))-Math.tan(latRad) * Math.tan(sdRad)));

        return -HA;              // in radians
    }

    private double calcJD(int year, int month, int day) {
        if (month <= 2) {
            year -= 1;
            month += 12;
        }
        double A = Math.floor(year / 100.0);
        double B = 2 - A + Math.floor(A / 4);
        return Math.floor(365.25 * (year + 4716)) + Math.floor(30.6001 * (month + 1)) + day + B - 1524.5;
    }

    private double calcJDFromJulianCent(double t) {
        return t * 36525.0 + 2451545.0;
    }

    private double calcSunEqOfCenter(double t) {
        double m = calcGeomMeanAnomalySun(t);
        double mrad = degreesToRadians(m);
        double sinm = Math.sin(mrad);
        double sin2m = Math.sin(mrad+mrad);
        double sin3m = Math.sin(mrad+mrad+mrad);

        return sinm * (1.914602 - t * (0.004817 + 0.000014 * t)) + sin2m * (0.019993 - 0.000101 * t) + sin3m * 0.000289;		// in degrees
    }

    private double calcAbsSunrise(double offset) {
        double t = calcTimeJulianCent(this.julianDate);
        // *** First pass to approximate sunrise
        double  eqTime = calcEquationOfTime(t);
        double  solarDec = calcSunDeclination(t);
        double  hourAngle = calcHourAngleSunrise(this.latitude, solarDec, offset);
        double  delta = this.longitude + radiansToDegrees(hourAngle);
        double  timeDiff = 4 * delta;	// in minutes of time
        double  timeUTC = 720 - timeDiff - eqTime;	// in minutes
        double  newt = calcTimeJulianCent(calcJDFromJulianCent(t) + timeUTC/1440.0);

        eqTime = calcEquationOfTime(newt);
        solarDec = calcSunDeclination(newt);

        hourAngle = calcHourAngleSunrise(this.latitude, solarDec, offset);
        delta = this.longitude + radiansToDegrees(hourAngle);
        timeDiff = 4 * delta;
        timeUTC = 720 - timeDiff - eqTime; // in minutes
        return timeUTC;	// return time in minutes from midnight
    }

    private double calcAbsSunset(double offset) {
        double t = calcTimeJulianCent(this.julianDate);
        // *** First pass to approximate sunset
        double  eqTime = calcEquationOfTime(t);
        double  solarDec = calcSunDeclination(t);
        double  hourAngle = calcHourAngleSunset(this.latitude, solarDec, offset);
        double  delta = this.longitude + radiansToDegrees(hourAngle);
        double  timeDiff = 4 * delta;	// in minutes of time
        double  timeUTC = 720 - timeDiff - eqTime;	// in minutes
        double  newt = calcTimeJulianCent(calcJDFromJulianCent(t) + timeUTC/1440.0);

        eqTime = calcEquationOfTime(newt);
        solarDec = calcSunDeclination(newt);

        hourAngle = calcHourAngleSunset(this.latitude, solarDec, offset);
        delta = this.longitude + radiansToDegrees(hourAngle);
        timeDiff = 4 * delta;
        timeUTC = 720 - timeDiff - eqTime; // in minutes

        return timeUTC;	// return time in minutes from midnight
    }

    public double getLatitude() {
        return latitude;
    }

    public double getTimezoneOffset() {
        return timezoneOffset;
    }

    public double getLongitude() {
        return longitude;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }
}
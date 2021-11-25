package dev.lypt0x.weathersync.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static java.lang.Double.NaN;
import static org.junit.jupiter.api.Assertions.*;

class SunsetTest {

    @Test
    void setPosition() {
        Sunset sunset = new Sunset(187, 187, 1337);
        Assertions.assertEquals(187, sunset.getLatitude());
        Assertions.assertEquals(187, sunset.getLongitude());
    }

    @Test
    void setTimezoneOffset() {
        Sunset sunset = new Sunset(187, 187, 1337);
        Assertions.assertEquals(0, sunset.getTimezoneOffset());
    }

    @Test
    void setCurrentDate() {
        Sunset sunset = new Sunset(187, 187, 1337);
        sunset.setCurrentDate(1997, 18, 7);
        Assertions.assertEquals(1997, sunset.getYear());
        Assertions.assertEquals(18, sunset.getMonth());
        Assertions.assertEquals(7, sunset.getDay());
    }

    @Test
    void calcNauticalSunrise() {
        Sunset sunset = new Sunset(187, 187, 1337);
        sunset.setCurrentDate(1997, 18, 7);
        Assertions.assertEquals(-344.5325239309478, sunset.calcNauticalSunrise());
    }

    @Test
    void calcNauticalSunset() {
        Sunset sunset = new Sunset(187, 187, 1337);
        sunset.setCurrentDate(1997, 18, 7);
        Assertions.assertEquals(286.7029355489705, sunset.calcNauticalSunset());
    }

    @Test
    void calcCivilSunrise() {
        Sunset sunset = new Sunset(187, 187, 1337);
        sunset.setCurrentDate(1997, 18, 7);
        Assertions.assertEquals(-374.3374286527816, sunset.calcCivilSunrise());
    }

    @Test
    void calcCivilSunset() {
        Sunset sunset = new Sunset(187, 187, 1337);
        sunset.setCurrentDate(1997, 18, 7);
        Assertions.assertEquals(316.5173801888045, sunset.calcCivilSunset());
    }

    @Test
    void calcAstronomicalSunrise() {
        Sunset sunset = new Sunset(187, 187, 1337);
        sunset.setCurrentDate(1997, 18, 7);
        Assertions.assertEquals(-321.7314427736209, sunset.calcAstronomicalSunrise());
    }

    @Test
    void calcAstronomicalSunset() {
        Sunset sunset = new Sunset(187, 187, 1337);
        sunset.setCurrentDate(1997, 18, 7);
        Assertions.assertEquals(263.89554843753393, sunset.calcAstronomicalSunset());
    }

    @Test
    void calcOfficialSunrise() {
        Sunset sunset = new Sunset(187, 187, 1337);
        sunset.setCurrentDate(1997, 18, 7);
        Assertions.assertEquals(-397.19497214621174, sunset.calcOfficialSunrise());
    }

    @Test
    void calcOfficialSunset() {
        Sunset sunset = new Sunset(187, 187, 1337);
        sunset.setCurrentDate(1997, 18, 7);
        Assertions.assertEquals(339.38340766116943, sunset.calcOfficialSunset());
    }

    @Test
    void calcCustomSunrise() {
        Sunset sunset = new Sunset(187, 187, 1337);
        sunset.setCurrentDate(1997, 18, 7);
        Assertions.assertEquals(NaN, sunset.calcCustomSunrise(0));
    }

    @Test
    void calcCustomSunset() {
        Sunset sunset = new Sunset(187, 187, 1337);
        sunset.setCurrentDate(1997, 18, 7);
        Assertions.assertEquals(NaN, sunset.calcCustomSunset(0));
    }

}
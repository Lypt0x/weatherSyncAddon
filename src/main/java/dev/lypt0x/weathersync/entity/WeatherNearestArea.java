package dev.lypt0x.weathersync.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(value = { "areaName", "country", "population", "region", "weatherUrl" })
public class WeatherNearestArea {

    private String latitude;
    private String longitude;

    public double getLatitude() {
        return Double.parseDouble(latitude);
    }

    public double getLongitude() {
        return Double.parseDouble(longitude);
    }
}

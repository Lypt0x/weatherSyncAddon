package dev.lypt0x.weathersync.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import dev.lypt0x.weathersync.util.Sunset;

import java.util.List;

@JsonIgnoreProperties(value = { "request", "weather", "current_condition" })
public class WeatherEntity {

    @JsonProperty("nearest_area")
    private List<WeatherNearestArea> nearestAreas;

    @JsonIgnore
    private final Sunset sunset;

    public WeatherEntity() {
        this.sunset = new Sunset();
        this.sunset.recognizeDate();
        this.sunset.recognizeTimezoneOffset();
    }

    public List<WeatherNearestArea> getNearestAreas() {
        return nearestAreas;
    }

    public Sunset getSunset() {
        WeatherNearestArea nearestArea = nearestAreas.get(0);
        this.sunset.setPosition(nearestArea.getLatitude(), nearestArea.getLongitude(), 1);
        this.sunset.recognizeTimezoneOffset();
        return this.sunset;
    }
}

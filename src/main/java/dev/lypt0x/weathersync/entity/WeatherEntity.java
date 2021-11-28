package dev.lypt0x.weathersync.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import dev.lypt0x.weathersync.addon.WeatherSyncAddon;
import dev.lypt0x.weathersync.util.Sunset;

import java.time.Instant;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@JsonIgnoreProperties(value = { "request", "weather", "current_condition" })
public class WeatherEntity {

    @JsonProperty("nearest_area")
    private List<WeatherNearestArea> nearestAreas;

    @JsonIgnore
    private final Sunset sunset;
    private final ZoneId zoneId;

    public WeatherEntity() {
        this.sunset = new Sunset();
        this.sunset.recognizeDate();
        this.sunset.recognizeTimezoneOffset();
        this.zoneId = WeatherSyncAddon.getAddon().getGeoManager().getTimeZoneEngine().query(this.sunset.getLatitude(), this.sunset.getLongitude())
                .orElse(ZoneId.systemDefault());
        System.out.println("ZoneId: " + this.zoneId.getRules().getOffset(Instant.now()).getTotalSeconds() / 3600.0);
    }

    public List<WeatherNearestArea> getNearestAreas() {
        return nearestAreas;
    }

    public Sunset getSunset() {
        WeatherNearestArea nearestArea = nearestAreas.get(0);
        this.sunset.setPosition(nearestArea.getLatitude(), nearestArea.getLongitude(),
                this.zoneId.getRules().getOffset(Instant.now()).getTotalSeconds() / 3600.0);
        //this.sunset.recognizeTimezoneOffset();
        return this.sunset;
    }
}

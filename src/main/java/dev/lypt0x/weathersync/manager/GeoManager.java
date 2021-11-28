package dev.lypt0x.weathersync.manager;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.reflect.TypeToken;
import dev.lypt0x.weathersync.addon.WeatherSyncAddon;
import dev.lypt0x.weathersync.rest.WeatherRest;
import net.iakovlev.timeshape.TimeZoneEngine;
import net.labymod.api.LabyModAddon;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class GeoManager {

    private final JavaType geoMapType;
    private final LabyModAddon labyModAddon;
    private ObjectMapper geoObjectMapper;

    private final WeatherRest weatherRest;
    private final TimeZoneEngine timeZoneEngine;

    private Map<String, Set<String>> geoMap;

    public GeoManager(LabyModAddon labyModAddon) {
        this.labyModAddon = labyModAddon;
        this.geoObjectMapper = new ObjectMapper();
        this.geoMapType = this.geoObjectMapper.getTypeFactory().constructParametricType(Map.class, String.class, Set.class);
        this.weatherRest = new WeatherRest(this.geoObjectMapper);
        this.timeZoneEngine = TimeZoneEngine.initialize();
    }

    public void loadGeoData() {
        try {
            try(InputStream url = WeatherSyncAddon.class.getResourceAsStream("/assets/geo.json")) {
                this.geoMap = this.geoObjectMapper.readValue(
                        url,
                        this.geoMapType
                );
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public Set<String> getCitiesByCountry(String country) {
        return this.geoMap.get(country);
    }

    public Set<String> getCountries() {
        return geoMap.keySet();
    }

    public Map<String, Set<String>> getGeoMap() {
        return geoMap;
    }

    public WeatherRest getWeatherRest() {
        return weatherRest;
    }

    public TimeZoneEngine getTimeZoneEngine() {
        return timeZoneEngine;
    }

    public ObjectMapper getGeoObjectMapper() {
        return geoObjectMapper;
    }
}

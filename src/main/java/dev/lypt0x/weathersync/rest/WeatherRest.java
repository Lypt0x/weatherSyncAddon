package dev.lypt0x.weathersync.rest;

import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonParseException;
import dev.lypt0x.weathersync.entity.WeatherEntity;
import net.iakovlev.timeshape.TimeZoneEngine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.net.URL;
import java.time.ZoneId;
import java.util.Optional;

public class WeatherRest {

    private final ObjectMapper geoObjectMapper;

    public WeatherRest(final ObjectMapper geoObjectMapper) {
        this.geoObjectMapper = geoObjectMapper;
    }

    public WeatherEntity getWeather(String city) throws IOException {
        // Request to https://wttr.in/{city}?format=j1

        // Create a new HTTP client
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet(String.format("https://wttr.in/%s?format=j1", city));

            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                return this.geoObjectMapper.readValue(response.getEntity().getContent(), WeatherEntity.class);
            }
        }

    }

}

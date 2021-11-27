package dev.lypt0x.weathersync.addon;

import dev.lypt0x.weathersync.listener.WorldListener;
import dev.lypt0x.weathersync.rest.WeatherRest;
import dev.lypt0x.weathersync.util.Sunset;
import net.iakovlev.timeshape.TimeZoneEngine;
import net.labymod.api.LabyModAddon;
import net.labymod.settings.elements.SettingsElement;

import java.io.IOException;
import java.util.List;

public final class WeatherSyncAddon extends LabyModAddon {

    private static WeatherSyncAddon addon;
    private WeatherRest weatherRest;
    private TimeZoneEngine timeZoneEngine;

    @Override
    public void onEnable() {
        WeatherSyncAddon.addon = this;
        this.weatherRest = new WeatherRest();
        this.timeZoneEngine = TimeZoneEngine.initialize();

        this.api.getEventService().registerListener(new WorldListener());
    }

    @Override
    public void loadConfig() {

    }

    @Override
    protected void fillSettings(List<SettingsElement> list) {

    }

    public TimeZoneEngine getTimeZoneEngine() {
        return timeZoneEngine;
    }

    public WeatherRest getWeatherRest() {
        return weatherRest;
    }

    public static WeatherSyncAddon getAddon() {
        return addon;
    }
}

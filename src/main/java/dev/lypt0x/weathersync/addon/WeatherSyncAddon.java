package dev.lypt0x.weathersync.addon;

import com.google.gson.JsonObject;
import dev.lypt0x.weathersync.listener.WorldListener;
import dev.lypt0x.weathersync.manager.SettingsManager;
import dev.lypt0x.weathersync.rest.WeatherRest;
import net.iakovlev.timeshape.TimeZoneEngine;
import net.labymod.api.LabyModAddon;
import net.labymod.gui.elements.DropDownMenu;
import net.labymod.main.LabyMod;
import net.labymod.settings.elements.*;
import net.labymod.utils.Material;
import org.apache.commons.compress.utils.Lists;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public final class WeatherSyncAddon extends LabyModAddon {

    private static WeatherSyncAddon addon;
    private WeatherRest weatherRest;
    private TimeZoneEngine timeZoneEngine;
    private SettingsManager settingsManager;

    private List<String> countries = Lists.newArrayList();


    @Override
    public void onEnable() {
        WeatherSyncAddon.addon = this;
        this.weatherRest = new WeatherRest();
        this.timeZoneEngine = TimeZoneEngine.initialize();
        this.settingsManager = new SettingsManager();
        this.api.getEventService().registerListener(new WorldListener());

        String[] countryCodes = Locale.getISOCountries();

        for (String countryCode : countryCodes){
            Locale locale = new Locale("", countryCode);
            String name = locale.getDisplayCountry();
            countries.add(name);
        }

    }

    @Override
    public void loadConfig() {

    }

    @Override
    protected void fillSettings(List<SettingsElement> list) {
        DropDownMenu<String> country = new DropDownMenu("Country", 4, 4, 4,4);
        for(String countries : countries) {
            country.addOption(countries);
        }

        BooleanElement enabled = new BooleanElement("Enabled", new ControlElement.IconData(Material.LEVER));
        list.add(enabled);
        list.add(new DropDownElement<>("Country", country));

        this.settingsManager.setEnabled(enabled.getCurrentValue());
        this.saveConfig();
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

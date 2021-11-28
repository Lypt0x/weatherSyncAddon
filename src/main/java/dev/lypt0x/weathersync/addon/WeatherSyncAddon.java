package dev.lypt0x.weathersync.addon;

import com.google.gson.JsonObject;
import dev.lypt0x.weathersync.listener.WorldListener;
import dev.lypt0x.weathersync.manager.GeoManager;
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
    private final GeoManager geoManager;
    private final SettingsManager settingsManager;

    public WeatherSyncAddon() {
        WeatherSyncAddon.addon = this;
        this.geoManager = new GeoManager(this);
        this.settingsManager = new SettingsManager();
    }

    @Override
    public void onEnable() {
        this.geoManager.loadGeoData();

        this.api.getEventService().registerListener(new WorldListener());
    }

    @Override
    public void loadConfig() {
        this.settingsManager.setEnabled(
                !this.getConfig().has("enabled") || this.getConfig().get("enabled").getAsBoolean()
        );
    }

    @Override
    protected void fillSettings(List<SettingsElement> list) {
        DropDownMenu<String> country = new DropDownMenu<>("Country", 4, 4, 4,4);
        this.geoManager.getCountries().forEach(country::addOption);

        list.add(
                new BooleanElement(
                        "Enabled", this, new ControlElement.IconData(Material.LEVER),
                        "enabled", true
                )
        );

        list.add(new DropDownElement<>("Country", country));
    }

    public GeoManager getGeoManager() {
        return geoManager;
    }

    public static WeatherSyncAddon getAddon() {
        return addon;
    }
}

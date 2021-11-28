package dev.lypt0x.weathersync.addon;

import dev.lypt0x.weathersync.listener.WorldListener;
import dev.lypt0x.weathersync.manager.GeoManager;
import dev.lypt0x.weathersync.manager.SettingsContainer;
import net.labymod.api.LabyModAddon;
import net.labymod.gui.elements.DropDownMenu;
import net.labymod.settings.elements.*;
import net.labymod.utils.Consumer;
import net.labymod.utils.Material;

import java.util.List;

public final class WeatherSyncAddon extends LabyModAddon {

    private static WeatherSyncAddon addon;
    private final GeoManager geoManager;
    private final SettingsContainer settingsContainer;

    public WeatherSyncAddon() {
        WeatherSyncAddon.addon = this;
        this.geoManager = new GeoManager(this);
        this.settingsContainer = new SettingsContainer();
    }

    @Override
    public void onEnable() {
        this.geoManager.loadGeoData();

        this.api.getEventService().registerListener(new WorldListener());
    }

    @Override
    public void loadConfig() {
        this.settingsContainer.setEnabled(
                !this.getConfig().has("enabled") || this.getConfig().get("enabled").getAsBoolean()
        );
    }

    @Override
    protected void fillSettings(List<SettingsElement> list) {
        DropDownMenu<String> country = new DropDownMenu<>("Country", 4, 4, 4, 4);
        this.geoManager.getCountries().forEach(country::addOption);

        DropDownMenu<String> city = new DropDownMenu<>("City", 4, 8, 4, 4);

        list.add(
                new BooleanElement(
                        "Enabled", this, new ControlElement.IconData(Material.LEVER),
                        "enabled", true
                )
        );

        DropDownElement<String> countryElement = new DropDownElement<>("Country", country);
        DropDownElement<String> cityElement = new DropDownElement<>("City", city);

        countryElement.setChangeListener(new Consumer() {
            @Override
            public void accept(Object o) {
                cityElement.getDropDownMenu().clear();
                String object = (String) o;
                getGeoManager().getCitiesByCountry((object != null) ? !object.equals("Country") ? object : "Germany" : "Germany")
                        .forEach(cityElement.getDropDownMenu()::addOption);
            }
        });

        list.add(countryElement);
        list.add(cityElement);

    }

    public GeoManager getGeoManager() {
        return geoManager;
    }

    public static WeatherSyncAddon getAddon() {
        return addon;
    }
}

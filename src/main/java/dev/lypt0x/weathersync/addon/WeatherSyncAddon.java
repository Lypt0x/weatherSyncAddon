package dev.lypt0x.weathersync.addon;

import com.google.common.base.Preconditions;
import dev.lypt0x.weathersync.listener.WorldListener;
import dev.lypt0x.weathersync.manager.GeoManager;
import dev.lypt0x.weathersync.manager.SettingsContainer;
import dev.lypt0x.weathersync.util.LabyModClass;
import net.labymod.api.LabyModAddon;
import net.labymod.gui.elements.DropDownMenu;
import net.labymod.settings.elements.*;
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

        Runtime.getRuntime().addShutdownHook(new Thread(this::saveConfig));
    }

    @Override
    public void loadConfig() {
        this.settingsContainer.setEnabled(
                !this.getConfig().has("enabled") || this.getConfig().get("enabled").getAsBoolean()
        );
    }

    @Override
    protected void fillSettings(List<SettingsElement> list) {
        DropDownMenu<String> countryMenu = new DropDownMenu<>("Country", 4, 4, 4, 4);
        DropDownMenu<String> cityMenu = new DropDownMenu<>("City", 4, 8, 4, 4);

        DropDownElement<String> countryElement = new DropDownElement<>("Country", countryMenu);
        DropDownElement<String> cityElement = new DropDownElement<>("City", cityMenu);

        this.geoManager.getCountries().forEach(countryMenu::addOption);

        list.add(
                new BooleanElement(
                        "Enabled", this, new ControlElement.IconData(Material.LEVER),
                        "enabled", true
                )
        );

        this.applyListener(countryElement, cityElement);

        list.add(countryElement);
        list.add(cityElement);
    }

    private void applyListener(DropDownElement<String> countryElement, DropDownElement<String> cityElement) {
        countryElement.setChangeListener(country -> {
            if (country == null) {
                cityElement.getDropDownMenu().setEnabled(false);
            } else {
                cityElement.getDropDownMenu().setEnabled(true);
                cityElement.getDropDownMenu().clear();

                DropDownMenu<String> cityMenu = LabyModClass.invoke(cityElement, "getDropDownMenu");
                Preconditions.checkNotNull(cityMenu);

                this.getGeoManager().getCitiesByCountry(country.equals("Country") ? "Germany" : country)
                        .forEach(cityMenu::addOption);
            }
        });
    }

    public GeoManager getGeoManager() {
        return geoManager;
    }

    public static WeatherSyncAddon getAddon() {
        return addon;
    }
}

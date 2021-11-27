package dev.lypt0x.weathersync.addon;

import dev.lypt0x.weathersync.listener.WorldListener;
import dev.lypt0x.weathersync.rest.WeatherRest;
import net.labymod.api.LabyModAddon;
import net.labymod.settings.elements.SettingsElement;

import java.util.List;

public final class WeatherSyncAddon extends LabyModAddon {

    @Override
    public void onEnable() {
        this.api.getEventService().registerListener(new WorldListener());
    }

    @Override
    public void loadConfig() {

    }

    @Override
    protected void fillSettings(List<SettingsElement> list) {

    }

    public static long time = 12000;

    public static boolean getSpeedEnabled() {
        return true;
    }

    public static void updateTime() {
        System.out.println("update");
    }

}

package dev.lypt0x.weathersync.listener;

import dev.lypt0x.weathersync.addon.WeatherSyncAddon;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.events.client.renderer.RenderWorldLastEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.play.ClientPlayNetHandler;

public class WorldListener {

    @Subscribe
    public void handleWorldLastEvent(RenderWorldLastEvent event) {

        /*

        TODO: Get data about settings page

        long time = (long)(12000 * WeatherSyncAddon.getAddon().getSunset().getPercentSunset());

        if (Minecraft.getInstance().world != null) {
            Minecraft.getInstance().world.setDayTime(-time);
        }
         */

    }

}

package dev.lypt0x.weathersync.listener;

import net.labymod.api.event.Subscribe;
import net.labymod.api.event.events.client.renderer.RenderWorldLastEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.play.ClientPlayNetHandler;

public class WorldListener {

    @Subscribe
    public void handleWorldLastEvent(RenderWorldLastEvent event) {
        /*
        public void handleTimeUpdate(SUpdateTimePacket packetIn) {
      PacketThreadUtil.checkThreadAndEnqueue(packetIn, this, this.client);
      this.client.world.func_239134_a_(packetIn.getTotalWorldTime());
      this.client.world.setDayTime(packetIn.getWorldTime());
   }
         */
        
        if (Minecraft.getInstance().player == null)
            return;
        
        Minecraft.getInstance().player.worldClient.setDayTime(12000);
    }

}

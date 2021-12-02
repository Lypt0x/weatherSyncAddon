package dev.lypt0x.weathersync.mixin;

import dev.lypt0x.weathersync.addon.WeatherSyncAddon;
import dev.lypt0x.weathersync.manager.SettingsContainer;
import dev.lypt0x.weathersync.rest.WeatherRest;
import dev.lypt0x.weathersync.util.Sunset;
import io.netty.channel.ChannelHandlerContext;
import net.labymod.user.cosmetic.layers.ParticleStar;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.play.ClientPlayNetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SSpawnParticlePacket;
import net.minecraft.network.play.server.SUpdateTimePacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.world.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;

@Mixin(NetworkManager.class)
public class PacketMixin {

    private final SettingsContainer settingsContainer = WeatherSyncAddon.getAddon().getSettingsContainer();

    @Inject(method = "channelRead0*", at = @At("HEAD"), cancellable = true)
    private void onChannelRead(ChannelHandlerContext context, IPacket<?> packet, CallbackInfo callbackInfo) {

        if (packet instanceof SUpdateTimePacket) {
            //cancel if setting is enabled
            if (this.settingsContainer.isEnabled()) callbackInfo.cancel();
        }

        if (packet instanceof SSpawnParticlePacket)
            System.out.println("Packet: " + ((SSpawnParticlePacket) packet).getParticle().getParameters());

    }

}

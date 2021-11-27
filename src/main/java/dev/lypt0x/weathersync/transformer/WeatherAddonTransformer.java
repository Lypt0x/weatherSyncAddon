package dev.lypt0x.weathersync.transformer;

import net.labymod.addon.AddonTransformer;
import net.labymod.api.TransformerType;

public class WeatherAddonTransformer extends AddonTransformer {

    @Override
    public void registerTransformers() {
        this.registerTransformer(TransformerType.VANILLA, "weathersync.mixin.json");
    }

}

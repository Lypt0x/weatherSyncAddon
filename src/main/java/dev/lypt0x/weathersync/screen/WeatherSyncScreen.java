package dev.lypt0x.weathersync.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.labymod.gui.elements.Tabs;
import net.minecraft.client.gui.IngameGui;
import net.minecraft.client.gui.screen.IngameMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class WeatherSyncScreen extends Screen {

    public WeatherSyncScreen() {
        super(ITextComponent.getTextComponentOrEmpty("WeatherSync"));
    }

    private void addIngameButton() {
        this.addButton(new Button(this.width / 2 - 102, this.height / 4, 98, 20,
                new TranslationTextComponent("Back"),
                (buttons) -> {
                    this.minecraft.displayGuiScreen(new IngameMenuScreen(false));
                }));
    }

    private void addButton() {
        this.addButton(new Button(this.width / 2, this.height / 4, 98, 20,
                new TranslationTextComponent("Back"),
                (buttons) -> {
                    this.minecraft.displayGuiScreen(null);
                }));
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);

        drawCenteredString(matrixStack, this.font, this.title, this.width / 2, 40, 16777215);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }
}

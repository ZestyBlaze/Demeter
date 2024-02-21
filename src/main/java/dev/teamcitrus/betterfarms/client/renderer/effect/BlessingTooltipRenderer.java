package dev.teamcitrus.betterfarms.client.renderer.effect;

import com.mojang.blaze3d.platform.Window;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.neoforged.neoforge.client.extensions.common.IClientMobEffectExtensions;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class BlessingTooltipRenderer implements IClientMobEffectExtensions {
    private final Function<MobEffectInstance, String> constructEffectTooltip;

    public BlessingTooltipRenderer(Function<MobEffectInstance, String> constructEffectTooltip) {
        this.constructEffectTooltip = constructEffectTooltip;
    }

    @Override
    public boolean renderInventoryIcon(MobEffectInstance instance, EffectRenderingInventoryScreen<?> screen, GuiGraphics guiGraphics, int x, int y, int blitOffset) {
        renderTooltip(guiGraphics, x, y, Component.literal(constructEffectTooltip.apply(instance)));
        return false;
    }

    @Override
    public boolean isVisibleInGui(MobEffectInstance instance) {
        return false;
    }

    private void renderTooltip(GuiGraphics graphics, int x, int y, Component tooltip) {
        Minecraft mc = Minecraft.getInstance();
        Window window = mc.getWindow();

        int width = window.getGuiScaledWidth();
        int height = window.getGuiScaledHeight();
        int mouseX = (int) (mc.mouseHandler.xpos() * width / window.getScreenWidth());
        int mouseY = (int) (mc.mouseHandler.ypos() * height / window.getScreenHeight());

        if (x < mouseX && mouseX < x + 120 && y < mouseY && mouseY < y + 32) {
            graphics.renderTooltip(mc.font, List.of(tooltip), Optional.empty(), mouseX, mouseY);
        }
    }
}

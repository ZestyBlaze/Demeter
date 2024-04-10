package dev.teamcitrus.betterfarms;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.teamcitrus.betterfarms.api.util.QualityUtil;
import dev.teamcitrus.betterfarms.registry.BlockRegistry;
import dev.teamcitrus.betterfarms.registry.ItemRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;

@Mod.EventBusSubscriber(modid = BetterFarms.MODID, bus = Mod.EventBusSubscriber.Bus.MOD ,value = Dist.CLIENT)
public class BetterFarmsClient {
    @SubscribeEvent
    public static void registerColors(RegisterColorHandlersEvent.Block event) {
        event.register(((pState, pLevel, pPos, pTintIndex) -> 0xFFFEFCFF), BlockRegistry.MILK_CAULDRON_BLOCK.get());
    }

    @SubscribeEvent
    public static void registerColors(RegisterColorHandlersEvent.Item event) {
        event.register(((pStack, pTintIndex) -> 0xFEFCFF), ItemRegistry.MILK_BOTTLE.get());
    }

    public static void renderIcon(GuiGraphics guiGraphics, ItemStack stack, int xOffset, int yOffset) {
        if (!isItemMouseCarried(stack)) {
            PoseStack poseStack = guiGraphics.pose();
            poseStack.pushPose();
            guiGraphics.blit(BetterFarms.id("textures/item/quality/" + QualityUtil.getQuality(stack).getName() + ".png"), xOffset, yOffset, 200, 0, 0, 16, 16, 16, 16);
            poseStack.popPose();
        }
    }

    private static boolean isItemMouseCarried(ItemStack stack) {
        return Minecraft.getInstance().screen instanceof AbstractContainerScreen<?> containerScreen && containerScreen.getMenu().getCarried() == stack;
    }
}

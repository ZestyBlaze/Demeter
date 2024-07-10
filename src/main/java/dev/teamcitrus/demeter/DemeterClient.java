package dev.teamcitrus.demeter;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.teamcitrus.citruslib.util.ScreenUtil;
import dev.teamcitrus.demeter.registry.BlockRegistry;
import dev.teamcitrus.demeter.registry.ItemRegistry;
import dev.teamcitrus.demeter.util.QualityUtil;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;

@EventBusSubscriber(modid = Demeter.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class DemeterClient {
    @SubscribeEvent
    public static void registerColors(RegisterColorHandlersEvent.Block event) {
        event.register(((pState, pLevel, pPos, pTintIndex) -> 0xFFFEFCFF), BlockRegistry.MILK_CAULDRON_BLOCK.get());
    }

    @SubscribeEvent
    public static void registerColors(RegisterColorHandlersEvent.Item event) {
        event.register(((pStack, pTintIndex) -> 0xFFFEFCFF), ItemRegistry.MILK_BOTTLE.get());
    }

    public static void renderIcon(GuiGraphics guiGraphics, ItemStack stack, int xOffset, int yOffset) {
        if (!ScreenUtil.isItemInHand(stack)) {
            PoseStack poseStack = guiGraphics.pose();
            poseStack.pushPose();
            guiGraphics.blit(Demeter.id("textures/item/quality/" + QualityUtil.getQuality(stack).getName() + ".png"), xOffset, yOffset, 200, 0, 0, 16, 16, 16, 16);
            poseStack.popPose();
        }
    }
}

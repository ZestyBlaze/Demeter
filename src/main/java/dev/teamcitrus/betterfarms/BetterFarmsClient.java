package dev.teamcitrus.betterfarms;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.teamcitrus.betterfarms.api.util.QualityUtil;
import dev.teamcitrus.betterfarms.client.renderer.entity.HarvestGoddessRenderer;
import dev.teamcitrus.betterfarms.registry.BlockRegistry;
import dev.teamcitrus.betterfarms.registry.EntityTypeRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;

@Mod.EventBusSubscriber(modid = BetterFarms.MODID, bus = Mod.EventBusSubscriber.Bus.MOD ,value = Dist.CLIENT)
public class BetterFarmsClient {
    public static final ModelLayerLocation GODDESS_MODEL = new ModelLayerLocation(BetterFarms.id("harvest_goddess"), "main");

    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(GODDESS_MODEL, () -> LayerDefinition.create(HumanoidModel.createMesh(CubeDeformation.NONE, 0.0f), 64, 64));
    }

    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(EntityTypeRegistry.HARVEST_GODDESS.get(), HarvestGoddessRenderer::new);
    }

    @SubscribeEvent
    public static void registerColors(RegisterColorHandlersEvent.Block event) {
        event.register(((pState, pLevel, pPos, pTintIndex) -> 0xFFFEFCFF), BlockRegistry.MILK_CAULDRON_BLOCK.get());
    }

    public static boolean renderIcon(GuiGraphics guiGraphics, Font font, ItemStack stack, int xOffset, int yOffset) {
        if (!isItemMouseCarried(stack)) {
            PoseStack poseStack = guiGraphics.pose();
            poseStack.pushPose();
            guiGraphics.blit(BetterFarms.id("textures/item/quality/" + QualityUtil.getQuality(stack).getName() + ".png"), xOffset, yOffset, 200, 0, 0, 16, 16, 16, 16);
            poseStack.popPose();
            return true;
        }
        return false;
    }

    private static boolean isItemMouseCarried(ItemStack stack) {
        return Minecraft.getInstance().screen instanceof AbstractContainerScreen<?> containerScreen && containerScreen.getMenu().getCarried() == stack;
    }
}

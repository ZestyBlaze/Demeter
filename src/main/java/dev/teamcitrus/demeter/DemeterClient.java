package dev.teamcitrus.demeter;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.teamcitrus.citruslib.util.ScreenUtil;
import dev.teamcitrus.demeter.component.QualityLevel;
import dev.teamcitrus.demeter.registry.BlockRegistry;
import dev.teamcitrus.demeter.registry.ComponentRegistry;
import dev.teamcitrus.demeter.registry.ItemRegistry;
import dev.teamcitrus.demeter.util.QualityUtil;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(modid = Demeter.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class DemeterClient {
    @SubscribeEvent
    public static void registerItemProperties(FMLClientSetupEvent event) {
        event.enqueueWork(() -> Sheets.addWoodType(BlockRegistry.MAPLE_WOOD_TYPE));

        ItemProperties.register(ItemRegistry.WATERING_CAN.get(), Demeter.id("level"), ((itemStack, clientLevel, livingEntity, i) -> {
            if (itemStack.has(ComponentRegistry.QUALITY_LEVEL.get())) {
                QualityLevel level = itemStack.get(ComponentRegistry.QUALITY_LEVEL.get()).level();
                if (level.equals(QualityLevel.COPPER)) {
                    return 1;
                }
                if (level.equals(QualityLevel.IRON)) {
                    return 2;
                }
                if (level.equals(QualityLevel.NETHERITE)) {
                    return 3;
                }
            }
            return 0;
        }));
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

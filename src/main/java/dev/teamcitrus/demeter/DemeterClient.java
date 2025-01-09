package dev.teamcitrus.demeter;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.teamcitrus.citruslib.util.ScreenUtil;
import dev.teamcitrus.demeter.client.DemeterHud;
import dev.teamcitrus.demeter.client.HUDRenderData;
import dev.teamcitrus.demeter.client.property.QualityProperty;
import dev.teamcitrus.demeter.compat.accessories.AccessoriesCompat;
import dev.teamcitrus.demeter.config.DemeterConfig;
import dev.teamcitrus.demeter.data.providers.DemeterItemTagsProvider;
import dev.teamcitrus.demeter.registry.BlockRegistry;
import dev.teamcitrus.demeter.util.PlayerUtil;
import dev.teamcitrus.demeter.util.QualityUtil;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.ItemDecoratorHandler;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.event.RegisterItemDecorationsEvent;
import net.neoforged.neoforge.client.event.RegisterSelectItemModelPropertyEvent;

@EventBusSubscriber(modid = Demeter.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class DemeterClient {
    public static Object2ObjectMap<ResourceKey<Level>, HUDRenderData> RENDERERS = new Object2ObjectOpenHashMap<>();

    @SubscribeEvent
    public static void registerItemProperties(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            Sheets.addWoodType(BlockRegistry.MAPLE_WOOD_TYPE);

            RENDERERS.put(Level.OVERWORLD, new DemeterHud());
        });
    }

    @SubscribeEvent
    public static void registerSelectItemModels(RegisterSelectItemModelPropertyEvent event) {
        event.register(QualityProperty.QUALITY, QualityProperty.TYPE);
    }

    @SubscribeEvent
    public static void registerGuiOverlay(RegisterGuiLayersEvent event) {
        event.registerAboveAll(Demeter.id("hud"), (guiGraphics, deltaTracker) -> {
            if (RENDERERS.isEmpty()) return;
            if (!Minecraft.getInstance().options.hideGui) {
                guiGraphics.pose().pushPose();
                renderHUD(Minecraft.getInstance(), guiGraphics);
                guiGraphics.pose().popPose();
            }
        });
    }

    private static void renderHUD(Minecraft mc, GuiGraphics graphics) {
        if (mc.getDebugOverlay().showDebugScreen()) return;
        HUDRenderData hud = RENDERERS.get(mc.level.dimension());
        if (hud != null) {
            PoseStack matrix = graphics.pose();
            RenderSystem.enableBlend();
            int x = hud.getX();
            int y = hud.getY();
            if (hud.isEnabled(mc)) {
                ResourceLocation texture = hud.getTexture(mc);
                if (texture != null) {
                    RenderSystem.setShaderColor(1F, 1F, 1F, 1F);
                    //mc.getTextureManager().bindForSetup(texture);//inMine ? MINE_HUD : season.HUD);
                    //graphics.blit(resourceLocation -> RenderType.gui(), texture, x - 44, y - 35, 0, 0, 256, 110);
                    graphics.blit(resourceLocation -> RenderType.gui(), texture, x - 44, y - 35, 0, 0, 256, 110, 1, 1);
                }

                //Enlarge the Day
                matrix.pushPose();
                matrix.scale(1.4F, 1.4F, 1.4F);
                Component header = hud.getHeader(mc);
                graphics.drawString(mc.font, header, (int)(x / 1.4F) + 30, (int)(y / 1.4F) + 7, 0xFFFFFFFF);
                matrix.popPose();
            }

            //Draw the time
            if (DemeterConfig.displayClockInHUD.get()) {
                if (!DemeterConfig.requireClockItemForTime.get() || (DemeterConfig.requireClockItemForTime.get() &&
                        (PlayerUtil.hasInHand(mc.player, DemeterItemTagsProvider.CLOCKS) || AccessoriesCompat.isWearing(mc.player, AccessoriesCompat.Items.POCKET_WATCH.get()))))
                    graphics.drawString(mc.font, hud.getFooter(mc), x + hud.getClockX(), y + hud.getClockY(), 0xFFFFFFFF);
            }
            RenderSystem.disableBlend();
        }
    }

    public static void renderIcon(GuiGraphics guiGraphics, ItemStack stack, int xOffset, int yOffset) {
        if (!ScreenUtil.isItemInHand(stack)) {
            PoseStack poseStack = guiGraphics.pose();
            poseStack.pushPose();
            guiGraphics.blit(RenderType::guiTextured,
                    Demeter.id("textures/item/quality/" + QualityUtil.getQuality(stack).getName() + ".png"),
                    xOffset, yOffset, 0, 0, 16, 16, 16, 16, 0xFFFFFFFF);
            RenderSystem.disableDepthTest();
            poseStack.popPose();
        }
    }
}

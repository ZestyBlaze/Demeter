package dev.teamcitrus.demeter;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.teamcitrus.citruslib.util.ScreenUtil;
import dev.teamcitrus.demeter.attachment.AnimalAttachment;
import dev.teamcitrus.demeter.client.DemeterHud;
import dev.teamcitrus.demeter.client.HUDRenderData;
import dev.teamcitrus.demeter.client.models.layer.TuskLayer;
import dev.teamcitrus.demeter.client.models.model.TuskModel;
import dev.teamcitrus.demeter.client.property.QualityProperty;
import dev.teamcitrus.demeter.compat.accessories.AccessoriesCompat;
import dev.teamcitrus.demeter.config.DemeterConfig;
import dev.teamcitrus.demeter.data.providers.DemeterItemTagsProvider;
import dev.teamcitrus.demeter.registry.BlockRegistry;
import dev.teamcitrus.demeter.util.AnimalUtil;
import dev.teamcitrus.demeter.util.PlayerUtil;
import dev.teamcitrus.demeter.util.QualityUtil;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.entity.PigRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.context.ContextKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.event.RegisterSelectItemModelPropertyEvent;
import net.neoforged.neoforge.client.renderstate.RegisterRenderStateModifiersEvent;

@EventBusSubscriber(modid = Demeter.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class DemeterClient {
    public static final ContextKey<AnimalAttachment.AnimalGenders> GENDER_KEY = new ContextKey<>(Demeter.id("gender"));
    public static final ModelLayerLocation TUSKS = new ModelLayerLocation(Demeter.id("tusks"), "tusks");
    public static Object2ObjectMap<ResourceKey<Level>, HUDRenderData> RENDERERS = new Object2ObjectOpenHashMap<>();

    @SubscribeEvent
    public static void clientCommonSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            Sheets.addWoodType(BlockRegistry.MAPLE_WOOD_TYPE);

            RENDERERS.put(Level.OVERWORLD, new DemeterHud());
        });
    }

    @SubscribeEvent
    public static void registerModels(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(TUSKS, TuskModel::createModel);
    }

    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.AddLayers event) {
        PigRenderer pigRenderer = event.getRenderer(EntityType.PIG);
        pigRenderer.addLayer(new TuskLayer(pigRenderer, event.getEntityModels()));
    }

    @SubscribeEvent
    public static void registerStateModifiers(RegisterRenderStateModifiersEvent event) {
        event.registerEntityModifier(PigRenderer.class, (pig, pigRenderState) ->
                pigRenderState.setRenderData(GENDER_KEY, AnimalUtil.getAnimalData(pig).getGender()));
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
                    graphics.blit(RenderType::guiTextured, texture, x - 44, y - 35, 0, 0, 256, 110, 16, 16);
                }

                matrix.pushPose();
                matrix.scale(1.4F, 1.4F, 1.4F);
                Component header = hud.getHeader(mc);
                graphics.drawString(mc.font, header, (int)(x / 1.4F) + 30, (int)(y / 1.4F) + 7, 0xFFFFFFFF);
                matrix.popPose();
            }

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
            poseStack.translate(0.0f, 0.0f, 200.0f);
            guiGraphics.blit(RenderType::guiTextured,
                    Demeter.id("textures/item/quality/" + QualityUtil.getQuality(stack).getName() + ".png"),
                    xOffset, yOffset, 0, 0, 16, 16, 16, 16, 0xFFFFFFFF);
            poseStack.popPose();
        }
    }
}

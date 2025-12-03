package dev.teamcitrus.demeter;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.teamcitrus.citruslib.util.ScreenUtil;
import dev.teamcitrus.demeter.client.DemeterHud;
import dev.teamcitrus.demeter.client.HUDRenderData;
import dev.teamcitrus.demeter.client.layer.TuskLayer;
import dev.teamcitrus.demeter.client.model.TuskModel;
import dev.teamcitrus.demeter.client.property.QualityProperty;
import dev.teamcitrus.demeter.client.tooltip.ClientFoodPouchTooltip;
import dev.teamcitrus.demeter.compat.curios.CuriosCompat;
import dev.teamcitrus.demeter.config.DemeterConfig;
import dev.teamcitrus.demeter.data.providers.DemeterItemTagsProvider;
import dev.teamcitrus.demeter.duck.AnimalSexes;
import dev.teamcitrus.demeter.item.pouch.FoodPouchTooltip;
import dev.teamcitrus.demeter.registry.BlockRegistry;
import dev.teamcitrus.demeter.registry.EntityTypeRegistry;
import dev.teamcitrus.demeter.registry.FluidRegistry;
import dev.teamcitrus.demeter.registry.FluidTypeRegistry;
import dev.teamcitrus.demeter.util.AnimalUtil;
import dev.teamcitrus.demeter.util.PlayerUtil;
import dev.teamcitrus.demeter.util.QualityUtil;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.entity.BoatRenderer;
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
import net.neoforged.neoforge.client.event.RegisterClientTooltipComponentFactoriesEvent;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.event.RegisterSelectItemModelPropertyEvent;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.client.renderstate.RegisterRenderStateModifiersEvent;

@EventBusSubscriber(modid = Demeter.MODID, value = Dist.CLIENT)
public class DemeterClient {
    public static final ResourceLocation WINE_TEXTURE = Demeter.id("block/fluids/wine");

    public static final ContextKey<AnimalSexes> SEX_KEY = new ContextKey<>(Demeter.id("sex"));
    public static final ModelLayerLocation MAPLE_BOAT = new ModelLayerLocation(Demeter.id("boat/maple"), "main");
    public static final ModelLayerLocation MAPLE_CHEST_BOAT = new ModelLayerLocation(Demeter.id("chest_boat/maple"), "main");
    public static final ModelLayerLocation TUSKS = new ModelLayerLocation(Demeter.id("tusks"), "main");
    public static final Object2ObjectMap<ResourceKey<Level>, HUDRenderData> RENDERERS = new Object2ObjectOpenHashMap<>();

    @SubscribeEvent
    public static void clientCommonSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            Sheets.addWoodType(BlockRegistry.MAPLE_WOOD_TYPE);
            RENDERERS.put(Level.OVERWORLD, new DemeterHud());
            ItemBlockRenderTypes.setRenderLayer(FluidRegistry.WINE.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(FluidRegistry.WINE_FLOWING.get(), RenderType.translucent());
        });
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(EntityTypeRegistry.MAPLE_BOAT.get(), context -> new BoatRenderer(context, MAPLE_BOAT));
        event.registerEntityRenderer(EntityTypeRegistry.MAPLE_CHEST_BOAT.get(), context -> new BoatRenderer(context, MAPLE_CHEST_BOAT));
    }

    @SubscribeEvent
    public static void registerModels(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(TUSKS, TuskModel::createModel);
        event.registerLayerDefinition(MAPLE_BOAT, BoatModel::createBoatModel);
        event.registerLayerDefinition(MAPLE_CHEST_BOAT, BoatModel::createChestBoatModel);
    }

    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.AddLayers event) {
        PigRenderer pigRenderer = event.getRenderer(EntityType.PIG);
        pigRenderer.addLayer(new TuskLayer(pigRenderer, event.getEntityModels()));
    }

    @SubscribeEvent
    public static void registerStateModifiers(RegisterRenderStateModifiersEvent event) {
        event.registerEntityModifier(PigRenderer.class, (pig, pigRenderState) ->
                pigRenderState.setRenderData(SEX_KEY, AnimalUtil.getAnimalData(pig).getSex()));
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

    @SubscribeEvent
    public static void registerClientExtensions(RegisterClientExtensionsEvent event) {
        event.registerFluidType(new IClientFluidTypeExtensions() {
            @Override
            public ResourceLocation getStillTexture() {
                return WINE_TEXTURE;
            }

            @Override
            public ResourceLocation getFlowingTexture() {
                return WINE_TEXTURE;
            }

            @Override
            public ResourceLocation getOverlayTexture() {
                return ResourceLocation.withDefaultNamespace("block/water_overlay");
            }
        }, FluidTypeRegistry.WINE);
    }

    @SubscribeEvent
    public static void registerClientTooltips(RegisterClientTooltipComponentFactoriesEvent event) {
        event.register(FoodPouchTooltip.class, foodPouchTooltip -> new ClientFoodPouchTooltip(foodPouchTooltip.contents()));
    }

    private static void renderHUD(Minecraft mc, GuiGraphics graphics) {
        if (mc.getDebugOverlay().showDebugScreen()) return;
        HUDRenderData hud = RENDERERS.get(mc.level.dimension());
        if (hud != null) {
            PoseStack matrix = graphics.pose();
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
                        (PlayerUtil.hasInHand(mc.player, DemeterItemTagsProvider.CLOCKS) || CuriosCompat.isWearing(mc.player, CuriosCompat.Items.POCKET_WATCH.get()))))
                    graphics.drawString(mc.font, hud.getFooter(mc), x + hud.getClockX(), y + hud.getClockY(), 0xFFFFFFFF);
            }
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

package dev.teamcitrus.betterfarms;

import dev.teamcitrus.betterfarms.api.quality.Quality;
import dev.teamcitrus.betterfarms.api.util.QualityUtil;
import dev.teamcitrus.betterfarms.client.renderer.entity.HarvestGoddessRenderer;
import dev.teamcitrus.betterfarms.registry.BlockRegistry;
import dev.teamcitrus.betterfarms.registry.EntityTypeRegistry;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
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

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        ItemProperties.registerGeneric(new ResourceLocation("quality"), ((pStack, pLevel, pEntity, pSeed) -> {
            Quality quality = QualityUtil.getQuality(pStack);
            if (quality == null) return 0.0f;
            return switch (quality) {
                case COPPER -> 1.0f;
                case IRON -> 2.0F;
                case GOLD -> 3.0F;
                case DIAMOND -> 4.0F;
                case NETHERITE -> 5.0F;
            };
        }));
    }
}

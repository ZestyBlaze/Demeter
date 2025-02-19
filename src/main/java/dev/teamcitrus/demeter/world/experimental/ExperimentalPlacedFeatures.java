package dev.teamcitrus.demeter.world.experimental;

import dev.teamcitrus.demeter.Demeter;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.RarityFilter;

public class ExperimentalPlacedFeatures {
    public static final ResourceKey<PlacedFeature> BAMBOO_SHOOTS = register("bamboo_shoots");

    public static void bootstrap(BootstrapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> holderGetter = context.lookup(Registries.CONFIGURED_FEATURE);
        Holder<ConfiguredFeature<?, ?>> bamboo_shoots = holderGetter.getOrThrow(ExperimentalConfiguredFeatures.BAMBOO_SHOOTS);

        PlacementUtils.register(
                context, BAMBOO_SHOOTS, bamboo_shoots,
                RarityFilter.onAverageOnceEvery(32), InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP, BiomeFilter.biome()
        );
    }

    private static ResourceKey<PlacedFeature> register(String id) {
        return ResourceKey.create(Registries.PLACED_FEATURE, Demeter.id(id));
    }
}

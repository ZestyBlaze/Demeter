package dev.teamcitrus.demeter.world.tree;

import dev.teamcitrus.demeter.Demeter;
import dev.teamcitrus.demeter.registry.BlockRegistry;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.SurfaceWaterDepthFilter;

import java.util.Optional;

public class DemeterTrees {
    public static final ResourceKey<ConfiguredFeature<?, ?>> MAPLE_TREE_CF = ResourceKey.create(
            Registries.CONFIGURED_FEATURE, Demeter.id("maple"));
    public static final ResourceKey<PlacedFeature> MAPLE_TREE_PF = ResourceKey.create(
            Registries.PLACED_FEATURE, Demeter.id("maple"));
    public static final TreeGrower MAPLE = new TreeGrower("maple", Optional.empty(),
            Optional.of(DemeterTrees.MAPLE_TREE_CF), Optional.empty());

    public static void bootstrapCF(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        FeatureUtils.register(context, MAPLE_TREE_CF, Feature.TREE, createMaple().build());
    }

    public static void bootstrapPF(BootstrapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        PlacementUtils.register(context, MAPLE_TREE_PF, configuredFeatures.getOrThrow(MAPLE_TREE_CF),
                PlacementUtils.countExtra(1, 0.1f, 1),
                InSquarePlacement.spread(),
                SurfaceWaterDepthFilter.forMaxDepth(0),
                PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
                BiomeFilter.biome(),
                PlacementUtils.filteredByBlockSurvival(BlockRegistry.MAPLE_SAPLING.get())
                );
    }

    private static TreeConfiguration.TreeConfigurationBuilder createStraightBlobTree(Block logBlock, Block leavesBlock,
                                                                                     int baseHeight, int heightRandA,
                                                                                     int heightRandB, int radius) {
        return new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(logBlock),
                new StraightTrunkPlacer(baseHeight, heightRandA, heightRandB),
                BlockStateProvider.simple(leavesBlock),
                new BlobFoliagePlacer(ConstantInt.of(radius),
                        ConstantInt.of(0), 3),
                new TwoLayersFeatureSize(1, 0, 1)
        );
    }

    private static TreeConfiguration.TreeConfigurationBuilder createMaple() {
        return createStraightBlobTree(BlockRegistry.MAPLE_LOG.get(),
                BlockRegistry.MAPLE_LEAVES.get(), 5, 1, 0, 2)
                .ignoreVines();
    }
}

package dev.teamcitrus.demeter.world.tree;

import dev.teamcitrus.demeter.Demeter;
import dev.teamcitrus.demeter.registry.BlockRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
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

import java.util.Optional;

public class MapleTreeGrower {
    public static final ResourceKey<ConfiguredFeature<?, ?>> MAPLE_TREE = ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(Demeter.MODID, "maple"));
    public static final TreeGrower MAPLE = new TreeGrower("maple", Optional.empty(), Optional.of(MapleTreeGrower.MAPLE_TREE), Optional.empty());

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        FeatureUtils.register(context, MAPLE_TREE, Feature.TREE, createMaple().build());
    }

    private static TreeConfiguration.TreeConfigurationBuilder createStraightBlobTree(Block logBlock, Block leavesBlock, int baseHeight, int heightRandA, int heightRandB, int radius) {
        return new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(logBlock), new StraightTrunkPlacer(baseHeight, heightRandA, heightRandB), BlockStateProvider.simple(leavesBlock), new BlobFoliagePlacer(ConstantInt.of(radius), ConstantInt.of(0), 3), new TwoLayersFeatureSize(1, 0, 1));
    }

    private static TreeConfiguration.TreeConfigurationBuilder createMaple() {
        return createStraightBlobTree(BlockRegistry.MAPLE_LOG.get(), BlockRegistry.MAPLE_LEAVES.get(), 4, 2, 0, 2).ignoreVines();
    }
}

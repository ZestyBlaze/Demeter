package dev.teamcitrus.demeter.world;

import dev.teamcitrus.demeter.Demeter;
import dev.teamcitrus.demeter.data.providers.DemeterBiomeTagProvider;
import dev.teamcitrus.demeter.world.tree.DemeterTrees;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class DemeterBiomeModifiers {
    public static final ResourceKey<BiomeModifier> MAPLE_TREE = ResourceKey.create(
            NeoForgeRegistries.Keys.BIOME_MODIFIERS,
            Demeter.id("add_maple_trees")
    );

    public static void bootstrap(BootstrapContext<BiomeModifier> context) {
        HolderGetter<Biome> biomes = context.lookup(Registries.BIOME);
        HolderGetter<PlacedFeature> placedFeatures = context.lookup(Registries.PLACED_FEATURE);

        context.register(MAPLE_TREE, new BiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(DemeterBiomeTagProvider.HAS_MAPLE_TREES),
                HolderSet.direct(placedFeatures.getOrThrow(DemeterTrees.MAPLE_TREE_PF)),
                GenerationStep.Decoration.VEGETAL_DECORATION
        ));
    }
}

package dev.teamcitrus.demeter.data.providers.experimental;

import dev.teamcitrus.demeter.Demeter;
import dev.teamcitrus.demeter.data.providers.DemeterBiomesTagProvider;
import dev.teamcitrus.demeter.world.experimental.ExperimentalPlacedFeatures;
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

public class ExperimentalBiomeModifiers {
    public static final ResourceKey<BiomeModifier> BAMBOO_SHOOTS = key("bamboo_shoots");

    public static void bootstrap(BootstrapContext<BiomeModifier> context) {
        HolderGetter<Biome> biomes = context.lookup(Registries.BIOME);
        HolderGetter<PlacedFeature> placedFeatures = context.lookup(Registries.PLACED_FEATURE);

        context.register(BAMBOO_SHOOTS, new BiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(DemeterEBiomeProvider.HAS_BAMBOO_SHOOTS),
                HolderSet.direct(placedFeatures.getOrThrow(ExperimentalPlacedFeatures.BAMBOO_SHOOTS)),
                GenerationStep.Decoration.VEGETAL_DECORATION
        ));
    }

    private static ResourceKey<BiomeModifier> key(String id) {
        return ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, Demeter.id(id));
    }
}

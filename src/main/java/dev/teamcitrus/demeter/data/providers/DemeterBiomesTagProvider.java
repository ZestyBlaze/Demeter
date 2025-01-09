package dev.teamcitrus.demeter.data.providers;

import dev.teamcitrus.demeter.Demeter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;

import java.util.concurrent.CompletableFuture;

public class DemeterBiomesTagProvider extends TagsProvider<Biome> {
    public static final TagKey<Biome> HAS_MAPLE_TREES = modTag("has_maple_trees");

    public DemeterBiomesTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, Registries.BIOME, lookupProvider, Demeter.MODID);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(HAS_MAPLE_TREES).add(Biomes.FOREST, Biomes.FLOWER_FOREST, Biomes.BIRCH_FOREST, Biomes.OLD_GROWTH_BIRCH_FOREST);
    }

    private static TagKey<Biome> modTag(String id) {
        return TagKey.create(Registries.BIOME, Demeter.id(id));
    }
}

package dev.teamcitrus.demeter.data.providers.experimental;

import dev.teamcitrus.demeter.Demeter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;

import java.util.concurrent.CompletableFuture;

public class DemeterEBiomeProvider extends TagsProvider<Biome> {
    public static final TagKey<Biome> HAS_BAMBOO_SHOOTS = modTag("has_bamboo_shoots");

    public DemeterEBiomeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, Registries.BIOME, lookupProvider, Demeter.MODID);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(HAS_BAMBOO_SHOOTS).add(Biomes.JUNGLE, Biomes.BAMBOO_JUNGLE, Biomes.SPARSE_JUNGLE);
    }

    private static TagKey<Biome> modTag(String id) {
        return TagKey.create(Registries.BIOME, Demeter.id(id));
    }
}

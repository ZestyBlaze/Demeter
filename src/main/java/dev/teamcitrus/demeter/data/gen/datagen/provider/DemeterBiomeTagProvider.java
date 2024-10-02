package dev.teamcitrus.demeter.data.gen.datagen.provider;

import dev.teamcitrus.demeter.Demeter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class DemeterBiomeTagProvider extends TagsProvider<Biome> {
    public static final TagKey<Biome> HAS_MAPLE_TREES = TagKey.create(Registries.BIOME, Demeter.id("has_maple_trees"));

    public DemeterBiomeTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, Registries.BIOME, lookupProvider, Demeter.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(HAS_MAPLE_TREES).add(Biomes.FOREST, Biomes.FLOWER_FOREST, Biomes.BIRCH_FOREST);
    }
}

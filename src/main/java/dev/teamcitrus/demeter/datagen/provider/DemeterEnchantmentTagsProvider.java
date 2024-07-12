package dev.teamcitrus.demeter.datagen.provider;

import dev.teamcitrus.demeter.Demeter;
import dev.teamcitrus.demeter.enchant.DemeterEnchantments;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EnchantmentTagsProvider;
import net.minecraft.tags.EnchantmentTags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class DemeterEnchantmentTagsProvider extends EnchantmentTagsProvider {
    public DemeterEnchantmentTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, Demeter.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(EnchantmentTags.NON_TREASURE).add(DemeterEnchantments.BARBER);
    }
}

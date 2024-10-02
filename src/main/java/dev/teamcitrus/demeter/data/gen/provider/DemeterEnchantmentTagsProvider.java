package dev.teamcitrus.demeter.data.gen.provider;

import dev.teamcitrus.demeter.Demeter;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EnchantmentTagsProvider;
import net.minecraft.tags.EnchantmentTags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

import static dev.teamcitrus.demeter.enchantment.DemeterEnchantments.*;

public class DemeterEnchantmentTagsProvider extends EnchantmentTagsProvider {
    public DemeterEnchantmentTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, Demeter.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(EnchantmentTags.ON_TRADED_EQUIPMENT).add(BARBER, COMFORT, SPITE);
        tag(EnchantmentTags.ON_RANDOM_LOOT).add(BARBER, COMFORT, SPITE);
        tag(EnchantmentTags.CURSE).add(SPITE);
        tag(EnchantmentTags.TREASURE).add(BARBER, COMFORT);
    }
}

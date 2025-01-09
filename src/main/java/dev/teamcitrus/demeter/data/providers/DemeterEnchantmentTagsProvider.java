package dev.teamcitrus.demeter.data.providers;

import dev.teamcitrus.demeter.Demeter;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EnchantmentTagsProvider;
import net.minecraft.tags.EnchantmentTags;

import java.util.concurrent.CompletableFuture;

import static dev.teamcitrus.demeter.enchantment.DemeterEnchantments.*;

public class DemeterEnchantmentTagsProvider extends EnchantmentTagsProvider {
    public DemeterEnchantmentTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, Demeter.MODID);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(EnchantmentTags.ON_TRADED_EQUIPMENT).add(BARBER, COMFORT, SPITE);
        tag(EnchantmentTags.ON_RANDOM_LOOT).add(BARBER, COMFORT, SPITE);
        tag(EnchantmentTags.CURSE).add(SPITE);
        tag(EnchantmentTags.TREASURE).add(BARBER, COMFORT);
    }
}

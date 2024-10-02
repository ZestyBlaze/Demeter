package dev.teamcitrus.demeter.data.gen.provider;

import dev.teamcitrus.demeter.Demeter;
import dev.teamcitrus.demeter.enchantment.DemeterEnchantments;
import dev.teamcitrus.demeter.world.DemeterBiomeModifiers;
import dev.teamcitrus.demeter.world.tree.DemeterTrees;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class DemeterDatapackProvider extends DatapackBuiltinEntriesProvider {
    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.CONFIGURED_FEATURE, DemeterTrees::bootstrapCF)
            .add(Registries.PLACED_FEATURE, DemeterTrees::bootstrapPF)
            .add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, DemeterBiomeModifiers::bootstrap)
            .add(Registries.ENCHANTMENT, DemeterEnchantments::bootstrap);


    public DemeterDatapackProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(Demeter.MODID));
    }
}

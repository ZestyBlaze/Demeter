package dev.teamcitrus.demeter.data;

import dev.teamcitrus.demeter.Demeter;
import dev.teamcitrus.demeter.data.providers.*;
import dev.teamcitrus.demeter.data.providers.experimental.DemeterEBiomeProvider;
import dev.teamcitrus.demeter.data.providers.experimental.DemeterExperimentalDPProvider;
import dev.teamcitrus.demeter.data.providers.experimental.DemeterExperimentalLootProvider;
import dev.teamcitrus.demeter.data.providers.lang.EnUsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.metadata.PackMetadataGenerator;
import net.minecraft.network.chat.Component;
import net.minecraft.world.flag.FeatureFlagSet;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = Demeter.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class DemeterDatagen {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent.Client event) {
        DataGenerator gen = event.getGenerator();
        PackOutput output = gen.getPackOutput();
        CompletableFuture<HolderLookup.Provider> provider = event.getLookupProvider();

        event.addProvider(new DemeterModelProvider(output));

        event.addProvider(new DemeterDataMapGenerator(output, provider));
        event.addProvider(new DemeterAdvancementProvider(output, provider));
        event.addProvider(DemeterLootProvider.create(output, provider));
        CompletableFuture<HolderLookup.Provider> datapack = event.addProvider(new DemeterDatapackProvider(output, provider)).getRegistryProvider();

        DemeterBlockTagsProvider blockTags = new DemeterBlockTagsProvider(output, provider);
        event.addProvider(blockTags);
        event.addProvider(new DemeterItemTagsProvider(output, provider, blockTags.contentsGetter()));
        event.addProvider(new DemeterBiomesTagProvider(output, provider));
        event.addProvider(new DemeterEnchantmentTagsProvider(output, datapack));
        event.addProvider(new DemeterEntityTagProvider(output, provider));
        event.addProvider(new DemeterRecipeProvider.Runner(output, provider));
        event.addProvider(new DemeterLootModifierProvider(output, provider));

        event.addProvider(new EnUsProvider(output));

        DataGenerator.PackGenerator featurePack = gen.getBuiltinDatapack(true, Demeter.MODID, "experimental");
        featurePack.addProvider(out -> PackMetadataGenerator.forFeaturePack(
                out, Component.literal("Enable experimental features for Demeter"),
                FeatureFlagSet.of(Demeter.EXPERIMENTAL)
        ));
        featurePack.addProvider(out -> DemeterExperimentalLootProvider.create(out, provider));
        featurePack.addProvider(out -> new DemeterExperimentalDPProvider(out, provider));
        featurePack.addProvider(out -> new DemeterEBiomeProvider(out, provider));
    }
}

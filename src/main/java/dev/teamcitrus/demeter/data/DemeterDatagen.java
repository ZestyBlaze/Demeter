package dev.teamcitrus.demeter.data;

import dev.teamcitrus.demeter.Demeter;
import dev.teamcitrus.demeter.data.providers.*;
import dev.teamcitrus.demeter.data.providers.lang.EnUsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = Demeter.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class DemeterDatagen {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent.Client event) {
        DataGenerator gen = event.getGenerator();
        PackOutput output = gen.getPackOutput();
        CompletableFuture<HolderLookup.Provider> provider = event.getLookupProvider();
        ExistingFileHelper helper = event.getExistingFileHelper();

        event.addProvider(new DemeterModelProvider(output));

        event.addProvider(new DemeterDataMapGenerator(output, provider));
        event.addProvider(new DemeterAdvancementProvider(output, provider, helper));
        event.addProvider(DemeterLootProvider.create(output, provider));
        CompletableFuture<HolderLookup.Provider> datapack = event.addProvider(new DemeterDatapackProvider(output, provider)).getRegistryProvider();

        DemeterBlockTagsProvider blockTags = new DemeterBlockTagsProvider(output, provider, helper);
        event.addProvider(blockTags);
        event.addProvider(new DemeterItemTagsProvider(output, provider, blockTags.contentsGetter(), helper));
        event.addProvider(new DemeterBiomesTagProvider(output, provider, helper));
        event.addProvider(new DemeterEnchantmentTagsProvider(output, datapack, helper));
        event.addProvider(new DemeterEntityTagProvider(output, provider, helper));
        //event.addProvider(new DemeterRecipeProvider(output, provider));
        event.addProvider(new DemeterLootModifierProvider(output, provider));

        event.addProvider(new EnUsProvider(output));
    }
}

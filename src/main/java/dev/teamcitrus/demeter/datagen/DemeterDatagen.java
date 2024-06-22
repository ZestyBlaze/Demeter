package dev.teamcitrus.demeter.datagen;

import dev.teamcitrus.citruslib.util.DatagenUtils;
import dev.teamcitrus.demeter.Demeter;
import dev.teamcitrus.demeter.datagen.provider.*;
import dev.teamcitrus.demeter.datagen.provider.lang.EnUsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = Demeter.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DemeterDatagen {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        PackOutput output = gen.getPackOutput();
        CompletableFuture<HolderLookup.Provider> provider = event.getLookupProvider();
        ExistingFileHelper helper = event.getExistingFileHelper();

        gen.addProvider(event.includeClient(), new DemeterBlockStateProvider(output, helper));
        gen.addProvider(event.includeClient(), new DemeterItemModelProvider(output, helper));

        gen.addProvider(event.includeServer(), new DemeterDataMapGenerator(output, provider));
        gen.addProvider(event.includeServer(), new DemeterAdvancementProvider(output, provider, helper));
        gen.addProvider(event.includeServer(), DemeterLootProvider.create(output));

        DemeterBlockTagsProvider blockTags = new DemeterBlockTagsProvider(output, provider, helper);
        gen.addProvider(event.includeServer(), blockTags);
        gen.addProvider(event.includeServer(), new DemeterItemTagsProvider(output, provider, blockTags.contentsGetter(), helper));
        gen.addProvider(event.includeServer(), new DemeterRecipeProvider(output));
        gen.addProvider(event.includeServer(), new DemeterLootModifierProvider(output));

        gen.addProvider(event.includeClient(), new EnUsProvider(output));

        gen.addProvider(true, DatagenUtils.makeMetadataFile(output, Demeter.MODID));
    }
}
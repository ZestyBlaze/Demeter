package dev.teamcitrus.demeter.data.gen;

import dev.teamcitrus.citruslib.util.DatagenUtil;
import dev.teamcitrus.demeter.Demeter;
import dev.teamcitrus.demeter.data.gen.provider.*;
import dev.teamcitrus.demeter.data.gen.provider.lang.EnUsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = Demeter.MODID, bus = EventBusSubscriber.Bus.MOD)
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
        gen.addProvider(event.includeServer(), DemeterLootProvider.create(output, provider));
        var prov = gen.addProvider(event.includeServer(), new DemeterDatapackProvider(output, provider)).getRegistryProvider();

        DemeterBlockTagsProvider blockTags = new DemeterBlockTagsProvider(output, provider, helper);
        gen.addProvider(event.includeServer(), blockTags);
        gen.addProvider(event.includeServer(), new DemeterItemTagsProvider(output, provider, blockTags.contentsGetter(), helper));
        gen.addProvider(event.includeServer(), new DemeterBiomeTagProvider(output, provider, helper));
        gen.addProvider(event.includeServer(), new DemeterEnchantmentTagsProvider(output, prov, helper));
        gen.addProvider(event.includeServer(), new DemeterRecipeProvider(output, provider));
        gen.addProvider(event.includeServer(), new DemeterLootModifierProvider(output, provider));

        gen.addProvider(event.includeClient(), new EnUsProvider(output));

        gen.addProvider(true, DatagenUtil.makeMetadataFile(output, Demeter.MODID));
    }
}

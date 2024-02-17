package dev.teamcitrus.betterfarms.datagen;

import dev.teamcitrus.betterfarms.BetterFarms;
import dev.teamcitrus.betterfarms.datagen.provider.*;
import dev.teamcitrus.betterfarms.datagen.provider.lang.EnUsProvider;
import net.minecraft.DetectedVersion;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.metadata.PackMetadataGenerator;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraft.util.InclusiveRange;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = BetterFarms.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class BFDatagen {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        PackOutput output = gen.getPackOutput();
        CompletableFuture<HolderLookup.Provider> provider = event.getLookupProvider();
        ExistingFileHelper helper = event.getExistingFileHelper();

        gen.addProvider(event.includeClient(), new BFBlockStateProvider(output, helper));
        gen.addProvider(event.includeClient(), new BFItemModelProvider(output, helper));

        gen.addProvider(event.includeServer(), new BFDataMapGenerator(output, provider));
        gen.addProvider(event.includeServer(), new BFAdvancementProvider(output, provider, helper));
        gen.addProvider(event.includeServer(), new BFRecipeProvider(output));
        gen.addProvider(event.includeClient(), new EnUsProvider(output));

        gen.addProvider(true, new PackMetadataGenerator(output).add(PackMetadataSection.TYPE, new PackMetadataSection(
                Component.literal("Resources for BetterFarms"),
                DetectedVersion.BUILT_IN.getPackVersion(PackType.SERVER_DATA),
                Optional.of(new InclusiveRange<>(0, Integer.MAX_VALUE))
        )));
    }
}

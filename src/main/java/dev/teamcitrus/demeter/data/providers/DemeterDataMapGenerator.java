package dev.teamcitrus.demeter.data.providers;

import dev.teamcitrus.demeter.datamaps.AnimalData;
import dev.teamcitrus.demeter.datamaps.AnimalData.Activity;
import dev.teamcitrus.demeter.datamaps.AnimalData.MilkingCodec;
import dev.teamcitrus.demeter.datamaps.CropData;
import dev.teamcitrus.demeter.registry.DataMapRegistry;
import dev.teamcitrus.demeter.registry.ItemRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.registries.datamaps.builtin.Compostable;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@SuppressWarnings("deprecation")
public class DemeterDataMapGenerator extends DataMapProvider {
    public DemeterDataMapGenerator(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider);
    }

    @Override
    protected void gather(HolderLookup.Provider provider) {
        Builder<Compostable, Item> compostables = builder(NeoForgeDataMaps.COMPOSTABLES);
        compostables.add(ItemRegistry.MAPLE_LEAVES.get().builtInRegistryHolder(), new Compostable(0.3f), false);
        compostables.add(ItemRegistry.MAPLE_SAPLING.get().builtInRegistryHolder(), new Compostable(0.3f), false);

        Builder<CropData, Block> cropData = builder(DataMapRegistry.CROP_DATA);
        cropData.add(Blocks.BEETROOTS.builtInRegistryHolder(), new CropData(3), false);
        cropData.add(Blocks.CARROTS.builtInRegistryHolder(), new CropData(4), false);
        cropData.add(Blocks.PITCHER_CROP.builtInRegistryHolder(), new CropData(2), false);
        cropData.add(Blocks.POTATOES.builtInRegistryHolder(), new CropData(5), false);
        cropData.add(Blocks.TORCHFLOWER_CROP.builtInRegistryHolder(), new CropData(2), false);
        cropData.add(Blocks.WHEAT.builtInRegistryHolder(), new CropData(3), false);

        Map<Item, Item> cowMilkIO = new HashMap<>();
        cowMilkIO.put(Items.BUCKET, Items.MILK_BUCKET);
        cowMilkIO.put(Items.GLASS_BOTTLE, ItemRegistry.MILK_BOTTLE.get());

        Builder<AnimalData, EntityType<?>> animalData = builder(DataMapRegistry.ANIMAL_DATA);
        animalData.add(EntityType.CAMEL.builtInRegistryHolder(), new AnimalData(
                Activity.DIURNAL, 10, 14, 11, 13
        ), false);
        animalData.add(EntityType.CAT.builtInRegistryHolder(), new AnimalData(
                Activity.NOCTURNAL, 10, 20, 5, 7
        ), false);
        animalData.add(EntityType.COW.builtInRegistryHolder(), new AnimalData(
                Activity.DIURNAL, 12, 20, 9, 14, new MilkingCodec(cowMilkIO)
        ), false);
        animalData.add(EntityType.DONKEY.builtInRegistryHolder(), new AnimalData(
                Activity.DIURNAL, 25, 30, 24, 12
        ), false);
        animalData.add(EntityType.FOX.builtInRegistryHolder(), new AnimalData(
                Activity.NOCTURNAL, 3, 8, 6, 7
        ), false);
        animalData.add(EntityType.GOAT.builtInRegistryHolder(), new AnimalData(
                Activity.DIURNAL, 15, 18, 7, 9
        ), false);
        animalData.add(EntityType.HORSE.builtInRegistryHolder(), new AnimalData(
                Activity.DIURNAL, 25, 30, 24, 12
        ), false);
        animalData.add(EntityType.LLAMA.builtInRegistryHolder(), new AnimalData(
                Activity.DIURNAL, 15, 25, 11, 10
        ), false);
        animalData.add(EntityType.MOOSHROOM.builtInRegistryHolder(), new AnimalData(
                Activity.DIURNAL, 13, 21, 10, 21
        ), false);
        animalData.add(EntityType.MULE.builtInRegistryHolder(), new AnimalData(
                Activity.DIURNAL, 25, 30, 24, 12
        ), false);
        animalData.add(EntityType.OCELOT.builtInRegistryHolder(), new AnimalData(
                Activity.NOCTURNAL, 6, 12, 6, 7
        ), false);
        animalData.add(EntityType.PANDA.builtInRegistryHolder(), new AnimalData(
                Activity.DIURNAL, 15, 25, 12, 10
        ), false);
        animalData.add(EntityType.PARROT.builtInRegistryHolder(), new AnimalData(
                Activity.DIURNAL, 25, 50, 4, 36
        ), false);
        animalData.add(EntityType.PIG.builtInRegistryHolder(), new AnimalData(
                Activity.DIURNAL, 6, 10,  4, 4, 4, 7,
                List.of(ItemRegistry.TRUFFLE.toStack())
        ), false);
        animalData.add(EntityType.POLAR_BEAR.builtInRegistryHolder(), new AnimalData(
                Activity.DIURNAL, 12, 16, 14, 8
        ), false);
        animalData.add(EntityType.RABBIT.builtInRegistryHolder(), new AnimalData(
                Activity.DIURNAL, 8, 12, 4, 3
        ), false);
        animalData.add(EntityType.SHEEP.builtInRegistryHolder(), new AnimalData(
                Activity.DIURNAL, 8, 12, 5, 4
        ), false);
        animalData.add(EntityType.WOLF.builtInRegistryHolder(), new AnimalData(
                Activity.NOCTURNAL, 10, 13, 5, 7
        ), false);
    }
}

package dev.teamcitrus.demeter.data.providers;

import dev.teamcitrus.demeter.data.maps.DemeterDataMaps;
import dev.teamcitrus.demeter.datamaps.AnimalData;
import dev.teamcitrus.demeter.datamaps.AnimalData.Activity;
import dev.teamcitrus.demeter.datamaps.AnimalData.MilkingCodec;
import dev.teamcitrus.demeter.datamaps.CropData;
import dev.teamcitrus.demeter.registry.ItemRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.registries.datamaps.builtin.Compostable;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;

import java.util.concurrent.CompletableFuture;

@SuppressWarnings("deprecation")
public class DemeterDataMapGenerator extends DataMapProvider {
    public DemeterDataMapGenerator(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider);
    }

    @Override
    protected void gather() {
        Builder<Compostable, Item> compostables = builder(NeoForgeDataMaps.COMPOSTABLES);
        compostables.add(ItemRegistry.MAPLE_LEAVES.get().builtInRegistryHolder(), new Compostable(0.3f), false);
        compostables.add(ItemRegistry.MAPLE_SAPLING.get().builtInRegistryHolder(), new Compostable(0.3f), false);

        Builder<CropData, Block> cropData = builder(DemeterDataMaps.CROP_DATA);
        cropData.add(Blocks.BEETROOTS.builtInRegistryHolder(), new CropData(3), false);
        cropData.add(Blocks.CARROTS.builtInRegistryHolder(), new CropData(4), false);
        cropData.add(Blocks.PITCHER_CROP.builtInRegistryHolder(), new CropData(2), false);
        cropData.add(Blocks.POTATOES.builtInRegistryHolder(), new CropData(5), false);
        cropData.add(Blocks.TORCHFLOWER_CROP.builtInRegistryHolder(), new CropData(2), false);
        cropData.add(Blocks.WHEAT.builtInRegistryHolder(), new CropData(3), false);

        Builder<AnimalData, EntityType<?>> animalData = builder(DemeterDataMaps.ANIMAL_DATA);
        animalData.add(EntityType.CAMEL.builtInRegistryHolder(), new AnimalData(
                Activity.DIURNAL, 11, 1
        ), false);
        animalData.add(EntityType.CAT.builtInRegistryHolder(), new AnimalData(
                Activity.NOCTURNAL, 5, 1
        ), false);
        animalData.add(EntityType.COW.builtInRegistryHolder(), new AnimalData(
                Activity.DIURNAL, 2, 1, new MilkingCodec()
        ), false);
        animalData.add(EntityType.DONKEY.builtInRegistryHolder(), new AnimalData(
                Activity.DIURNAL, 24, 1
        ), false);
        animalData.add(EntityType.FOX.builtInRegistryHolder(), new AnimalData(
                Activity.NOCTURNAL, 6, 1
        ), false);
        animalData.add(EntityType.GOAT.builtInRegistryHolder(), new AnimalData(
                Activity.DIURNAL, 7, 1
        ), false);
        animalData.add(EntityType.HORSE.builtInRegistryHolder(), new AnimalData(
                Activity.DIURNAL, 24, 1
        ), false);
        animalData.add(EntityType.LLAMA.builtInRegistryHolder(), new AnimalData(
                Activity.DIURNAL, 11, 1
        ), false);
        animalData.add(EntityType.MOOSHROOM.builtInRegistryHolder(), new AnimalData(
                Activity.DIURNAL, 10, 1
        ), false);
        animalData.add(EntityType.OCELOT.builtInRegistryHolder(), new AnimalData(
                Activity.NOCTURNAL, 6, 1
        ), false);
        animalData.add(EntityType.PANDA.builtInRegistryHolder(), new AnimalData(
                Activity.DIURNAL, 12, 1
        ), false);
        animalData.add(EntityType.PARROT.builtInRegistryHolder(), new AnimalData(
                Activity.DIURNAL, 4, 1
        ), false);
        animalData.add(EntityType.PIG.builtInRegistryHolder(), new AnimalData(
                Activity.DIURNAL, 4, 1, 4, 7
        ), false);
        animalData.add(EntityType.POLAR_BEAR.builtInRegistryHolder(), new AnimalData(
                Activity.DIURNAL, 14, 1
        ), false);
        animalData.add(EntityType.RABBIT.builtInRegistryHolder(), new AnimalData(
                Activity.DIURNAL, 4, 1
        ), false);
        animalData.add(EntityType.SHEEP.builtInRegistryHolder(), new AnimalData(
                Activity.DIURNAL, 5, 1
        ), false);
        animalData.add(EntityType.WOLF.builtInRegistryHolder(), new AnimalData(
                Activity.NOCTURNAL, 5, 1
        ), false);
    }
}

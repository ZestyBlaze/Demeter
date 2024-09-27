package dev.teamcitrus.demeter.datagen.provider;

import dev.teamcitrus.demeter.datamaps.DemeterDataMaps;
import dev.teamcitrus.demeter.datamaps.crops.CropData;
import dev.teamcitrus.demeter.registry.ItemRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
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
        cropData.add(Blocks.WHEAT.builtInRegistryHolder(), new CropData(3), false);
        cropData.add(Blocks.CARROTS.builtInRegistryHolder(), new CropData(4), false);
        cropData.add(Blocks.POTATOES.builtInRegistryHolder(), new CropData(5), false);
        cropData.add(Blocks.BEETROOTS.builtInRegistryHolder(), new CropData(3), false);
        cropData.add(Blocks.TORCHFLOWER_CROP.builtInRegistryHolder(), new CropData(2), false);
        cropData.add(Blocks.PITCHER_CROP.builtInRegistryHolder(), new CropData(2), false);

    }
}

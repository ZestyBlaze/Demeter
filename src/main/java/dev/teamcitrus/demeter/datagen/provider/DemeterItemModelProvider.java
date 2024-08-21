package dev.teamcitrus.demeter.datagen.provider;

import dev.teamcitrus.citruslib.datagen.CitrusItemModelProvider;
import dev.teamcitrus.demeter.Demeter;
import dev.teamcitrus.demeter.registry.BlockRegistry;
import dev.teamcitrus.demeter.registry.ItemRegistry;
import dev.teamcitrus.demeter.registry.WoodSetRegistry;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class DemeterItemModelProvider extends CitrusItemModelProvider {
    public DemeterItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Demeter.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        basicItem(ItemRegistry.ANIMAL_TAG.get());
        basicItem(ItemRegistry.BRUSH.get());
        basicItem(ItemRegistry.MIRACLE_POTION.get());
        toBlock(BlockRegistry.MAPLE_LOG.get());
        toBlock(BlockRegistry.MAPLE_WOOD.get());
        toBlock(BlockRegistry.STRIPPED_MAPLE_LOG.get());
        toBlock(BlockRegistry.STRIPPED_MAPLE_WOOD.get());
        toBlock(BlockRegistry.MAPLE_LEAVES.get());
        basicItem(ItemRegistry.MAPLE_SAPLING.get());
        basicItem(ItemRegistry.MAPLE_SIGN.get());
        basicItem(ItemRegistry.MAPLE_BOAT.get());
        basicItem(ItemRegistry.MAPLE_CHEST_BOAT.get());
        basicItem(ItemRegistry.BUTTER.get());
        generateSetModels(WoodSetRegistry.MAPLE);
    }
}

package dev.teamcitrus.betterfarms.datagen.provider;

import dev.teamcitrus.betterfarms.BetterFarms;
import dev.teamcitrus.betterfarms.registry.BlockRegistry;
import dev.teamcitrus.betterfarms.registry.WoodSetRegistry;
import dev.teamcitrus.citruslib.datagen.CitrusItemModelProvider;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class BFItemModelProvider extends CitrusItemModelProvider {
    public BFItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, BetterFarms.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        toBlock(BlockRegistry.MAPLE_LOG.get());
        toBlock(BlockRegistry.MAPLE_WOOD.get());
        toBlock(BlockRegistry.STRIPPED_MAPLE_LOG.get());
        toBlock(BlockRegistry.STRIPPED_MAPLE_WOOD.get());
        toBlock(BlockRegistry.MAPLE_LEAVES.get());
        generateSetModels(WoodSetRegistry.MAPLE);
    }
}

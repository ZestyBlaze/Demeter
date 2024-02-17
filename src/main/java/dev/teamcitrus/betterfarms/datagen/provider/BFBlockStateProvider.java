package dev.teamcitrus.betterfarms.datagen.provider;

import dev.teamcitrus.betterfarms.BetterFarms;
import dev.teamcitrus.betterfarms.registry.BlockRegistry;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class BFBlockStateProvider extends BlockStateProvider {
    public BFBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, BetterFarms.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        logBlock(BlockRegistry.MAPLE_LOG.get());
        simpleBlock(BlockRegistry.MAPLE_PLANKS.get());
        simpleBlock(BlockRegistry.MAPLE_LEAVES.get(), models().cubeAll("maple_leaves", BetterFarms.id("block/maple_leaves")).renderType("cutout"));
    }
}

package dev.teamcitrus.demeter.datagen.provider;

import dev.teamcitrus.demeter.Demeter;
import dev.teamcitrus.demeter.registry.BlockRegistry;
import dev.teamcitrus.demeter.registry.WoodSetRegistry;
import dev.teamcitrus.citruslib.datagen.CitrusBlockStateProvider;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class DemeterBlockStateProvider extends CitrusBlockStateProvider {
    public DemeterBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, Demeter.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        axisBlock(BlockRegistry.MAPLE_WOOD.get(), models().cubeAll("maple_wood", modLoc("block/maple_log")), models().cubeAll("maple_wood", modLoc("block/maple_log")));
        logBlock(BlockRegistry.STRIPPED_MAPLE_LOG.get());
        axisBlock(BlockRegistry.STRIPPED_MAPLE_WOOD.get(), models().cubeAll("stripped_maple_wood", modLoc("block/stripped_maple_log")), models().cubeAll("stripped_maple_wood", modLoc("block/stripped_maple_log")));
        simpleBlock(BlockRegistry.MAPLE_LEAVES.get(), models().cubeAll("maple_leaves", Demeter.id("block/maple_leaves")).renderType("cutout"));
        generateSetModels(WoodSetRegistry.MAPLE);
    }
}

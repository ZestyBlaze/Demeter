package dev.teamcitrus.betterfarms.datagen.provider;

import dev.teamcitrus.betterfarms.BetterFarms;
import dev.teamcitrus.betterfarms.registry.BlockRegistry;
import dev.teamcitrus.betterfarms.registry.WoodSetRegistry;
import dev.teamcitrus.citruslib.datagen.CitrusBlockModelProvider;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class BFBlockStateProvider extends CitrusBlockModelProvider {
    public BFBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, BetterFarms.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        axisBlock(BlockRegistry.MAPLE_WOOD.get(), models().cubeAll("maple_wood", modLoc("block/maple_log")), models().cubeAll("maple_wood", modLoc("block/maple_log")));
        logBlock(BlockRegistry.STRIPPED_MAPLE_LOG.get());
        axisBlock(BlockRegistry.STRIPPED_MAPLE_WOOD.get(), models().cubeAll("stripped_maple_wood", modLoc("block/stripped_maple_log")), models().cubeAll("stripped_maple_wood", modLoc("block/stripped_maple_log")));
        /*
        simpleBlock(BlockRegistry.MAPLE_PLANKS.get());
        stairsBlock(BlockRegistry.MAPLE_STAIRS.get(), blockTexture(BlockRegistry.MAPLE_PLANKS.get()));
        slabBlock(BlockRegistry.MAPLE_SLAB.get(), blockTexture(BlockRegistry.MAPLE_PLANKS.get()), blockTexture(BlockRegistry.MAPLE_PLANKS.get()));
        fenceBlock(BlockRegistry.MAPLE_FENCE.get(), blockTexture(BlockRegistry.MAPLE_PLANKS.get()));
        fenceGateBlock(BlockRegistry.MAPLE_FENCE_GATE.get(), blockTexture(BlockRegistry.MAPLE_PLANKS.get()));
        doorBlockWithRenderType(BlockRegistry.MAPLE_DOOR.get(), modLoc("block/maple_door_bottom"), modLoc("block/maple_door_top"), "cutout");
        trapdoorBlockWithRenderType(BlockRegistry.MAPLE_TRAPDOOR.get(), modLoc("block/maple_trapdoor"), true, "cutout");
        pressurePlateBlock(BlockRegistry.MAPLE_PRESSURE_PLATE.get(), blockTexture(BlockRegistry.MAPLE_PLANKS.get()));
        buttonBlock(BlockRegistry.MAPLE_BUTTON.get(), blockTexture(BlockRegistry.MAPLE_PLANKS.get()));
         */
        generateSetModels(WoodSetRegistry.MAPLE);
        simpleBlock(BlockRegistry.MAPLE_LEAVES.get(), models().cubeAll("maple_leaves", BetterFarms.id("block/maple_leaves")).renderType("cutout"));
    }
}

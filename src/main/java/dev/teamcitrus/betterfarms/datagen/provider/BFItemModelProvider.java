package dev.teamcitrus.betterfarms.datagen.provider;

import dev.teamcitrus.betterfarms.BetterFarms;
import dev.teamcitrus.betterfarms.registry.BlockRegistry;
import dev.teamcitrus.citruslib.datagen.CitrusItemModelProvider;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
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
        /*
        toBlock(BlockRegistry.MAPLE_PLANKS.get());
        toBlock(BlockRegistry.MAPLE_STAIRS.get());
        toBlock(BlockRegistry.MAPLE_SLAB.get());
        fenceInventory("maple_fence", modLoc("block/maple_planks"));
        toBlock(BlockRegistry.MAPLE_FENCE_GATE.get());
        toBlock(BlockRegistry.MAPLE_PRESSURE_PLATE.get());
        buttonInventory("maple_button", modLoc("block/maple_planks"));
        toBlock(BlockRegistry.MAPLE_LEAVES.get());
        basicItem(BlockRegistry.MAPLE_DOOR.get().asItem());
        toBlockModel(BlockRegistry.MAPLE_TRAPDOOR.get(), "maple_trapdoor_bottom");
         */

        toBlock(BlockRegistry.MAPLE_LEAVES.get());
    }
}

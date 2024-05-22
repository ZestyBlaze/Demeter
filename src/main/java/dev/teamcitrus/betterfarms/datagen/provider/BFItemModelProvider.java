package dev.teamcitrus.betterfarms.datagen.provider;

import dev.teamcitrus.betterfarms.BetterFarms;
import dev.teamcitrus.betterfarms.registry.BlockRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class BFItemModelProvider extends ItemModelProvider {
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
    }

    private void toBlock(Block b) {
        toBlockModel(b, BuiltInRegistries.BLOCK.getKey(b).getPath());
    }

    private void toBlockModel(Block b, String model) {
        toBlockModel(b, BetterFarms.id("block/" + model));
    }

    private void toBlockModel(Block b, ResourceLocation model) {
        withExistingParent(BuiltInRegistries.BLOCK.getKey(b).getPath(), model);
    }
}

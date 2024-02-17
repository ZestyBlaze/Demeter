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
        toBlock(BlockRegistry.MAPLE_PLANKS.get());
        toBlock(BlockRegistry.MAPLE_LEAVES.get());
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

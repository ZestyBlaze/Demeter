package dev.teamcitrus.demeter.datagen.provider;

import dev.teamcitrus.demeter.Demeter;
import dev.teamcitrus.demeter.registry.BlockRegistry;
import dev.teamcitrus.demeter.registry.WoodSetRegistry;
import dev.teamcitrus.citruslib.datagen.CitrusItemModelProvider;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class DemeterItemModelProvider extends CitrusItemModelProvider {
    public DemeterItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Demeter.MODID, existingFileHelper);
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

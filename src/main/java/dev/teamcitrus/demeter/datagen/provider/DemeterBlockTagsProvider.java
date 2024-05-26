package dev.teamcitrus.demeter.datagen.provider;

import dev.teamcitrus.demeter.Demeter;
import dev.teamcitrus.demeter.registry.BlockRegistry;
import dev.teamcitrus.demeter.registry.WoodSetRegistry;
import dev.teamcitrus.citruslib.datagen.CitrusBlockTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class DemeterBlockTagsProvider extends CitrusBlockTagsProvider {
    public static final TagKey<Block> MAPLE_LOGS = BlockTags.create(Demeter.id("maple_logs"));

    public DemeterBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, Demeter.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        tag(MAPLE_LOGS).add(BlockRegistry.MAPLE_LOG.get(), BlockRegistry.MAPLE_WOOD.get(),
                BlockRegistry.STRIPPED_MAPLE_LOG.get(), BlockRegistry.STRIPPED_MAPLE_WOOD.get());
        tag(BlockTags.LOGS).add(BlockRegistry.MAPLE_LOG.get(), BlockRegistry.MAPLE_WOOD.get(),
                BlockRegistry.STRIPPED_MAPLE_LOG.get(), BlockRegistry.STRIPPED_MAPLE_WOOD.get());
        tag(BlockTags.LOGS_THAT_BURN).add(BlockRegistry.MAPLE_LOG.get(), BlockRegistry.MAPLE_WOOD.get(),
                BlockRegistry.STRIPPED_MAPLE_LOG.get(), BlockRegistry.STRIPPED_MAPLE_WOOD.get());
        tag(BlockTags.OVERWORLD_NATURAL_LOGS).add(BlockRegistry.MAPLE_LOG.get());
        tag(BlockTags.COMPLETES_FIND_TREE_TUTORIAL).add(BlockRegistry.MAPLE_LOG.get());
        tag(BlockTags.LEAVES).add(BlockRegistry.MAPLE_LEAVES.get());
        tag(BlockTags.MINEABLE_WITH_AXE).add(BlockRegistry.MAPLE_LOG.get(), BlockRegistry.MAPLE_WOOD.get(),
                BlockRegistry.STRIPPED_MAPLE_LOG.get(), BlockRegistry.STRIPPED_MAPLE_WOOD.get());
        tag(BlockTags.MINEABLE_WITH_HOE).add(BlockRegistry.MAPLE_LEAVES.get());
        generateSetTags(WoodSetRegistry.MAPLE);
    }
}

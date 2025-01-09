package dev.teamcitrus.demeter.data.providers;

import dev.teamcitrus.demeter.Demeter;
import dev.teamcitrus.demeter.registry.BlockRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.BlockTagsProvider;

import java.util.concurrent.CompletableFuture;

@SuppressWarnings("unchecked")
public class DemeterBlockTagsProvider extends BlockTagsProvider {
    public static final TagKey<Block> MAPLE_LOGS = BlockTags.create(Demeter.id("maple_logs"));
    public static final TagKey<Block> CAN_HAVE_TRUFFLES = BlockTags.create(Demeter.id("can_have_truffles"));

    public DemeterBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, Demeter.MODID);
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
                BlockRegistry.STRIPPED_MAPLE_LOG.get(), BlockRegistry.STRIPPED_MAPLE_WOOD.get(), BlockRegistry.TROUGH.get());
        tag(BlockTags.MINEABLE_WITH_HOE).add(BlockRegistry.MAPLE_LEAVES.get());
        tag(CAN_HAVE_TRUFFLES).addTags(BlockTags.DIRT);
        //generateSetTags(WoodSetRegistry.MAPLE);
    }
}

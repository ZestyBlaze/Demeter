package dev.teamcitrus.betterfarms.datagen.provider;

import dev.teamcitrus.betterfarms.BetterFarms;
import dev.teamcitrus.betterfarms.registry.BlockRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class BFBlockTagProvider extends BlockTagsProvider {
    public static final TagKey<Block> MAPLE_LOGS = BlockTags.create(BetterFarms.id("maple_logs"));

    public BFBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, BetterFarms.MODID, existingFileHelper);
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
        tag(BlockTags.PLANKS).add(BlockRegistry.MAPLE_PLANKS.get());
        tag(BlockTags.STAIRS).add(BlockRegistry.MAPLE_STAIRS.get());
        tag(BlockTags.WOODEN_STAIRS).add(BlockRegistry.MAPLE_STAIRS.get());
        tag(BlockTags.SLABS).add(BlockRegistry.MAPLE_SLAB.get());
        tag(BlockTags.WOODEN_SLABS).add(BlockRegistry.MAPLE_SLAB.get());
        tag(BlockTags.FENCES).add(BlockRegistry.MAPLE_FENCE.get());
        tag(BlockTags.WOODEN_FENCES).add(BlockRegistry.MAPLE_FENCE.get());
        tag(BlockTags.FENCE_GATES).add(BlockRegistry.MAPLE_FENCE_GATE.get());
        tag(Tags.Blocks.FENCE_GATES_WOODEN).add(BlockRegistry.MAPLE_FENCE_GATE.get());
        tag(BlockTags.DOORS).add(BlockRegistry.MAPLE_DOOR.get());
        tag(BlockTags.WOODEN_DOORS).add(BlockRegistry.MAPLE_DOOR.get());
        tag(BlockTags.TRAPDOORS).add(BlockRegistry.MAPLE_TRAPDOOR.get());
        tag(BlockTags.WOODEN_TRAPDOORS).add(BlockRegistry.MAPLE_TRAPDOOR.get());
        tag(BlockTags.PRESSURE_PLATES).add(BlockRegistry.MAPLE_PRESSURE_PLATE.get());
        tag(BlockTags.WOODEN_PRESSURE_PLATES).add(BlockRegistry.MAPLE_PRESSURE_PLATE.get());
        tag(BlockTags.BUTTONS).add(BlockRegistry.MAPLE_BUTTON.get());
        tag(BlockTags.WOODEN_BUTTONS).add(BlockRegistry.MAPLE_BUTTON.get());
        tag(BlockTags.LEAVES).add(BlockRegistry.MAPLE_LEAVES.get());
        //tag(BlockTags.SAPLINGS).add(BlockRegistry.GREAT_HEMLOCK_SAPLING.get());
        tag(BlockTags.MINEABLE_WITH_AXE).add(BlockRegistry.MAPLE_LOG.get(), BlockRegistry.MAPLE_WOOD.get(),
                BlockRegistry.STRIPPED_MAPLE_LOG.get(), BlockRegistry.STRIPPED_MAPLE_WOOD.get(), BlockRegistry.MAPLE_PLANKS.get(),
                BlockRegistry.MAPLE_STAIRS.get(), BlockRegistry.MAPLE_SLAB.get(), BlockRegistry.MAPLE_FENCE.get(),
                BlockRegistry.MAPLE_FENCE_GATE.get(), BlockRegistry.MAPLE_DOOR.get(), BlockRegistry.MAPLE_TRAPDOOR.get(),
                BlockRegistry.MAPLE_PRESSURE_PLATE.get(), BlockRegistry.MAPLE_BUTTON.get()
        );
        tag(BlockTags.MINEABLE_WITH_HOE).add(BlockRegistry.MAPLE_LEAVES.get());
    }
}

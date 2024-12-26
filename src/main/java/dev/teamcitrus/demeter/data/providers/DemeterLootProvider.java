package dev.teamcitrus.demeter.data.providers;

import dev.teamcitrus.demeter.compat.accessories.AccessoriesCompat;
import dev.teamcitrus.demeter.registry.BlockRegistry;
import dev.teamcitrus.demeter.registry.ItemRegistry;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class DemeterLootProvider {
    public static LootTableProvider create(PackOutput output, CompletableFuture<HolderLookup.Provider> provider) {
        return new LootTableProvider(output, Set.of(), List.of(new LootTableProvider.SubProviderEntry(BFSubLootProvider::new, LootContextParamSets.BLOCK)), provider);
    }

    public static class BFSubLootProvider extends BlockLootSubProvider {
        public BFSubLootProvider(HolderLookup.Provider provider) {
            super(Set.of(), FeatureFlags.REGISTRY.allFlags(), provider);
        }

        @Override
        protected void generate() {
            dropSelf(BlockRegistry.MAPLE_LOG.get());
            dropSelf(BlockRegistry.MAPLE_WOOD.get());
            dropSelf(BlockRegistry.STRIPPED_MAPLE_LOG.get());
            dropSelf(BlockRegistry.STRIPPED_MAPLE_WOOD.get());
            dropSelf(BlockRegistry.MAPLE_PLANKS.get());
            dropSelf(BlockRegistry.MAPLE_STAIRS.get());
            dropSelf(BlockRegistry.MAPLE_SLAB.get());
            dropSelf(BlockRegistry.MAPLE_FENCE.get());
            dropSelf(BlockRegistry.MAPLE_FENCE_GATE.get());
            dropSelf(BlockRegistry.MAPLE_DOOR.get());
            dropSelf(BlockRegistry.MAPLE_TRAPDOOR.get());
            dropSelf(BlockRegistry.MAPLE_PRESSURE_PLATE.get());
            dropSelf(BlockRegistry.MAPLE_BUTTON.get());
            dropSelf(BlockRegistry.MAPLE_SIGN.get());
            dropSelf(BlockRegistry.MAPLE_WALL_SIGN.get());
            dropSelf(BlockRegistry.MAPLE_HANGING_SIGN.get());
            dropSelf(BlockRegistry.MAPLE_WALL_HANGING_SIGN.get());
            dropSelf(BlockRegistry.MAPLE_SAPLING.get());
            add(BlockRegistry.MAPLE_LEAVES.get(), createLeavesDrops(BlockRegistry.MAPLE_LEAVES.get(), BlockRegistry.MAPLE_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));
            dropSelf(BlockRegistry.MAPLE_SYRUP_BLOCK.get());
            dropSelf(BlockRegistry.TROUGH.get());
            dropOther(BlockRegistry.DEAD_CROP.get(), Items.DEAD_BUSH);
            dropPottedContents(BlockRegistry.POTTED_MAPLE_SAPLING.get());
        }

        @Override
        protected Iterable<Block> getKnownBlocks() {
            return BlockRegistry.BLOCKS.getEntries().stream().map(Holder::value).toList();
        }
    }
}

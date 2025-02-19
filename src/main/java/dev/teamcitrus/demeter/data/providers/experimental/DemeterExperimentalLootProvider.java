package dev.teamcitrus.demeter.data.providers.experimental;

import dev.teamcitrus.demeter.Demeter;
import dev.teamcitrus.demeter.registry.BlockRegistry;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class DemeterExperimentalLootProvider {
    public static LootTableProvider create(PackOutput output, CompletableFuture<HolderLookup.Provider> provider) {
        return new LootTableProvider(output, Set.of(), List.of(new LootTableProvider.SubProviderEntry(DemeterSubLootProvider::new, LootContextParamSets.BLOCK)), provider);
    }

    public static class DemeterSubLootProvider extends BlockLootSubProvider {
        public DemeterSubLootProvider(HolderLookup.Provider registries) {
            super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
        }

        @Override
        protected void generate() {
            dropSelf(BlockRegistry.BAMBOO_SHOOTS.get());
            dropSelf(BlockRegistry.COUNTER.get());
        }

        @Override
        protected Iterable<Block> getKnownBlocks() {
            var blocks = BlockRegistry.BLOCKS.getEntries();
            var copy = new HashSet<>(blocks);
            blocks.forEach(blockDeferredHolder -> {
                if (!blockDeferredHolder.get().requiredFeatures().contains(Demeter.EXPERIMENTAL)) {
                    copy.remove(blockDeferredHolder);
                }
            });
            return copy.stream().map(Holder::value).toList();
        }
    }
}

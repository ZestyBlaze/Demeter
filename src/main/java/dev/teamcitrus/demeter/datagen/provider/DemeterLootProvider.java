package dev.teamcitrus.demeter.datagen.provider;

import dev.teamcitrus.demeter.registry.BlockRegistry;
import it.unimi.dsi.fastutil.objects.ReferenceOpenHashSet;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.List;
import java.util.Set;

public class DemeterLootProvider {
    public static LootTableProvider create(PackOutput output) {
        return new LootTableProvider(output, Set.of(), List.of(new LootTableProvider.SubProviderEntry(BFSubLootProvider::new, LootContextParamSets.BLOCK)));
    }

    public static class BFSubLootProvider extends BlockLootSubProvider {
        private final Set<Block> knownBlocks = new ReferenceOpenHashSet<>();

        public BFSubLootProvider() {
            super(Set.of(), FeatureFlags.REGISTRY.allFlags());
        }

        @Override
        protected void generate() {
            dropSelf(BlockRegistry.MAPLE_LOG.get());
            //dropSelf(BlockRegistry.MAPLE_PLANKS.get());
            //add(BlockRegistry.MAPLE_DOOR.get(), createDoorTable(BlockRegistry.MAPLE_DOOR.get()));
            //dropSelf(BlockRegistry.MAPLE_TRAPDOOR.get());
        }

        @Override
        protected void dropSelf(Block pBlock) {
            super.dropSelf(pBlock);
            knownBlocks.add(pBlock);
        }

        @Override
        protected void add(Block pBlock, LootTable.Builder pBuilder) {
            super.add(pBlock, pBuilder);
            knownBlocks.add(pBlock);
        }

        @Override
        protected Iterable<Block> getKnownBlocks() {
            return knownBlocks;
        }
    }
}

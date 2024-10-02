package dev.teamcitrus.demeter.data.gen.datagen.provider;

import dev.teamcitrus.citruslib.datagen.CitrusBlockDropProvider;
import dev.teamcitrus.demeter.registry.BlockRegistry;
import dev.teamcitrus.demeter.registry.WoodSetRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class DemeterLootProvider {
    public static LootTableProvider create(PackOutput output, CompletableFuture<HolderLookup.Provider> provider) {
        return new LootTableProvider(output, Set.of(), List.of(new LootTableProvider.SubProviderEntry(BFSubLootProvider::new, LootContextParamSets.BLOCK)), provider);
    }

    public static class BFSubLootProvider extends CitrusBlockDropProvider {
        public BFSubLootProvider(HolderLookup.Provider provider) {
            super(provider);
        }

        @Override
        protected void generate() {
            dropSelf(BlockRegistry.MAPLE_LOG.get());
            dropSelf(BlockRegistry.MAPLE_WOOD.get());
            dropSelf(BlockRegistry.STRIPPED_MAPLE_LOG.get());
            dropSelf(BlockRegistry.STRIPPED_MAPLE_WOOD.get());
            dropSelf(BlockRegistry.MAPLE_SIGN.get());
            dropSelf(BlockRegistry.MAPLE_WALL_SIGN.get());
            generateSetDrops(WoodSetRegistry.MAPLE);
        }
    }
}

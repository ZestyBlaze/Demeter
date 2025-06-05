package dev.teamcitrus.demeter.data.providers;

import dev.teamcitrus.demeter.Demeter;
import dev.teamcitrus.demeter.registry.BlockRegistry;
import dev.teamcitrus.demeter.registry.ItemRegistry;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.core.Holder;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

public class DemeterModelProvider extends ModelProvider {
    public DemeterModelProvider(PackOutput output) {
        super(output, Demeter.MODID);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        ItemModelGenerators demeterItems = new DemeterItemModelGenerator(itemModels.itemModelOutput, itemModels.modelOutput);
        BlockModelGenerators demeterBlocks = new DemeterBlockModelGenerator(blockModels.blockStateOutput, demeterItems.itemModelOutput, blockModels.modelOutput);
        demeterItems.run();
        demeterBlocks.run();
    }

    @Override
    protected Stream<? extends Holder<Block>> getKnownBlocks() {
        Collection<DeferredHolder<Block, ? extends Block>> BLOCKS = BlockRegistry.BLOCKS.getEntries();
        Set<DeferredHolder<Block, ? extends Block>> COPY = new HashSet<>(BLOCKS);
        COPY.remove(BlockRegistry.MAPLE_SYRUP_BLOCK);
        COPY.remove(BlockRegistry.DEAD_CROP);
        COPY.remove(BlockRegistry.TROUGH);
        COPY.remove(BlockRegistry.FEEDING_TRAY);

        COPY.remove(BlockRegistry.WINE);
        COPY.remove(BlockRegistry.NEST);
        COPY.remove(BlockRegistry.MILK_CAN);
        return COPY.stream();
    }

    @Override
    protected Stream<? extends Holder<Item>> getKnownItems() {
        Collection<DeferredHolder<Item, ? extends Item>> ITEMS = ItemRegistry.ITEMS.getEntries();
        Set<DeferredHolder<Item, ? extends Item>> COPY = new HashSet<>(ITEMS);
        COPY.remove(ItemRegistry.TRUFFLE);
        COPY.remove(ItemRegistry.DIARY);
        return COPY.stream();
    }
}

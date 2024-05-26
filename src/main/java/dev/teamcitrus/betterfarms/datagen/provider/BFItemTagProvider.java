package dev.teamcitrus.betterfarms.datagen.provider;

import dev.teamcitrus.betterfarms.BetterFarms;
import dev.teamcitrus.betterfarms.registry.BlockRegistry;
import dev.teamcitrus.betterfarms.registry.WoodSetRegistry;
import dev.teamcitrus.citruslib.datagen.CitrusItemTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class BFItemTagProvider extends CitrusItemTagProvider {
    public static final TagKey<Item> QUALITY_PRODUCTS = ItemTags.create(BetterFarms.id("quality_products"));
    public static final TagKey<Item> MAPLE_LOGS = ItemTags.create(BetterFarms.id("maple_logs"));

    public BFItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> holder, CompletableFuture<TagLookup<Block>> blockTag, ExistingFileHelper existingFileHelper) {
        super(output, holder, blockTag, BetterFarms.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        tag(QUALITY_PRODUCTS).add(Items.WHEAT, Items.CARROT, Items.POTATO, Items.MELON_SLICE, Items.BEETROOT, Items.COD,
                Items.SALMON, Items.PUFFERFISH, Items.TROPICAL_FISH).addTag(ItemTags.WOOL);
        tag(MAPLE_LOGS).add(BlockRegistry.MAPLE_LOG.get().asItem(), BlockRegistry.MAPLE_WOOD.get().asItem(),
                BlockRegistry.STRIPPED_MAPLE_LOG.get().asItem(), BlockRegistry.STRIPPED_MAPLE_WOOD.get().asItem());
        tag(ItemTags.LOGS).add(BlockRegistry.MAPLE_LOG.get().asItem(), BlockRegistry.MAPLE_WOOD.get().asItem(),
                BlockRegistry.STRIPPED_MAPLE_LOG.get().asItem(), BlockRegistry.STRIPPED_MAPLE_WOOD.get().asItem());
        tag(ItemTags.LOGS_THAT_BURN).add(BlockRegistry.MAPLE_LOG.get().asItem(), BlockRegistry.MAPLE_WOOD.get().asItem(),
                BlockRegistry.STRIPPED_MAPLE_LOG.get().asItem(), BlockRegistry.STRIPPED_MAPLE_WOOD.get().asItem());
        tag(ItemTags.COMPLETES_FIND_TREE_TUTORIAL).add(BlockRegistry.MAPLE_LOG.get().asItem());
        tag(ItemTags.LEAVES).add(BlockRegistry.MAPLE_LEAVES.get().asItem());
        generateSetTags(WoodSetRegistry.MAPLE);
    }
}

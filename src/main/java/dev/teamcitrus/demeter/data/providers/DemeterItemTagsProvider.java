package dev.teamcitrus.demeter.data.providers;

import dev.teamcitrus.citruslib.datagen.CitrusItemTagsProvider;
import dev.teamcitrus.demeter.Demeter;
import dev.teamcitrus.demeter.compat.accessories.AccessoriesCompat;
import dev.teamcitrus.demeter.registry.BlockRegistry;
import dev.teamcitrus.demeter.registry.ItemRegistry;
import dev.teamcitrus.demeter.registry.WoodSetRegistry;
import io.wispforest.accessories.api.data.AccessoriesTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

@SuppressWarnings("unchecked")
public class DemeterItemTagsProvider extends CitrusItemTagsProvider {
    public static final TagKey<Item> QUALITY_PRODUCTS = modTag("quality_products");
    public static final TagKey<Item> MAPLE_LOGS = modTag("maple_logs");

    public static final TagKey<Item> TROUGH_FOODS = modTag("trough_foods");
    public static final TagKey<Item> TROUGH_FOODS_HAY = modTag("trough_foods/hay");
    public static final TagKey<Item> TROUGH_FOODS_SLOP = modTag("trough_foods/slop");

    public static final TagKey<Item> CLOCKS = commonTag("clocks");
    public static final TagKey<Item> TOOLS_ANIMAL_BRUSH = commonTag("tools/animal_brush");

    public DemeterItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> holder, CompletableFuture<TagLookup<Block>> blockTag, ExistingFileHelper existingFileHelper) {
        super(output, holder, blockTag, Demeter.MODID, existingFileHelper);
    }

    private static TagKey<Item> modTag(String id) {
        return ItemTags.create(Demeter.id(id));
    }

    private static TagKey<Item> commonTag(String id) {
        return ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", id));
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        tag(QUALITY_PRODUCTS).add(Items.WHEAT, Items.CARROT, Items.POTATO, Items.MELON_SLICE, Items.PUMPKIN,
                Items.BEETROOT, Items.MILK_BUCKET).addTags(ItemTags.WOOL, Tags.Items.FOODS_RAW_FISH);
        tag(MAPLE_LOGS).add(BlockRegistry.MAPLE_LOG.get().asItem(), BlockRegistry.MAPLE_WOOD.get().asItem(),
                BlockRegistry.STRIPPED_MAPLE_LOG.get().asItem(), BlockRegistry.STRIPPED_MAPLE_WOOD.get().asItem());
        tag(ItemTags.LOGS).add(BlockRegistry.MAPLE_LOG.get().asItem(), BlockRegistry.MAPLE_WOOD.get().asItem(),
                BlockRegistry.STRIPPED_MAPLE_LOG.get().asItem(), BlockRegistry.STRIPPED_MAPLE_WOOD.get().asItem());
        tag(ItemTags.LOGS_THAT_BURN).add(BlockRegistry.MAPLE_LOG.get().asItem(), BlockRegistry.MAPLE_WOOD.get().asItem(),
                BlockRegistry.STRIPPED_MAPLE_LOG.get().asItem(), BlockRegistry.STRIPPED_MAPLE_WOOD.get().asItem());
        tag(ItemTags.COMPLETES_FIND_TREE_TUTORIAL).add(BlockRegistry.MAPLE_LOG.get().asItem());
        tag(ItemTags.LEAVES).add(BlockRegistry.MAPLE_LEAVES.get().asItem());
        tag(CLOCKS).add(Items.CLOCK).addOptional(AccessoriesCompat.Items.BREEDING_CHARM.getId());
        tag(TOOLS_ANIMAL_BRUSH).add(ItemRegistry.ANIMAL_BRUSH.get());
        tag(Tags.Items.TOOLS).addTag(TOOLS_ANIMAL_BRUSH);
        tag(TROUGH_FOODS).addTags(TROUGH_FOODS_HAY, TROUGH_FOODS_SLOP);
        tag(TROUGH_FOODS_HAY).add(Items.WHEAT, Items.SEAGRASS);
        tag(TROUGH_FOODS_SLOP).add(Items.CARROT, Items.POTATO);
        tag(AccessoriesTags.CHARM_TAG).add(AccessoriesCompat.Items.BREEDING_CHARM.get());
        tag(AccessoriesTags.NECKLACE_TAG).add(AccessoriesCompat.Items.POCKET_WATCH.get());
        generateSetTags(WoodSetRegistry.MAPLE);
    }
}

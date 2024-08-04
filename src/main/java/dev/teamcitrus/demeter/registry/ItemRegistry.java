package dev.teamcitrus.demeter.registry;

import dev.teamcitrus.demeter.Demeter;
import dev.teamcitrus.demeter.item.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.BoatItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ItemRegistry {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Demeter.MODID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(BuiltInRegistries.CREATIVE_MODE_TAB, Demeter.MODID);

    private static final DeferredHolder<CreativeModeTab, CreativeModeTab> DEMETER_TAB = CREATIVE_MODE_TABS.register("demeter", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.demeter"))
            .icon(ItemRegistry.MAPLE_LOG.get()::getDefaultInstance)
            .displayItems((parameters, output) -> ItemRegistry.ITEMS.getEntries().forEach(item -> output.accept(item.get())))
            .build());

    public static final DeferredItem<Item> DEV_DEBUG_ITEM = ITEMS.register("dev_debug_item", DevDebugItem::new);
    public static final DeferredItem<Item> ANIMAL_TAG = ITEMS.register("animal_tag", AnimalTagItem::new);
    public static final DeferredItem<Item> BRUSH = ITEMS.register("brush", BrushItem::new);
    public static final DeferredItem<Item> MILK_BOTTLE = ITEMS.register("milk_bottle", MilkBottleItem::new);
    public static final DeferredItem<Item> MIRACLE_POTION = ITEMS.register("miracle_potion", MiraclePotionItem::new);
    public static final DeferredItem<BlockItem> MAPLE_LOG = ITEMS.registerSimpleBlockItem(BlockRegistry.MAPLE_LOG);
    public static final DeferredItem<BlockItem> MAPLE_WOOD = ITEMS.registerSimpleBlockItem(BlockRegistry.MAPLE_WOOD);
    public static final DeferredItem<BlockItem> STRIPPED_MAPLE_LOG = ITEMS.registerSimpleBlockItem(BlockRegistry.STRIPPED_MAPLE_LOG);
    public static final DeferredItem<BlockItem> STRIPPED_MAPLE_WOOD = ITEMS.registerSimpleBlockItem(BlockRegistry.STRIPPED_MAPLE_WOOD);
    public static final DeferredItem<Item> MAPLE_BOAT = ITEMS.register("maple_boat", () -> new BoatItem(false, EnumRegistry.MAPLE_BOAT_TYPE.getValue(), new Item.Properties().stacksTo(1)));
    public static final DeferredItem<BlockItem> MAPLE_LEAVES = ITEMS.registerSimpleBlockItem(BlockRegistry.MAPLE_LEAVES);
}

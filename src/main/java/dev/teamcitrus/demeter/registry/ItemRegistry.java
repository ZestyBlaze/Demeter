package dev.teamcitrus.demeter.registry;

import dev.teamcitrus.citruslib.util.ModUtil;
import dev.teamcitrus.demeter.Demeter;
import dev.teamcitrus.demeter.compat.AccessoriesCompat;
import dev.teamcitrus.demeter.item.BrushItem;
import dev.teamcitrus.demeter.item.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ItemRegistry {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Demeter.MODID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(BuiltInRegistries.CREATIVE_MODE_TAB, Demeter.MODID);

    public static final ResourceKey<CreativeModeTab> DEMETER_TAB_KEY = ResourceKey.create(Registries.CREATIVE_MODE_TAB, ResourceLocation.fromNamespaceAndPath(Demeter.MODID, "demeter"));
    private static final DeferredHolder<CreativeModeTab, CreativeModeTab> DEMETER_TAB = CREATIVE_MODE_TABS.register("demeter", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.demeter"))
            .icon(ItemRegistry.MAPLE_LOG.get()::getDefaultInstance)
            .displayItems((parameters, output) -> ItemRegistry.ITEMS.getEntries().forEach(item -> {
                if (!(item.get() instanceof WateringCanItem))
                    output.accept(item.get());
                if (ModUtil.isModInstalled("accessories"))
                    AccessoriesCompat.addItemsToTab(output);
            }))
            .build());

    public static final DeferredItem<Item> DEV_DEBUG_ITEM = ITEMS.register("dev_debug_item", DevDebugItem::new);
    public static final DeferredItem<Item> ANIMAL_TAG = ITEMS.register("animal_tag", AnimalTagItem::new);
    public static final DeferredItem<Item> BRUSH = ITEMS.register("brush", BrushItem::new);
    public static final DeferredItem<Item> MILK_BOTTLE = ITEMS.register("milk_bottle", MilkBottleItem::new);
    public static final DeferredItem<Item> MAPLE_BOTTLE = ITEMS.register("maple_bottle", MapleBottleItem::new);
    public static final DeferredItem<Item> MIRACLE_POTION = ITEMS.register("miracle_potion", MiraclePotionItem::new);
    public static final DeferredItem<Item> BUTTER = ITEMS.registerSimpleItem("butter");
    public static final DeferredItem<WateringCanItem> WATERING_CAN = ITEMS.register("watering_can", WateringCanItem::new);
    public static final DeferredItem<BlockItem> MAPLE_LOG = ITEMS.registerSimpleBlockItem(BlockRegistry.MAPLE_LOG);
    public static final DeferredItem<BlockItem> MAPLE_WOOD = ITEMS.registerSimpleBlockItem(BlockRegistry.MAPLE_WOOD);
    public static final DeferredItem<BlockItem> STRIPPED_MAPLE_LOG = ITEMS.registerSimpleBlockItem(BlockRegistry.STRIPPED_MAPLE_LOG);
    public static final DeferredItem<BlockItem> STRIPPED_MAPLE_WOOD = ITEMS.registerSimpleBlockItem(BlockRegistry.STRIPPED_MAPLE_WOOD);
    public static final DeferredItem<BlockItem> MAPLE_LEAVES = ITEMS.registerSimpleBlockItem(BlockRegistry.MAPLE_LEAVES);
    public static final DeferredItem<BlockItem> MAPLE_SAPLING = ITEMS.registerSimpleBlockItem(BlockRegistry.MAPLE_SAPLING);
    public static final DeferredItem<BlockItem> MAPLE_SYRUP_BLOCK = ITEMS.registerSimpleBlockItem(BlockRegistry.MAPLE_SYRUP_BLOCK);
    public static final DeferredItem<Item> MAPLE_SIGN = ITEMS.register("maple_sign", () -> new SignItem(new Item.Properties(), BlockRegistry.MAPLE_SIGN.get(), BlockRegistry.MAPLE_WALL_SIGN.get()));
    public static final DeferredItem<Item> MAPLE_HANGING_SIGN = ITEMS.register("maple_hanging_sign", () -> new HangingSignItem(BlockRegistry.MAPLE_HANGING_SIGN.get(), BlockRegistry.MAPLE_WALL_HANGING_SIGN.get(), new Item.Properties()));
    public static final DeferredItem<Item> MAPLE_BOAT = ITEMS.register("maple_boat", () -> new BoatItem(false, EnumRegistry.MAPLE_BOAT_TYPE.getValue(), new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> MAPLE_CHEST_BOAT = ITEMS.register("maple_chest_boat", () -> new BoatItem(true, EnumRegistry.MAPLE_BOAT_TYPE.getValue(), new Item.Properties().stacksTo(1)));
}

package dev.teamcitrus.demeter.registry;

import dev.teamcitrus.citruslib.util.ModUtil;
import dev.teamcitrus.demeter.Demeter;
import dev.teamcitrus.demeter.compat.accessories.AccessoriesCompat;
import dev.teamcitrus.demeter.item.*;
import dev.teamcitrus.demeter.item.pouch.FoodPouchItem;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ItemRegistry {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Demeter.MODID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(BuiltInRegistries.CREATIVE_MODE_TAB, Demeter.MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> DEMETER_TAB = CREATIVE_MODE_TABS.register("demeter", () -> CreativeModeTab.builder()
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
    public static final DeferredItem<Item> ANIMAL_BRUSH = ITEMS.register("animal_brush", AnimalBrushItem::new);
    public static final DeferredItem<Item> MILK_BOTTLE = ITEMS.register("milk_bottle", MilkBottleItem::new);
    public static final DeferredItem<Item> MAPLE_SYRUP_BOTTLE = ITEMS.register("maple_syrup_bottle", () -> new Item(new Item.Properties().craftRemainder(Items.GLASS_BOTTLE).food(DemeterFoods.MAPLE_BOTTLE).usingConvertsTo(Items.GLASS_BOTTLE).stacksTo(16).setId(createID("maple_syrup_bottle"))));
    public static final DeferredItem<Item> MIRACLE_POTION = ITEMS.register("miracle_potion", MiraclePotionItem::new);
    public static final DeferredItem<Item> BUTTER = ITEMS.registerSimpleItem("butter");
    public static final DeferredItem<Item> TRUFFLE = ITEMS.registerSimpleItem("truffle");
    public static final DeferredItem<WateringCanItem> WATERING_CAN = ITEMS.register("watering_can", WateringCanItem::new);
    public static final DeferredItem<Item> FOOD_POUCH = ITEMS.register("food_pouch", FoodPouchItem::new);
    public static final DeferredItem<BlockItem> MAPLE_LOG = ITEMS.registerSimpleBlockItem(BlockRegistry.MAPLE_LOG);
    public static final DeferredItem<BlockItem> MAPLE_WOOD = ITEMS.registerSimpleBlockItem(BlockRegistry.MAPLE_WOOD);
    public static final DeferredItem<BlockItem> STRIPPED_MAPLE_LOG = ITEMS.registerSimpleBlockItem(BlockRegistry.STRIPPED_MAPLE_LOG);
    public static final DeferredItem<BlockItem> STRIPPED_MAPLE_WOOD = ITEMS.registerSimpleBlockItem(BlockRegistry.STRIPPED_MAPLE_WOOD);
    public static final DeferredItem<BlockItem> MAPLE_PLANKS = ITEMS.registerSimpleBlockItem("maple_planks", BlockRegistry.MAPLE_PLANKS);
    public static final DeferredItem<BlockItem> MAPLE_STAIRS = ITEMS.registerSimpleBlockItem("maple_stairs", BlockRegistry.MAPLE_STAIRS);
    public static final DeferredItem<BlockItem> MAPLE_SLAB = ITEMS.registerSimpleBlockItem("maple_slab", BlockRegistry.MAPLE_SLAB);
    public static final DeferredItem<BlockItem> MAPLE_FENCE = ITEMS.registerSimpleBlockItem("maple_fence", BlockRegistry.MAPLE_FENCE);
    public static final DeferredItem<BlockItem> MAPLE_FENCE_GATE = ITEMS.registerSimpleBlockItem("maple_fence_gate", BlockRegistry.MAPLE_FENCE_GATE);
    public static final DeferredItem<BlockItem> MAPLE_DOOR = ITEMS.registerSimpleBlockItem("maple_door", BlockRegistry.MAPLE_DOOR);
    public static final DeferredItem<BlockItem> MAPLE_TRAPDOOR = ITEMS.registerSimpleBlockItem("maple_trapdoor", BlockRegistry.MAPLE_TRAPDOOR);
    public static final DeferredItem<BlockItem> MAPLE_PRESSURE_PLATE = ITEMS.registerSimpleBlockItem("maple_pressure_plate", BlockRegistry.MAPLE_PRESSURE_PLATE);
    public static final DeferredItem<BlockItem> MAPLE_BUTTON = ITEMS.registerSimpleBlockItem("maple_button", BlockRegistry.MAPLE_BUTTON);
    public static final DeferredItem<BlockItem> MAPLE_LEAVES = ITEMS.registerSimpleBlockItem(BlockRegistry.MAPLE_LEAVES);
    public static final DeferredItem<BlockItem> MAPLE_SAPLING = ITEMS.registerSimpleBlockItem(BlockRegistry.MAPLE_SAPLING);
    public static final DeferredItem<Item> MAPLE_SIGN = ITEMS.register("maple_sign", () -> new SignItem(new Item.Properties().setId(createID("maple_sign")).useBlockDescriptionPrefix(), BlockRegistry.MAPLE_SIGN.get(), BlockRegistry.MAPLE_WALL_SIGN.get(), Direction.DOWN));
    public static final DeferredItem<Item> MAPLE_HANGING_SIGN = ITEMS.register("maple_hanging_sign", () -> new HangingSignItem(BlockRegistry.MAPLE_HANGING_SIGN.get(), BlockRegistry.MAPLE_WALL_HANGING_SIGN.get(), new Item.Properties().setId(createID("maple_hanging_sign")).useBlockDescriptionPrefix()));
    public static final DeferredItem<Item> MAPLE_BOAT = ITEMS.register("maple_boat", () -> new BoatItem(EntityTypeRegistry.MAPLE_BOAT.get(), new Item.Properties().stacksTo(1).setId(createID("maple_boat"))));
    public static final DeferredItem<Item> MAPLE_CHEST_BOAT = ITEMS.register("maple_chest_boat", () -> new BoatItem(EntityTypeRegistry.MAPLE_CHEST_BOAT.get(), new Item.Properties().stacksTo(1).setId(createID("maple_chest_boat"))));
    public static final DeferredItem<BlockItem> MAPLE_SYRUP_BLOCK = ITEMS.registerSimpleBlockItem(BlockRegistry.MAPLE_SYRUP_BLOCK);
    public static final DeferredItem<BlockItem> TROUGH = ITEMS.registerSimpleBlockItem(BlockRegistry.TROUGH);

    //Experimental Items
    public static final DeferredItem<BlockItem> STRAWBERRIES = ITEMS.register("strawberries", () -> new BlockItem(BlockRegistry.STRAWBERRY_BUSH.get(), new Item.Properties().requiredFeatures(Demeter.EXPERIMENTAL).setId(createID("strawberries"))));
    public static final DeferredItem<BlockItem> BAMBOO_SHOOTS = ITEMS.register("bamboo_shoots", () -> new BlockItem(BlockRegistry.BAMBOO_SHOOTS.get(), new Item.Properties().useBlockDescriptionPrefix().requiredFeatures(Demeter.EXPERIMENTAL).setId(createID("bamboo_shoots"))));
    public static final DeferredItem<BlockItem> COUNTER = ITEMS.register("counter", () -> new BlockItem(BlockRegistry.COUNTER.get(), new Item.Properties().useBlockDescriptionPrefix().requiredFeatures(Demeter.EXPERIMENTAL).setId(createID("counter"))));

    public static ResourceKey<Item> createID(String id) {
        return ResourceKey.create(Registries.ITEM, Demeter.id(id));
    }
}

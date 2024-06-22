package dev.teamcitrus.demeter.registry;

import dev.teamcitrus.demeter.Demeter;
import dev.teamcitrus.demeter.item.AnimalTagItem;
import dev.teamcitrus.demeter.item.BrushItem;
import dev.teamcitrus.demeter.item.MilkBottleItem;
import dev.teamcitrus.demeter.item.dev.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ItemRegistry {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Demeter.MODID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(BuiltInRegistries.CREATIVE_MODE_TAB, Demeter.MODID);

    public static final DeferredRegister.Items DEV_ITEMS = DeferredRegister.createItems(Demeter.MODID);
    public static final DeferredRegister<CreativeModeTab> DEV_TABS = DeferredRegister.create(BuiltInRegistries.CREATIVE_MODE_TAB, Demeter.MODID);

    private static final DeferredHolder<CreativeModeTab, CreativeModeTab> BETTER_FARMS_TAB = CREATIVE_MODE_TABS.register("betterfarms", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.demeter"))
            .icon(ItemRegistry.MAPLE_LOG.get()::getDefaultInstance)
            .displayItems((parameters, output) -> ItemRegistry.ITEMS.getEntries().forEach(item -> output.accept(item.get())))
            .build());

    private static final DeferredHolder<CreativeModeTab, CreativeModeTab> DEV_ITEMS_TAB = DEV_TABS.register("betterfarms_dev", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.demeter.dev"))
            .icon(Items.COMMAND_BLOCK::getDefaultInstance)
            .displayItems(((pParameters, pOutput) -> ItemRegistry.DEV_ITEMS.getEntries().forEach(item -> pOutput.accept(item.get()))))
            .build()
    );

    public static final DeferredItem<Item> GENDER_TEST = DEV_ITEMS.register("gender_test", GenderTestItem::new);
    public static final DeferredItem<Item> PREGNANCY_TEST = DEV_ITEMS.register("pregnancy_test", PregnancyTestItem::new);
    public static final DeferredItem<Item> LOVE_ITEM = DEV_ITEMS.register("love_checker", LoveCheckerItem::new);
    public static final DeferredItem<Item> MISC_ITEM = DEV_ITEMS.register("misc_test", MiscTestItem::new);
    public static final DeferredItem<Item> REGISTRY_ITEM = DEV_ITEMS.register("registry_test", RegistryTestItem::new);

    public static final DeferredItem<Item> ANIMAL_TAG = ITEMS.register("animal_tag", AnimalTagItem::new);
    public static final DeferredItem<Item> BRUSH = ITEMS.register("brush", BrushItem::new);
    public static final DeferredItem<Item> MILK_BOTTLE = ITEMS.register("milk_bottle", MilkBottleItem::new);
    public static final DeferredItem<BlockItem> MAPLE_LOG = ITEMS.registerSimpleBlockItem(BlockRegistry.MAPLE_LOG);
    public static final DeferredItem<BlockItem> MAPLE_WOOD = ITEMS.registerSimpleBlockItem(BlockRegistry.MAPLE_WOOD);
    public static final DeferredItem<BlockItem> STRIPPED_MAPLE_LOG = ITEMS.registerSimpleBlockItem(BlockRegistry.STRIPPED_MAPLE_LOG);
    public static final DeferredItem<BlockItem> STRIPPED_MAPLE_WOOD = ITEMS.registerSimpleBlockItem(BlockRegistry.STRIPPED_MAPLE_WOOD);
    public static final DeferredItem<BlockItem> MAPLE_LEAVES = ITEMS.registerSimpleBlockItem(BlockRegistry.MAPLE_LEAVES);
}
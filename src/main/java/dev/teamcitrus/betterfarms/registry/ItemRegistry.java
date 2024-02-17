package dev.teamcitrus.betterfarms.registry;

import dev.teamcitrus.betterfarms.BetterFarms;
import dev.teamcitrus.betterfarms.item.CodecTestItem;
import dev.teamcitrus.betterfarms.item.GenderTestItem;
import dev.teamcitrus.betterfarms.item.MilkBottleItem;
import dev.teamcitrus.betterfarms.item.PregnancyTestItem;
import net.minecraft.client.gui.components.toasts.SystemToast;
import net.minecraft.client.gui.components.toasts.TutorialToast;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ItemRegistry {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(BetterFarms.MODID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, BetterFarms.MODID);

    private static final DeferredHolder<CreativeModeTab, CreativeModeTab> BETTER_FARMS_TAB = CREATIVE_MODE_TABS.register("betterfarms", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.betterfarms"))
            .icon(() -> ItemRegistry.TRUFFLE.get().getDefaultInstance())
            .displayItems((parameters, output) -> ItemRegistry.ITEMS.getEntries().forEach(item -> output.accept(item.get())))
            .build());

    public static final DeferredItem<Item> CODEC_TEST = ITEMS.register("codec_test", CodecTestItem::new);
    public static final DeferredItem<Item> GENDER_TEST = ITEMS.register("gender_test", GenderTestItem::new);
    public static final DeferredItem<Item> PREGNANCY_TEST = ITEMS.register("pregnancy_test", PregnancyTestItem::new);
    public static final DeferredItem<Item> MILK_BOTTLE = ITEMS.register("milk_bottle", MilkBottleItem::new);
    public static final DeferredItem<Item> TRUFFLE = ITEMS.registerSimpleItem("truffle", new Item.Properties().food(new FoodProperties.Builder()
                    .nutrition(6).saturationMod(1.2f)
                    .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 15 * 20), 1.0f)
                    .build())
    );
    public static final DeferredItem<BlockItem> MAPLE_LOG = ITEMS.registerSimpleBlockItem(BlockRegistry.MAPLE_LOG);
    public static final DeferredItem<BlockItem> MAPLE_PLANKS = ITEMS.registerSimpleBlockItem(BlockRegistry.MAPLE_PLANKS);
    public static final DeferredItem<BlockItem> MAPLE_LEAVES = ITEMS.registerSimpleBlockItem(BlockRegistry.MAPLE_LEAVES);
}

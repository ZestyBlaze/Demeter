package dev.zestyblaze.betterfauna.registry;

import com.google.common.collect.Sets;
import dev.zestyblaze.betterfauna.BetterFauna;
import dev.zestyblaze.betterfauna.item.GenderTestItem;
import dev.zestyblaze.betterfauna.item.PregnancyTestItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.Set;

public class ItemRegistry {
    public static final Set<Item> ITEMS = Sets.newLinkedHashSet();
    private static final CreativeModeTab TAB = FabricItemGroup.builder()
            .icon(() -> new ItemStack(Items.WHEAT))
            .title(Component.translatable("itemGroup.betterfauna"))
            .displayItems((itemDisplayParameters, output) -> ITEMS.forEach(output::accept))
            .build();

    public static final Item GENDER_TEST = registerItem("gender_test", new GenderTestItem());
    public static final Item PREGNANCY_TEST = registerItem("pregnancy_test", new PregnancyTestItem());
    public static final Item TRUFFLE = registerItem("truffle", new Item(
            new FabricItemSettings().food(new FoodProperties.Builder()
                    .nutrition(6).saturationMod(1.2f)
                            .effect(new MobEffectInstance(MobEffects.REGENERATION, 15 * 20), 1.0f)
                    .build())
    ));

    private static Item registerItem(String id, Item item) {
        Item registryItem = Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(BetterFauna.MODID, id), item);
        ITEMS.add(registryItem);
        return registryItem;
    }

    public static void init() {
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, new ResourceLocation(BetterFauna.MODID, "betterfauna"), TAB);
    }
}

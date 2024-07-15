package dev.teamcitrus.demeter.enchant;

import dev.teamcitrus.demeter.Demeter;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.neoforged.neoforge.common.Tags;

public class DemeterEnchantments {
    public static final ResourceKey<Enchantment> BARBER = key("barber");

    public static void bootstrap(BootstrapContext<Enchantment> context) {
        HolderGetter<Item> itemGetter = context.lookup(Registries.ITEM);
        register(context, BARBER, Enchantment.enchantment(
                Enchantment.definition(
                       itemGetter.getOrThrow(Tags.Items.TOOLS_SHEAR),
                        2, 1,
                        Enchantment.dynamicCost(15, 9),
                        Enchantment.dynamicCost(65, 9), 4,
                        EquipmentSlotGroup.MAINHAND
                )
        ));
    }

    private static void register(BootstrapContext<Enchantment> context, ResourceKey<Enchantment> key, Enchantment.Builder builder) {
        context.register(key, builder.build(key.location()));
    }

    private static ResourceKey<Enchantment> key(String name) {
        return ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.fromNamespaceAndPath(Demeter.MODID, name));
    }
}
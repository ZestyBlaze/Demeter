package dev.teamcitrus.demeter.enchantment;

import dev.teamcitrus.demeter.Demeter;
import dev.teamcitrus.demeter.data.gen.datagen.provider.DemeterItemTagsProvider;
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
    public static final ResourceKey<Enchantment> COMFORT = key("comfort");
    public static final ResourceKey<Enchantment> SPITE = key("spite");

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
        register(context, COMFORT, Enchantment.enchantment(
                Enchantment.definition(
                        itemGetter.getOrThrow(DemeterItemTagsProvider.TOOLS_ANIMAL_BRUSH),
                        2, 3,
                        Enchantment.dynamicCost(15, 9),
                        Enchantment.dynamicCost(65, 9), 4,
                        EquipmentSlotGroup.MAINHAND
                )
        ));
        register(context, SPITE, Enchantment.enchantment(
                Enchantment.definition(
                        itemGetter.getOrThrow(DemeterItemTagsProvider.TOOLS_ANIMAL_BRUSH),
                        2, 3,
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

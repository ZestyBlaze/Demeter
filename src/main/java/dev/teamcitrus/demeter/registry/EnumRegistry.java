package dev.teamcitrus.demeter.registry;

import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Items;
import net.neoforged.fml.common.asm.enumextension.EnumProxy;

import java.util.function.Supplier;

public class EnumRegistry {
    public static final EnumProxy<Boat.Type> MAPLE_BOAT_TYPE = new EnumProxy<>(
            Boat.Type.class, WoodSetRegistry.MAPLE.getPlanks(), "demeter:maple", ItemRegistry.MAPLE_BOAT,
            ItemRegistry.MAPLE_CHEST_BOAT, supplier(Items.STICK), false
    );

    public static <T> Supplier<T> supplier(T object) {
        return () -> object;
    }
}

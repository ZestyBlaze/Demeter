package dev.teamcitrus.demeter.registry;

import dev.teamcitrus.demeter.Demeter;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.entity.vehicle.ChestBoat;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class EntityTypeRegistry {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, Demeter.MODID);

    public static final DeferredHolder<EntityType<?>, EntityType<Boat>> MAPLE_BOAT = ENTITY_TYPES.register(
            "maple_boat", () -> EntityType.Builder.of(boatFactory(ItemRegistry.MAPLE_BOAT), MobCategory.MISC).noLootTable().sized(1.375F, 0.5625F).eyeHeight(0.5625F).clientTrackingRange(10).build(null));
    public static final DeferredHolder<EntityType<?>, EntityType<ChestBoat>> MAPLE_CHEST_BOAT = ENTITY_TYPES.register(
            "maple_chest_boat", () -> EntityType.Builder.of(chestBoatFactory(ItemRegistry.MAPLE_CHEST_BOAT), MobCategory.MISC).noLootTable().sized(1.375F, 0.5625F).eyeHeight(0.5625F).clientTrackingRange(10).build(null));

    private static EntityType.EntityFactory<Boat> boatFactory(Supplier<Item> boatItemGetter) {
        return (p_375558_, p_375559_) -> new Boat(p_375558_, p_375559_, boatItemGetter);
    }

    private static EntityType.EntityFactory<ChestBoat> chestBoatFactory(Supplier<Item> boatItemGetter) {
        return (p_375555_, p_375556_) -> new ChestBoat(p_375555_, p_375556_, boatItemGetter);
    }
}

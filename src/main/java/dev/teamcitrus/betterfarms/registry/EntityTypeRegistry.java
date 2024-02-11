package dev.teamcitrus.betterfarms.registry;

import dev.teamcitrus.betterfarms.BetterFarms;
import dev.teamcitrus.betterfarms.entity.HarvestGoddess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class EntityTypeRegistry {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, BetterFarms.MODID);

    public static final DeferredHolder<EntityType<?>, EntityType<HarvestGoddess>> HARVEST_GODDESS = ENTITY_TYPES.register(
            "harvest_goddess",
            () -> EntityType.Builder.of(HarvestGoddess::new, MobCategory.AMBIENT)
                    .sized(0.6f, 1.2f)
                    .build("harvest_goddess")
    );
}

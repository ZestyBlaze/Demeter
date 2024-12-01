package dev.teamcitrus.demeter.registry;

import dev.teamcitrus.demeter.Demeter;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.damagesource.DamageType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class DamageTypeRegistry {
    public static final DeferredRegister<DamageType> DAMAGE_SOURCES = DeferredRegister.create(Registries.DAMAGE_TYPE, Demeter.MODID);
    public static final DeferredHolder<DamageType, DamageType> OLD_AGE = DAMAGE_SOURCES.register("old_age", () -> new DamageType("old_age", 0F));
}

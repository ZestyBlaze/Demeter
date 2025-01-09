package dev.teamcitrus.demeter.registry;

import dev.teamcitrus.demeter.Demeter;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class StatsRegistry {
    public static final DeferredRegister<ResourceLocation> STATS = DeferredRegister.create(BuiltInRegistries.CUSTOM_STAT, Demeter.MODID);

    public static final DeferredHolder<ResourceLocation, ResourceLocation> ANIMALS_FED = STATS.register("animals_fed", () -> Demeter.id("animals_fed"));
    public static final DeferredHolder<ResourceLocation, ResourceLocation> TIMES_PET = STATS.register("times_pet", () -> Demeter.id("times_pet"));
}

package dev.teamcitrus.demeter.registry;

import dev.teamcitrus.demeter.Demeter;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class FluidTypeRegistry {
    public static final DeferredRegister<FluidType> FLUIDS = DeferredRegister.create(NeoForgeRegistries.FLUID_TYPES, Demeter.MODID);

    public static final DeferredHolder<FluidType, FluidType> WINE = FLUIDS.register("wine", () -> new FluidType(
            FluidType.Properties.create().density(985).temperature(295)
    ));
}

package dev.teamcitrus.betterfarms.registry;

import dev.teamcitrus.betterfarms.BetterFarms;
import dev.teamcitrus.betterfarms.block.fluid.BaseFluidType;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class FluidTypeRegistry {
    public static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(NeoForgeRegistries.Keys.FLUID_TYPES, BetterFarms.MODID);

    public static final DeferredHolder<FluidType, BaseFluidType> MILK_FLUID_TYPE = FLUID_TYPES.register(
            "milk_fluid",
            () -> new BaseFluidType(
                    new ResourceLocation("block/water_still"),
                    new ResourceLocation("block/water_flow"),
                    FluidType.Properties.create()
                            .density(1024)
                            .viscosity(1024)
            )
    );
}

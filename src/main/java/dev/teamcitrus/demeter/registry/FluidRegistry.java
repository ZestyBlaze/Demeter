package dev.teamcitrus.demeter.registry;

import dev.teamcitrus.demeter.Demeter;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class FluidRegistry {
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(Registries.FLUID, Demeter.MODID);

    public static final DeferredHolder<Fluid, FlowingFluid> WINE_FLOWING = FLUIDS.register("flowing_wine", () -> new BaseFlowingFluid.Flowing(FluidRegistry.WINE_PROPERTIES));
    public static final DeferredHolder<Fluid, FlowingFluid> WINE = FLUIDS.register("wine", () -> new BaseFlowingFluid.Source(FluidRegistry.WINE_PROPERTIES));
    public static final BaseFlowingFluid.Properties WINE_PROPERTIES = new BaseFlowingFluid.Properties(FluidTypeRegistry.WINE, WINE, WINE_FLOWING).block(BlockRegistry.WINE);
}

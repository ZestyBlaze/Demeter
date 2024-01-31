package dev.teamcitrus.betterfarms.registry;

import dev.teamcitrus.betterfarms.BetterFarms;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class FluidRegistry {
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(Registries.FLUID, BetterFarms.MODID);

    public static final DeferredHolder<Fluid, FlowingFluid> FLOWING_MILK = FLUIDS.register("flowing_milk", () -> new BaseFlowingFluid.Flowing(FluidRegistry.MILK_FLUID_PROPERTIES));
    public static final DeferredHolder<Fluid, FlowingFluid> MILK = FLUIDS.register("milk_fluid", () -> new BaseFlowingFluid.Source(FluidRegistry.MILK_FLUID_PROPERTIES));
    public static final BaseFlowingFluid.Properties MILK_FLUID_PROPERTIES = new BaseFlowingFluid.Properties(FluidTypeRegistry.MILK_FLUID_TYPE, MILK, FLOWING_MILK).block(BlockRegistry.MILK_BLOCK).bucket(() -> Items.MILK_BUCKET);
}

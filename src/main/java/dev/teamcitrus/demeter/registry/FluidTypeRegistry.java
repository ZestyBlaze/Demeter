package dev.teamcitrus.demeter.registry;

import dev.teamcitrus.demeter.Demeter;
import dev.teamcitrus.citruslib.fluid.CommonFluidType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.neoforged.neoforge.common.SoundActions;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.joml.Vector3f;

public class FluidTypeRegistry {
    public static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(NeoForgeRegistries.Keys.FLUID_TYPES, Demeter.MODID);

    public static final DeferredHolder<FluidType, CommonFluidType> MILK_FLUID_TYPE = FLUID_TYPES.register(
            "milk_fluid",
            () -> new CommonFluidType(
                    new ResourceLocation("block/water_still"),
                    new ResourceLocation("block/water_flow"),
                    0xFFFEFCFF,
                    new Vector3f(1.0f, 245.0f / 255.0f, 238.0f / 255.0f),
                    FluidType.Properties.create()
                            .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY)
                            .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL)
                            .supportsBoating(true)
                            .density(10)
                            .viscosity(15),
                    0.75f,
                    2f
            )
    );
}

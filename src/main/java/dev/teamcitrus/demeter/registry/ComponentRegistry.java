package dev.teamcitrus.demeter.registry;

import dev.teamcitrus.demeter.Demeter;
import dev.teamcitrus.demeter.component.QualityLevelComponent;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.fluids.SimpleFluidContent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ComponentRegistry {
    public static final DeferredRegister.DataComponents COMPONENTS = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, Demeter.MODID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<QualityLevelComponent>> QUALITY_LEVEL =
            COMPONENTS.registerComponentType("quality_level", builder -> builder.persistent(QualityLevelComponent.CODEC).networkSynchronized(QualityLevelComponent.STREAM_CODEC));
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<SimpleFluidContent>> FLUID_HANDLER =
            COMPONENTS.registerComponentType("fluid_handler", builder -> builder.persistent(SimpleFluidContent.CODEC).networkSynchronized(SimpleFluidContent.STREAM_CODEC));
}

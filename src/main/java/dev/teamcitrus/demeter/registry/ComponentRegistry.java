package dev.teamcitrus.demeter.registry;

import dev.teamcitrus.demeter.Demeter;
import dev.teamcitrus.demeter.component.QualityDataComponent;
import dev.teamcitrus.demeter.component.IrrigationLevelComponent;
import net.minecraft.core.component.DataComponentType;
import net.neoforged.neoforge.fluids.SimpleFluidContent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ComponentRegistry {
    public static final DeferredRegister.DataComponents COMPONENTS = DeferredRegister.createDataComponents(Demeter.MODID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<QualityDataComponent>> QUALITY =
            COMPONENTS.registerComponentType("quality", builder -> builder.persistent(QualityDataComponent.CODEC).networkSynchronized(QualityDataComponent.STREAM_CODEC));
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<IrrigationLevelComponent>> WATERING_CAN_LEVEL =
            COMPONENTS.registerComponentType("watering_can_level", builder -> builder.persistent(IrrigationLevelComponent.CODEC).networkSynchronized(IrrigationLevelComponent.STREAM_CODEC));
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<SimpleFluidContent>> FLUID =
            COMPONENTS.registerComponentType("fluids", builder -> builder.persistent(SimpleFluidContent.CODEC).networkSynchronized(SimpleFluidContent.STREAM_CODEC));
}

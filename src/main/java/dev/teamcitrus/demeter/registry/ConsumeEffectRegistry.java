package dev.teamcitrus.demeter.registry;

import dev.teamcitrus.demeter.Demeter;
import dev.teamcitrus.demeter.consumable.ClearRandomNegativeEffect;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.consume_effects.ConsumeEffect;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ConsumeEffectRegistry {
    public static final DeferredRegister<ConsumeEffect.Type<?>> TYPE = DeferredRegister.create(BuiltInRegistries.CONSUME_EFFECT_TYPE, Demeter.MODID);

    public static final DeferredHolder<ConsumeEffect.Type<?>, ConsumeEffect.Type<ClearRandomNegativeEffect>> CLEAR_RANDOM_NEGATIVE =
            TYPE.register("clear_random_negative_effect", () -> new ConsumeEffect.Type<>(ClearRandomNegativeEffect.CODEC, ClearRandomNegativeEffect.STREAM_CODEC));
}

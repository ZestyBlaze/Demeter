package dev.teamcitrus.demeter.registry;

import com.mojang.serialization.MapCodec;
import dev.teamcitrus.demeter.Demeter;
import dev.teamcitrus.demeter.glm.QualityLootModifier;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class LootModifierRegistry {
    public static final DeferredRegister<MapCodec<? extends IGlobalLootModifier>> LOOT_MODIFIERS = DeferredRegister.create(NeoForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, Demeter.MODID);

    public static final DeferredHolder<MapCodec<? extends IGlobalLootModifier>, MapCodec<QualityLootModifier>> QUALITY_DROPS = LOOT_MODIFIERS.register("quality_drops", QualityLootModifier.CODEC);
}

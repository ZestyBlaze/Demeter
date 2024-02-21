package dev.teamcitrus.betterfarms.registry;

import com.mojang.serialization.Codec;
import dev.teamcitrus.betterfarms.BetterFarms;
import dev.teamcitrus.betterfarms.data.QualityLootModifier;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class LootModifierRegistry {
    public static final DeferredRegister<Codec<? extends IGlobalLootModifier>> LOOT_MODIFIERS = DeferredRegister.create(NeoForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, BetterFarms.MODID);

    public static final DeferredHolder<Codec<? extends IGlobalLootModifier>, Codec<QualityLootModifier>> QUALITY_DROPS = LOOT_MODIFIERS.register("quality_drops", QualityLootModifier.CODEC);
}

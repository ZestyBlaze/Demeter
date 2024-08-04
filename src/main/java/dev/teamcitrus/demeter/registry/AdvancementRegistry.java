package dev.teamcitrus.demeter.registry;

import dev.teamcitrus.demeter.Demeter;
import dev.teamcitrus.demeter.advancement.AnimalBrushedTrigger;
import dev.teamcitrus.demeter.advancement.AnimalLoveMaxTrigger;
import dev.teamcitrus.demeter.advancement.AnimalPetTrigger;
import dev.teamcitrus.demeter.advancement.MiraclePotionTrigger;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AdvancementRegistry {
    public static final DeferredRegister<CriterionTrigger<?>> CRITERION = DeferredRegister.create(BuiltInRegistries.TRIGGER_TYPES, Demeter.MODID);
    public static DeferredHolder<CriterionTrigger<?>, AnimalBrushedTrigger> BRUSHED = CRITERION.register("brush_animal", AnimalBrushedTrigger::new);
    public static DeferredHolder<CriterionTrigger<?>, AnimalBrushedTrigger> SPITEFUL_BRUSHED = CRITERION.register("spiteful_brushed_animal", AnimalBrushedTrigger::new);
    public static DeferredHolder<CriterionTrigger<?>, AnimalPetTrigger> PET = CRITERION.register("pet_animal", AnimalPetTrigger::new);
    public static DeferredHolder<CriterionTrigger<?>, AnimalLoveMaxTrigger> LOVE_MAX = CRITERION.register("love_max", AnimalLoveMaxTrigger::new);
    public static DeferredHolder<CriterionTrigger<?>, MiraclePotionTrigger> USE_MIRACLE_POTION = CRITERION.register("use_miracle_potion", MiraclePotionTrigger::new);
    public static DeferredHolder<CriterionTrigger<?>, MiraclePotionTrigger> USE_MIRACLE_ON_FROG = CRITERION.register("use_miracle_on_frog", MiraclePotionTrigger::new);
}

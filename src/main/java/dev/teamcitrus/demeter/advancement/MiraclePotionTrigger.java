package dev.teamcitrus.demeter.advancement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.teamcitrus.demeter.registry.AdvancementRegistry;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.server.level.ServerPlayer;

import java.util.Optional;

public class MiraclePotionTrigger extends SimpleCriterionTrigger<MiraclePotionTrigger.TriggerInstance> {
    @Override
    public Codec<TriggerInstance> codec() {
        return TriggerInstance.CODEC;
    }

    public void trigger(ServerPlayer player) {
        this.trigger(player, triggerInstance -> true);
    }

    public record TriggerInstance(
            Optional<ContextAwarePredicate> player
    ) implements SimpleCriterionTrigger.SimpleInstance {
        public static final Codec<TriggerInstance> CODEC = RecordCodecBuilder.create(
                func -> func.group(
                        EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(TriggerInstance::player)
                ).apply(func, TriggerInstance::new)
        );

        public static Criterion<TriggerInstance> usePotion() {
            return AdvancementRegistry.USE_MIRACLE_POTION.get().createCriterion(
                    new TriggerInstance(Optional.empty())
            );
        }

        public static Criterion<TriggerInstance> usePotionOnFrog() {
            return AdvancementRegistry.USE_MIRACLE_ON_FROG.get().createCriterion(
                    new TriggerInstance(Optional.empty())
            );
        }
    }
}

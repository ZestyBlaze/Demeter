package dev.teamcitrus.demeter.advancement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.teamcitrus.demeter.registry.AdvancementRegistry;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.Optional;

public class AnimalLoveMaxTrigger extends SimpleCriterionTrigger<AnimalLoveMaxTrigger.TriggerInstance> {
    @Override
    public Codec<TriggerInstance> codec() {
        return TriggerInstance.CODEC;
    }

    public void trigger(Player player) {
        if (player instanceof ServerPlayer serverPlayer) {
            this.trigger(serverPlayer, triggerInstance -> true);
        }
    }

    public record TriggerInstance(
            Optional<ContextAwarePredicate> player
    ) implements SimpleCriterionTrigger.SimpleInstance {
        public static final Codec<TriggerInstance> CODEC = RecordCodecBuilder.create(
                func -> func.group(
                        EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(TriggerInstance::player)
                ).apply(func, TriggerInstance::new)
        );

        public static Criterion<TriggerInstance> loveMax() {
            return AdvancementRegistry.LOVE_MAX.get().createCriterion(
                    new TriggerInstance(Optional.empty())
            );
        }
    }
}

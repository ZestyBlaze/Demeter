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

public class AnimalPetTrigger extends SimpleCriterionTrigger<AnimalPetTrigger.PetTrigger> {
    @Override
    public Codec<PetTrigger> codec() {
        return PetTrigger.CODEC;
    }

    public void trigger(Player player) {
        if (player instanceof ServerPlayer serverPlayer) {
            this.trigger(serverPlayer, triggerInstance -> true);
        }
    }

    public record PetTrigger(
            Optional<ContextAwarePredicate> player
    ) implements SimpleCriterionTrigger.SimpleInstance {
        public static final Codec<PetTrigger> CODEC = RecordCodecBuilder.create(
                func -> func.group(
                        EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(PetTrigger::player)
                ).apply(func, PetTrigger::new)
        );

        public static Criterion<PetTrigger> pet() {
            return AdvancementRegistry.PET.get().createCriterion(
                    new PetTrigger(Optional.empty())
            );
        }
    }
}

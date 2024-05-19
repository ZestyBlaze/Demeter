package dev.teamcitrus.betterfarms.util;

import dev.teamcitrus.betterfarms.BetterFarms;
import dev.teamcitrus.betterfarms.attachment.AnimalAttachment;
import dev.teamcitrus.betterfarms.data.AnimalStats;
import dev.teamcitrus.betterfarms.data.StatsRegistry;
import dev.teamcitrus.betterfarms.registry.AttachmentRegistry;
import dev.teamcitrus.citruslib.reload.DynamicHolder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.animal.Animal;

public class AnimalUtil {
    public static AnimalAttachment getAnimalData(Animal animal) {
        return animal.getData(AttachmentRegistry.ANIMAL);
    }

    public static DynamicHolder<AnimalStats> getStats(Animal animal) {
        return StatsRegistry.INSTANCE.holder(BuiltInRegistries.ENTITY_TYPE.getKey(animal.getType()));
    }

    public static boolean statsContains(Animal animal) {
        return StatsRegistry.INSTANCE.getKeys().contains(BuiltInRegistries.ENTITY_TYPE.getKey(animal.getType()));
    }

    public static boolean isAnimalHappy(Animal animal) {
        //TODO: Will be an equation that calculates health, hunger, warmth and love levels for a "happy" level
        return getAnimalData(animal).getLove() >= 80;
    }

    public static AnimalAttachment.AnimalGenders getGender(Animal animal) {
        return getAnimalData(animal).getGender();
    }

    public static boolean areOppositeGenders(Animal animal1, Animal animal2) {
        return getGender(animal1) != getGender(animal2);
    }

    public static void handleBirth(Animal self, ServerLevel serverLevel, Animal otherEntity) {
        try {
            DynamicHolder<AnimalStats> stats = getStats(self);
            int numberOfTimes = serverLevel.random.nextIntBetweenInclusive(stats.get().minChildrenPerBirth(), stats.get().maxChildrenPerBirth());
            breedMultiple(self, serverLevel, otherEntity, numberOfTimes);
        } catch (IllegalArgumentException e) {
            BetterFarms.LOGGER.error(Component.translatable("error.betterfarms.maxhighermin").getString());
        }
    }

    public static void breedMultiple(Animal self, ServerLevel serverLevel, Animal otherEntity, int maxChildren) {
        for (int i = 0; i < serverLevel.random.nextInt(maxChildren) + 1; i++) {
            self.spawnChildFromBreeding(serverLevel, otherEntity);
        }
    }
}

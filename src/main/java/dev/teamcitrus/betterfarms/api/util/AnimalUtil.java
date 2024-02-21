package dev.teamcitrus.betterfarms.api.util;

import dev.teamcitrus.betterfarms.BetterFarms;
import dev.teamcitrus.betterfarms.attachment.AnimalAttachment;
import dev.teamcitrus.betterfarms.data.BFStatsManager;
import dev.teamcitrus.betterfarms.registry.AttachmentRegistry;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.animal.Animal;

public class AnimalUtil {
    public static boolean isAnimalHappy(Animal animal) {
        return true;
        //return animal.getComponent(CapabilityRegistry.ANIMAL).getHappiness() >= BetterFaunaConfig.animalHappinessMin.get(); TODO: Will be an equation that calculates health, hunger, warmth and love levels for a "happy" level
    }

    public static AnimalAttachment.AnimalGenders getGender(Animal animal) {
        return animal.getData(AttachmentRegistry.ANIMAL).getGender();
    }

    public static boolean areOppositeGenders(Animal animal1, Animal animal2) {
        return getGender(animal1) != getGender(animal2);
    }

    public static void handleBirth(Animal self, ServerLevel serverLevel, Animal otherEntity) {
        if (BFStatsManager.getStats(self).maxChildrenPerBirth() > 1) {
            breedMultiple(self, serverLevel, otherEntity, BFStatsManager.getStats(self).maxChildrenPerBirth());
        } else if (BFStatsManager.getStats(self).maxChildrenPerBirth() == 1) {
            self.spawnChildFromBreeding(serverLevel, otherEntity);
        } else {
            BetterFarms.LOGGER.error("An error has occurred when a {} attempted to give birth as there were 0 'maxChildrenPerBirth' specified", self.getName().getString());
        }
    }

    public static void breedMultiple(Animal self, ServerLevel serverLevel, Animal otherEntity, int maxChildren) {
        for (int i = 0; i < serverLevel.random.nextInt(maxChildren) + 1; i++) {
            self.spawnChildFromBreeding(serverLevel, otherEntity);
        }
    }
}

package dev.teamcitrus.betterfarms.util;

import dev.teamcitrus.betterfarms.BetterFarms;
import dev.teamcitrus.betterfarms.data.BFStatsListener;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.animal.Animal;

public class AnimalUtil {
    public static boolean isAnimalHappy(Animal animal) {
        return true;
        //return animal.getComponent(CapabilityRegistry.ANIMAL).getHappiness() >= BetterFaunaConfig.animalHappinessMin.get(); TODO: Will be an equation that calculates health, hunger, warmth and love levels for a "happy" level
    }

    public static void handleBirth(Animal self, ServerLevel serverLevel, Animal otherEntity) {
        if (BFStatsListener.getManager(self).getMaxChildrenPerBirth() > 1) {
            breedMultiple(self, serverLevel, otherEntity, BFStatsListener.getManager(self).getMaxChildrenPerBirth());
        } else if (BFStatsListener.getManager(self).getMaxChildrenPerBirth() == 1) {
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

package dev.teamcitrus.betterfarms.util;

import dev.teamcitrus.betterfarms.config.BetterFarmsConfig;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.entity.animal.Rabbit;

public class AnimalUtil {
    public static boolean isAnimalHappy(Animal animal) {
        return true;
        //return animal.getComponent(CapabilityRegistry.ANIMAL).getHappiness() >= BetterFaunaConfig.animalHappinessMin.get(); TODO: Will be an equation that calculates health, hunger, warmth and love levels for a "happy" level
    }

    public static void handleBirth(Animal self, ServerLevel serverLevel, Animal otherEntity) {
        if (self instanceof Pig || self instanceof Rabbit) {
            breedMultiple(self, serverLevel, otherEntity, self instanceof Pig ? BetterFarmsConfig.pigsMultipleChildrenMax.get() : BetterFarmsConfig.rabbitsMultipleChildrenMax.get());
        } else {
            self.spawnChildFromBreeding(serverLevel, otherEntity);
        }
    }

    public static void breedMultiple(Animal self, ServerLevel serverLevel, Animal otherEntity, int maxChildren) {
        for (int i = 0; i < serverLevel.random.nextInt(maxChildren) + 1; i++) {
            self.spawnChildFromBreeding(serverLevel, otherEntity);
        }
    }
}

package dev.teamcitrus.demeter.util;

import dev.teamcitrus.citruslib.util.ModUtil;
import dev.teamcitrus.demeter.Demeter;
import dev.teamcitrus.demeter.attachment.AnimalAttachment;
import dev.teamcitrus.demeter.compat.AccessoriesCompat;
import dev.teamcitrus.demeter.data.maps.DemeterDataMaps;
import dev.teamcitrus.demeter.datamaps.AnimalData;
import dev.teamcitrus.demeter.registry.AttachmentRegistry;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.animal.Animal;

@SuppressWarnings("deprecation")
public class AnimalUtil {
    public static AnimalAttachment getAnimalData(Animal animal) {
        return animal.getData(AttachmentRegistry.ANIMAL);
    }

    public static AnimalData getStats(Animal animal) {
        return animal.getType().builtInRegistryHolder().getData(DemeterDataMaps.ANIMAL_DATA);
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
            AnimalData stats = getStats(self);
            if (stats != null) {
                int numberOfTimes = serverLevel.random.nextIntBetweenInclusive(stats.minChildrenPerBirth(), stats.maxChildrenPerBirth());
                if (self.getLoveCause() != null) {
                    if (ModUtil.isModInstalled("accessories")) {
                        if (AccessoriesCompat.isWearing(self.getLoveCause(), AccessoriesCompat.Items.BREEDING_CHARM.get())) {
                            numberOfTimes += 2;
                        }
                    }
                }
                birth(self, serverLevel, otherEntity, numberOfTimes);
                getAnimalData(self).setLove(100);
            }
        } catch (IllegalArgumentException e) {
            Demeter.LOGGER.error(Component.translatable("error.demeter.maxhighermin").getString());
        }
    }

    public static void birth(Animal self, ServerLevel serverLevel, Animal otherEntity, int maxChildren) {
        for (int i = 0; i < maxChildren; i++) {
            self.spawnChildFromBreeding(serverLevel, otherEntity);
        }
    }
}

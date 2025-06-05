package dev.teamcitrus.demeter.util;

import dev.teamcitrus.citruslib.util.ModUtil;
import dev.teamcitrus.demeter.Demeter;
import dev.teamcitrus.demeter.attachment.AnimalAttachment;
import dev.teamcitrus.demeter.compat.curios.CuriosCompat;
import dev.teamcitrus.demeter.config.DemeterConfig;
import dev.teamcitrus.demeter.datamaps.AnimalData;
import dev.teamcitrus.demeter.duck.AnimalSexes;
import dev.teamcitrus.demeter.registry.AttachmentRegistry;
import dev.teamcitrus.demeter.registry.DataMapRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.animal.Animal;

@SuppressWarnings("deprecation")
public class AnimalUtil {
    public static AnimalAttachment getAnimalData(Animal animal) {
        return animal.getData(AttachmentRegistry.ANIMAL);
    }

    public static AnimalData getStats(Animal animal) {
        return animal.getType().builtInRegistryHolder().getData(DataMapRegistry.ANIMAL_DATA);
    }

    public static boolean isAnimalHappy(Animal animal) {
        return getAnimalData(animal).getLove() >= 80
                && getAnimalData(animal).hasBeenFedToday();
    }

    public static AnimalSexes getSex(Animal animal) {
        return getAnimalData(animal).getSex();
    }

    public static boolean areOppositeSexes(Animal animal1, Animal animal2) {
        return getSex(animal1) != getSex(animal2);
    }

    public static void handleBirth(Animal self, ServerLevel serverLevel, Animal otherEntity) {
        try {
            AnimalData stats = getStats(self);
            if (stats != null) {
                int numberOfTimes = serverLevel.random.nextIntBetweenInclusive(stats.minChildrenPerBirth(), stats.maxChildrenPerBirth());
                if (self.getLoveCause() != null) {
                    if (ModUtil.isModInstalled("curios")) {
                        if (CuriosCompat.isWearing(self.getLoveCause(), CuriosCompat.Items.BREEDING_CHARM.get())) {
                            numberOfTimes += 2;
                        }
                    }
                }
                birth(self, serverLevel, otherEntity, numberOfTimes);
                getAnimalData(self).setLove(100);
                getAnimalData(self).setDownPeriod(DemeterConfig.pregnancyDownPeriod.get());
            }
        } catch (IllegalArgumentException e) {
            Demeter.LOGGER.error(Component.translatable("error.demeter.maxhighermin", BuiltInRegistries.ENTITY_TYPE.getKey(self.getType())).getString());
            if (self.getLoveCause() != null) {
                self.getLoveCause().sendSystemMessage(Component.translatable("message.demeter.birth.fail"), true);
            }
        }
    }

    public static void birth(Animal self, ServerLevel serverLevel, Animal otherEntity, int maxChildren) {
        for (int i = 0; i < maxChildren; i++) {
            self.spawnChildFromBreeding(serverLevel, otherEntity);
        }
    }
}

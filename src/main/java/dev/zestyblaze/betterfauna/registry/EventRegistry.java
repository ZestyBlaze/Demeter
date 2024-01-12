package dev.zestyblaze.betterfauna.registry;

import dev.zestyblaze.betterfauna.component.animal.AnimalComponent;
import dev.zestyblaze.betterfauna.entity.ai.DropsProductGoal;
import dev.zestyblaze.betterfauna.event.EntityAddedToLevelEvent;
import dev.zestyblaze.betterfauna.event.NewDayEvent;
import net.fabricmc.fabric.api.entity.event.v1.EntitySleepEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.level.entity.EntityTypeTest;

public class EventRegistry {
    public static void register() {
        EntitySleepEvents.ALLOW_SLEEP_TIME.register((player, sleepingPos, vanillaResult) -> InteractionResult.SUCCESS);
        ServerTickEvents.END_WORLD_TICK.register(world -> {
            if(!world.isClientSide() && world.getDayTime() % 24000 == 1) {
                NewDayEvent.NEW_DAY.invoker().newDay(world);
            }
        });
        NewDayEvent.NEW_DAY.register(level -> {
            level.getEntities(EntityTypeTest.forClass(Cow.class), (LivingEntity::isAlive)).forEach(cow -> {
                cow.getComponent(ComponentRegistry.MILK).setBeenMilked(false);
            });
        });
        NewDayEvent.NEW_DAY.register(level -> {
            level.getEntities(EntityTypeTest.forClass(Animal.class), animal -> ComponentRegistry.PREGNANCY.maybeGet(animal).isPresent()).forEach(animal -> {
                animal.getComponent(ComponentRegistry.PREGNANCY).onNewDay();
            });
        });
        EntityAddedToLevelEvent.ON_ADD.register((entity, level, loadedFromDisk) -> {
            if (!loadedFromDisk && entity instanceof Animal animal) {
                animal.getComponent(ComponentRegistry.ANIMAL).setGender(
                        AnimalComponent.Gender.values()[entity.level().random.nextInt(AnimalComponent.Gender.values().length - 1)]
                );
            }
            if (entity instanceof Pig pig) {
                pig.goalSelector.addGoal(4, new DropsProductGoal(pig));
            }
            return true;
        });
    }
}

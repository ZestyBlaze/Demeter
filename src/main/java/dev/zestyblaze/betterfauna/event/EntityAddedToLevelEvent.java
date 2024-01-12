package dev.zestyblaze.betterfauna.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

public class EntityAddedToLevelEvent {
    public static Event<AddedToWorld> ON_ADD = EventFactory.createArrayBacked(AddedToWorld.class, (entity, level, loadedFromDisk) -> true, callbacks -> (entity, level, loadedFromDisk) -> {
        for (AddedToWorld callback : callbacks) {
            if (callback.onAddedToWorld(entity, level, loadedFromDisk)) {
                return true;
            }
        }
        return false;
    });

    @FunctionalInterface
    public interface AddedToWorld {
        boolean onAddedToWorld(Entity entity, Level level, boolean loadedFromDisk);
    }
}

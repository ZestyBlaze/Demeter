package dev.zestyblaze.betterfauna.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.server.level.ServerLevel;

public class NewDayEvent {
    public static final Event<NewDay> NEW_DAY = EventFactory.createArrayBacked(NewDay.class, callbacks -> level -> {
        for (NewDay callback : callbacks) {
            callback.newDay(level);
        }
    });

    @FunctionalInterface
    public interface NewDay {
        void newDay(ServerLevel level);
    }
}

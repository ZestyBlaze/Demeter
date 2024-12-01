package dev.teamcitrus.demeter.event.internal;

import net.minecraft.server.level.ServerLevel;
import net.neoforged.neoforge.event.level.LevelEvent;

public class NewYearEvent extends LevelEvent {
    public NewYearEvent(ServerLevel level) {
        super(level);
    }

    @Override
    public ServerLevel getLevel() {
        return (ServerLevel) super.getLevel();
    }
}

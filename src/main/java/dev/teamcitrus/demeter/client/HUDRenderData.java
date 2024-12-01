package dev.teamcitrus.demeter.client;

import dev.teamcitrus.demeter.util.TimeUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;

public abstract class HUDRenderData {
    public boolean isEnabled(Minecraft mc) {
        return true;
    }

    @Nullable
    public ResourceLocation getTexture(Minecraft mc) {
        return null;
    }

    public abstract Component getHeader(Minecraft mc);

    public String getFooter(Minecraft mc) {
        String time = TimeUtil.formatTime((int) TimeUtil.getTimeOfDay(mc.level.getDayTime()));
        return "(" + TimeUtil.shortName(TimeUtil.getWeekday(mc.level.getDayTime())) + ")" + "  " + time;
    }

    public int getX() {
        return 0;
    }

    public int getY() {
        return 0;
    }

    public int getClockX() {
        return 42;
    }

    public int getClockY() {
        return 23;
    }
}

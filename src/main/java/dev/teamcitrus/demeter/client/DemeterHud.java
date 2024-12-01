package dev.teamcitrus.demeter.client;

import dev.teamcitrus.demeter.config.DemeterConfig;
import dev.teamcitrus.demeter.util.TimeUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
 
public class DemeterHud extends HUDRenderData {
    @Override
    public Component getHeader(Minecraft mc) {
        int days = TimeUtil.getElapsedDays(mc.level.getDayTime());
        int years = TimeUtil.getElapsedYears(mc.level.getDayTime());
        if (years >= 1) {
            days = days - (years * TimeUtil.YEAR_DAYS);
        }
        return Component.translatable("Year %s, Day %s", 1 + years, 1 + days);
    }

    @Override
    public int getX() {
        return -20;
    }

    @Override
    public boolean isEnabled(Minecraft mc) {
        return DemeterConfig.displayClockInHUD.get();
    }
}

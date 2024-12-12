package dev.teamcitrus.demeter.attachment;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.entity.animal.Sheep;

public class SheepAttachment {
    private int daysSinceSheared;

    public static final Codec<SheepAttachment> CODEC = RecordCodecBuilder.create(func -> func.group(
            Codec.INT.fieldOf("daysSinceSheared").forGetter(s -> s.daysSinceSheared)
    ).apply(func, SheepAttachment::new));

    public SheepAttachment() {
        this(0);
    }

    public SheepAttachment(int daysSinceSheared) {
        this.daysSinceSheared = daysSinceSheared;
    }

    public void onNewDay(Sheep self) {
        daysSinceSheared += 1;
        if (daysSinceSheared >= 7) {
            self.setSheared(false);
            daysSinceSheared = 0;
        }
    }
}

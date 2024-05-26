package dev.teamcitrus.demeter.attachment;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

/**
 * A smaller attachment that attaches itself onto all mobs that can be milked to track if it's been milked each day
 */
public class MilkAttachment {
    public static final Codec<MilkAttachment> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.BOOL.fieldOf("hasBeenMilked").forGetter(o -> o.hasBeenMilked)
    ).apply(instance, MilkAttachment::new));

    private boolean hasBeenMilked;

    public MilkAttachment() {
        this(false);
    }

    public MilkAttachment(boolean value) {
        this.hasBeenMilked = value;
    }

    public boolean getHasBeenMilked() {
        return hasBeenMilked;
    }

    public void setHasBeenMilked(boolean value) {
        hasBeenMilked = value;
    }
}

package dev.teamcitrus.betterfarms.attachment;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

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

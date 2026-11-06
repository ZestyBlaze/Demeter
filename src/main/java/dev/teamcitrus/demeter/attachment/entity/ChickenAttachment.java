package dev.teamcitrus.demeter.attachment.entity;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;

public class ChickenAttachment {
    private boolean isNesting;
    private BlockPos targetPos;

    public static final Codec<ChickenAttachment> CODEC = RecordCodecBuilder.create(func -> func.group(
            Codec.BOOL.fieldOf("isNesting").forGetter(o -> o.isNesting),
            BlockPos.CODEC.fieldOf("targetPos").forGetter(o -> o.targetPos)
    ).apply(func, ChickenAttachment::new));

    public ChickenAttachment() {
        this(false, BlockPos.ZERO);
    }

    public ChickenAttachment(boolean value, BlockPos targetPos) {
        this.isNesting = value;
        this.targetPos = targetPos;
    }

    public boolean isNesting() {
        return isNesting;
    }

    public void setNesting(boolean nesting) {
        isNesting = nesting;
    }

    public BlockPos getTargetPos() {
        return targetPos;
    }

    public void setTargetPos(BlockPos targetPos) {
        this.targetPos = targetPos;
    }
}

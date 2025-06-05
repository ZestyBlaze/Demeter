package dev.teamcitrus.demeter.util;

import net.minecraft.server.level.ChunkHolder;
import net.minecraft.world.level.chunk.LevelChunk;

public record ChunkAndHolder(LevelChunk chunk, ChunkHolder holder) {
}

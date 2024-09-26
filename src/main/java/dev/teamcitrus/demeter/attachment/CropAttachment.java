package dev.teamcitrus.demeter.attachment;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class CropAttachment {
    public static final Codec<CropAttachment> CODEC = RecordCodecBuilder.create(func -> func.group(
            Codec.unboundedMap(Codec.STRING.flatXmap(
                            s -> {
                                String[] parts = s.split(",");
                                if (parts.length == 3) {
                                    try {
                                        return DataResult.success(new BlockPos(
                                                Integer.parseInt(parts[0]),
                                                Integer.parseInt(parts[1]),
                                                Integer.parseInt(parts[2])
                                        ));
                                    } catch (NumberFormatException e) {
                                        return DataResult.error(() -> "Invalid input: " + s + "(" + e.getMessage() + ")");
                                    }
                                }
                                return DataResult.error(() -> "Invalid input: " + s);
                            },
                            p -> DataResult.success(p.getX() + "," + p.getY() + "," + p.getZ())
                    ),
                    Codec.INT).xmap(HashMap::new, Function.identity()).fieldOf("locations").forGetter(o -> (HashMap<BlockPos, Integer>) o.locations)
    ).apply(func, CropAttachment::new));

    private final Map<BlockPos, Integer> locations;

    public CropAttachment() {
        this(new HashMap<>());
    }

    public CropAttachment(Map<BlockPos, Integer> locations) {
        this.locations = locations;
    }

    public void addLocation(BlockPos pos) {
        if (!locations.containsKey(pos)) {
            locations.put(pos, 0);
        }
    }

    public void removeLocation(BlockPos pos) {
        locations.remove(pos);
    }

    public Map<BlockPos, Integer> getLocations() {
        return locations;
    }

    public void incrementDays(BlockPos pos) {
        if (locations.containsKey(pos)) {
            int old = locations.get(pos);
            locations.replace(pos, ++old);
        }
    }

    public int getDays(BlockPos pos) {
        return locations.get(pos);
    }
}

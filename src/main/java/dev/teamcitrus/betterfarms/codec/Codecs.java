package dev.teamcitrus.betterfarms.codec;

import com.mojang.serialization.Codec;
import net.minecraft.util.ExtraCodecs;

import java.util.Locale;

/**
 * Taken from Placebo with consent of Shadows
 * @author Shadows of Fire
 * @link https://github.com/Shadows-of-Fire/Placebo/tree/1.20.4
 */
public class Codecs {
    public static <E extends Enum<E>> Codec<E> enumCodec(Class<E> clazz) {
        return ExtraCodecs.stringResolverCodec(e -> e.name().toLowerCase(Locale.ROOT), name -> Enum.valueOf(clazz, name.toUpperCase(Locale.ROOT)));
    }
}

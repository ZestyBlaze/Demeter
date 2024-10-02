package dev.teamcitrus.demeter.data.loaders;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public record Names(boolean replace, List<String> names) {
    public static final Codec<Names> CODEC = RecordCodecBuilder.create(func -> func.group(
            Codec.BOOL.optionalFieldOf("replace", false).forGetter(Names::replace),
            Codec.STRING.listOf().fieldOf("names").forGetter(Names::names)
    ).apply(func, Names::new));

    public static List<String> merge(List<Names> raws) {
        Set<String> set = new HashSet<>();
        for (Names raw : raws) {
            if (raw.replace) {
                set = new HashSet<>();
            }
            set.addAll(raw.names());
        }

        List<String> out = new ArrayList<>(set.size());
        out.addAll(set);
        return out;
    }
}

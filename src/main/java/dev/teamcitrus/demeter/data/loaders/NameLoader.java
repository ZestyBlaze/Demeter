package dev.teamcitrus.demeter.data.loaders;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.teamcitrus.demeter.Demeter;
import dev.teamcitrus.demeter.attachment.AnimalAttachment.AnimalGenders;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import org.antlr.v4.runtime.misc.MultiMap;

import java.util.*;

public class NameLoader extends SimpleJsonResourceReloadListener {
    public static final MultiMap<AnimalGenders, String> nameList = new MultiMap<>();

    public NameLoader() {
        super(new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create(), "demeter/names");
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> map, ResourceManager resourceManager,
                         ProfilerFiller profilerFiller) {
        nameList.clear();
        for (Map.Entry<ResourceLocation, JsonElement> entry : map.entrySet()) {
            if (Arrays.stream(AnimalGenders.values()).anyMatch(gender ->
                    gender.name().toLowerCase(Locale.ROOT).equals(entry.getKey().getPath()))) {
                Names.CODEC.parse(
                                JsonOps.INSTANCE, entry.getValue()
                        ).resultOrPartial(error -> Demeter.LOGGER.error("Failed to parse name data"))
                        .ifPresent(value -> {
                            List<String> list = nameList.computeIfAbsent(getGenderFromKey(entry.getKey()), k ->
                                    new ArrayList<>());
                            list.addAll(value.names());
                        });
            }
        }
    }

    private AnimalGenders getGenderFromKey(ResourceLocation key) {
        return AnimalGenders.valueOf(key.getPath().toUpperCase(Locale.ROOT));
    }

    public record Names(List<String> names) {
        public static final Codec<Names> CODEC = RecordCodecBuilder.create(func -> func.group(
                Codec.STRING.listOf().fieldOf("names").forGetter(Names::names)
        ).apply(func, Names::new));
    }
}

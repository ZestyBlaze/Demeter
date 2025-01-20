package dev.teamcitrus.demeter.data.loaders;

import dev.teamcitrus.citruslib.reload.MergeableCodecDataManager;
import dev.teamcitrus.demeter.duck.AnimalSexes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.*;

public class NamesLoader extends MergeableCodecDataManager<Names, List<String>> {
    public static final Map<AnimalSexes, List<String>> NAME_LIST = new HashMap<>();

    public NamesLoader() {
        super("demeter/names", Names.CODEC, Names::merge);
    }

    @Override
    protected void apply(Map<ResourceLocation, List<String>> processedData, ResourceManager resourceManager, ProfilerFiller profiler) {
        super.apply(processedData, resourceManager, profiler);

        this.data.forEach((id, string) -> {
            if (Arrays.stream(AnimalSexes.values()).anyMatch(
                    sex -> sex.name().toLowerCase(Locale.ROOT).equals(id.getPath()))) {
                AnimalSexes sex = getSexFromKey(id);
                NAME_LIST.put(sex, string);
            }
        });
    }

    private AnimalSexes getSexFromKey(ResourceLocation key) {
        return AnimalSexes.valueOf(key.getPath().toUpperCase(Locale.ROOT));
    }
}

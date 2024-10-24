package dev.teamcitrus.demeter.data.loaders;

import dev.teamcitrus.citruslib.reload.MergeableCodecDataManager;
import dev.teamcitrus.demeter.attachment.AnimalAttachment.AnimalGenders;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import org.antlr.v4.runtime.misc.MultiMap;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class NamesLoader extends MergeableCodecDataManager<Names, List<String>> {
    public static final MultiMap<AnimalGenders, String> NAME_LIST = new MultiMap<>();

    public NamesLoader() {
        super("demeter/names", Names.CODEC, Names::merge);
    }

    @Override
    protected void apply(Map<ResourceLocation, List<String>> processedData, ResourceManager resourceManager, ProfilerFiller profiler) {
        super.apply(processedData, resourceManager, profiler);

        this.data.forEach((id, string) -> {
            if (Arrays.stream(AnimalGenders.values()).anyMatch(
                    gender -> gender.name().toLowerCase(Locale.ROOT).equals(id.getPath()))) {
                AnimalGenders gender = getGenderFromKey(id);
                NAME_LIST.put(gender, string);
            }
        });
    }

    private AnimalGenders getGenderFromKey(ResourceLocation key) {
        return AnimalGenders.valueOf(key.getPath().toUpperCase(Locale.ROOT));
    }
}

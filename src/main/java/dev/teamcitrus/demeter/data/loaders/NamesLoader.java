package dev.teamcitrus.demeter.data.loaders;

import dev.teamcitrus.demeter.attachment.AnimalAttachment.AnimalGenders;
import dev.teamcitrus.demeter.data.MergeableCodecDataManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import org.antlr.v4.runtime.misc.MultiMap;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class NamesLoader extends MergeableCodecDataManager<Names, List<String>> {
    public static final MultiMap<AnimalGenders, String> nameList = new MultiMap<>();

    public NamesLoader(String folderName) {
        super(folderName, Names.CODEC, Names::merge);
    }

    @Override
    protected void apply(Map<ResourceLocation, List<String>> processedData, ResourceManager resourceManager, ProfilerFiller profiler) {
        super.apply(processedData, resourceManager, profiler);

        this.data.forEach((id, string) -> {
            if (Arrays.stream(AnimalGenders.values()).anyMatch(
                    gender -> gender.name().toLowerCase(Locale.ROOT).equals(id.getPath()))) {
                AnimalGenders gender = getGenderFromKey(id);
                nameList.put(gender, string);
            }
        });
    }

    private AnimalGenders getGenderFromKey(ResourceLocation key) {
        return AnimalGenders.valueOf(key.getPath().toUpperCase(Locale.ROOT));
    }
}

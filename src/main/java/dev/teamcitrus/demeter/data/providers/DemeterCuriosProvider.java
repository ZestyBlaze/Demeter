package dev.teamcitrus.demeter.data.providers;

import dev.teamcitrus.demeter.Demeter;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import top.theillusivec4.curios.api.CuriosDataProvider;
import top.theillusivec4.curios.api.CuriosSlotTypes.Preset;

import java.util.concurrent.CompletableFuture;

public class DemeterCuriosProvider extends CuriosDataProvider {
    public DemeterCuriosProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(Demeter.MODID, output, registries);
    }

    @Override
    public void generate(HolderLookup.Provider registries) {
        createEntities(Demeter.MODID).addPlayer().addPresetSlots(Preset.NECKLACE, Preset.CHARM);
    }
}

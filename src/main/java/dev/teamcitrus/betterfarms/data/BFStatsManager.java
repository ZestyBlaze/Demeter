package dev.teamcitrus.betterfarms.data;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.mojang.serialization.JsonOps;
import dev.teamcitrus.betterfarms.BetterFarms;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;

import java.util.HashMap;
import java.util.Map;

public class BFStatsManager extends SimpleJsonResourceReloadListener {
    public static final Map<EntityType<?>, AnimalStats> newMap = new HashMap<>();

    public BFStatsManager() {
        super(new GsonBuilder().setPrettyPrinting().create(), "betterfarms/stats");
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> map, ResourceManager pResourceManager, ProfilerFiller pProfiler) {
        newMap.clear();
        for (Map.Entry<ResourceLocation, JsonElement> entry : map.entrySet()) {
            if (BuiltInRegistries.ENTITY_TYPE.containsKey(entry.getKey())) {
                AnimalStats.CODEC.parse(
                        JsonOps.INSTANCE, entry.getValue())
                        .resultOrPartial(errorMsg -> BetterFarms.LOGGER.error("Failed to parse data json for {} due to: {}", entry.getKey(), errorMsg))
                        .ifPresent(value -> newMap.put(BuiltInRegistries.ENTITY_TYPE.get(entry.getKey()), value));
            }
        }
    }

    public static AnimalStats getStats(LivingEntity livingEntity) {
        return newMap.get(livingEntity.getType());
    }
}

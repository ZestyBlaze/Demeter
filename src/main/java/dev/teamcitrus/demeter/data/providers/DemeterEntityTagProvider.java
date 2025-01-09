package dev.teamcitrus.demeter.data.providers;

import dev.teamcitrus.demeter.Demeter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;

import java.util.concurrent.CompletableFuture;

public class DemeterEntityTagProvider extends EntityTypeTagsProvider {
    public static final TagKey<EntityType<?>> EATS_HAY = modTag("eats_hay");
    public static final TagKey<EntityType<?>> EATS_SLOP = modTag("eats_slop");

    public DemeterEntityTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider) {
        super(output, provider, Demeter.MODID);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(EATS_HAY).add(EntityType.COW, EntityType.HORSE, EntityType.DONKEY, EntityType.MULE);
        tag(EATS_SLOP).add(EntityType.PIG);
    }

    private static TagKey<EntityType<?>> modTag(String id) {
        return TagKey.create(Registries.ENTITY_TYPE, Demeter.id(id));
    }
}

package dev.teamcitrus.demeter.registry;

import dev.teamcitrus.demeter.Demeter;
import dev.teamcitrus.demeter.attachment.chunk.CropsInChunkAttachment;
import dev.teamcitrus.demeter.attachment.entity.AnimalAttachment;
import dev.teamcitrus.demeter.attachment.entity.ChickenAttachment;
import dev.teamcitrus.demeter.attachment.entity.SheepAttachment;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class AttachmentRegistry {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, Demeter.MODID);

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<AnimalAttachment>> ANIMAL = ATTACHMENT_TYPES.register("animal", () -> AttachmentType.builder(AnimalAttachment::new).serialize(AnimalAttachment.CODEC).build());
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<CropsInChunkAttachment>> CROPS_IN_CHUNK = ATTACHMENT_TYPES.register("crops_in_chunk", () -> AttachmentType.builder(() -> new CropsInChunkAttachment()).serialize(CropsInChunkAttachment.CODEC).build());
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<ChickenAttachment>> CHICKEN = ATTACHMENT_TYPES.register("chicken", () -> AttachmentType.builder(ChickenAttachment::new).serialize(ChickenAttachment.CODEC).build());
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<SheepAttachment>> SHEEP = ATTACHMENT_TYPES.register("sheep", () -> AttachmentType.builder(() -> new SheepAttachment()).serialize(SheepAttachment.CODEC).build());
}

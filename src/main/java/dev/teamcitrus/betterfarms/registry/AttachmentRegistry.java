package dev.teamcitrus.betterfarms.registry;

import dev.teamcitrus.betterfarms.BetterFarms;
import dev.teamcitrus.betterfarms.attachment.AnimalAttachment;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class AttachmentRegistry {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, BetterFarms.MODID);

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<AnimalAttachment>> ANIMAL = ATTACHMENT_TYPES.register("animal", () -> AttachmentType.builder(AnimalAttachment::new).serialize(AnimalAttachment.CODEC).build());
}

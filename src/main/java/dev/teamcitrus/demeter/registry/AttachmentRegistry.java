package dev.teamcitrus.demeter.registry;

import dev.teamcitrus.demeter.Demeter;
import dev.teamcitrus.demeter.attachment.AnimalAttachment;
import dev.teamcitrus.demeter.attachment.CropAttachment;
import dev.teamcitrus.demeter.attachment.MilkAttachment;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class AttachmentRegistry {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, Demeter.MODID);

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<AnimalAttachment>> ANIMAL = ATTACHMENT_TYPES.register("animal", () -> AttachmentType.builder(AnimalAttachment::new).serialize(AnimalAttachment.CODEC).build());
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<MilkAttachment>> MILK = ATTACHMENT_TYPES.register("milk", () -> AttachmentType.builder(() -> new MilkAttachment()).serialize(MilkAttachment.CODEC).build());
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<CropAttachment>> CROP = ATTACHMENT_TYPES.register("crop", () -> AttachmentType.builder(() -> new CropAttachment()).serialize(CropAttachment.CODEC).build());
}

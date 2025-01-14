package dev.teamcitrus.demeter.client.models.state;

import dev.teamcitrus.demeter.attachment.AnimalAttachment;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;

public class GenderRenderState extends LivingEntityRenderState {
    public AnimalAttachment.AnimalGenders gender;
}

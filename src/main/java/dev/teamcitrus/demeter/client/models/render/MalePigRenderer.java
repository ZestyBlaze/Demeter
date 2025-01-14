package dev.teamcitrus.demeter.client.models.render;

import dev.teamcitrus.demeter.Demeter;
import dev.teamcitrus.demeter.DemeterClient;
import dev.teamcitrus.demeter.client.models.model.MalePigModel;
import dev.teamcitrus.demeter.client.models.state.GenderRenderState;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Pig;

public class MalePigRenderer extends LivingEntityRenderer<Pig, GenderRenderState, MalePigModel> {
    public static final ResourceLocation MALE_PIG_LOCATION = Demeter.id("textures/entity/gender/male/pig");

    public MalePigRenderer(EntityRendererProvider.Context context) {
        super(context, new MalePigModel(context.bakeLayer(DemeterClient.MALE_PIG)), 0.25f);
    }

    @Override
    public ResourceLocation getTextureLocation(GenderRenderState genderRenderState) {
        return MALE_PIG_LOCATION;
    }

    @Override
    public GenderRenderState createRenderState() {
        return new GenderRenderState();
    }
}

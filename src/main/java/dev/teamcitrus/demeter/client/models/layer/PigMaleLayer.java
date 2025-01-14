package dev.teamcitrus.demeter.client.models.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.teamcitrus.demeter.DemeterClient;
import dev.teamcitrus.demeter.client.models.model.MalePigModel;
import dev.teamcitrus.demeter.client.models.render.MalePigRenderer;
import dev.teamcitrus.demeter.client.models.state.GenderRenderState;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;

public class PigMaleLayer extends RenderLayer<GenderRenderState, MalePigModel> {
    private final MalePigModel model;

    public PigMaleLayer(RenderLayerParent<GenderRenderState, MalePigModel> renderer, EntityModelSet modelSet) {
        super(renderer);
        this.model = new MalePigModel(modelSet.bakeLayer(DemeterClient.MALE_PIG));
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource multiBufferSource, int i, GenderRenderState genderRenderState, float v, float v1) {
        if (!genderRenderState.isInvisible) {
            VertexConsumer vertexconsumer = multiBufferSource.getBuffer(RenderType.entitySolid(MalePigRenderer.MALE_PIG_LOCATION));
            this.model.setupAnim(genderRenderState);
            this.model.renderToBuffer(poseStack, vertexconsumer, i, LivingEntityRenderer.getOverlayCoords(genderRenderState, 0.0f));
        }
    }
}

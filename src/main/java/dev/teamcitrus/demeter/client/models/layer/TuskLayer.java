package dev.teamcitrus.demeter.client.models.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.teamcitrus.demeter.Demeter;
import dev.teamcitrus.demeter.DemeterClient;
import dev.teamcitrus.demeter.attachment.AnimalAttachment;
import dev.teamcitrus.demeter.client.models.model.TuskModel;
import net.minecraft.client.model.PigModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.AgeableMobRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.PigRenderState;

public class TuskLayer extends RenderLayer<PigRenderState, PigModel> {
    private final TuskModel model;

    public TuskLayer(RenderLayerParent<PigRenderState, PigModel> renderer, EntityModelSet modelSet) {
        super(renderer);
        this.model = new TuskModel(modelSet.bakeLayer(DemeterClient.TUSKS));
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource multiBufferSource, int i, PigRenderState pigrenderState, float v, float v1) {
        if (!pigrenderState.isInvisible && !pigrenderState.isBaby) {
            if (pigrenderState.getRenderData(DemeterClient.KEY).equals(AnimalAttachment.AnimalGenders.MALE)) {
                VertexConsumer consumer = multiBufferSource.getBuffer(RenderType.entityCutoutNoCull(Demeter.id("textures/entity/layer/tusks.png")));
                ModelPart head = this.getParentModel().root().getChild("head");
                ModelPart tusks = this.model.tusks;
                tusks.copyFrom(head);
                tusks.render(poseStack, consumer, i, AgeableMobRenderer.getOverlayCoords(pigrenderState, 0.0f));
            }
        }
    }
}

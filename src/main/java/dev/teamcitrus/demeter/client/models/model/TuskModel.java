package dev.teamcitrus.demeter.client.models.model;

import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.RenderType;

public class TuskModel extends Model {
    public final ModelPart tusks;

    public TuskModel(ModelPart root) {
        super(root, RenderType::entityCutoutNoCull);
        this.tusks = root.getChild("tusks");
    }

    public static LayerDefinition createModel() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        root.addOrReplaceChild("tusks", CubeListBuilder.create()
                .texOffs(1, 0).addBox(-3.0f, 2.0f, -10.0f, 1.0f, 1.0f, 2.0f, new CubeDeformation(0.0f))
                .texOffs(1, 0).addBox(2.0f, 2.0f, -10.0f, 1.0f, 1.0f, 2.0f, new CubeDeformation(0.0f)), PartPose.offset(0.0f, 12.0f, -6.0f));
        return LayerDefinition.create(mesh, 6, 3);
    }
}

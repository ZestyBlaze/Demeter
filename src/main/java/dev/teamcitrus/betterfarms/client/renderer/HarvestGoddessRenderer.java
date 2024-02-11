package dev.teamcitrus.betterfarms.client.renderer;

import dev.teamcitrus.betterfarms.BetterFarms;
import dev.teamcitrus.betterfarms.BetterFarmsClient;
import dev.teamcitrus.betterfarms.entity.HarvestGoddess;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.resources.ResourceLocation;

public class HarvestGoddessRenderer extends HumanoidMobRenderer<HarvestGoddess, HumanoidModel<HarvestGoddess>> {
    public HarvestGoddessRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new HumanoidModel<>(pContext.bakeLayer(BetterFarmsClient.GODDESS_MODEL)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(HarvestGoddess pEntity) {
        return BetterFarms.id("textures/entity/harvest_goddess.png");
    }
}

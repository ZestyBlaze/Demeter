package dev.teamcitrus.betterfarms.entity;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class HarvestGoddess extends PathfinderMob {
    private int lastTalk = 1200;

    public HarvestGoddess(EntityType<? extends PathfinderMob> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public static AttributeSupplier createAttributes() {
        return PathfinderMob.createMobAttributes().add(Attributes.MOVEMENT_SPEED, 0.5d).build();
    }

    @Override
    public boolean isNoGravity() {
        return true;
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        return false;
    }

    @Override
    protected InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
        if (!pPlayer.level().isClientSide()) {
            lastTalk = 600;
            pPlayer.sendSystemMessage(Component.literal("Hello " + pPlayer.getDisplayName().getString()));
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.FAIL;
    }

    @Override
    public void tick() {
        super.tick();

        for (int i = 0; i < 16; i++) {
            level().addParticle(ParticleTypes.CLOUD, getX() + 0.1 * random.nextFloat(), getY() + 0.2 * random.nextFloat(), getZ() + 0.1 * random.nextFloat(), 0, -0.05, 0);
        }

        if (!level().isClientSide()) {
            if (lastTalk > 0) {
                lastTalk--;

                if (lastTalk <= 0) {
                    discard();
                }
            }
        }
    }
}

package dev.teamcitrus.demeter.item;

import dev.teamcitrus.citruslib.item.CitrusItem;
import dev.teamcitrus.demeter.config.DemeterConfig;
import dev.teamcitrus.demeter.enchantment.DemeterEnchantments;
import dev.teamcitrus.demeter.registry.AdvancementRegistry;
import dev.teamcitrus.demeter.util.AnimalUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class AnimalBrushItem extends CitrusItem {
    public AnimalBrushItem() {
        super(new Properties().stacksTo(1).durability(156));
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity target, InteractionHand hand) {
        if (target instanceof Animal animal) {
            if (!AnimalUtil.getAnimalData(animal).hasBeenBrushedToday()) {
                target.playSound(SoundEvents.BRUSH_GENERIC, 1.0f, 1.0f);
                for (int i = 0; i <= 8; i++) {
                    target.level().addParticle(ParticleTypes.HEART, animal.getRandomX(1.0), animal.getRandomY() + 0.5, animal.getRandomZ(1.0), animal.getRandom().nextGaussian() * 0.02, animal.getRandom().nextGaussian() * 0.02, animal.getRandom().nextGaussian() * 0.02);
                }
                //level.sendParticles(ParticleTypes.HEART, animal.getX(), animal.getY() + 0.7, animal.getZ(), 4, 0.5, 0, 0.5, animal.getRandom().nextGaussian() * 0.02);

                int comfortLevel = stack.getEnchantmentLevel(player.level().registryAccess().holderOrThrow(DemeterEnchantments.COMFORT));
                int spiteLevel = stack.getEnchantmentLevel(player.level().registryAccess().holderOrThrow(DemeterEnchantments.SPITE));
                int love = DemeterConfig.brushingLoveValue.get();

                if (comfortLevel > 0) {
                    love += (comfortLevel * DemeterConfig.comfortBonusPerLevel.get());
                }

                if (spiteLevel > 0) {
                    switch (DemeterConfig.spiteEffect.get()) {
                        case HALVE -> love *= (int) getSpiteHalving(spiteLevel);
                        case INVERT -> love *= -1;
                        case REDUCE -> love -= (spiteLevel * DemeterConfig.loveLossPerSpiteLevel.get());
                    }
                    AdvancementRegistry.SPITEFUL_BRUSHED.get().trigger(player);
                } else {
                    AdvancementRegistry.BRUSHED.get().trigger(player);
                }

                AnimalUtil.getAnimalData(animal).alterLove(player, love);
                AnimalUtil.getAnimalData(animal).setHasBeenBrushedToday(true);
                stack.hurtAndBreak(1, player, EquipmentSlot.MAINHAND);

                return InteractionResult.SUCCESS;
            } else {
                player.displayClientMessage(
                        Component.translatable("message.demeter.brush.fail_daily")
                                .withStyle(ChatFormatting.RED), true
                );
            }
        }
        return InteractionResult.FAIL;
    }

    private float getSpiteHalving(int spiteLevel) {
        return switch (spiteLevel) {
            case 1 -> 0.75f;
            case 2 -> 0.5f;
            case 3 -> 0.25f;
            default -> throw new IllegalStateException("Unexpected value: " + spiteLevel);
        };
    }
}

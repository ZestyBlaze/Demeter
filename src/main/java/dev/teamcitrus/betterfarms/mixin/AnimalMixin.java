package dev.teamcitrus.betterfarms.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import dev.teamcitrus.betterfarms.attachment.AnimalAttachment;
import dev.teamcitrus.betterfarms.data.BFStatsListener;
import dev.teamcitrus.betterfarms.registry.AttachmentRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Animal.class)
public class AnimalMixin {
    @Unique private final Animal betterFarms$animal = (Animal)(Object)this;

    @ModifyExpressionValue(
            method = "canMate",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/animal/Animal;isInLove()Z", ordinal = 0)
    )
    private boolean betterFarms$checkMateGender(boolean original, Animal otherEntity) {
        return original && otherEntity.getData(AttachmentRegistry.ANIMAL).getGender() != betterFarms$animal.getData(AttachmentRegistry.ANIMAL).getGender();
    }

    @Inject(
            method = "mobInteract",
            at = @At("HEAD"),
            cancellable = true)
    private void betterFarms$handleNewMilking(Player pPlayer, InteractionHand pHand, CallbackInfoReturnable<InteractionResult> cir) {
        if (BFStatsListener.newMap.containsKey(betterFarms$animal.getType())) {
            if (BFStatsListener.getManager(betterFarms$animal).canBeMilked() && betterFarms$animal.hasData(AttachmentRegistry.MILK)) {
                ItemStack stack = pPlayer.getItemInHand(pHand);
                if (stack.is(Items.BUCKET)) {
                    if (betterFarms$animal.getData(AttachmentRegistry.ANIMAL).getGender().equals(AnimalAttachment.AnimalGenders.FEMALE)) {
                        if (!betterFarms$animal.getData(AttachmentRegistry.MILK).getHasBeenMilked()) {
                            pPlayer.playSound(SoundEvents.COW_MILK, 1.0F, 1.0F);
                            ItemStack itemStack1 = ItemUtils.createFilledResult(stack, pPlayer, Items.MILK_BUCKET.getDefaultInstance());
                            pPlayer.setItemInHand(pHand, itemStack1);
                            betterFarms$animal.getData(AttachmentRegistry.MILK).setHasBeenMilked(true);
                            cir.setReturnValue(InteractionResult.SUCCESS);
                        } else {
                            pPlayer.displayClientMessage(Component.translatable("message.betterfarms.milk.fail_daily").withStyle(ChatFormatting.RED), true);
                            cir.setReturnValue(InteractionResult.FAIL);
                        }
                    } else {
                        pPlayer.displayClientMessage(Component.translatable("message.betterfarms.milk.fail_gender").withStyle(ChatFormatting.RED), true);
                        cir.setReturnValue(InteractionResult.FAIL);
                    }
                }
            }
        }
    }
}

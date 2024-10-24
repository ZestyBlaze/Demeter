package dev.teamcitrus.demeter.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import dev.teamcitrus.demeter.util.AnimalUtil;
import net.minecraft.world.entity.animal.Animal;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Debug(export = true)
@Mixin(Animal.class)
public class AnimalMixin {
    @Unique
    private final Animal demeter$animal = (Animal) (Object) this;

    /*
    @Inject(
            method = "mobInteract",
            at = @At("HEAD"),
            cancellable = true
    )
    private void demeter$handleNewMilking(Player pPlayer, InteractionHand pHand, CallbackInfoReturnable<InteractionResult> cir) {
        if (pPlayer.level().isClientSide) return;
        AnimalData stats = AnimalUtil.getStats(demeter$animal);
        if (!(AnimalUtil.getStats(demeter$animal) != null && stats.milking().isPresent())) return;
        AnimalData.MilkingCodec milking = stats.milking().get();
        ItemStack stack = pPlayer.getItemInHand(pHand);

        if (!stack.is(milking.input())) return;
        if (!AnimalUtil.getGender(demeter$animal).equals(AnimalAttachment.AnimalGenders.FEMALE)) {
            pPlayer.displayClientMessage(Component.translatable("message.demeter.milk.fail_gender").withStyle(ChatFormatting.RED), true);
            return;
        }

        MilkAttachment attachment = demeter$animal.getData(AttachmentRegistry.MILK);
        if (attachment.getHasBeenMilked()) {
            pPlayer.displayClientMessage(Component.translatable("message.demeter.milk.fail_daily").withStyle(ChatFormatting.RED), true);
            return;
        }

        ItemStack output = QualityUtil.randomiseQuality(milking.output().getDefaultInstance());
        ItemStack result = ItemUtils.createFilledResult(stack, pPlayer, output);
        ServerPlayer serverPlayer = (ServerPlayer) pPlayer;
        serverPlayer.connection.send(new ClientboundSoundPacket(Holder.direct(SoundEvents.COW_MILK), SoundSource.PLAYERS, demeter$animal.getX(), demeter$animal.getY(), demeter$animal.getZ(), 1.0f, 1.0f, 0));
        pPlayer.setItemInHand(pHand, result);
        attachment.setHasBeenMilked(true);
        cir.setReturnValue(InteractionResult.SUCCESS);
    }
     */

    @ModifyReturnValue(
            method = "canFallInLove",
            at = @At("RETURN")
    )
    private boolean demeter$canFallInLove(boolean original) {
        return original && AnimalUtil.isAnimalHappy(demeter$animal)
                && !AnimalUtil.getAnimalData(demeter$animal).getPregnant();
    }

    @ModifyExpressionValue(
            method = "canMate",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/animal/Animal;isInLove()Z",
                    ordinal = 0
            )
    )
    private boolean demeter$checkMateGender(boolean original, Animal otherEntity) {
        return original && !AnimalUtil.getAnimalData(demeter$animal).getPregnant()
                //&& !AnimalUtil.getAnimalData(demeter$animal).isOnDownPeriod()
                && AnimalUtil.areOppositeGenders(demeter$animal, otherEntity);
    }
}

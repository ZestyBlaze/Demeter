package dev.teamcitrus.demeter.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import dev.teamcitrus.demeter.util.AnimalUtil;
import net.minecraft.world.entity.animal.Animal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
 
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
    private void demeter$handleNewMilking(Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir) {
        if (player.level().isClientSide) return;
        AnimalData stats = AnimalUtil.getStats(demeter$animal);
        if (!(AnimalUtil.getStats(demeter$animal) != null && stats.milking().isPresent())) return;
        AnimalData.MilkingCodec milking = stats.milking().get();
        ItemStack stack = player.getItemInHand(hand);

        if (!milking.inputOutputMap().containsKey(stack.getItem())) return;
        if (!AnimalUtil.getSex(demeter$animal).equals(AnimalSexes.FEMALE)) {
            player.displayClientMessage(Component.translatable("message.demeter.milk.fail_sex").withStyle(ChatFormatting.RED), true);
            return;
        }

        MilkAttachment attachment = demeter$animal.getData(AttachmentRegistry.MILK);
        if (attachment.getHasBeenMilked()) {
            player.displayClientMessage(Component.translatable("message.demeter.milk.fail_daily").withStyle(ChatFormatting.RED), true);
            return;
        }

        ItemStack output = milking.inputOutputMap().get(stack.getItem()).getDefaultInstance();
        QualityUtil.randomiseQuality(output);
        ItemStack result = ItemUtils.createFilledResult(stack, player, output);
        ServerPlayer serverPlayer = (ServerPlayer) player;
        serverPlayer.connection.send(new ClientboundSoundPacket(Holder.direct(SoundEvents.COW_MILK), SoundSource.PLAYERS, demeter$animal.getX(), demeter$animal.getY(), demeter$animal.getZ(), 1.0f, 1.0f, 0));
        player.setItemInHand(hand, result);
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
                && !AnimalUtil.getAnimalData(demeter$animal).isOnDownPeriod()
                && !AnimalUtil.getAnimalData(demeter$animal).isPregnant();
    }

    @ModifyExpressionValue(
            method = "canMate",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/animal/Animal;isInLove()Z",
                    ordinal = 0
            )
    )
    private boolean demeter$checkMateSex(boolean original, Animal otherEntity) {
        return original && !AnimalUtil.getAnimalData(demeter$animal).isPregnant()
                //&& !AnimalUtil.getAnimalData(demeter$animal).isOnDownPeriod()
                && AnimalUtil.areOppositeSexes(demeter$animal, otherEntity);
    }
}

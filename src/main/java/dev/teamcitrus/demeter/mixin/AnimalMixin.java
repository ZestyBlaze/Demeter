package dev.teamcitrus.demeter.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import dev.teamcitrus.citruslib.reload.DynamicHolder;
import dev.teamcitrus.citruslib.util.ModUtils;
import dev.teamcitrus.demeter.attachment.AnimalAttachment.AnimalGenders;
import dev.teamcitrus.demeter.attachment.MilkAttachment;
import dev.teamcitrus.demeter.data.AnimalStats;
import dev.teamcitrus.demeter.data.IStats;
import dev.teamcitrus.demeter.registry.AttachmentRegistry;
import dev.teamcitrus.demeter.util.AnimalUtil;
import dev.teamcitrus.demeter.util.QualityUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.crafting.Ingredient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Animal.class)
public class AnimalMixin {
    @Unique
    private final Animal demeter$animal = (Animal) (Object) this;

    @WrapOperation(
            method = "isFood",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/ItemStack;is(Lnet/minecraft/world/item/Item;)Z"
            )
    )
    private boolean betterFarms$isFood(ItemStack stack, Item item, Operation<Boolean> original) {
        if (!AnimalUtil.getStats(demeter$animal).isBound()) return original.call(stack, item);
        DynamicHolder<AnimalStats> stats = AnimalUtil.getStats(demeter$animal);
        if (stats.get().breedingItems().isEmpty()) return original.call(stack, item);
        for (Ingredient ingredient : stats.get().breedingItems().get()) {
            if (ingredient.test(stack)) {
                AnimalUtil.getAnimalData(demeter$animal).alterLove(8);
                AnimalUtil.getAnimalData(demeter$animal).setHasBeenFedToday(true);
                return true;
            }
        }
        return false;
    }

    @Inject(
            method = "mobInteract",
            at = @At("HEAD"),
            cancellable = true
    )
    private void betterFarms$handleNewMilking(Player pPlayer, InteractionHand pHand, CallbackInfoReturnable<InteractionResult> cir) {
        if (pPlayer.level().isClientSide) return;
        DynamicHolder<AnimalStats> stats = AnimalUtil.getStats(demeter$animal);
        if (!(AnimalUtil.getStats(demeter$animal).isBound() && stats.get().milking().isPresent())) return;
        IStats.MilkingCodec milking = stats.get().milking().get();
        ItemStack stack = pPlayer.getItemInHand(pHand);

        if (!stack.is(milking.input())) return;
        if (!AnimalUtil.getGender(demeter$animal).equals(AnimalGenders.FEMALE)) {
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

    @Inject(
            method = "mobInteract",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/animal/Animal;getAge()I",
                    shift = At.Shift.AFTER
            )
    )
    private void betterFarms$mobInteract(Player pPlayer, InteractionHand pHand, CallbackInfoReturnable<InteractionResult> cir) {
        AnimalUtil.getAnimalData(demeter$animal).setHasBeenFedToday(true);
    }

    @Inject(
            method = "setInLove",
            at = @At("HEAD"),
            cancellable = true
    )
    private void betterFarms$setInLove(Player pPlayer, CallbackInfo ci) {
        if (!AnimalUtil.isAnimalHappy(demeter$animal) && !ModUtils.isDevelopmentEnvironment()) ci.cancel();
    }

    @ModifyExpressionValue(
            method = "canMate",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/animal/Animal;isInLove()Z",
                    ordinal = 0
            )
    )
    private boolean betterFarms$checkMateGender(boolean original, Animal otherEntity) {
        return original && AnimalUtil.areOppositeGenders(demeter$animal, otherEntity);
    }
}

package dev.teamcitrus.betterfarms.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import dev.teamcitrus.betterfarms.attachment.AnimalAttachment.AnimalGenders;
import dev.teamcitrus.betterfarms.attachment.MilkAttachment;
import dev.teamcitrus.betterfarms.data.AnimalStats;
import dev.teamcitrus.betterfarms.data.BFStatsManager;
import dev.teamcitrus.betterfarms.registry.AttachmentRegistry;
import dev.teamcitrus.betterfarms.util.AnimalUtil;
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Animal.class)
public class AnimalMixin {
    @Unique private final Animal animal = (Animal)(Object)this;

    @ModifyExpressionValue(
            method = "canMate",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/animal/Animal;isInLove()Z", ordinal = 0)
    )
    private boolean betterFarms$checkMateGender(boolean original, Animal otherEntity) {
        return original && otherEntity.getData(AttachmentRegistry.ANIMAL).getGender() != animal.getData(AttachmentRegistry.ANIMAL).getGender();
    }

    @Inject(
            method = "mobInteract",
            at = @At("HEAD"),
            cancellable = true)
    private void betterFarms$handleNewMilking(Player pPlayer, InteractionHand pHand, CallbackInfoReturnable<InteractionResult> cir) {
        if (pPlayer.level().isClientSide) return;
        AnimalStats stats = BFStatsManager.getStats(animal);
        if (!(BFStatsManager.newMap.containsKey(animal.getType()) && stats.milking().isPresent())) return;
        AnimalStats.MilkingCodec milking = stats.milking().get();
        ItemStack stack = pPlayer.getItemInHand(pHand);

        if (!stack.is(milking.input())) return;
        if (!AnimalUtil.getGender(animal).equals(AnimalGenders.FEMALE)) {
            pPlayer.displayClientMessage(Component.translatable("message.betterfarms.milk.fail_gender").withStyle(ChatFormatting.RED), true);
            return;
        }

        MilkAttachment attachment = animal.getData(AttachmentRegistry.MILK);
        if (attachment.getHasBeenMilked()) {
            pPlayer.displayClientMessage(Component.translatable("message.betterfarms.milk.fail_daily").withStyle(ChatFormatting.RED), true);
            return;
        }

        ItemStack result = ItemUtils.createFilledResult(stack, pPlayer, milking.output().getDefaultInstance());
        ServerPlayer serverPlayer = (ServerPlayer)pPlayer;
        serverPlayer.connection.send(new ClientboundSoundPacket(Holder.direct(SoundEvents.COW_MILK), SoundSource.PLAYERS, animal.getX(), animal.getY(), animal.getZ(), 1.0f, 1.0f, 0));
        pPlayer.setItemInHand(pHand, result);
        attachment.setHasBeenMilked(true);
        cir.setReturnValue(InteractionResult.SUCCESS);
    }
}

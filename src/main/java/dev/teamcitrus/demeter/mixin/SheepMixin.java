package dev.teamcitrus.demeter.mixin;

import net.minecraft.world.entity.animal.sheep.Sheep;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Sheep.class)
public class SheepMixin {
    @Redirect(method = "ate", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/animal/sheep/Sheep;setSheared(Z)V"))
    private void demeter$ate(Sheep instance, boolean sheared) {}
}

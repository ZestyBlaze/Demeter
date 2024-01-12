package dev.zestyblaze.betterfauna.mixin;

import dev.zestyblaze.betterfauna.event.EntityAddedToLevelEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.entity.EntityAccess;
import net.minecraft.world.level.entity.PersistentEntitySectionManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(PersistentEntitySectionManager.class)
public class PersistentEntitySectionManagerMixin<T extends EntityAccess> {
    @Inject(method = "addEntity", at = @At(value = "HEAD"), locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
    private void betterFauna$fireEntityLoadEvent(T object, boolean existing, CallbackInfoReturnable<Boolean> cir){
        if(object instanceof Entity entity){
            if(!EntityAddedToLevelEvent.ON_ADD.invoker().onAddedToWorld(entity, entity.level(), existing)){
                cir.setReturnValue(false);
            }
        }
    }
}

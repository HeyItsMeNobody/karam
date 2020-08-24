package nl.dyonb.karam.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Direction;
import nl.dyonb.karam.common.block.ElevatorBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {

    @Inject(at = @At("HEAD"), method = "jump()V", locals = LocalCapture.CAPTURE_FAILSOFT)
    private void onJump(CallbackInfo ci) {
        ElevatorBlock.moveInDirection(Direction.UP, (Entity) (Object) this);
    }

}

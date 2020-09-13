package nl.dyonb.karam.mixin;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.render.SkyProperties;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import nl.dyonb.karam.common.block.ElevatorBlock;
import nl.dyonb.karam.common.block.entity.EnderStopperBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    @Inject(at = @At("HEAD"), method = "teleport", locals = LocalCapture.CAPTURE_FAILSOFT, cancellable = true)
    private void onTeleport(double x, double y, double z, boolean particleEffects, CallbackInfoReturnable ci) {
        if ((Object)this instanceof EndermanEntity) {
            EndermanEntity endermanEntity = (EndermanEntity)(Object)this;

            for (final BlockEntity blockEntity : endermanEntity.getEntityWorld().blockEntities) {
                if (blockEntity instanceof EnderStopperBlockEntity) {
                    EnderStopperBlockEntity enderStopperBlockEntity = (EnderStopperBlockEntity) blockEntity;
                    // If the EnderStopperBlockEntity is close enough stop the enderman from teleporting
                    if (enderStopperBlockEntity.isEntityCloseEnough(endermanEntity)) {
                        ci.cancel();
                    }
                }
            }
        }
    }

}

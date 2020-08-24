package nl.dyonb.karam.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import nl.dyonb.karam.common.block.ElevatorBlock;
import nl.dyonb.karam.registry.KaramConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(Entity.class)
public abstract class EntityMixin {
    // Heavy thank you to the author(s) of Kibe

    @Shadow public abstract BlockPos getBlockPos();
    @Shadow public World world;
    @Shadow public abstract void teleport(double destX, double destY, double destZ);
    @Shadow private Vec3d pos;

    @Inject(at = @At("HEAD"), method = "setSneaking(Z)V")
    private void onSneak(boolean sneaking, CallbackInfo ci) {
        // Check if the entity is sneaking
        if (sneaking == true) {
            ElevatorBlock.moveInDirection(Direction.DOWN, (Entity) (Object) this);
        }
    }

}

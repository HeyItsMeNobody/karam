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
//            // Get the block position
//            BlockPos pos = getBlockPos();
//            // Store the original value for later usage
//            BlockPos originalPos = getBlockPos();
//            // Integer for the amount of full cubes passed
//            int fullCubeBlocksPassed = 0;
//            // Get the block one down
//            Block block = world.getBlockState(pos.down()).getBlock();
//            // Check if the block is an elevator
//            if (block instanceof ElevatorBlock && world.getBlockState(pos).getCollisionShape(world, pos).isEmpty()) {
//                // Count how many blocks have gone down
//                int totalBlocksTraveled = 1;
//                // Set pos one lower than the ElevatorBlock
//                pos = pos.down();
//                // While the Y level isn't 0
//                while(pos.getY() > 0) {
//                    // Check if the block downwards is an elevator block
//                    if (world.getBlockState(pos.down()).getBlock().equals(block)) {
//                        // Teleport to that elevator
//                        teleport(this.pos.x, pos.down().getY() + 1.15, this.pos.z);
//                        // Play the ender chest open sound
//                        world.playSound(null, pos, SoundEvents.BLOCK_ENDER_CHEST_OPEN, SoundCategory.BLOCKS, 0.5F, world.random.nextFloat() * 0.15F + 0.6F);
//                        break;
//                    } else if (totalBlocksTraveled == KaramConfig.CONFIG.maxBlocksBetweenElevator) {
//                        // Break if max blocks have been passed and play the ender eye death sound
//                        world.playSound(null, originalPos, SoundEvents.ENTITY_ENDER_EYE_DEATH, SoundCategory.BLOCKS, 0.5F, world.random.nextFloat() * 0.15F + 0.6F);
//                        break;
//                    } else if (fullCubeBlocksPassed == KaramConfig.CONFIG.maxSolidBlocksBetweenElevator) {
//                        // Break if max solid blocks have been passed and play the ender eye death sound
//                        world.playSound(null, originalPos, SoundEvents.ENTITY_ENDER_EYE_DEATH, SoundCategory.BLOCKS, 0.5F, world.random.nextFloat() * 0.15F + 0.6F);
//                        break;
//                    } else {
//                        // If that block isn't an elevator block go one more down
//                        pos = pos.down();
//                        // Increase the total blocks traveled
//                        totalBlocksTraveled++;
//                        // If the block at the pos is a full cube, Add to the full cube counter.
//                        if (world.getBlockState(pos).isFullCube(world, pos)) {
//                            fullCubeBlocksPassed++;
//                        }
//                    }
//                }
//                if (pos.getY() == 0) {
//                    // Play the ender eye death sound if the world limit has been reached
//                    world.playSound(null, originalPos, SoundEvents.ENTITY_ENDER_EYE_DEATH, SoundCategory.BLOCKS, 0.5F, world.random.nextFloat() * 0.15F + 0.6F);
//                }
//            }
        }
    }

}

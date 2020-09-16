package nl.dyonb.karam.common.block;

import nl.dyonb.karam.Karam;
import org.apache.logging.log4j.Level;
import org.jetbrains.annotations.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.stat.Stats;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import nl.dyonb.karam.common.block.entity.BaseColorBlockEntity;
import nl.dyonb.karam.common.block.entity.ElevatorBlockEntity;
import nl.dyonb.karam.util.ColorHelper;

public interface BaseColorBlockInterface {

    public static void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof BaseColorBlockEntity) {
            BaseColorBlockEntity baseColorBlockEntity = (BaseColorBlockEntity) blockEntity;

            int color = ColorHelper.getColorFromItemStack(itemStack);
            baseColorBlockEntity.setColor(color);
        }
    }

    /**
     * Overrides the afterBreak method to add custom NBT data.
     *
     * @param world       the world
     * @param player      the player
     * @param pos         the pos
     * @param state       the state
     * @param blockEntity the block entity
     * @param stack       the stack
     */
    public static void afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack stack) {
        player.incrementStat(Stats.MINED.getOrCreateStat(state.getBlock()));
        player.addExhaustion(0.005F);
        dropNBTStacks(state, world, pos, blockEntity, player, stack);
    }

    /**
     * Drop nbt stacks.
     *
     * @param state       the state
     * @param world       the world
     * @param pos         the pos
     * @param blockEntity the block entity
     * @param entity      the entity
     * @param stack       the stack
     */
    // Not completely sure what this does, It gets the item that should be dropped and passes it to dropNBTStack.
    public static void dropNBTStacks(BlockState state, World world, BlockPos pos, @Nullable BlockEntity blockEntity, Entity entity, ItemStack stack) {
        if (world instanceof ServerWorld) {
            Block.getDroppedStacks(state, (ServerWorld) world, pos, blockEntity, entity, stack).forEach((itemStack) -> dropNBTStack(world, pos, blockEntity, itemStack));
            state.onStacksDropped((ServerWorld) world, pos, stack);
        }
    }

    /**
     * Drop nbt stack.
     *
     * @param world       the world
     * @param pos         the pos
     * @param blockEntity the block entity
     * @param stack       the stack
     */
    // This allows for dropping custom nbt tagged items when the Elevator block is mined.
    public static void dropNBTStack(World world, BlockPos pos, BlockEntity blockEntity, ItemStack stack) {
        if (!world.isClient && !stack.isEmpty() && blockEntity instanceof BaseColorBlockEntity) {
            float generalOffset = 0.5F;
            double xOffset = (world.random.nextFloat() * generalOffset) + 0.25D;
            double yOffset = (world.random.nextFloat() * generalOffset) + 0.25D;
            double zOffset = (world.random.nextFloat() * generalOffset) + 0.25D;

            // Get the block entity
            BaseColorBlockEntity baseColorBlockEntity = (BaseColorBlockEntity) blockEntity;
            ItemEntity itemEntity;

            // Put the color into the stack
            stack.getOrCreateTag().putInt("color", baseColorBlockEntity.getColor());

            itemEntity = new ItemEntity(world, pos.getX() + xOffset, pos.getY() + yOffset, pos.getZ() + zOffset, stack);
            itemEntity.setToDefaultPickupDelay();

            world.spawnEntity(itemEntity);
        }
    }

}

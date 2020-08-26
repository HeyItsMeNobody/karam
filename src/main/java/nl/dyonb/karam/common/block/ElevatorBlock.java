package nl.dyonb.karam.common.block;

import jdk.internal.jline.internal.Nullable;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.data.client.model.BlockStateSupplier;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterials;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.state.State;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import nl.dyonb.karam.common.block.entity.ElevatorBlockEntity;
import nl.dyonb.karam.registry.KaramConfig;

import java.util.Collections;
import java.util.List;

public class ElevatorBlock extends Block implements BlockEntityProvider {
    public ElevatorBlock() {
        super(FabricBlockSettings.of(Material.WOOL).sounds(BlockSoundGroup.WOOL)
        .breakByTool(FabricToolTags.SHEARS, ToolMaterials.WOOD.getMiningLevel())
        .strength(1F, 1F));
    }

    /**
     * @param direction
     *        Direction to move in
     * @param entity
     *        Entity you're trying to move
     * @return void
     */
    public static void moveInDirection(Direction direction, Entity entity) {
        // Set the world variable
        World world = entity.world;
        // Get the block position
        BlockPos pos = entity.getBlockPos();
        // Store the original value for later usage
        BlockPos originalPos = entity.getBlockPos();
        // Integer for the amount of full cubes passed
        int fullCubeBlocksPassed = 0;
        // Get the block one down
        Block block = world.getBlockState(pos.down()).getBlock();
        // Check if the block is an elevator and check if that elevator is valid
        if (block instanceof ElevatorBlock && world.getBlockState(pos).getCollisionShape(world, pos).isEmpty() && ElevatorBlock.isElevatorValid(world, pos.down())) {
            // Count how many blocks have gone into the direction
            int totalBlocksTraveled = 1;
            // Set pos one to the direction than the ElevatorBlock
            pos = pos.offset(direction);
            // While the Y level isn't 0
            while(pos.getY() > 0) {
                // Check if the block in direction is an elevator block and check if it is a valid block
                if (world.getBlockState(pos.offset(direction)).getBlock().equals(block) && ElevatorBlock.isElevatorValid(world, pos.offset(direction))) {
                    // Teleport to that elevator (using entity.getPos() to not move the player around)
                    entity.teleport(entity.getPos().x, pos.offset(direction).getY() + 1.15, entity.getPos().z);
                    // Play the ender chest open sound
                    world.playSound(null, pos, SoundEvents.BLOCK_ENDER_CHEST_OPEN, SoundCategory.BLOCKS, 0.5F, world.random.nextFloat() * 0.15F + 0.6F);
                    break;
                } else if (totalBlocksTraveled == KaramConfig.CONFIG.maxBlocksBetweenElevator) {
                    // Break if max blocks have been passed and play the ender eye death sound
                    world.playSound(null, originalPos, SoundEvents.ENTITY_ENDER_EYE_DEATH, SoundCategory.BLOCKS, 0.5F, world.random.nextFloat() * 0.15F + 0.6F);
                    break;
                } else if (fullCubeBlocksPassed == KaramConfig.CONFIG.maxSolidBlocksBetweenElevator) {
                    // Break if max solid blocks have been passed and play the ender eye death sound
                    world.playSound(null, originalPos, SoundEvents.ENTITY_ENDER_EYE_DEATH, SoundCategory.BLOCKS, 0.5F, world.random.nextFloat() * 0.15F + 0.6F);
                    break;
                } else {
                    // If that block isn't an elevator block go one more in direction
                    pos = pos.offset(direction);
                    // Increase the total blocks traveled
                    totalBlocksTraveled++;
                    // If the block at the pos is a full cube, Add to the full cube counter.
                    if (world.getBlockState(pos).isFullCube(world, pos)) {
                        fullCubeBlocksPassed++;
                    }
                }
            }
            if (pos.getY() == 0) {
                // Play the ender eye death sound if the world limit has been reached
                world.playSound(null, originalPos, SoundEvents.ENTITY_ENDER_EYE_DEATH, SoundCategory.BLOCKS, 0.5F, world.random.nextFloat() * 0.15F + 0.6F);
            }
        }
    }

    /**
     * @return If the elevator is valid or not
     */
    public static boolean isElevatorValid(World world, BlockPos pos) {
        // Check if the block should be deactivated on redstone power and return based on that
        if (KaramConfig.CONFIG.deactivateElevatorOnRedstonePower == true && world.isReceivingRedstonePower(pos) == true) {
            return false;
        } else if (KaramConfig.CONFIG.ignoreObstructedElevator == false) {
            // See if the first and second block above the elevator is free
            boolean bottomBlockEmpty = world.getBlockState(pos.up()).getCollisionShape(world, pos.up()).isEmpty();
            boolean secondBlockEmpty = world.getBlockState(pos.up().up()).getCollisionShape(world, pos.up().up()).isEmpty();

            // Return true or false based on results
            return (bottomBlockEmpty && secondBlockEmpty);
        }

        return true;
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
    @Override
    public void afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack stack) {
        player.incrementStat(Stats.MINED.getOrCreateStat(this));
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
            getDroppedStacks(state, (ServerWorld) world, pos, blockEntity, entity, stack).forEach((itemStack) -> dropNBTStack(world, pos, blockEntity, itemStack));
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
        if (!world.isClient && !stack.isEmpty() && blockEntity instanceof ElevatorBlockEntity) {
            float generalOffset = 0.5F;
            double xOffset = (world.random.nextFloat() * generalOffset) + 0.25D;
            double yOffset = (world.random.nextFloat() * generalOffset) + 0.25D;
            double zOffset = (world.random.nextFloat() * generalOffset) + 0.25D;

            // Get the block entity
            ElevatorBlockEntity elevatorBlockEntity = (ElevatorBlockEntity) blockEntity;
            ItemEntity itemEntity;

            // Put the color into the stack
            stack.getTag().putInt("color", elevatorBlockEntity.getColor());

            itemEntity = new ItemEntity(world, pos.getX() + xOffset, pos.getY() + yOffset, pos.getZ() + zOffset, stack);
            itemEntity.setToDefaultPickupDelay();

            world.spawnEntity(itemEntity);
        }
    }

    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return new ElevatorBlockEntity();
    }
}

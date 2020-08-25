package nl.dyonb.karam.common.block.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import nl.dyonb.karam.common.block.entity.ElevatorBlockEntity;

public class ElevatorBlockItem extends BlockItem {
    public ElevatorBlockItem(Block block, Settings settings) {
        super(block, settings);
    }

    // Put NBT back into block
    @Override
    protected boolean postPlacement(BlockPos pos, World world, PlayerEntity player, ItemStack stack, BlockState state) {
        ElevatorBlockEntity blockEntity = (ElevatorBlockEntity) world.getBlockEntity(pos);
        CompoundTag nbt = stack.getOrCreateTag();
        if (!nbt.contains("color")) {
            nbt.putInt("color", 16777215);
        } else if (nbt.get("color") instanceof StringTag) {
            // Make sure it is always an integer
            nbt.putInt("color", Integer.parseInt(nbt.getString("color"), 16));
        }

        blockEntity.fromClientTag(nbt);
        if (!world.isClient()) {
            blockEntity.sync();
        }
//        // Put back the NBT into the block
//        blockEntity.fromTag(state, nbt);
//        // Make sure the NBT gets saved
//        blockEntity.markDirty();

        return super.postPlacement(pos, world, player, stack, state);
    }
}

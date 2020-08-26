package nl.dyonb.karam.common.block.entity;

import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable;
import net.fabricmc.fabric.api.util.NbtType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import nl.dyonb.karam.common.block.ElevatorBlock;
import nl.dyonb.karam.registry.KaramBlockEntityTypes;

public class ElevatorBlockEntity extends BlockEntity implements BlockEntityClientSerializable {
    private int color = 16777215;
    public int getColor() { return color; }

    public void setColor(int colorToSet) {
        this.color = colorToSet;
        markDirty();
        if (!world.isClient) {
            sync();
        }
    }

    public static int getColorFromItemStack(ItemStack stack) {
        CompoundTag nbt = stack.getOrCreateTag();

        int color = 16777215;
        if (nbt.contains("color", NbtType.INT)) { // nbt.contains(String, int) allows you to check for a subtag of a specific type, very cool
            color = nbt.getInt("color");
        } else if (nbt.contains("BlockEntityTag") && nbt.getCompound("BlockEntityTag").contains("color", NbtType.INT)) {
            color = nbt.getCompound("BlockEntityTag").getInt("color");
        } else if (nbt.contains("color", NbtType.STRING)) {
            try {
                color = Integer.parseInt(nbt.getString("color"));
            } catch (NumberFormatException ignored) { }
        }

        return color;
    }

    public static void setColorForItemStack(ItemStack stack, int color) {
        CompoundTag nbt = stack.getOrCreateTag();
        nbt.putInt("color", color);
    }

    public ElevatorBlockEntity() {
        super(KaramBlockEntityTypes.ELEVATOR);
    }

    @Override
    public void fromTag(BlockState state, CompoundTag tag) {
        super.fromTag(state, tag);
        fromClientTag(tag);
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        super.toTag(tag);
        return toClientTag(tag);
    }

    @Override
    public void fromClientTag(CompoundTag tag) {
        color = tag.getInt("color");
    }

    @Override
    public CompoundTag toClientTag(CompoundTag tag) {
        if (color == 0)
            color = 16777215;
        tag.putInt("color", color);
        return tag;
    }
}

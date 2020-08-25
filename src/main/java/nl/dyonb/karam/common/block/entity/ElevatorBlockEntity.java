package nl.dyonb.karam.common.block.entity;

import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.CompoundTag;
import nl.dyonb.karam.registry.KaramBlockEntityTypes;

public class ElevatorBlockEntity extends BlockEntity implements BlockEntityClientSerializable {
    private int color = 16777215;
    public int getColor() { return color; }

    public void setColor(int colorToSet) {
        color = colorToSet;

        CompoundTag compoundTag = new CompoundTag();
        compoundTag.putInt("color", color);

        fromTag(this.getCachedState(), compoundTag);
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

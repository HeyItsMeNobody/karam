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

    public ElevatorBlockEntity() {
        super(KaramBlockEntityTypes.ELEVATOR);
    }

//    // Serialize the BlockEntity
//    @Override
//    public CompoundTag toTag(CompoundTag tag) {
//        super.toTag(tag);
//
//        // Save the current value of the number to the tag
//        // If color is 0 set it to 16777215 (white)
//        if (color == 0) {
//            color = 16777215;
//        }
//        tag.putInt("color", color);
//
//        return tag;
//    }
//
//    // Deserialize the BlockEntity
//    @Override
//    public void fromTag(BlockState state, CompoundTag tag) {
//        super.fromTag(state, tag);
//        color = tag.getInt("color");
//
//        this.markDirty();
//    }

    @Override
    public void fromClientTag(CompoundTag compoundTag) {
        color = compoundTag.getInt("color");
    }

    @Override
    public CompoundTag toClientTag(CompoundTag compoundTag) {
        // Save the current value of the number to the tag
        // If color is 0 set it to 16777215 (white)
        System.out.println("before check");
        System.out.println(color);
        if (color == 0) {
            color = 16777215;
        }
        System.out.println("after check");
        System.out.println(color);
        compoundTag.putInt("color", color);
        System.out.println("tag after put");
        System.out.println(compoundTag);

        return compoundTag;
    }
}

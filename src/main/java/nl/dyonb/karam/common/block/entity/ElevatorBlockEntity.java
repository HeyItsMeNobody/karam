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

public class ElevatorBlockEntity extends BaseColorBlockEntity {
    public ElevatorBlockEntity() {
        super(KaramBlockEntityTypes.ELEVATOR);
    }
}

package nl.dyonb.karam.client.registry;

import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.CompoundTag;
import nl.dyonb.karam.common.block.entity.ElevatorBlockEntity;
import nl.dyonb.karam.registry.KaramBlocks;

public class KaramColorProviders {

    public static void initialize() {
        // Not used here

        //ColorProviderRegistry.BLOCK.register((blockState, renderView, blockPos, tintIndex) -> renderView.getBlockEntity(blockPos).toTag(new CompoundTag()).getInt("color"), KaramBlocks.ELEVATOR);
        ColorProviderRegistry.BLOCK.register((blockState, renderView, blockPos, tintIndex) -> {
            //return renderView.getBlockEntity(blockPos).toTag(new CompoundTag()).getInt("color");
            //BlockEntityClientSerializable blockEntityClientSerializable = (BlockEntityClientSerializable) renderView.getBlockEntity(blockPos);
            //return blockEntityClientSerializable.toClientTag(new CompoundTag()).getInt("color");

            BlockEntity blockEntity = renderView.getBlockEntity(blockPos);
            if (!(blockEntity instanceof ElevatorBlockEntity))
                return 0xFFFFFFFF; // this shouldn't be possible! but just in case, return white
            return ((ElevatorBlockEntity) blockEntity).getColor();
        }, KaramBlocks.ELEVATOR);
    }

}

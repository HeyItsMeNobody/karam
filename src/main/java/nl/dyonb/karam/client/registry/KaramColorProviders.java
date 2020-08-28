package nl.dyonb.karam.client.registry;

import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.block.entity.BlockEntity;
import nl.dyonb.karam.common.block.entity.ElevatorBlockEntity;
import nl.dyonb.karam.registry.KaramBlocks;
import nl.dyonb.karam.util.ColorHelper;

public class KaramColorProviders {

    public static void initialize() {
        ColorProviderRegistry.BLOCK.register((blockState, renderView, blockPos, tintIndex) -> {
            BlockEntity blockEntity = renderView.getBlockEntity(blockPos);
            if (!(blockEntity instanceof ElevatorBlockEntity))
                return 0xFFFFFFFF; // this shouldn't be possible! but just in case, return white
            return ((ElevatorBlockEntity) blockEntity).getColor();
        }, KaramBlocks.ELEVATOR);

        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> {
            return ColorHelper.getColorFromItemStack(stack);
        }, KaramBlocks.baseColorBlockItem);
    }

}

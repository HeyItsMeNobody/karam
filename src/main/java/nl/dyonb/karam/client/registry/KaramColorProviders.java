package nl.dyonb.karam.client.registry;

import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.Items;
import nl.dyonb.karam.common.block.BaseColorBlockInterface;
import nl.dyonb.karam.common.block.entity.BaseColorBlockEntity;
import nl.dyonb.karam.common.block.entity.ElevatorBlockEntity;
import nl.dyonb.karam.registry.KaramBlocks;
import nl.dyonb.karam.util.ColorHelper;

public class KaramColorProviders {

    public static void initialize() {
        ColorProviderRegistry.BLOCK.register((blockState, renderView, blockPos, tintIndex) -> {
            BlockEntity blockEntity = renderView.getBlockEntity(blockPos);
            if (!(blockEntity instanceof BaseColorBlockEntity))
                return 0xFFFFFFFF; // this shouldn't be possible! but just in case, return white
            return ((BaseColorBlockEntity) blockEntity).getColor();
        }, KaramBlocks.ELEVATOR, Blocks.LANTERN);

        ColorProviderRegistry.ITEM.register((stack, layerIndex) -> {
            int color = ColorHelper.getColorFromItemStack(stack);
            if (stack.getItem() == Items.LANTERN) {
                if (layerIndex == 1) {
                    return color;
                }
                return 0xFFFFFF;
            }

            return color;
        }, KaramBlocks.elevatorBlockItem, Items.LANTERN);
    }

}

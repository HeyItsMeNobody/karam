package nl.dyonb.karam.client.registry;

import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import nl.dyonb.karam.common.block.entity.BaseColorBlockEntity;
import nl.dyonb.karam.util.ColorHelper;
import nl.dyonb.karam.util.ColorProviderReference;

public class KaramColorProviders {

    public static void initialize() {
        ColorProviderRegistry.BLOCK.register((blockState, renderView, blockPos, tintIndex) -> {
            BlockEntity blockEntity = renderView.getBlockEntity(blockPos);
            if (!(blockEntity instanceof BaseColorBlockEntity))
                return 0xFFFFFFFF; // this shouldn't be possible! but just in case, return white
            return ((BaseColorBlockEntity) blockEntity).getColor();
        }, ColorProviderReference.blockList().toArray(new Block[0]));

        ColorProviderRegistry.ITEM.register((stack, layerIndex) -> {
            int color = ColorHelper.getColorFromItemStack(stack);
            if (stack.getItem() == Items.LANTERN) {
                if (layerIndex == 1) {
                    return color;
                }
                return 0xFFFFFF;
            }

            return color;
        }, ColorProviderReference.itemList().toArray(new Item[0]));
    }

}

package nl.dyonb.karam.client.registry;

import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.block.Block;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderLayers;
import nl.dyonb.karam.registry.KaramBlocks;

public class KaramRenderLayers {

    public static void initialize() {
        register(KaramBlocks.ENDERSTOPPER, RenderLayer.getCutout());
        register(KaramBlocks.WALL_ENDERSTOPPER, RenderLayer.getCutout());
    }

    public static void register(Block block, RenderLayer renderLayer) {
        BlockRenderLayerMap.INSTANCE.putBlock(block, renderLayer);
    }

}

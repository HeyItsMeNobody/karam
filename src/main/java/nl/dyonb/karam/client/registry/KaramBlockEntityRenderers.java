package nl.dyonb.karam.client.registry;

import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.EndGatewayBlockEntityRenderer;
import nl.dyonb.karam.client.blockentityrenderer.EnderStopperBlockEntityRenderer;
import nl.dyonb.karam.common.block.entity.EnderStopperBlockEntity;
import nl.dyonb.karam.registry.KaramBlockEntityTypes;

public class KaramBlockEntityRenderers {

    public static void initialize() {
        BlockEntityRendererRegistry.INSTANCE.register(KaramBlockEntityTypes.ENDERSTOPPER, EnderStopperBlockEntityRenderer::new);
    }

}

package nl.dyonb.karam.client.blockentityrenderer;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import nl.dyonb.karam.common.block.entity.EnderStopperBlockEntity;

public class EnderStopperBlockEntityRenderer extends BlockEntityRenderer<EnderStopperBlockEntity> {
    public EnderStopperBlockEntityRenderer(BlockEntityRenderDispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    public void render(EnderStopperBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        if (!entity.showBorder) {
            return;
        }

        matrices.push();
        WorldRenderer.drawBox(matrices, vertexConsumers.getBuffer(RenderLayer.LINES), entity.area, 1f, 0f, 0f, 0.5f);
        matrices.pop();
    }
}

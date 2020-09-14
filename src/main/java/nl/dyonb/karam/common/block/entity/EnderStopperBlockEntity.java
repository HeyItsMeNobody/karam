package nl.dyonb.karam.common.block.entity;

import com.mojang.blaze3d.platform.GlStateManager;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.MinecraftClientGame;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import nl.dyonb.karam.registry.KaramBlockEntityTypes;
import nl.dyonb.karam.registry.KaramConfig;
import org.lwjgl.opengl.GL11;

public class EnderStopperBlockEntity extends BlockEntity {
    public EnderStopperBlockEntity() {
        super(KaramBlockEntityTypes.ENDERSTOPPER);
    }

    private int range = KaramConfig.CONFIG.enderStopperBlockRange;
    public boolean showBorder = false;
    public Box area = new Box(this.pos.add(-range, -range, -range), this.pos.add(range + 1, range + 1, range + 1));

    public boolean isEntityCloseEnough(LivingEntity livingEntity) {
        Box area = new Box(this.pos.add(-range, -range, -range), this.pos.add(range + 1, range + 1, range + 1));
        return area == null || livingEntity == null || livingEntity.getBoundingBox() == null || area.intersects(livingEntity.getBoundingBox());
    }
}

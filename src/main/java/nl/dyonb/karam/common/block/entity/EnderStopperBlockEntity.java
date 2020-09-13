package nl.dyonb.karam.common.block.entity;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Box;
import nl.dyonb.karam.registry.KaramBlockEntityTypes;
import nl.dyonb.karam.registry.KaramConfig;

public class EnderStopperBlockEntity extends BlockEntity {
    public EnderStopperBlockEntity() {
        super(KaramBlockEntityTypes.ENDERSTOPPER);
    }

    private int range = KaramConfig.CONFIG.enderStopperBlockRange;

    public boolean isEntityCloseEnough(LivingEntity livingEntity) {
        Box area = new Box(this.pos.add(-range, -range, -range), this.pos.add(range + 1, range + 1, range + 1));
        return area == null || livingEntity == null || livingEntity.getBoundingBox() == null || area.intersects(livingEntity.getBoundingBox());
    }
}

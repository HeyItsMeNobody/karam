package nl.dyonb.karam.common.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.Material;
import net.minecraft.block.WallTorchBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.world.BlockView;
import nl.dyonb.karam.common.block.entity.EnderStopperBlockEntity;
import nl.dyonb.karam.registry.KaramBlocks;

public class WallEnderStopperBlock extends WallTorchBlock implements BlockEntityProvider {
    public WallEnderStopperBlock() {
        super(FabricBlockSettings.of(Material.SUPPORTED).noCollision().breakInstantly().lightLevel(14).sounds(BlockSoundGroup.WOOD), ParticleTypes.FLAME);
    }

    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return new EnderStopperBlockEntity();
    }
}

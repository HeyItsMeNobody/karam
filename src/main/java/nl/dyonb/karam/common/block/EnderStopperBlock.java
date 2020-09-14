package nl.dyonb.karam.common.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.TorchBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ToolMaterials;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import nl.dyonb.karam.common.block.entity.EnderStopperBlockEntity;
import nl.dyonb.karam.registry.KaramBlockEntityTypes;

public class EnderStopperBlock extends TorchBlock implements BlockEntityProvider {
    public EnderStopperBlock() {
        super(FabricBlockSettings.of(Material.SUPPORTED).noCollision().breakInstantly().lightLevel(14).sounds(BlockSoundGroup.WOOD), ParticleTypes.FLAME);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        EnderStopperBlockEntity enderStopperBlockEntity = (EnderStopperBlockEntity) world.getBlockEntity(pos);
        enderStopperBlockEntity.showBorder = !enderStopperBlockEntity.showBorder;
        if (world.isClient) {
            MinecraftClient.getInstance().player.sendMessage(new TranslatableText("block.karam.enderstopper.show_border", enderStopperBlockEntity.showBorder), true);
        }
        return ActionResult.SUCCESS;
    }

    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return new EnderStopperBlockEntity();
    }
}

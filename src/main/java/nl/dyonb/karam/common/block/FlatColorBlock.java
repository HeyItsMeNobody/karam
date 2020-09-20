package nl.dyonb.karam.common.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterials;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import nl.dyonb.karam.common.block.entity.BaseColorBlockEntity;
import nl.dyonb.karam.registry.KaramBlockEntityTypes;
import org.jetbrains.annotations.Nullable;

public class FlatColorBlock extends Block implements BaseColorBlockInterface, BlockEntityProvider {
    public FlatColorBlock() {
        super(FabricBlockSettings.of(Material.STONE).breakByTool(FabricToolTags.PICKAXES, ToolMaterials.WOOD.getMiningLevel())
                .strength(1F, 1F).sounds(BlockSoundGroup.STONE));
    }

    // Colors
    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        BaseColorBlockInterface.onPlaced(world, pos, state, placer, itemStack);
    }

    @Override
    public void afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack stack) {
        BaseColorBlockInterface.afterBreak(world, player, pos, state, blockEntity, stack);
    }

    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return new BaseColorBlockEntity(KaramBlockEntityTypes.BASE_COLOR);
    }
}

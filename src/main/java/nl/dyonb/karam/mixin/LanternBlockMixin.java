package nl.dyonb.karam.mixin;

import jdk.internal.jline.internal.Nullable;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.LanternBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import nl.dyonb.karam.common.block.BaseColorBlockInterface;
import nl.dyonb.karam.common.block.entity.LanternBlockEntity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(LanternBlock.class)
public abstract class LanternBlockMixin implements BaseColorBlockInterface, BlockEntityProvider {

    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        BaseColorBlockInterface.onPlaced(world, pos, state, placer, itemStack);
    }

    public void afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack stack) {
        BaseColorBlockInterface.afterBreak(world, player, pos, state, blockEntity, stack);
    }

    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return new LanternBlockEntity();
    }

}

package nl.dyonb.karam.mixin;

import net.minecraft.block.Block;
import nl.dyonb.karam.Karam;
import org.apache.logging.log4j.Level;
import org.jetbrains.annotations.Nullable;
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
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.SoftOverride;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

// the @Intrinsic basically states "please don't apply this mixin method if the original already has a method with the same name"

@SuppressWarnings("UnresolvedMixinReference")
@Mixin(LanternBlock.class)
public class LanternBlockMixin extends Block implements BaseColorBlockInterface, BlockEntityProvider {

    public LanternBlockMixin(Settings settings) {
        super(settings);
        throw new UnsupportedOperationException("mixin not transformed"); //should NEVER happen
    }

    @Intrinsic
    @Override
    public void afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack stack) {
        super.afterBreak(world, player, pos, state, blockEntity, stack);
    }

    @Intrinsic
    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        super.onPlaced(world, pos, state, placer, itemStack);
    }

    @Inject(at = @At("HEAD"), method = "onPlaced(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/item/ItemStack;)V", cancellable = true)
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack, CallbackInfo ci) {
        BaseColorBlockInterface.onPlaced(world, pos, state, placer, itemStack);
        ci.cancel();
    }

    @Inject(at = @At("HEAD"), method = "afterBreak(Lnet/minecraft/world/World;Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;Lnet/minecraft/block/entity/BlockEntity;Lnet/minecraft/item/ItemStack;)V", cancellable = true)
    public void afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack stack, CallbackInfo ci) {
        BaseColorBlockInterface.afterBreak(world, player, pos, state, blockEntity, stack);
        ci.cancel();
    }

    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return new LanternBlockEntity();
    }
}

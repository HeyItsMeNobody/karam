package nl.dyonb.karam.registry;

import com.google.common.collect.ImmutableSet;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.LanternBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.registry.Registry;
import nl.dyonb.karam.Karam;
import nl.dyonb.karam.common.block.WallEnderStopperBlock;
import nl.dyonb.karam.common.block.entity.*;

import java.util.function.Supplier;

public class KaramBlockEntityTypes {

    public static final BlockEntityType<ElevatorBlockEntity> ELEVATOR = register("elevator", ElevatorBlockEntity::new, KaramBlocks.ELEVATOR);
    public static final BlockEntityType<RgbifierBlockEntity> RGBIFIER = register("rgbifier", RgbifierBlockEntity::new, KaramBlocks.RGBIFIER);
    public static final BlockEntityType<LanternBlockEntity> LANTERN = register("lantern", LanternBlockEntity::new, Blocks.LANTERN);
    public static final BlockEntityType<EnderStopperBlockEntity> ENDERSTOPPER = register("enderstopper", EnderStopperBlockEntity::new, KaramBlocks.ENDERSTOPPER, KaramBlocks.WALL_ENDERSTOPPER);

    public static void initialize() {
        // Not used here
    }

    /**
     * @param name
     *        Name of BlockEntityType instance to be registered
     * @param supplier
     *        Supplier of BlockEntity to use for BlockEntityType
     * @param supportedBlocks
     *        Blocks the BlockEntity can be attached to
     * @return Registered BlockEntityType
     */
    public static <B extends BlockEntity> BlockEntityType<B> register(String name, Supplier<B> supplier, Block... supportedBlocks) {
        return Registry.register(Registry.BLOCK_ENTITY_TYPE, Karam.identifier(name), BlockEntityType.Builder.create(supplier, supportedBlocks).build(null));
    }

}

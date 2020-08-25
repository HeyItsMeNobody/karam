package nl.dyonb.karam.registry;

import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.registry.Registry;
import nl.dyonb.karam.Karam;
import nl.dyonb.karam.common.block.entity.ElevatorBlockEntity;

import java.util.function.Supplier;

public class KaramBlockEntityTypes {

    public static final BlockEntityType<ElevatorBlockEntity> ELEVATOR = register("elevator", ElevatorBlockEntity::new, KaramBlocks.ELEVATOR);

    public static void initialize() {
        // Not used here
    }

    /**
     * @param name
     *        Name of BlockEntityType instance to be registered
     * @param supplier
     *        Supplier of BlockEntity to use for BlockEntityType
     * @param supportedBlock
     *        Block the BlockEntity can be attached to
     * @return Registered BlockEntityType
     */
    public static <B extends BlockEntity> BlockEntityType<B> register(String name, Supplier<B> supplier, Block supportedBlock) {
        return Registry.register(Registry.BLOCK_ENTITY_TYPE, Karam.identifier(name), BlockEntityType.Builder.create(supplier, supportedBlock).build(null));
    }

}

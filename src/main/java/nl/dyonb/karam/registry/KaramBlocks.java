package nl.dyonb.karam.registry;

import net.minecraft.block.Block;
import net.minecraft.block.EnderChestBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.WallStandingBlockItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import nl.dyonb.karam.Karam;
import nl.dyonb.karam.common.block.ElevatorBlock;
import nl.dyonb.karam.common.block.EnderStopperBlock;
import nl.dyonb.karam.common.block.RgbifierBlock;
import nl.dyonb.karam.common.block.WallEnderStopperBlock;

public class KaramBlocks {

    private static ElevatorBlock elevatorBlock = new ElevatorBlock();
    public static BlockItem elevatorBlockItem = new BlockItem(elevatorBlock, KaramItems.getBasicItemSettings());
    public static final Block ELEVATOR = register("elevator", elevatorBlock, elevatorBlockItem);

    public static final Block RGBIFIER = register("rgbifier", new RgbifierBlock(), KaramItems.getBasicItemSettings());

    // Set-up for the Ender Stopper
    private static final EnderStopperBlock ENDERSTOPPER_BLOCK_STANDING = new EnderStopperBlock();
    private static final WallEnderStopperBlock ENDERSTOPPER_BLOCK_WALL = new WallEnderStopperBlock();
    public static final WallStandingBlockItem ENDERSTOPPER_ITEM = new WallStandingBlockItem(ENDERSTOPPER_BLOCK_STANDING, ENDERSTOPPER_BLOCK_WALL, KaramItems.getBasicItemSettings());
    // Registering the blocks for the Ender Stopper
    public static final Block ENDERSTOPPER = register("enderstopper", ENDERSTOPPER_BLOCK_STANDING, ENDERSTOPPER_ITEM);
    public static final Block WALL_ENDERSTOPPER = register("wall_enderstopper", ENDERSTOPPER_BLOCK_WALL);

    public static void initialize() {
        // Not used here
    }

    /**
     * @param name
     *        Name of block instance to be registered
     * @param block
     *        Block instance to be registered
     * @param settings
     *        Item.Settings of BlockItem of Block instance to be registered
     * @return Block instance registered
     */
    public static <T extends Block> T register(String name, T block, Item.Settings settings) {
        return register(name, block, new BlockItem(block, settings));
    }

    /**
     * @param name
     *        Name of block instance to be registered
     * @param block
     *        Block instance to be registered
     * @param item
     *        BlockItem instance of Block to be registered
     * @return Block instance registered
     */
    public static <T extends Block> T register(String name, T block, BlockItem item) {
        T b = register(Karam.identifier(name), block);
        if (item != null) {
            KaramItems.register(name, item);
        }
        return b;
    }

    /**
     * @param name
     *        Name of block instance to be registered
     * @param block
     *        Block instance to be registered
     * @return Block instance registered
     */
    public static <T extends Block> T register(String name, T block) {
        return register(Karam.identifier(name), block);
    }

    /**
     * @param name
     *        Identifier of block instance to be registered
     * @param block
     *        Block instance to be registered
     * @return Block instance registered
     */
    public static <T extends Block> T register(Identifier name, T block) {
        return Registry.register(Registry.BLOCK, name, block);
    }

}

package nl.dyonb.karam.registry;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.MaterialColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import nl.dyonb.karam.Karam;
import nl.dyonb.karam.common.block.ElevatorBlock;
import nl.dyonb.karam.common.block.item.ElevatorBlockItem;

public class KaramBlocks {

    private static ElevatorBlock elevatorBlock = new ElevatorBlock();
    public static ElevatorBlockItem elevatorBlockItem = new ElevatorBlockItem(elevatorBlock, KaramItems.getBasicItemSettings());
    public static final Block ELEVATOR = register("elevator", elevatorBlock, elevatorBlockItem);

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

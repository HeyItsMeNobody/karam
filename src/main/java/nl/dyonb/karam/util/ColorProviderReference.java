package nl.dyonb.karam.util;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import nl.dyonb.karam.registry.KaramBlocks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ColorProviderReference {

    public static List<Block> blockList() {
        List<Block> newBlockList = new ArrayList<Block>();
        newBlockList.addAll(Arrays.asList(KaramBlocks.ELEVATOR, Blocks.LANTERN));
        return newBlockList;
    }

    public static List<Item> itemList() {
        List<Item> newItemList = new ArrayList<Item>();
        newItemList.addAll(Arrays.asList(KaramBlocks.elevatorBlockItem, Items.LANTERN));
        return newItemList;
    }

}

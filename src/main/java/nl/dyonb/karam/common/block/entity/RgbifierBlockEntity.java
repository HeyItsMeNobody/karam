package nl.dyonb.karam.common.block.entity;

import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Tickable;
import net.minecraft.util.collection.DefaultedList;
import nl.dyonb.karam.common.ImplementedInventory;
import nl.dyonb.karam.common.screen.RgbifierScreenHandler;
import nl.dyonb.karam.registry.KaramBlockEntityTypes;
import nl.dyonb.karam.registry.KaramBlocks;

public class RgbifierBlockEntity extends BlockEntity implements NamedScreenHandlerFactory, ImplementedInventory, BlockEntityClientSerializable, Tickable {
    private DefaultedList<ItemStack> inventory = DefaultedList.ofSize(1, ItemStack.EMPTY);
    int currentColor;

    //PropertyDelegate is an interface which we will implement inline here.
    //It can normally contain multiple integers as data identified by the index, but in this example we only have one.
    private final PropertyDelegate propertyDelegate = new PropertyDelegate() {
        @Override
        public int get(int index) {
            return currentColor;
        }

        @Override
        public void set(int index, int value) {
            currentColor = value;
        }

        //this is supposed to return the amount of integers you have in your delegate, in our example only one
        @Override
        public int size() {
            return 1;
        }
    };

    public RgbifierBlockEntity() {
        super(KaramBlockEntityTypes.RGBIFIER);
    }

    //From the ImplementedInventory Interface
    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    //These Methods are from the NamedScreenHandlerFactory Interface
    //createMenu creates the ScreenHandler itself
    //getDisplayName will Provide its name which is normally shown at the top
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        //We provide *this* to the screenHandler as our class Implements Inventory
        //Only the Server has the Inventory at the start, this will be synced to the client in the ScreenHandler
        return new RgbifierScreenHandler(syncId, playerInventory, this, propertyDelegate);
    }

    @Override
    public Text getDisplayName() {
        return new TranslatableText(getCachedState().getBlock().getTranslationKey());
    }

    @Override
    public void tick() {
        ItemStack itemStack = inventory.get(0);
        int colorInTick = ElevatorBlockEntity.getColorFromItemStack(itemStack);

        if (colorInTick != currentColor) {
            currentColor = colorInTick;
            propertyDelegate.set(0, currentColor);
            if (!this.getWorld().isClient()) {
                sync();
            }
            System.out.println(currentColor);
        }
    }

    //    @Override
//    public void fromTag(BlockState state, CompoundTag tag) {
//        super.fromTag(state, tag);
//        inventory = DefaultedList.ofSize(1, ItemStack.EMPTY);
//        Inventories.fromTag(tag, this.inventory);
//
//        ItemStack itemStack = inventory.get(0);
//        currentColor = ElevatorBlockEntity.getColorFromItemStack(itemStack);
//        System.out.println(currentColor);
//    }
//
//    @Override
//    public CompoundTag toTag(CompoundTag tag) {
//        super.toTag(tag);
//        Inventories.toTag(tag, this.inventory);
//        return tag;
//    }

    @Override
    public void fromTag(BlockState state, CompoundTag tag) {
        super.fromTag(state, tag);
        fromClientTag(tag);
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        super.toTag(tag);
        return toClientTag(tag);
    }

    @Override
    public void fromClientTag(CompoundTag tag) {
        inventory = DefaultedList.ofSize(1, ItemStack.EMPTY);
        Inventories.fromTag(tag, this.inventory);

//        ItemStack itemStack = inventory.get(0);
//        currentColor = ElevatorBlockEntity.getColorFromItemStack(itemStack);
        //System.out.println(currentColor);
    }

    @Override
    public CompoundTag toClientTag(CompoundTag tag) {
        Inventories.toTag(tag, this.inventory);
        return tag;
    }
}

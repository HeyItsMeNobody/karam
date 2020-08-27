package nl.dyonb.karam.common.block.entity;

import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
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

public class RgbifierBlockEntity extends BlockEntity implements NamedScreenHandlerFactory, ImplementedInventory, BlockEntityClientSerializable, Tickable {
    private DefaultedList<ItemStack> inventory = DefaultedList.ofSize(1, ItemStack.EMPTY);

    // PropertyDelegate is an interface which we will implement inline here.
    // It can normally contain multiple integers as data identified by the index, but in this example we only have one.
    private final PropertyDelegate propertyDelegate = new PropertyDelegate() {
        private int currentColor;

        @Override
        public int get(int index) {
            return this.currentColor;
        }

        @Override
        public void set(int index, int value) {
            this.currentColor = value;
        }

        // this is supposed to return the amount of integers you have in your delegate, in our example only one
        @Override
        public int size() {
            return 1;
        }
    };

    public RgbifierBlockEntity() {
        super(KaramBlockEntityTypes.RGBIFIER);
    }

    // From the ImplementedInventory Interface
    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    // These Methods are from the NamedScreenHandlerFactory Interface
    // createMenu creates the ScreenHandler itself
    // getDisplayName will Provide its name which is normally shown at the top
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        // We provide *this* to the screenHandler as our class Implements Inventory
        // Only the Server has the Inventory at the start, this will be synced to the client in the ScreenHandler
        return new RgbifierScreenHandler(syncId, playerInventory, this, propertyDelegate);
    }

    @Override
    public Text getDisplayName() {
        return new TranslatableText(getCachedState().getBlock().getTranslationKey());
    }

    @Override
    public void tick() {
        // Note: You really don't need to tick this, but the code isn't computationally expensive, so it doesn't matter.
        // If you do want to get rid of ticking, just handle this when the server receives a packet from the client to change the color.
        if (this.getWorld() == null)
            return;

        ItemStack itemStack = inventory.get(0);
        int colorInTick = ElevatorBlockEntity.getColorFromItemStack(itemStack);

        // Handles Setting the ItemStack Color From PropertyDelegate
        if (colorInTick != getColor()) {
            // TODO: Create a client to server packet to update propertyDelegate so this works properly: https://discordapp.com/channels/507304429255393322/507982478276034570/748285835836915812
            ElevatorBlockEntity.setColorForItemStack(inventory.get(0), getColor());
            sync();

            // Only runs on the server.
            System.out.println("Client: " + this.getWorld().isClient() + " - Color: " + getColor());
        }
    }

    public int getColor() {
        return this.propertyDelegate.get(0);
    }

    public void setColor(int color) {
        this.propertyDelegate.set(0, color);
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        // Sets propertyDelegate to current color of ItemStack
        ImplementedInventory.super.setStack(slot, stack);

        this.setColor(ElevatorBlockEntity.getColorFromItemStack(stack));
    }

    @Override
    public void fromTag(BlockState state, CompoundTag tag) {
        super.fromTag(state, tag);
        this.fromClientTag(tag);
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        super.toTag(tag);

        return this.toClientTag(tag);
    }

    @Override
    public void fromClientTag(CompoundTag tag) {
        inventory = DefaultedList.ofSize(1, ItemStack.EMPTY);
        Inventories.fromTag(tag, this.inventory);
    }

    @Override
    public CompoundTag toClientTag(CompoundTag tag) {
        Inventories.toTag(tag, this.inventory);

        return tag;
    }
}

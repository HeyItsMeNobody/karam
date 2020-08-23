package nl.dyonb.karam.common.screen;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.AirBlockItem;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import nl.dyonb.karam.Karam;
import nl.dyonb.karam.common.item.DevNullItem;
import nl.dyonb.karam.registry.KaramScreenHandlers;
import org.apache.logging.log4j.Level;

// ScreenHandler classes are used to synchronize GUI state between the server and the client
public class DevNullScreenHandler extends ScreenHandler {
    private final Inventory inventory;

    //This constructor gets called on the client when the server wants it to open the screenHandler,
    //The client will call the other constructor with an empty Inventory and the screenHandler will automatically
    //sync this empty inventory with the inventory on the server.
    public DevNullScreenHandler(int syncId, PlayerInventory playerInventory) {
        //this(syncId, playerInventory, new SimpleInventory(9));
        this(syncId, playerInventory, new SimpleInventory(1));
    }

    //This constructor gets called from the BlockEntity on the server without calling the other constructor first, the server knows the inventory of the container
    //and can therefore directly provide it as an argument. This inventory will then be synced to the client.
    public DevNullScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory) {
        super(KaramScreenHandlers.DEV_NULL_SCREEN_HANDLER, syncId);
        //checkSize(inventory, 9);
        checkSize(inventory, 1);
        this.inventory = inventory;
        //some inventories do custom logic when a player opens it.
        inventory.onOpen(playerInventory.player);

        //This will place the slot in the correct locations for a 3x3 Grid. The slots exist on both server and client!
        //This will not render the background of the slots however, this is the Screens job
        int m;
        int l;
        //Our inventory
        this.addSlot(new Slot(inventory, 0, 62 + 18, 17 + 18));
        //The player inventory
        for (m = 0; m < 3; ++m) {
            for (l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + m * 9 + 9, 8 + l * 18, 84 + m * 18));
            }
        }
        //The player Hotbar
        for (m = 0; m < 9; ++m) {
            this.addSlot(new Slot(playerInventory, m, 8 + m * 18, 142));
        }

    }

    @Override
    public ItemStack onSlotClick(int slotId, int clickData, SlotActionType actionType, PlayerEntity playerEntity) {
        if (slotId >= 0) { // slotId < 0 are used for networking internals
            ItemStack stack = getSlot(slotId).getStack();

            if (stack.getItem() instanceof DevNullItem) {
                // Prevent moving the DevNullItem around
                return stack;
            } else if (slotId == 0 && !stack.isEmpty()) {
                // If the slotId is 0 and there is already an item in it remove it and return an empty ItemStack
                this.setStackInSlot(slotId, ItemStack.EMPTY);
                return ItemStack.EMPTY;
            } else if (slotId == 0) {
                // Copy the cursor ItemStack
                ItemStack copiedCursorStack = playerEntity.inventory.getCursorStack().copy();
                // Make the count of the item only 1
                if (copiedCursorStack.getCount() != 1) {
                    copiedCursorStack.decrement(copiedCursorStack.getCount() - 1);
                }
                // Put the cursor stack inside the slot
                this.setStackInSlot(slotId, copiedCursorStack);
                // Return a stack so it doesn't do anything further
                return copiedCursorStack;
            }
        }

        return super.onSlotClick(slotId, clickData, actionType, playerEntity);
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    // Disable shift-click
    @Override
    public ItemStack transferSlot(PlayerEntity player, int invSlot) {
        return ItemStack.EMPTY;
    }
}



package nl.dyonb.karam.common.item.inventory;

import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.collection.DefaultedList;
import nl.dyonb.karam.common.ImplementedInventory;

public final class DevNullInventory implements ImplementedInventory {
    private final ItemStack stack;
    //private final DefaultedList<ItemStack> items = DefaultedList.ofSize(9, ItemStack.EMPTY);
    private final DefaultedList<ItemStack> items = DefaultedList.ofSize(1, ItemStack.EMPTY);

    public DevNullInventory(ItemStack stack) {
        this.stack = stack;
        CompoundTag tag = stack.getSubTag("Items");

        if (tag != null) {
            Inventories.fromTag(tag, items);
        }
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return items;
    }

    @Override
    public void markDirty() {
        CompoundTag tag = stack.getOrCreateSubTag("Items");
        Inventories.toTag(tag, items);
    }
}
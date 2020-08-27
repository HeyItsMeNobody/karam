package nl.dyonb.karam.util;

import net.fabricmc.fabric.api.util.NbtType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;

public class ColorHelper {

    /**
     * @param stack
     *        An ItemStack to get the nbt color from
     * @return The color integer.
     */
    public static int getColorFromItemStack(ItemStack stack) {
        CompoundTag nbt = stack.getOrCreateTag();

        int color = 16777215;
        if (nbt.contains("color", NbtType.INT)) { // nbt.contains(String, int) allows you to check for a subtag of a specific type, very cool
            color = nbt.getInt("color");
        } else if (nbt.contains("BlockEntityTag") && nbt.getCompound("BlockEntityTag").contains("color", NbtType.INT)) {
            color = nbt.getCompound("BlockEntityTag").getInt("color");
        } else if (nbt.contains("color", NbtType.STRING)) {
            try {
                color = Integer.parseInt(nbt.getString("color"));
            } catch (NumberFormatException ignored) { }
        }

        return color;
    }

}

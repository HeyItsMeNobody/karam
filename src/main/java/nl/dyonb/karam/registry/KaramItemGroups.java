package nl.dyonb.karam.registry;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import nl.dyonb.karam.Karam;

import java.util.function.Supplier;

public class KaramItemGroups {

    public static final ItemGroup KARAM_ITEM_GROUP = register("main", () -> KaramItems.DEV_NULL);

    public static void initialize() {
        // Not used here
    }

    /**
     * @param id
     *        Id of Item Group instance to be registered
     * @param icon
     *        ItemConvertible instance for the icon
     * @return The ItemGroup registered
     */
    public static ItemGroup register(String id, Supplier<ItemConvertible> icon) {
        return FabricItemGroupBuilder.build(Karam.identifier(id), () -> new ItemStack(icon.get()));
    }
}

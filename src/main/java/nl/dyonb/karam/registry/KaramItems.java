package nl.dyonb.karam.registry;

import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import nl.dyonb.karam.Karam;
import nl.dyonb.karam.common.item.DevNullItem;

import static nl.dyonb.karam.registry.KaramItemGroups.KARAM_ITEM_GROUP;

public class KaramItems {

    public static final Item DEV_NULL = register("dev_null", new DevNullItem(getBasicItemSettings().maxCount(1)));

    public static void initialize() {
        // Not used here
    }

    /**
     * @param name
     *        Name of item instance to be registered
     * @param item
     *        Item instance to be registered
     * @return Item instanced registered
     */
    public static <T extends Item> T register(String name, T item) {
        return register(Karam.identifier(name), item);
    }

    /**
     * @param name
     *        Identifier of item instance to be registered
     * @param item
     *        Item instance to be registered
     * @return Item instance registered
     */
    public static <T extends Item> T register(Identifier name, T item) {
        return Registry.register(Registry.ITEM, name, item);
    }

    /**
     * @return Basic Item.Settings
     */
    public static Item.Settings getBasicItemSettings() {
        return new Item.Settings().group(KARAM_ITEM_GROUP);
    }

}

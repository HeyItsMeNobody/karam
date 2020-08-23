package nl.dyonb.karam.registry;

import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.ConfigData;
import me.sargunvohra.mcmods.autoconfig1u.annotation.Config;
import me.sargunvohra.mcmods.autoconfig1u.annotation.ConfigEntry;
import me.sargunvohra.mcmods.autoconfig1u.serializer.JanksonConfigSerializer;
import me.sargunvohra.mcmods.autoconfig1u.shadowed.blue.endless.jankson.Comment;
import nl.dyonb.karam.Karam;

@Config(name = Karam.MOD_ID)
public class KaramConfig implements ConfigData {

    @ConfigEntry.Gui.Excluded
    public static KaramConfig config = new KaramConfig();

    @Comment("Destroy item instead of leaving it on the ground when using the /dev/null item")
    public boolean destroyItemDevNull = false;

    public static void initialize() {
        AutoConfig.register(KaramConfig.class, JanksonConfigSerializer::new);
        config = AutoConfig.getConfigHolder(KaramConfig.class).getConfig();
    }

}

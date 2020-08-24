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
    public static KaramConfig CONFIG = new KaramConfig();

    // dev_null
    @Comment("Destroy item instead of leaving it on the ground when using the /dev/null item")
    public boolean destroyItemDevNull = false;

    // elevator
    @Comment("Max distance between elevators")
    public int maxBlocksBetweenElevator = 20;
    @Comment("Max solid blocks between elevators")
    public int maxSolidBlocksBetweenElevator = 4;
    @Comment("Ignore elevator obstructions")
    public boolean ignoreObstructedElevator = true;
    @Comment("Deactivate elevator when powered")
    public boolean deactivateElevatorOnRedstonePower = true;

    public static void initialize() {
        AutoConfig.register(KaramConfig.class, JanksonConfigSerializer::new);
        CONFIG = AutoConfig.getConfigHolder(KaramConfig.class).getConfig();
    }

}

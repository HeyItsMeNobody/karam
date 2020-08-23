package nl.dyonb.karam.client;

import io.github.prospector.modmenu.api.ConfigScreenFactory;
import io.github.prospector.modmenu.api.ModMenuApi;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import nl.dyonb.karam.Karam;
import nl.dyonb.karam.registry.KaramConfig;

@Environment(EnvType.CLIENT)
public class KaramModMenuIntegration implements ModMenuApi {

    @Override
    public String getModId() {
        return Karam.MOD_ID; // Return your modid here
    }

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> AutoConfig.getConfigScreen(KaramConfig.class, parent).get();
    }

}

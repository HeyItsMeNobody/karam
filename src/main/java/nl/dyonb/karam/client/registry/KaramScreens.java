package nl.dyonb.karam.client.registry;

import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.minecraft.client.gui.screen.Screen;
import nl.dyonb.karam.client.screen.DevNullScreen;
import nl.dyonb.karam.client.screen.RgbifierScreen;
import nl.dyonb.karam.registry.KaramScreenHandlers;

public class KaramScreens {

    public static void initialize() {
        ScreenRegistry.register(KaramScreenHandlers.DEV_NULL_SCREEN_HANDLER, DevNullScreen::new);

        ScreenRegistry.register(KaramScreenHandlers.RGBIFIER_SCREEN_HANDLER, RgbifierScreen::new);
    }

}

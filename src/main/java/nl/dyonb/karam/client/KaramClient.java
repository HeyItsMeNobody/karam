package nl.dyonb.karam.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import nl.dyonb.karam.client.registry.KaramClientPackets;
import nl.dyonb.karam.client.registry.KaramColorProviders;
import nl.dyonb.karam.client.registry.KaramScreens;
import nl.dyonb.karam.client.screen.DevNullScreen;
import nl.dyonb.karam.registry.KaramScreenHandlers;

@Environment(EnvType.CLIENT)
public class KaramClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        KaramClientPackets.initialize();
        // register a screen
        KaramScreens.initialize();
        // register a colorprovider
        KaramColorProviders.initialize();
    }

}

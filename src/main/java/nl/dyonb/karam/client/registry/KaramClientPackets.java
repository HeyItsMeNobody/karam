package nl.dyonb.karam.client.registry;

import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import nl.dyonb.karam.client.screen.RgbifierScreen;
import nl.dyonb.karam.util.PacketReference;

public class KaramClientPackets {

    public static void initialize() {
        ClientSidePacketRegistry.INSTANCE.register(PacketReference.RGBIFIER_COLOR_TO_CLIENT,
                (packetContext, attachedData) -> {
                    // Read in the correct, networking thread
                    int color = attachedData.readInt();
                    packetContext.getTaskQueue().execute(() -> {
                        /*
                         * Use num and str in the correct, main thread
                         */
                        Screen screen = MinecraftClient.getInstance().currentScreen;
                        if (screen != null && screen instanceof RgbifierScreen) {
                            RgbifierScreen rgbifierScreen = (RgbifierScreen) screen;

                            rgbifierScreen.updateColorsInGui(color);
                        }
                    });
                });
    }

}

package nl.dyonb.karam.registry;

import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.screen.ScreenHandler;
import nl.dyonb.karam.util.PacketReference;

public class KaramServerPackets {

    public static void initialize() {
        ServerSidePacketRegistry.INSTANCE.register(PacketReference.RGBIFIER_COLOR_TO_SERVER, (packetContext, attachedData) -> {
            int color = attachedData.readInt();
            int syncId = attachedData.readInt();
            packetContext.getTaskQueue().execute(() -> {
                // Execute on the main thread

                ScreenHandler currentScreenHandler = packetContext.getPlayer().currentScreenHandler;
                if (currentScreenHandler.syncId == syncId) {
                    ItemStack itemStack = currentScreenHandler.getSlot(0).getStack();

                    CompoundTag compoundTag = new CompoundTag();
                    compoundTag.putInt("color", color);

                    itemStack.setTag(compoundTag);
                }
            });
        });
    }

}

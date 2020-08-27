package nl.dyonb.karam.common.block.entity;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.fabricmc.fabric.api.server.PlayerStream;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Tickable;
import net.minecraft.util.collection.DefaultedList;
import nl.dyonb.karam.common.ImplementedInventory;
import nl.dyonb.karam.common.screen.RgbifierScreenHandler;
import nl.dyonb.karam.registry.KaramBlockEntityTypes;
import nl.dyonb.karam.util.PacketReference;

import java.util.UUID;

public class RgbifierBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory, ImplementedInventory, BlockEntityClientSerializable {
    private DefaultedList<ItemStack> inventory = DefaultedList.ofSize(1, ItemStack.EMPTY);
    private int currentColor;
    private UUID playerUUID;

    public RgbifierBlockEntity() {
        super(KaramBlockEntityTypes.RGBIFIER);
    }

    // From the ImplementedInventory Interface
    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    // These Methods are from the NamedScreenHandlerFactory Interface
    // createMenu creates the ScreenHandler itself
    // getDisplayName will Provide its name which is normally shown at the top
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        playerUUID = player.getUuid();
        // We provide *this* to the screenHandler as our class Implements Inventory
        // Only the Server has the Inventory at the start, this will be synced to the client in the ScreenHandler
        return new RgbifierScreenHandler(syncId, playerInventory, this);
    }

    @Override
    public Text getDisplayName() {
        return new TranslatableText(getCachedState().getBlock().getTranslationKey());
    }

    public int getColor() {
        return currentColor;
    }

    public void setColor(int color) {
        currentColor = color;
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        ImplementedInventory.super.setStack(slot, stack);

        currentColor = ElevatorBlockEntity.getColorFromItemStack(stack);

        sendColorDataToClient(currentColor);
    }

    @Override
    public void onClose(PlayerEntity player) {
        this.inventory.get(0);
    }

    public void sendColorDataToClient(int color) {
        if (!this.getWorld().isClient()) {
            PacketByteBuf passedData = new PacketByteBuf(Unpooled.buffer());
            passedData.writeInt(color);
            PlayerStream.watching(this).forEach(playerEntity -> {
                if (playerUUID == playerEntity.getUuid()) {
                    ServerSidePacketRegistry.INSTANCE.sendToPlayer(playerEntity, PacketReference.RGBIFIER_COLOR_TO_CLIENT, passedData);
                }
            });
        }
    }

    @Override
    public void fromTag(BlockState state, CompoundTag tag) {
        super.fromTag(state, tag);
        this.fromClientTag(tag);
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        super.toTag(tag);

        return this.toClientTag(tag);
    }

    @Override
    public void fromClientTag(CompoundTag tag) {
        inventory = DefaultedList.ofSize(1, ItemStack.EMPTY);
        Inventories.fromTag(tag, this.inventory);
    }

    @Override
    public CompoundTag toClientTag(CompoundTag tag) {
        Inventories.toTag(tag, this.inventory);

        return tag;
    }

    // This is used for sending initial color data to the Screen
    @Override
    public void writeScreenOpeningData(ServerPlayerEntity serverPlayerEntity, PacketByteBuf packetByteBuf) {
        // Write the current items color
        packetByteBuf.writeInt(ElevatorBlockEntity.getColorFromItemStack(this.inventory.get(0)));
    }
}

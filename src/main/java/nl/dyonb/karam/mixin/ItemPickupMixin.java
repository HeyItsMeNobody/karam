package nl.dyonb.karam.mixin;

import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import nl.dyonb.karam.Karam;
import nl.dyonb.karam.common.item.DevNullItem;
import nl.dyonb.karam.common.item.inventory.DevNullInventory;
import nl.dyonb.karam.registry.KaramConfig;
import nl.dyonb.karam.registry.KaramItems;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Set;

@Mixin(ItemEntity.class)
public class ItemPickupMixin {
	//@Inject(at=@At("HEAD"), method="onPlayerCollision")
	@Inject(at=@At(value="INVOKE",target="net/minecraft/entity/player/PlayerInventory.insertStack(Lnet/minecraft/item/ItemStack;)Z"), method="onPlayerCollision", locals = LocalCapture.CAPTURE_FAILEXCEPTION, cancellable = true)
	private void beforePlayerCollision(PlayerEntity player, CallbackInfo ci, ItemStack itemStack, Item item, int i) {
		// Iterate through players inventory
		player.inventory.main.forEach(currentInventoryStack -> {
			// See if it's an DevNullItem
			if (currentInventoryStack.getItem() instanceof DevNullItem) {
				// Get the items inventory
				DevNullInventory devNullInventory = new DevNullInventory(currentInventoryStack);
				// Get the item to filter
				Item itemToFilter = devNullInventory.getItems().get(0).getItem();
				// Check if the item to filter is the same as the item being picked up
				if (itemToFilter == item) {
					// If destroy item is true, destroy the item.
					if (KaramConfig.config.destroyItemDevNull == true) {
						itemStack.decrement(itemStack.getCount());
						ci.cancel();
					} else {
						ci.cancel();
					}
				}
			}
		});
	}
}

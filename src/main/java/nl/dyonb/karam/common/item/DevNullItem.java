package nl.dyonb.karam.common.item;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import nl.dyonb.karam.common.item.inventory.DevNullInventory;
import nl.dyonb.karam.common.screen.DevNullScreenHandler;

import java.util.List;

public class DevNullItem extends Item {

    public DevNullItem(Settings settings) {
        super(settings);
    }

    // Add two tooltips for info
    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(new TranslatableText("item.karam.dev_null.tooltip_1"));

        // Get the items inventory
        DevNullInventory devNullInventory = new DevNullInventory(stack);
        // Get the item that is being filtered
        Item itemToFilter = devNullInventory.getItems().get(0).getItem();
        // Put that in the tooltip
        tooltip.add(new TranslatableText("item.karam.dev_null.tooltip_2", itemToFilter.getName()));
    }

    // Prevent use from being called when used on a block or entity.
    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        // Returns success without swinging the hand to prevent use() from being called.
        return ActionResult.success(false);
    }
    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        // Returns success without swinging the hand to prevent use() from being called.
        return ActionResult.success(false);
    }

    // Only gets called when pointed at air.
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity playerEntity, Hand hand) {
        // Check if the player is sneaking and if it's client side
        if (playerEntity.isSneaking()) {
            // Open the GUI
            playerEntity.openHandledScreen(createScreenHandlerFactory(playerEntity.getStackInHand(hand)));
            // Do client side things
            if (world.isClient()) {
                // Play a sound
                playerEntity.playSound(SoundEvents.BLOCK_ANVIL_FALL, 1.0F, 1.0F);
            }

            // Return SUCCESS to swing the hand
            return new TypedActionResult<>(ActionResult.SUCCESS, playerEntity.getStackInHand(hand));
        } else {
            // Return failed to prevent swinging the hand
            return new TypedActionResult<>(ActionResult.FAIL, playerEntity.getStackInHand(hand));
        }
    }

    /**
     * @param stack
     *        An ItemStack
     * @return A screenhandler
     */
    private NamedScreenHandlerFactory createScreenHandlerFactory(ItemStack stack) {
        return new SimpleNamedScreenHandlerFactory((syncId, inventory, player) -> {
            return new DevNullScreenHandler(syncId, inventory, new DevNullInventory(stack));
        }, stack.getName());
    }

}

package nl.dyonb.karam.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.client.options.DoubleOption;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import nl.dyonb.karam.Karam;
import nl.dyonb.karam.common.screen.RgbifierScreenHandler;

import java.awt.*;

// HandledScreen classes are fully client-sided and are responsible for drawing GUI elements.
public class RgbifierScreen extends HandledScreen<ScreenHandler> {
    private int color;
    private int red;
    private int green;
    private int blue;

    RgbifierScreenHandler screenHandler;

    //A path to the gui texture. In this example we use the texture from the dispenser
    private static final Identifier TEXTURE = Karam.identifier("textures/gui/container/dev_null.png");
    private final DoubleOption doubleOptionRed = new DoubleOption("karam.menu.red", 1.0F, 255.0F, 1F, (gameOptions) -> {
        // Gets the red value from the full color
        return (double) this.red;
    }, (gameOptions, double_) -> {
        // Set the new red
        this.red = double_.intValue();
        this.updateColor();
    }, (gameOptions, doubleOption) -> {
        return new TranslatableText("block.karam.rgbifier.red", this.red);
    });

    private final DoubleOption doubleOptionGreen = new DoubleOption("karam.menu.green", 1.0F, 255.0F, 1F, (gameOptions) -> {
        // Gets the green value from the full color
        return (double) this.green;
    }, (gameOptions, double_) -> {
        // Set the new green
        this.green = double_.intValue();
        this.updateColor();
    }, (gameOptions, doubleOption) -> {
        return new TranslatableText("block.karam.rgbifier.green", this.green);
    });

    private final DoubleOption doubleOptionBlue = new DoubleOption("karam.menu.blue", 1.0F, 255.0F, 1F, (gameOptions) -> {
        // Gets the blue value from the full color
        return (double) this.blue;
    }, (gameOptions, double_) -> {
        // Set the new blu4
        this.blue = double_.intValue();
        this.updateColor();
    }, (gameOptions, doubleOption) -> {
        return new TranslatableText("block.karam.rgbifier.blue", this.blue);
    });

    public void updateColor() {
        this.color = new Color(this.red, this.green, this.blue).getRGB();

        // Updates PropertyDelegate From Client Side
        // screenHandler.setColor(this.color);
        // TODO: Send client to server packet here
    }

    public RgbifierScreen(ScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);

        this.screenHandler = (RgbifierScreenHandler) handler;
        // Inventory rgbifierInventory = screenHandler.getInventory();
    }

    // Draws the background
    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        if (this.client == null)
            return;

        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.client.getTextureManager().bindTexture(TEXTURE);

        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;

        drawTexture(matrices, x, y, 0, 0, backgroundWidth, backgroundHeight);
    }

    // Renders the background
    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);

        drawMouseoverTooltip(matrices, mouseX, mouseY);
    }

    @Override
    protected void init() {
        super.init();

        if (this.client == null)
            return;

        // Center the title
        titleX = (backgroundWidth - textRenderer.getWidth(title)) / 2;

        int sliderWidth = 100;
        int center = (width - sliderWidth) / 2;

        AbstractButtonWidget redSlider = this.addButton(doubleOptionRed.createButton(this.client.options, center - sliderWidth, 10, sliderWidth));
        AbstractButtonWidget greenSlider = this.addButton(doubleOptionGreen.createButton(this.client.options, center, 10, sliderWidth));
        AbstractButtonWidget blueSlider = this.addButton(doubleOptionBlue.createButton(this.client.options, center + sliderWidth, 10, sliderWidth));

        boolean slidersActive = true;
        redSlider.active = slidersActive;
        greenSlider.active = slidersActive;
        blueSlider.active = slidersActive;

        // this.updateSliders(some-color-here); - Should this be here?
    }

    public void updateSliders(int color) {
        // TODO: Have server tell client to run this method with the color.
        // Your best bet is to get rid of propertyDelegate and create a packet from server to client to send the updated color over.

        if (this.client == null)
            return;

        this.color = color;
        this.red = new Color(this.color).getRed();
        this.green = new Color(this.color).getGreen();
        this.blue = new Color(this.color).getBlue();

        doubleOptionRed.set(this.client.options, this.red);
        doubleOptionGreen.set(this.client.options, this.green);
        doubleOptionBlue.set(this.client.options, this.blue);
    }
}


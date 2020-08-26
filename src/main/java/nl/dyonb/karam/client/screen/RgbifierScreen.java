package nl.dyonb.karam.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.client.options.DoubleOption;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import nl.dyonb.karam.Karam;
import nl.dyonb.karam.common.block.entity.ElevatorBlockEntity;
import nl.dyonb.karam.common.screen.RgbifierScreenHandler;

import java.awt.*;

// HandledScreen classes are fully client-sided and are responsible for drawing GUI elements.
public class RgbifierScreen extends HandledScreen<ScreenHandler> {
    private int color;
    private int red;
    private int green;
    private int blue;

    private boolean slidersActive = true;

    private AbstractButtonWidget redSlider;
    private AbstractButtonWidget greenSlider;
    private AbstractButtonWidget blueSlider;

    RgbifierScreenHandler screenHandler;

    //A path to the gui texture. In this example we use the texture from the dispenser
    private static final Identifier TEXTURE = Karam.identifier("textures/gui/container/dev_null.png");
    private DoubleOption doubleOptionRed = new DoubleOption("karam.menu.red", 1.0F, 255.0F, 1F, (gameOptions) -> {
        // Gets the red value from the full color
        return (double) this.red;
    }, (gameOptions, double_) -> {
        // Set the new red
        this.red = double_.intValue();
        updateColor();
    }, (gameOptions, doubleOption) -> {
        return new TranslatableText("block.karam.rgbifier.red", this.red);
    });
    private DoubleOption doubleOptionGreen = new DoubleOption("karam.menu.green", 1.0F, 255.0F, 1F, (gameOptions) -> {
        // Gets the red value from the full color
        return (double) this.green;
    }, (gameOptions, double_) -> {
        // Set the new red
        this.green = double_.intValue();
        updateColor();
    }, (gameOptions, doubleOption) -> {
        return new TranslatableText("block.karam.rgbifier.green", this.green);
    });
    private DoubleOption doubleOptionBlue = new DoubleOption("karam.menu.blue", 1.0F, 255.0F, 1F, (gameOptions) -> {
        // Gets the red value from the full color
        return (double) this.blue;
    }, (gameOptions, double_) -> {
        // Set the new red
        this.blue = double_.intValue();
        updateColor();
    }, (gameOptions, doubleOption) -> {
        return new TranslatableText("block.karam.rgbifier.blue", this.blue);
    });

    public void updateColor() {
        this.color = 255 << 24 + this.red << 16 + this.green << 8 + this.blue;
    }

    public RgbifierScreen(ScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);

        screenHandler = (RgbifierScreenHandler) handler;

        this.color = screenHandler.getColor();
        this.red = (this.color >> 16) & 0xFF;
        this.green = (this.color >> 8) & 0xFF;
        this.blue = (this.color >> 0) & 0xFF;
//        } else {
//            slidersActive = false;
//        }
    }

    // Draws the background
    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        client.getTextureManager().bindTexture(TEXTURE);
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

        this.color = screenHandler.getColor();
        this.red = (this.color >> 16) & 0xFF;
        this.green = (this.color >> 8) & 0xFF;
        this.blue = (this.color >> 0) & 0xFF;

        // Center the title
        titleX = (backgroundWidth - textRenderer.getWidth(title)) / 2;

        int sliderWidth = 100;
        int center = (width - sliderWidth) / 2;

        redSlider = this.addButton(doubleOptionRed.createButton(this.client.options, center - sliderWidth, 10, sliderWidth));
        greenSlider = this.addButton(doubleOptionGreen.createButton(this.client.options, center, 10, sliderWidth));
        blueSlider = this.addButton(doubleOptionBlue.createButton(this.client.options, center + sliderWidth, 10, sliderWidth));

        redSlider.active = slidersActive;
        greenSlider.active = slidersActive;
        blueSlider.active = slidersActive;
    }

//    public int getRGB() {
//        return this.color;
//    }
//
//    /**
//     * Returns the red component in the range 0-255 in the default sRGB
//     * space.
//     * @return the red component.
//     * @see #getRGB
//     */
//    public int getRed() {
//        return (getRGB() >> 16) & 0xFF;
//    }
//
//    /**
//     * Returns the green component in the range 0-255 in the default sRGB
//     * space.
//     * @return the green component.
//     * @see #getRGB
//     */
//    public int getGreen() {
//        return (getRGB() >> 8) & 0xFF;
//    }
//
//    /**
//     * Returns the blue component in the range 0-255 in the default sRGB
//     * space.
//     * @return the blue component.
//     * @see #getRGB
//     */
//    public int getBlue() {
//        return (getRGB() >> 0) & 0xFF;
//    }
}


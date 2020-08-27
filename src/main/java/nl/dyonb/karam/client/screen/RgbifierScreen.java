package nl.dyonb.karam.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.client.options.DoubleOption;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import nl.dyonb.karam.Karam;
import nl.dyonb.karam.common.screen.RgbifierScreenHandler;
import nl.dyonb.karam.util.PacketReference;

import java.awt.*;

// HandledScreen classes are fully client-sided and are responsible for drawing GUI elements.
public class RgbifierScreen extends HandledScreen<ScreenHandler> {
    private boolean firstInitialize = true;
    private int color;
    private int red;
    private int green;
    private int blue;

    RgbifierScreenHandler screenHandler;

    //A path to the gui texture. In this example we use the texture from the dispenser
    private static final Identifier TEXTURE = Karam.identifier("textures/gui/container/dev_null.png");
    private final DoubleOption doubleOptionRed = new DoubleOption("karam.menu.red", 0.0F, 255.0F, 1F, (gameOptions) -> {
        // Gets the red value from the full color
        return (double) this.red;
    }, (gameOptions, double_) -> {
        // Set the new red
        this.red = double_.intValue();
        this.updateColor();
    }, (gameOptions, doubleOption) -> {
        return new TranslatableText("block.karam.rgbifier.red", this.red);
    });

    private final DoubleOption doubleOptionGreen = new DoubleOption("karam.menu.green", 0.0F, 255.0F, 1F, (gameOptions) -> {
        // Gets the green value from the full color
        return (double) this.green;
    }, (gameOptions, double_) -> {
        // Set the new green
        this.green = double_.intValue();
        this.updateColor();
    }, (gameOptions, doubleOption) -> {
        return new TranslatableText("block.karam.rgbifier.green", this.green);
    });

    private final DoubleOption doubleOptionBlue = new DoubleOption("karam.menu.blue", 0.0F, 255.0F, 1F, (gameOptions) -> {
        // Gets the blue value from the full color
        return (double) this.blue;
    }, (gameOptions, double_) -> {
        // Set the new blue
        this.blue = double_.intValue();
        this.updateColor();
    }, (gameOptions, doubleOption) -> {
        return new TranslatableText("block.karam.rgbifier.blue", this.blue);
    });

    public void updateColor() {
        this.color = (0xFF << 24) | (red << 16) | (green << 8) | (blue);

        // Send the packet to the server
        PacketByteBuf passedData = new PacketByteBuf(Unpooled.buffer());
        passedData.writeInt(this.color);
        passedData.writeInt(this.screenHandler.syncId);
        ClientSidePacketRegistry.INSTANCE.sendToServer(PacketReference.RGBIFIER_COLOR_TO_SERVER, passedData);
    }

    public RgbifierScreen(ScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);

        this.screenHandler = (RgbifierScreenHandler) handler;
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

        // Set the initial values
        if (firstInitialize == true) {
            RgbifierScreenHandler rgbifierScreenHandler = (RgbifierScreenHandler) this.handler;
            this.color = rgbifierScreenHandler.getInitialColor();
            this.red = (this.color >> 16) & 0xFF;
            this.green = (this.color >> 8) & 0xFF;
            this.blue = (this.color >> 0) & 0xFF;
        }

        // Center the title
        titleX = (backgroundWidth - textRenderer.getWidth(title)) / 2;

        int sliderWidth = 100;
        int center = (width - sliderWidth) / 2;
        int spaceBetweenSliders = 25;

        AbstractButtonWidget redSlider = this.addButton(doubleOptionRed.createButton(this.client.options, center - sliderWidth - spaceBetweenSliders, 10, sliderWidth));
        AbstractButtonWidget greenSlider = this.addButton(doubleOptionGreen.createButton(this.client.options, center, 10, sliderWidth));
        AbstractButtonWidget blueSlider = this.addButton(doubleOptionBlue.createButton(this.client.options, center + sliderWidth + spaceBetweenSliders, 10, sliderWidth));

        boolean slidersActive = true;
        redSlider.active = slidersActive;
        greenSlider.active = slidersActive;
        blueSlider.active = slidersActive;

        firstInitialize = false;
    }

    public void updateSliders(int color) {
        if (this.client == null)
            return;

        this.color = color;
        this.red = (this.color >> 16) & 0xFF;
        this.green = (this.color >> 8) & 0xFF;
        this.blue = (this.color >> 0) & 0xFF;

        // Reinitialize the sliders to set them
        this.init(this.client, this.width, this.height);
    }
}


package com.example.chickenring;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class FowlForgeScreen extends HandledScreen<FowlForgeScreenHandler> {
    private static final Identifier TEXTURE =
        new Identifier(ChickenRingMod.MOD_ID, "textures/gui/fowl_forge_gui.png");

    public FowlForgeScreen(FowlForgeScreenHandler handler, PlayerInventory inv, Text title) {
        super(handler, inv, title);
        this.backgroundWidth = 176;
        this.backgroundHeight = 166;
        this.playerInventoryTitleY = 72;
    }

    @Override
    protected void init() {
        super.init();
        this.titleX = (this.backgroundWidth - this.textRenderer.getWidth(this.title)) / 2;
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        this.drawMouseoverTooltip(matrices, mouseX, mouseY);
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);

        int guiLeft = this.x;
        int guiTop = this.y;

        // Draw full GUI texture
        this.drawTexture(matrices, guiLeft, guiTop, 0, 0, this.backgroundWidth, this.backgroundHeight);

        // Draw fluid bar background (empty frame)
        final int FB_X = 18;
        final int FB_Y_BOTTOM = 55;
        final int FB_WIDTH = 5;
        final int FB_HEIGHT = 47;
        this.drawTexture(matrices, guiLeft + FB_X, guiTop + (FB_Y_BOTTOM - FB_HEIGHT), 176, 0, FB_WIDTH, FB_HEIGHT);

        // Draw fluid fill portion
        long storedEssence = this.handler.getBlockEntity().getStoredEssence();
        long capacity = FowlForgeBlockEntity.CAPACITY_MB;
        int fillPixels = (int) (FB_HEIGHT * ((double) storedEssence / capacity));
        if (fillPixels > 0 && storedEssence > 0) {
            int fillTopOffset = FB_Y_BOTTOM - fillPixels;
            this.drawTexture(matrices, guiLeft + FB_X, guiTop + fillTopOffset, 181, FB_HEIGHT - fillPixels, FB_WIDTH, fillPixels);
        }
    }

    @Override
    protected void drawMouseoverTooltip(MatrixStack matrices, int mouseX, int mouseY) {
        super.drawMouseoverTooltip(matrices, mouseX, mouseY);
        int guiLeft = this.x;
        int guiTop = this.y;
        final int FB_X = 18;
        final int FB_Y_TOP = 8;
        final int FB_WIDTH = 5;
        final int FB_HEIGHT = 47;

        // Check if mouse is over fluid bar
        if (mouseX >= guiLeft + FB_X && mouseX < guiLeft + FB_X + FB_WIDTH &&
                mouseY >= guiTop + FB_Y_TOP && mouseY < guiTop + FB_Y_TOP + FB_HEIGHT) {
            long storedEssence = this.handler.getBlockEntity().getStoredEssence();
            long capacity = FowlForgeBlockEntity.CAPACITY_MB;
            Text tooltip = Text.literal(storedEssence + " / " + capacity + " mB Dark Chicken Essence");
            this.renderTooltip(matrices, tooltip, mouseX, mouseY);
        }
    }

}
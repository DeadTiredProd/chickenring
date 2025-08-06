package com.example.chickenring;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.util.math.Vec3f;

public class FowlForgeBlockEntityRenderer implements BlockEntityRenderer<FowlForgeBlockEntity> {
    public FowlForgeBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {}

    @Override
    public void render(FowlForgeBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        if (entity.getStoredEssence() > 0) {
            matrices.push();
            matrices.translate(0.5, 1.5, 0.5);
            matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(180 - MinecraftClient.getInstance().getEntityRenderDispatcher().camera.getYaw()));
            matrices.scale(0.02f, 0.02f, 0.02f);
            TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
            String fluidText = entity.getStoredEssence() + " mB";
            textRenderer.draw(matrices, fluidText, -textRenderer.getWidth(fluidText) / 2.0f, 0, 0xFFFFFF);
            matrices.pop();
        }
    }
}
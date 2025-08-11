package com.example.chickenring.client.render;

import com.example.chickenring.client.model.ChonkenModel;
import com.example.chickenring.entity.ChonkenEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class ChonkenRenderer extends MobEntityRenderer<ChonkenEntity, ChonkenModel<ChonkenEntity>> {
    private static final Identifier TEXTURE = new Identifier("hellfiremadeit", "textures/entity/chonken.png");
    private static final float SCALE = 2.0F; // Adjust this to scale the entity (e.g., 2.0F = 2x size)
    public ChonkenRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new ChonkenModel<>(ctx.getPart(ChonkenModel.LAYER_LOCATION)), 0.5F * SCALE);
    }

    @Override
    public Identifier getTexture(ChonkenEntity entity) {
        return TEXTURE;
    }

    @Override
    protected void scale(ChonkenEntity entity, MatrixStack matrices, float amount) {
        matrices.scale(SCALE, SCALE, SCALE); // Uniform scaling in x, y, z
    }
}
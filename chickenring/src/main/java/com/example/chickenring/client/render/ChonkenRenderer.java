package com.example.chickenring.client.render;

import com.example.chickenring.client.model.ChonkenModel;
import com.example.chickenring.entity.ChonkenEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class ChonkenRenderer extends GeoEntityRenderer<ChonkenEntity> {

    public ChonkenRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new ChonkenModel());
    }

    @Override
    public Identifier getTextureResource(ChonkenEntity instance) {
        return new Identifier("hellfiremadeit", "textures/entity/chonken.png");
    }

    @Override
    public RenderLayer getRenderType(ChonkenEntity animatable, float partialTicks, MatrixStack stack,
                                     VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder,
                                     int packedLightIn, Identifier textureLocation) {

        stack.scale(2f, 2f, 2f); // make entity twice as large
        return super.getRenderType(animatable, partialTicks, stack, renderTypeBuffer, vertexBuilder, packedLightIn, textureLocation);
    }
}

package com.example.chickenring.client.model;

import com.example.chickenring.entity.ChonkenEntity;
//import net.minecraft.client.render.entity.EntityRendererFactory;
//import net.minecraft.client.render.entity.MobEntityRenderer;
//import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ChonkenModel extends AnimatedGeoModel<ChonkenEntity>
{
    
    private static final Identifier TEXTURE = new Identifier("hellfiremadeit", "textures/entity/chonken.png");
    
    @Override
    public Identifier getModelResource(ChonkenEntity object) {
        return new Identifier("hellfiremadeit", "geo/chonken.geo.json");
    }

    @Override
    public Identifier getTextureResource(ChonkenEntity object) {
        return TEXTURE;
    }

    @Override
    public Identifier getAnimationResource(ChonkenEntity animatable) {
        return new Identifier("hellfiremadeit","animations/chonken.animation.json");
    }
    
}   
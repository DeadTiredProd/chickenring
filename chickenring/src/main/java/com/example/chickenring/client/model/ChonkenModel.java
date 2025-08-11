// Made with Blockbench 4.12.6
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports

package com.example.chickenring.client.model;
import com.example.chickenring.entity.ChonkenEntity;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class ChonkenModel<T extends ChonkenEntity> extends SinglePartEntityModel<T> {
	private final ModelPart body;
	private final ModelPart head;

	public ChonkenModel(ModelPart root) {
		this.body = root.getChild("Body");
		this.head = body.getChild("Head");

	}

	public static final EntityModelLayer LAYER_LOCATION = new EntityModelLayer(
			new Identifier("hellfiremadeit", "chonken"), "main"
		);	
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData body = modelPartData.addChild("Body", ModelPartBuilder.create().uv(0, 0).cuboid(-3.0F, -4.0F, -5.0F, 6.0F, 4.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 22.0F, 2.0F));

		ModelPartData tail = body.addChild("Tail", ModelPartBuilder.create().uv(3, 6).cuboid(-0.6F, -2.1F, 1.0F, 1.2F, 1.0F, 1.0F, new Dilation(0.0F))
		.uv(0, 0).cuboid(-1.0F, -2.0F, -1.0F, 2.0F, 1.5F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -1.0F, 1.0F));

		ModelPartData Left_leg = body.addChild("Left_leg", ModelPartBuilder.create().uv(14, 12).cuboid(0.5F, -1.0F, -1.0F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.5F, 1.2F, -1.3F));

		ModelPartData cube_r1 = Left_leg.addChild("cube_r1", ModelPartBuilder.create().uv(8, 13).cuboid(0.0F, 0.0F, 0.0F, 2.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, -1.5708F, 0.0F, 0.0F));

		ModelPartData cube_r2 = Left_leg.addChild("cube_r2", ModelPartBuilder.create().uv(14, 15).cuboid(0.0F, 0.0F, 0.0F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.5F, -2.5F, 0.3F, -0.7156F, 0.0F, 0.0F));

		ModelPartData Right_leg = body.addChild("Right_leg", ModelPartBuilder.create().uv(0, 15).cuboid(-1.0F, 0.0F, 0.0F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(-1.0F, 0.2F, -2.3F));

		ModelPartData cube_r3 = Right_leg.addChild("cube_r3", ModelPartBuilder.create().uv(8, 10).cuboid(-2.0F, 0.0F, 0.0F, 2.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.5F, 1.0F, 1.0F, -1.5708F, 0.0F, 0.0F));

		ModelPartData cube_r4 = Right_leg.addChild("cube_r4", ModelPartBuilder.create().uv(4, 15).cuboid(-1.0F, 0.0F, 0.0F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -1.5F, 1.3F, -0.7156F, 0.0F, 0.0F));

		ModelPartData Head = body.addChild("Head", ModelPartBuilder.create().uv(12, 25).cuboid(0.5F, -2.0F, -1.0F, 0.5F, 2.0F, 2.0F, new Dilation(0.0F))
		.uv(14, 10).cuboid(-1.0F, -2.0F, -2.0F, 2.0F, 1.0F, 1.0F, new Dilation(0.0F))
		.uv(10, 16).cuboid(-0.3F, -1.0F, -1.3F, 0.6F, 1.0F, 0.0F, new Dilation(0.0F))
		.uv(8, 16).cuboid(-0.3F, -1.0F, -1.3F, 0.6F, 1.0F, 0.3F, new Dilation(0.0F))
		.uv(6, 0).cuboid(-1.0F, -3.0F, -1.0F, 2.0F, 0.5F, 2.0F, new Dilation(0.0F))
		.uv(0, 10).cuboid(-1.0F, -2.0F, -1.0F, 0.5F, 2.0F, 2.0F, new Dilation(0.0F))
		.uv(0, 10).cuboid(-0.5F, -2.5F, -1.0F, 1.5F, 0.0F, 2.0F, new Dilation(0.0F))
		.uv(9, 3).cuboid(-0.5F, -2.0F, -1.0F, 1.0F, 2.0F, 2.0F, new Dilation(0.0F))
		.uv(0, 10).cuboid(0.5F, -2.5F, -1.0F, 0.5F, 0.5F, 2.0F, new Dilation(0.0F))
		.uv(0, 10).cuboid(-1.0F, -2.5F, -1.0F, 0.5F, 0.5F, 2.0F, new Dilation(0.0F))
		.uv(6, 0).cuboid(-0.5F, -2.5F, -1.0F, 0.0F, 2.5F, 2.0F, new Dilation(0.0F))
		.uv(21, 18).cuboid(-0.5F, -2.5F, -1.0F, 1.0F, 0.5F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -3.0F, -5.0F));
		return TexturedModelData.of(modelData, 32, 32);
	}
	@Override
	public void setAngles(ChonkenEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	}
	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red,
			float green, float blue, float alpha) {
		body.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}
	@Override
	public ModelPart getPart() {
		return body;
	}
	
}
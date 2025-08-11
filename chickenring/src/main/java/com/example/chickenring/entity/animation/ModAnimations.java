package com.example.chickenring.entity.animation;

import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.client.render.entity.animation.AnimationHelper;
import net.minecraft.client.render.entity.animation.Keyframe;
import net.minecraft.client.render.entity.animation.Transformation;
public class ModAnimations {
    public static final Animation walk = Animation.Builder.create(2.5417F).looping()
		.addBoneAnimation("Body", new Transformation(Transformation.Targets.ROTATE, 
			new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(0.25F, AnimationHelper.createRotationalVector(-1.0F, 0.0F, 1.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(0.5F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 2.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(1.2917F, AnimationHelper.createRotationalVector(2.0F, 0.0F, 0.6667F), Transformation.Interpolations.LINEAR),
			new Keyframe(2.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, -2.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(2.5F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
		))
		.addBoneAnimation("Body", new Transformation(Transformation.Targets.TRANSLATE, 
			new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(2.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, -4.0F), Transformation.Interpolations.LINEAR)
		))
		.addBoneAnimation("Left_leg", new Transformation(Transformation.Targets.ROTATE, 
			new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(0.5F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(1.5F, AnimationHelper.createRotationalVector(-20.0F, 0.0F, -1.75F), Transformation.Interpolations.LINEAR),
			new Keyframe(2.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, -0.5F), Transformation.Interpolations.LINEAR)
		))
		.addBoneAnimation("Left_leg", new Transformation(Transformation.Targets.TRANSLATE, 
			new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.2F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(0.5F, AnimationHelper.createTranslationalVector(0.0F, 0.2F, 1.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(1.5F, AnimationHelper.createTranslationalVector(0.0F, 0.5F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(2.0F, AnimationHelper.createTranslationalVector(0.0F, 0.175F, 0.0F), Transformation.Interpolations.LINEAR)
		))
		.addBoneAnimation("Right_leg", new Transformation(Transformation.Targets.ROTATE, 
			new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(0.5F, AnimationHelper.createRotationalVector(-20.0F, 0.0F, -1.75F), Transformation.Interpolations.LINEAR),
			new Keyframe(1.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, -0.5F), Transformation.Interpolations.LINEAR),
			new Keyframe(1.4583F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.75F), Transformation.Interpolations.LINEAR)
		))
		.addBoneAnimation("Right_leg", new Transformation(Transformation.Targets.TRANSLATE, 
			new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.2F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(0.5F, AnimationHelper.createTranslationalVector(0.0F, 0.5F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(1.0F, AnimationHelper.createTranslationalVector(0.0F, 0.175F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(1.4583F, AnimationHelper.createTranslationalVector(0.0F, 0.23F, 1.0F), Transformation.Interpolations.LINEAR)
		))
		.build();

	public static final Animation sleep = Animation.Builder.create(3.0F)
		.addBoneAnimation("Body", new Transformation(Transformation.Targets.TRANSLATE, 
			new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
			new Keyframe(1.5F, AnimationHelper.createTranslationalVector(0.0F, -1.8F, -1.0F), Transformation.Interpolations.CUBIC)
		))
		.addBoneAnimation("Head", new Transformation(Transformation.Targets.ROTATE, 
			new Keyframe(0.4167F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
			new Keyframe(1.4167F, AnimationHelper.createRotationalVector(165.0708F, -34.0641F, -82.807F), Transformation.Interpolations.CUBIC)
		))
		.addBoneAnimation("Head", new Transformation(Transformation.Targets.TRANSLATE, 
			new Keyframe(0.4167F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
			new Keyframe(1.4167F, AnimationHelper.createTranslationalVector(0.0F, -2.0F, -1.0F), Transformation.Interpolations.CUBIC)
		))
		.addBoneAnimation("Left_leg", new Transformation(Transformation.Targets.TRANSLATE, 
			new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(0.4167F, AnimationHelper.createTranslationalVector(0.0F, 0.5F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(1.0F, AnimationHelper.createTranslationalVector(-0.6F, 1.4F, 0.0F), Transformation.Interpolations.LINEAR)
		))
		.addBoneAnimation("Right_leg", new Transformation(Transformation.Targets.TRANSLATE, 
			new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(0.4167F, AnimationHelper.createTranslationalVector(0.0F, 0.5F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(1.0F, AnimationHelper.createTranslationalVector(0.4F, 1.4F, 0.0F), Transformation.Interpolations.LINEAR)
		))
		.addBoneAnimation("Tail", new Transformation(Transformation.Targets.ROTATE, 
			new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(1.6667F, AnimationHelper.createRotationalVector(-30.9756F, 33.7666F, 0.0F), Transformation.Interpolations.LINEAR)
		))
		.addBoneAnimation("Tail", new Transformation(Transformation.Targets.TRANSLATE, 
			new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(1.6667F, AnimationHelper.createTranslationalVector(-0.5F, 0.0F, -1.0F), Transformation.Interpolations.LINEAR)
		))
		.build();

	public static final Animation wake = Animation.Builder.create(3.0F)
		.addBoneAnimation("Body", new Transformation(Transformation.Targets.ROTATE, 
			new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(1.5833F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(2.25F, AnimationHelper.createRotationalVector(2.9891F, 0.0F, 0.8483F), Transformation.Interpolations.LINEAR),
			new Keyframe(2.7083F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
		))
		.addBoneAnimation("Body", new Transformation(Transformation.Targets.TRANSLATE, 
			new Keyframe(1.6667F, AnimationHelper.createTranslationalVector(0.0F, -1.8F, -1.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(2.75F, AnimationHelper.createTranslationalVector(0.0F, 0.2F, -1.0F), Transformation.Interpolations.LINEAR)
		))
		.addBoneAnimation("Head", new Transformation(Transformation.Targets.ROTATE, 
			new Keyframe(0.6667F, AnimationHelper.createRotationalVector(165.0708F, -34.0641F, -82.807F), Transformation.Interpolations.LINEAR),
			new Keyframe(1.7083F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(1.875F, AnimationHelper.createRotationalVector(0.0F, 0.0F, -1.4564F), Transformation.Interpolations.LINEAR),
			new Keyframe(2.125F, AnimationHelper.createRotationalVector(0.0F, 0.0F, -1.4564F), Transformation.Interpolations.LINEAR),
			new Keyframe(2.2083F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
		))
		.addBoneAnimation("Head", new Transformation(Transformation.Targets.TRANSLATE, 
			new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, -1.75F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(1.3333F, AnimationHelper.createTranslationalVector(0.0F, -2.0F, -0.25F), Transformation.Interpolations.LINEAR),
			new Keyframe(1.4167F, AnimationHelper.createTranslationalVector(0.0F, -1.75F, -0.75F), Transformation.Interpolations.LINEAR),
			new Keyframe(1.7083F, AnimationHelper.createTranslationalVector(0.0F, -1.09F, -1.09F), Transformation.Interpolations.LINEAR),
			new Keyframe(2.5F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, -1.0F), Transformation.Interpolations.LINEAR)
		))
		.addBoneAnimation("Left_leg", new Transformation(Transformation.Targets.ROTATE, 
			new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(1.875F, AnimationHelper.createRotationalVector(-2.0033F, -0.0463F, 1.2956F), Transformation.Interpolations.LINEAR),
			new Keyframe(2.375F, AnimationHelper.createRotationalVector(-0.75F, 0.0F, 4.5F), Transformation.Interpolations.LINEAR),
			new Keyframe(2.4167F, AnimationHelper.createRotationalVector(-0.7507F, -0.0113F, 3.2751F), Transformation.Interpolations.LINEAR),
			new Keyframe(2.5833F, AnimationHelper.createRotationalVector(-0.7502F, -0.006F, 0.875F), Transformation.Interpolations.LINEAR),
			new Keyframe(2.7917F, AnimationHelper.createRotationalVector(-0.75F, 0.0F, -0.25F), Transformation.Interpolations.LINEAR)
		))
		.addBoneAnimation("Left_leg", new Transformation(Transformation.Targets.TRANSLATE, 
			new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 2.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(1.875F, AnimationHelper.createTranslationalVector(0.0F, 1.65F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(2.0833F, AnimationHelper.createTranslationalVector(-0.075F, 1.225F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(2.375F, AnimationHelper.createTranslationalVector(0.0F, 0.7F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(2.4167F, AnimationHelper.createTranslationalVector(0.0F, 0.58F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(2.5833F, AnimationHelper.createTranslationalVector(0.0F, 0.27F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(2.7917F, AnimationHelper.createTranslationalVector(0.0F, 0.005F, 0.0F), Transformation.Interpolations.LINEAR)
		))
		.addBoneAnimation("Right_leg", new Transformation(Transformation.Targets.ROTATE, 
			new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(1.875F, AnimationHelper.createRotationalVector(-0.9868F, -0.0043F, 2.1185F), Transformation.Interpolations.LINEAR),
			new Keyframe(2.375F, AnimationHelper.createRotationalVector(-1.25F, 0.0F, 3.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(2.7917F, AnimationHelper.createRotationalVector(-1.2473F, -0.0817F, -0.7491F), Transformation.Interpolations.LINEAR)
		))
		.addBoneAnimation("Right_leg", new Transformation(Transformation.Targets.TRANSLATE, 
			new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 2.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(1.875F, AnimationHelper.createTranslationalVector(0.0F, 1.725F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(2.375F, AnimationHelper.createTranslationalVector(0.0F, 0.8F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(2.7917F, AnimationHelper.createTranslationalVector(0.0F, -0.02F, 0.0F), Transformation.Interpolations.LINEAR)
		))
		.addBoneAnimation("Tail", new Transformation(Transformation.Targets.ROTATE, 
			new Keyframe(0.8333F, AnimationHelper.createRotationalVector(-14.0019F, 44.136F, -9.8511F), Transformation.Interpolations.LINEAR),
			new Keyframe(1.875F, AnimationHelper.createRotationalVector(-15.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(3.0F, AnimationHelper.createRotationalVector(-1.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
		))
		.addBoneAnimation("Tail", new Transformation(Transformation.Targets.TRANSLATE, 
			new Keyframe(1.875F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, -1.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(2.5833F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, -0.5F), Transformation.Interpolations.LINEAR),
			new Keyframe(3.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
		))
		.build();

	public static final Animation forage = Animation.Builder.create(8.0F).looping()
		.addBoneAnimation("Body", new Transformation(Transformation.Targets.ROTATE, 
			new Keyframe(0.25F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(1.25F, AnimationHelper.createRotationalVector(10.48F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(1.6667F, AnimationHelper.createRotationalVector(16.36F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(2.4167F, AnimationHelper.createRotationalVector(16.42F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(3.0F, AnimationHelper.createRotationalVector(16.42F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(3.75F, AnimationHelper.createRotationalVector(15.077F, 9.8488F, 1.7411F), Transformation.Interpolations.LINEAR),
			new Keyframe(5.1667F, AnimationHelper.createRotationalVector(15.077F, 9.8488F, 1.7411F), Transformation.Interpolations.LINEAR),
			new Keyframe(5.5F, AnimationHelper.createRotationalVector(15.077F, 9.8488F, 1.7411F), Transformation.Interpolations.LINEAR),
			new Keyframe(6.0833F, AnimationHelper.createRotationalVector(2.5F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(6.625F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
		))
		.addBoneAnimation("Head", new Transformation(Transformation.Targets.ROTATE, 
			new Keyframe(0.25F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(1.25F, AnimationHelper.createRotationalVector(82.5F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(1.5417F, AnimationHelper.createRotationalVector(93.23F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(1.7083F, AnimationHelper.createRotationalVector(73.25F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(2.0417F, AnimationHelper.createRotationalVector(21.67F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(2.4583F, AnimationHelper.createRotationalVector(31.88F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(2.7917F, AnimationHelper.createRotationalVector(72.5F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(2.9167F, AnimationHelper.createRotationalVector(79.5615F, 8.3202F, 0.7511F), Transformation.Interpolations.LINEAR),
			new Keyframe(3.2083F, AnimationHelper.createRotationalVector(93.3502F, 30.0211F, 39.49F), Transformation.Interpolations.LINEAR),
			new Keyframe(3.75F, AnimationHelper.createRotationalVector(87.5722F, 43.3803F, 5.5995F), Transformation.Interpolations.LINEAR),
			new Keyframe(3.875F, AnimationHelper.createRotationalVector(72.8426F, 47.0782F, -18.2291F), Transformation.Interpolations.LINEAR),
			new Keyframe(4.5F, AnimationHelper.createRotationalVector(87.5F, 0.0F, -5.9462F), Transformation.Interpolations.LINEAR),
			new Keyframe(5.8333F, AnimationHelper.createRotationalVector(87.5F, 0.0F, -5.9462F), Transformation.Interpolations.LINEAR),
			new Keyframe(6.7917F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(7.2917F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
		))
		.addBoneAnimation("Head", new Transformation(Transformation.Targets.TRANSLATE, 
			new Keyframe(0.25F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(1.25F, AnimationHelper.createTranslationalVector(0.0F, -1.0F, -0.8F), Transformation.Interpolations.LINEAR),
			new Keyframe(1.7083F, AnimationHelper.createTranslationalVector(0.0F, -1.25F, -0.8F), Transformation.Interpolations.LINEAR),
			new Keyframe(2.75F, AnimationHelper.createTranslationalVector(0.0F, -1.36F, -0.8F), Transformation.Interpolations.LINEAR),
			new Keyframe(2.9167F, AnimationHelper.createTranslationalVector(0.0F, -1.03F, -0.8F), Transformation.Interpolations.LINEAR),
			new Keyframe(3.0F, AnimationHelper.createTranslationalVector(0.0F, -0.775F, -0.8F), Transformation.Interpolations.LINEAR),
			new Keyframe(3.5417F, AnimationHelper.createTranslationalVector(0.0F, -1.0F, -0.8F), Transformation.Interpolations.LINEAR),
			new Keyframe(3.875F, AnimationHelper.createTranslationalVector(0.0F, -1.14F, -0.79F), Transformation.Interpolations.LINEAR),
			new Keyframe(4.0F, AnimationHelper.createTranslationalVector(0.0F, -1.13F, -0.8F), Transformation.Interpolations.LINEAR),
			new Keyframe(4.1667F, AnimationHelper.createTranslationalVector(0.0F, -1.09F, -0.76F), Transformation.Interpolations.LINEAR),
			new Keyframe(7.2917F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
		))
		.addBoneAnimation("Left_leg", new Transformation(Transformation.Targets.ROTATE, 
			new Keyframe(0.25F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(1.4167F, AnimationHelper.createRotationalVector(-12.8828F, 3.0573F, -0.8377F), Transformation.Interpolations.LINEAR),
			new Keyframe(1.6667F, AnimationHelper.createRotationalVector(-16.5883F, 3.7487F, -1.0198F), Transformation.Interpolations.LINEAR),
			new Keyframe(2.125F, AnimationHelper.createRotationalVector(-16.3929F, 5.0438F, -1.3935F), Transformation.Interpolations.LINEAR),
			new Keyframe(3.7917F, AnimationHelper.createRotationalVector(-15.9829F, 8.9783F, -4.4434F), Transformation.Interpolations.LINEAR),
			new Keyframe(4.7083F, AnimationHelper.createRotationalVector(-14.4575F, 6.0966F, -3.5611F), Transformation.Interpolations.LINEAR),
			new Keyframe(5.25F, AnimationHelper.createRotationalVector(-15.4626F, 3.7639F, -3.3388F), Transformation.Interpolations.LINEAR),
			new Keyframe(5.625F, AnimationHelper.createRotationalVector(-11.8075F, 3.1817F, -2.2771F), Transformation.Interpolations.LINEAR),
			new Keyframe(6.0417F, AnimationHelper.createRotationalVector(-3.4142F, 0.952F, -0.8073F), Transformation.Interpolations.LINEAR),
			new Keyframe(6.375F, AnimationHelper.createRotationalVector(-2.1754F, 0.1823F, 0.2028F), Transformation.Interpolations.LINEAR),
			new Keyframe(6.625F, AnimationHelper.createRotationalVector(0.3442F, 0.0134F, 0.1926F), Transformation.Interpolations.LINEAR),
			new Keyframe(7.25F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
		))
		.addBoneAnimation("Left_leg", new Transformation(Transformation.Targets.TRANSLATE, 
			new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.2F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(1.6667F, AnimationHelper.createTranslationalVector(0.0F, 0.65F, -0.4F), Transformation.Interpolations.LINEAR),
			new Keyframe(4.7083F, AnimationHelper.createTranslationalVector(0.0F, 0.645F, -0.41F), Transformation.Interpolations.LINEAR),
			new Keyframe(5.25F, AnimationHelper.createTranslationalVector(0.0F, 0.59F, -0.41F), Transformation.Interpolations.LINEAR),
			new Keyframe(5.625F, AnimationHelper.createTranslationalVector(0.0F, 0.58F, -0.41F), Transformation.Interpolations.LINEAR),
			new Keyframe(6.0417F, AnimationHelper.createTranslationalVector(0.0F, 0.255F, -0.4F), Transformation.Interpolations.LINEAR),
			new Keyframe(6.2083F, AnimationHelper.createTranslationalVector(0.0F, 0.265F, -0.4F), Transformation.Interpolations.LINEAR),
			new Keyframe(6.375F, AnimationHelper.createTranslationalVector(0.0F, 0.225F, -0.4F), Transformation.Interpolations.LINEAR)
		))
		.addBoneAnimation("Right_leg", new Transformation(Transformation.Targets.ROTATE, 
			new Keyframe(0.25F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(1.4167F, AnimationHelper.createRotationalVector(-13.1126F, 1.2608F, -0.8674F), Transformation.Interpolations.LINEAR),
			new Keyframe(1.6667F, AnimationHelper.createRotationalVector(-15.978F, 1.555F, -1.0544F), Transformation.Interpolations.LINEAR),
			new Keyframe(2.125F, AnimationHelper.createRotationalVector(-16.3917F, 2.068F, -1.4466F), Transformation.Interpolations.LINEAR),
			new Keyframe(3.1667F, AnimationHelper.createRotationalVector(-16.2545F, 3.5884F, -1.3468F), Transformation.Interpolations.LINEAR),
			new Keyframe(3.7917F, AnimationHelper.createRotationalVector(-14.491F, 3.9753F, -3.1509F), Transformation.Interpolations.LINEAR),
			new Keyframe(3.9583F, AnimationHelper.createRotationalVector(-14.2721F, 4.1407F, -3.404F), Transformation.Interpolations.LINEAR),
			new Keyframe(4.5833F, AnimationHelper.createRotationalVector(-14.1535F, 3.5419F, -2.8647F), Transformation.Interpolations.LINEAR),
			new Keyframe(4.7083F, AnimationHelper.createRotationalVector(-15.9304F, 3.397F, -2.7798F), Transformation.Interpolations.LINEAR),
			new Keyframe(5.25F, AnimationHelper.createRotationalVector(-15.6307F, 2.7694F, -2.4118F), Transformation.Interpolations.LINEAR),
			new Keyframe(5.625F, AnimationHelper.createRotationalVector(-13.6009F, 2.3367F, -1.8639F), Transformation.Interpolations.LINEAR),
			new Keyframe(6.0417F, AnimationHelper.createRotationalVector(-2.7982F, 1.9492F, -1.2457F), Transformation.Interpolations.LINEAR),
			new Keyframe(6.375F, AnimationHelper.createRotationalVector(-1.0994F, 1.7169F, -0.2304F), Transformation.Interpolations.LINEAR),
			new Keyframe(7.4157F, AnimationHelper.createRotationalVector(-1.0994F, 1.7169F, -0.2304F), Transformation.Interpolations.LINEAR),
			new Keyframe(7.4167F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
		))
		.addBoneAnimation("Right_leg", new Transformation(Transformation.Targets.TRANSLATE, 
			new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.25F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(1.6667F, AnimationHelper.createTranslationalVector(0.0F, 0.575F, 1.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(3.9583F, AnimationHelper.createTranslationalVector(0.0F, 0.5F, 1.03F), Transformation.Interpolations.LINEAR),
			new Keyframe(4.5833F, AnimationHelper.createTranslationalVector(0.0F, 0.48F, 1.01F), Transformation.Interpolations.LINEAR),
			new Keyframe(5.25F, AnimationHelper.createTranslationalVector(0.0F, 0.5F, 1.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(5.625F, AnimationHelper.createTranslationalVector(0.0F, 0.45F, 1.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(6.5F, AnimationHelper.createTranslationalVector(0.0F, 0.25F, -0.375F), Transformation.Interpolations.LINEAR)
		))
		.addBoneAnimation("Tail", new Transformation(Transformation.Targets.ROTATE, 
			new Keyframe(1.875F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(2.0F, AnimationHelper.createRotationalVector(0.0F, -0.3641F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(2.375F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(3.9583F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(4.0833F, AnimationHelper.createRotationalVector(0.0F, -0.7282F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(6.5833F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
		))
		.build();

	public static final Animation startle = Animation.Builder.create(5.9167F)
		.addBoneAnimation("Body", new Transformation(Transformation.Targets.ROTATE, 
			new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(1.2917F, AnimationHelper.createRotationalVector(0.0F, 7.5F, 0.0F), Transformation.Interpolations.LINEAR)
		))
		.addBoneAnimation("Body", new Transformation(Transformation.Targets.TRANSLATE, 
			new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(0.25F, AnimationHelper.createTranslationalVector(0.0F, 1.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(4.2917F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
		))
		.addBoneAnimation("Left_leg", new Transformation(Transformation.Targets.TRANSLATE, 
			new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(0.25F, AnimationHelper.createTranslationalVector(0.0F, -1.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(4.2917F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
		))
		.addBoneAnimation("Right_leg", new Transformation(Transformation.Targets.TRANSLATE, 
			new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(0.25F, AnimationHelper.createTranslationalVector(0.0F, -1.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(4.2917F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
		))
		.addBoneAnimation("Head", new Transformation(Transformation.Targets.ROTATE, 
			new Keyframe(0.3333F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(0.625F, AnimationHelper.createRotationalVector(0.0F, 120.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(1.2917F, AnimationHelper.createRotationalVector(0.0F, 120.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(1.9583F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(2.5417F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(3.0417F, AnimationHelper.createRotationalVector(0.0F, -137.5F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(3.6667F, AnimationHelper.createRotationalVector(0.0F, -137.5F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(3.9167F, AnimationHelper.createRotationalVector(0.0F, -137.5F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(4.9583F, AnimationHelper.createRotationalVector(0.0F, -52.5F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(5.625F, AnimationHelper.createRotationalVector(0.0F, -52.5F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(5.9167F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
		))
		.addBoneAnimation("Head", new Transformation(Transformation.Targets.TRANSLATE, 
			new Keyframe(0.625F, AnimationHelper.createTranslationalVector(0.0F, 1.1F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(1.2917F, AnimationHelper.createTranslationalVector(0.0F, 1.1F, 0.0F), Transformation.Interpolations.LINEAR)
		))
		.build();
}

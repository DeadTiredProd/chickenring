package com.example.chickenring.entity;
import com.example.chickenring.ChickenRingMod;
import com.example.chickenring.entity.goals.FindGrassAtDayGoal;
import com.example.chickenring.entity.goals.FindNestGoal;
import com.example.chickenring.entity.goals.MoveToLightGoal;
import com.example.chickenring.entity.goals.MoveToShelterGoal;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.AnimalMateGoal;
import net.minecraft.entity.ai.goal.EscapeDangerGoal;
import net.minecraft.entity.ai.goal.FollowParentGoal;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.Ingredient;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.PlayState;
;
public class ChonkenEntity extends AnimalEntity implements IAnimatable {

    private AnimationFactory factory = GeckoLibUtil.createFactory(this);
    
    public ChonkenEntity(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
        this.eggLayTime = this.random.nextInt(1500) + 1500;
        this.setPathfindingPenalty(PathNodeType.WATER, 0.0F);
    }

    public static DefaultAttributeContainer.Builder setAttributes() {

        return AnimalEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 20.0D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 1.0f)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, 2.0f)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 1f);

    }
    
    private <T extends IAnimatable> PlayState predicate(AnimationEvent<T> event) {
        if (this.isForaging()) {
        event.getController().setAnimationSpeed(2f);
        event.getController().setAnimation(
                new AnimationBuilder().addAnimation("animation.chonken.forage", ILoopType.EDefaultLoopTypes.LOOP)
        );
        return PlayState.CONTINUE;
    }

    if (event.isMoving()) {
        event.getController().setAnimation(
            new AnimationBuilder().addAnimation("animation.chonken.walk", ILoopType.EDefaultLoopTypes.LOOP)
        );
        return PlayState.CONTINUE;
    }

    event.getController().setAnimation(
        new AnimationBuilder().addAnimation("animation.chonken.idle", ILoopType.EDefaultLoopTypes.LOOP)
    );
    return PlayState.CONTINUE;
}


    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController(this, "controller", 0, this::predicate));
    }
    
    @Override
    public AnimationFactory getFactory() {
        return factory;
    }


    

    
    private static final Ingredient BREEDING_INGREDIENT;
    public int eggLayTime;

    @Override
protected void initGoals() {
    // Survival basics
    this.goalSelector.add(0, new SwimGoal(this));//survival
    this.goalSelector.add(0, new EscapeDangerGoal(this, 1.4));//survival
    
    this.goalSelector.add(1, new FindNestGoal(this, 1.0D, 25)); 
    this.goalSelector.add(2, new MoveToShelterGoal(this));//during rain
    this.goalSelector.add(2, new MoveToLightGoal(this)); // Only if not nesting and night

    this.goalSelector.add(3, new AnimalMateGoal(this, 1.0));//instincts
    this.goalSelector.add(3, new TemptGoal(this, 1.0, BREEDING_INGREDIENT, false));//instincts

    this.goalSelector.add(4, new FollowParentGoal(this, 1.1));//Family

    this.goalSelector.add(5, new WanderAroundFarGoal(this, 1.0));
    this.goalSelector.add(5, new FindGrassAtDayGoal(this));//only during day
    this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
    this.goalSelector.add(7, new LookAroundGoal(this));
}

    public void tickMovement() {
        super.tickMovement();
        if (!this.world.isClient && this.isAlive() && !this.isBaby() && --this.eggLayTime <= 0) {
            this.playSound(SoundEvents.ENTITY_CHICKEN_EGG, 1.0F,
                    (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
            this.dropItem(Items.EGG);
            this.emitGameEvent(GameEvent.ENTITY_PLACE);
            this.eggLayTime = this.random.nextInt(1500) + 1500;
        }
    }
    

    
    @Override
    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return this.isBaby() ? dimensions.height * 0.5F : dimensions.height * 0.95F; // Adjusted eye height for baby
    }

    @Override
    public EntityDimensions getDimensions(EntityPose pose) {
        if (this.isBaby()) {
            // Scale down the baby size to 50% of the adult size
            EntityDimensions adultDimensions = super.getDimensions(pose);
            return EntityDimensions.changing(adultDimensions.width * 0.5F, adultDimensions.height * 0.5F);
        }
        return super.getDimensions(pose);
    }

    public static DefaultAttributeContainer.Builder createChickenAttributes() {
        return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 5.0).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.05);
    }

    @Override
    public ChonkenEntity createChild(ServerWorld serverWorld, PassiveEntity passiveEntity) {
        ChonkenEntity child = new ChonkenEntity(ChickenRingMod.CHONKEN, serverWorld);
        child.setBaby(true); // Mark the child as a baby
        child.setPosition(this.getPos()); // Spawn near the parent
        return child;
    }

    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        if (nbt.contains("EggLayTime")) {
            this.eggLayTime = nbt.getInt("EggLayTime");
        }
    }

        // In ChonkenEntity
    private static final TrackedData<Boolean> FORAGING =
        DataTracker.registerData(ChonkenEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(FORAGING, false);
    }

    public void setForaging(boolean value) {
        this.dataTracker.set(FORAGING, value);
    }

    public boolean isForaging() {
        return this.dataTracker.get(FORAGING);
    }


    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("EggLayTime", this.eggLayTime);
    }

    public boolean isBreedingItem(ItemStack stack) {
        return BREEDING_INGREDIENT.test(stack);
    }

    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        return false;
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_CHICKEN_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_CHICKEN_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_CHICKEN_DEATH;
    }

    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.ENTITY_CHICKEN_STEP, 0.15F, 1.0F);
    }

    static {
        BREEDING_INGREDIENT = Ingredient.ofItems(new ItemConvertible[] { Items.WHEAT_SEEDS, Items.MELON_SEEDS,
                Items.PUMPKIN_SEEDS, Items.BEETROOT_SEEDS, ChickenRingMod.ROTTING_CHICKEN, ChickenRingMod.CALCIUM_DUST, Items.CHICKEN, Items.COOKED_CHICKEN });
    }
}
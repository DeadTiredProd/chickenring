package com.example.chickenring.entity;
import com.example.chickenring.ChickenRingMod;
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
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;

public class ChonkenEntity extends AnimalEntity {
    public ChonkenEntity(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
        this.eggLayTime = this.random.nextInt(1500) + 1500;
        this.setPathfindingPenalty(PathNodeType.WATER, 0.0F);
    }

    private static final Ingredient BREEDING_INGREDIENT;
    public int eggLayTime;

    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new EscapeDangerGoal(this, 1.4));
        this.goalSelector.add(2, new AnimalMateGoal(this, 1.0));
        this.goalSelector.add(3, new TemptGoal(this, 1.0, BREEDING_INGREDIENT, false));
        this.goalSelector.add(4, new MoveToShelterGoal(this)); // Higher priority for rain
        this.goalSelector.add(5, new MoveToLightGoal(this)); // Higher priority for night
        this.goalSelector.add(6, new FindNestGoal(this, 1.0D, 25));     
        this.goalSelector.add(7, new FollowParentGoal(this, 1.1));
        this.goalSelector.add(8, new WanderAroundFarGoal(this, 1.0));
        this.goalSelector.add(9, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.add(10, new LookAroundGoal(this));
    }

    public void tickMovement() {
        super.tickMovement();

        if (!this.world.isClient && this.isAlive() && !this.isBaby() && --this.eggLayTime <= 0) {
            this.playSound(SoundEvents.ENTITY_CHICKEN_EGG, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
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
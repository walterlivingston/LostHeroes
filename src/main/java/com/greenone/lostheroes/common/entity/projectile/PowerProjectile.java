package com.greenone.lostheroes.common.entity.projectile;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IRendersAsItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.DamagingProjectileEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Util;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

// (TODO) Add Attack Damage and Particle Modifiers
public abstract class PowerProjectile extends DamagingProjectileEntity implements IRendersAsItem {
    private static final DataParameter<ItemStack> DATA_ITEM_STACK = EntityDataManager.defineId(PowerProjectile.class, DataSerializers.ITEM_STACK);
    protected static int numParticles = 0;

    public PowerProjectile(EntityType<? extends PowerProjectile> p_i50147_1_, World p_i50147_2_) {
        super(p_i50147_1_, p_i50147_2_);
    }

    public PowerProjectile(EntityType<? extends PowerProjectile> p_i50174_1_, double p_i50174_2_, double p_i50174_4_, double p_i50174_6_, double p_i50174_8_, double p_i50174_10_, double p_i50174_12_, World p_i50174_14_) {
        super(p_i50174_1_, p_i50174_2_, p_i50174_4_, p_i50174_6_, p_i50174_8_, p_i50174_10_, p_i50174_12_, p_i50174_14_);

    }

    public PowerProjectile(EntityType<? extends PowerProjectile> p_i50175_1_, LivingEntity p_i50175_2_, double p_i50175_3_, double p_i50175_5_, double p_i50175_7_, World p_i50175_9_) {
        super(p_i50175_1_, p_i50175_2_, p_i50175_3_, p_i50175_5_, p_i50175_7_, p_i50175_9_);
    }

    public void setItem(ItemStack p_213884_1_) {
        if (p_213884_1_.getItem() != this.getDefaultItem() || p_213884_1_.hasTag()) {
            this.getEntityData().set(DATA_ITEM_STACK, Util.make(p_213884_1_.copy(), (p_213883_0_) -> {
                p_213883_0_.setCount(1);
            }));
        }

    }

    protected abstract Item getDefaultItem();

    protected ItemStack getItemRaw() {
        return this.getEntityData().get(DATA_ITEM_STACK);
    }

    @Override
    public ItemStack getItem() {
        ItemStack itemstack = this.getItemRaw();
        return itemstack.isEmpty() ? new ItemStack(this.getDefaultItem()) : itemstack;
    }

    @Override
    protected void defineSynchedData() {
        this.getEntityData().define(DATA_ITEM_STACK, ItemStack.EMPTY);
    }

    @Override
    public void tick() {
        Entity entity = this.getOwner();
        if (this.level.isClientSide || (entity == null || !entity.removed) && this.level.hasChunkAt(this.blockPosition())) {
            super.tick();

            RayTraceResult raytraceresult = ProjectileHelper.getHitResult(this, this::canHitEntity);
            if (raytraceresult.getType() != RayTraceResult.Type.MISS && !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, raytraceresult)) {
                this.onHit(raytraceresult);
            }

            this.checkInsideBlocks();
            Vector3d vector3d = this.getDeltaMovement();
            double d0 = this.getX() + vector3d.x;
            double d1 = this.getY() + vector3d.y;
            double d2 = this.getZ() + vector3d.z;
            ProjectileHelper.rotateTowardsMovement(this, 0.2F);
            float f = this.getInertia();
            if (this.isInWater()) {
                for(int i = 0; i < 4; ++i) {
                    float f1 = 0.25F;
                    this.level.addParticle(ParticleTypes.BUBBLE, d0 - vector3d.x * 0.25D, d1 - vector3d.y * 0.25D, d2 - vector3d.z * 0.25D, vector3d.x, vector3d.y, vector3d.z);
                }

                f = 0.8F;
            }

            this.setDeltaMovement(vector3d.add(this.xPower, this.yPower, this.zPower).scale((double)f));
            for(int i = 0; i < getNumParticles(); i++){
//                this.level.addParticle(this.getTrailParticle(), d0 + 0.25*(random.nextDouble() - 0.5d), d1 + 0.25*(random.nextDouble() - 0.5d), d2 + 0.25*(random.nextDouble() - 0.5d), 0.0D, 0.0D, 0.0D);
                this.level.addParticle(this.getTrailParticle(), d0 - vector3d.x * random.nextDouble(), d1 - vector3d.y * random.nextDouble(), d2 - vector3d.z * random.nextDouble(), vector3d.x, vector3d.y, vector3d.z);
            }
            this.setPos(d0, d1, d2);
        } else {
            this.remove();
        }
    }

    @Override
    protected void onHitEntity(EntityRayTraceResult p_213868_1_) {
        super.onHitEntity(p_213868_1_);
        if (!this.level.isClientSide) {
            Entity entity = p_213868_1_.getEntity();
            Entity entity1 = this.getOwner();
            boolean flag;
            if (entity1 instanceof LivingEntity) {
                LivingEntity livingentity = (LivingEntity)entity1;
                flag = entity.hurt(DamageSource.MAGIC, 8.0F);
                if (flag) {
                    if (entity.isAlive()) {
                        this.doEnchantDamageEffects(livingentity, entity);
                    } else {
                        livingentity.heal(5.0F);
                    }
                }
            } else {
                flag = entity.hurt(DamageSource.MAGIC, 5.0F);
            }
        }
    }

    @Override
    public boolean isPickable() {
        return false;
    }

    @Override
    public boolean hurt(DamageSource p_70097_1_, float p_70097_2_) {
        return false;
    }

    @Override
    public boolean isOnFire() {
        return false;
    }

    @Override
    protected abstract IParticleData getTrailParticle();

    public static int getNumParticles() {
        return numParticles;
    }

    @Override
    public void addAdditionalSaveData(CompoundNBT p_213281_1_) {
        super.addAdditionalSaveData(p_213281_1_);
        ItemStack itemstack = this.getItemRaw();
        if (!itemstack.isEmpty()) {
            p_213281_1_.put("Item", itemstack.save(new CompoundNBT()));
        }

    }

    @Override
    public void readAdditionalSaveData(CompoundNBT p_70037_1_) {
        super.readAdditionalSaveData(p_70037_1_);
        ItemStack itemstack = ItemStack.of(p_70037_1_.getCompound("Item"));
        this.setItem(itemstack);
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
package com.greenone.lostheroes.common.entity.projectile;

import com.greenone.lostheroes.common.entity.LHEntities;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class WaterBallProjectile extends PowerProjectile {

    public WaterBallProjectile(EntityType<? extends WaterBallProjectile> p_i50147_1_, World p_i50147_2_) {
        super(p_i50147_1_, p_i50147_2_);
    }

    public WaterBallProjectile(World p_i1794_1_, LivingEntity p_i1794_2_, double p_i1794_3_, double p_i1794_5_, double p_i1794_7_) {
        super(LHEntities.WATER_BALL, p_i1794_2_, p_i1794_3_, p_i1794_5_, p_i1794_7_, p_i1794_1_);
        numParticles = 30;
    }

    @OnlyIn(Dist.CLIENT)
    public WaterBallProjectile(World p_i1795_1_, double p_i1795_2_, double p_i1795_4_, double p_i1795_6_, double p_i1795_8_, double p_i1795_10_, double p_i1795_12_) {
        super(LHEntities.WATER_BALL, p_i1795_2_, p_i1795_4_, p_i1795_6_, p_i1795_8_, p_i1795_10_, p_i1795_12_, p_i1795_1_);
    }

    @Override
    protected Item getDefaultItem() {
        return Blocks.WATER.asItem();
    }

    @Override
    protected void onHit(RayTraceResult p_70227_1_) {
        super.onHit(p_70227_1_);
        if (!this.level.isClientSide) {
            for(int i = 0; i < 10; i++){
                this.level.addParticle(ParticleTypes.SPLASH, p_70227_1_.getLocation().x() + random.nextDouble(), p_70227_1_.getLocation().y() + random.nextDouble(), p_70227_1_.getLocation().z() + random.nextDouble(), 0, 0, 0);
            }
            this.remove();
        }
    }

    @Override
    protected IParticleData getTrailParticle() {
        return ParticleTypes.SPLASH;
    }
}

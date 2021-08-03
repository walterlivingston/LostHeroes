package com.greenone.lostheroes.common.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Random;

public class LotusFlowerBlock extends FlowerBlock {
    public LotusFlowerBlock(MobEffect susStewEffect, int effectDuration, Properties props) {
        super(susStewEffect, effectDuration, props);
    }

    @Override
    public void animateTick(BlockState p_180655_1_, Level p_180655_2_, BlockPos p_180655_3_, Random p_180655_4_) {
        VoxelShape voxelshape = this.getShape(p_180655_1_, p_180655_2_, p_180655_3_, CollisionContext.empty());
        Vec3 vector3d = voxelshape.bounds().getCenter();
        double d0 = (double)p_180655_3_.getX() + vector3d.x;
        double d1 = (double)p_180655_3_.getZ() + vector3d.z;

        for(int i = 0; i < 3; ++i) {
            if (p_180655_4_.nextBoolean()) {
                p_180655_2_.addParticle(ParticleTypes.CLOUD, d0 + p_180655_4_.nextDouble() / 5.0D, (double)p_180655_3_.getY() + (0.5D - p_180655_4_.nextDouble()), d1 + p_180655_4_.nextDouble() / 5.0D, 0.0D, 0.0D, 0.0D);
            }
        }
    }

    //TODO Teleport to nearest Lotus Biome
    @Override
    public void entityInside(BlockState p_196262_1_, Level p_196262_2_, BlockPos p_196262_3_, Entity p_196262_4_) {
        super.entityInside(p_196262_1_, p_196262_2_, p_196262_3_, p_196262_4_);
    }
}

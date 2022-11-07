package com.greenone.lostheroes.common.deity.abilities;

import com.greenone.lostheroes.common.LHUtils;
import com.greenone.lostheroes.common.player.capability.IMana;
import com.greenone.lostheroes.common.potion.LHEffects;
import net.minecraft.block.Blocks;
import net.minecraft.block.WitherRoseBlock;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;

import java.util.List;

public class AphroditeAbilities extends AbstractAbility{
    @Override
    public void majorAbility(PlayerEntity player, IMana manaCap) {
        if (player.isCreative() || manaCap.consumeMana(minorManaReq(manaCap.getMaxMana()))) {
            List<MobEntity> list = player.level.getEntitiesOfClass(MobEntity.class, player.getBoundingBox().inflate(10.0d, 2.0D, 10.0d));
            AreaEffectCloudEntity areaeffectcloudentity = new AreaEffectCloudEntity(player.level, player.getX(), player.getY(), player.getZ());
            areaeffectcloudentity.setParticle(ParticleTypes.HEART);
            areaeffectcloudentity.setRadiusOnUse(0f);
            areaeffectcloudentity.setWaitTime(0);
            areaeffectcloudentity.setDuration(5);
            areaeffectcloudentity.setRadiusPerTick(0.5f);

            if(!list.isEmpty()){
                for(MobEntity entity : list){
                    entity.addEffect(new EffectInstance(LHEffects.APATHY, 300));
                }
            }

            player.level.addFreshEntity(areaeffectcloudentity);
        }
    }

    @Override
    public void minorAbility(PlayerEntity player, IMana manaCap) {
        if (player.isCreative() || manaCap.consumeMana(minorManaReq(manaCap.getMaxMana()))) {
            Vector3d lookVec = LHUtils.getLookingAt(player, 10).getLocation();
            BlockPos lookPos = new BlockPos(lookVec.x, lookVec.y, lookVec.z);
            WitherRoseBlock witherRose = (WitherRoseBlock) Blocks.WITHER_ROSE;
            if (witherRose.canSustainPlant(player.level.getBlockState(lookPos.below()), player.level, lookPos.below(), player.getDirection().getOpposite(), witherRose)) {
                for (double i = lookVec.x - 2; i < lookVec.x + 2; i++) {
                    for (double j = lookVec.z - 2; j < lookVec.z + 2; j++) {
                        BlockPos pos = new BlockPos(i, lookVec.y, j);
                        if (witherRose.canSustainPlant(player.level.getBlockState(pos.below()), player.level, pos.below(), player.getDirection().getOpposite(), witherRose)) {
                            if(player.level.getBlockState(pos).canBeReplaced(Fluids.FLOWING_WATER)) player.level.setBlockAndUpdate(pos, witherRose.defaultBlockState());
                        }else if(witherRose.canSustainPlant(player.level.getBlockState(pos.below().below()), player.level, pos.below().below(), player.getDirection().getOpposite(), witherRose)){
                            if(player.level.getBlockState(pos.below()).canBeReplaced(Fluids.FLOWING_WATER)) player.level.setBlockAndUpdate(pos.below(), witherRose.defaultBlockState());
                        }else if(witherRose.canSustainPlant(player.level.getBlockState(pos), player.level, pos, player.getDirection().getOpposite(), witherRose)){
                            if(player.level.getBlockState(pos.above()).canBeReplaced(Fluids.FLOWING_WATER)) player.level.setBlockAndUpdate(pos.above(), witherRose.defaultBlockState());
                        }
                    }
                }
            }
        }
    }

    @Override
    public float majorManaReq(float maxMana) {
        return maxMana / 2;
    }

    @Override
    public float minorManaReq(float maxMana) {
        return maxMana / 3;
    }
}

package com.greenone.lostheroes.common.deity.abilities;

import com.greenone.lostheroes.common.LHUtils;
import com.greenone.lostheroes.common.player.capability.IMana;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

import java.util.List;

public class ZeusAbilities extends AbstractAbility{
    // Lightning Strike
    @Override
    public void majorAbility(PlayerEntity player, IMana manaCap) {
        BlockPos pos = new BlockPos(player.getX(), player.getY(), player.getZ());
        if(player.level.canSeeSky(pos) && (player.isCreative() || manaCap.consumeMana(majorManaReq(manaCap.getMaxMana())))){
            LightningBoltEntity lightningBoltEntity = EntityType.LIGHTNING_BOLT.create(player.level);
            lightningBoltEntity.moveTo(LHUtils.getLookingAt(player, 64).getLocation());
            lightningBoltEntity.setCause(player instanceof ServerPlayerEntity ? (ServerPlayerEntity) player : null);
            player.level.addFreshEntity(lightningBoltEntity);
            SoundEvent soundEvent = SoundEvents.TRIDENT_THUNDER;
            player.level.playSound(player, pos, soundEvent, SoundCategory.WEATHER, 5.0F, 1.0F);
        }
    }

    // Gust
    @Override
    public void minorAbility(PlayerEntity player, IMana manaCap) {
        if(player.isCreative() || manaCap.consumeMana(minorManaReq(manaCap.getMaxMana()))) {
            List<LivingEntity> list = player.level.getEntitiesOfClass(LivingEntity.class, player.getBoundingBox().inflate(10.0d, 2.0D, 10.0d));
            AreaEffectCloudEntity areaeffectcloudentity = new AreaEffectCloudEntity(player.level, player.getX(), player.getY(), player.getZ());
            areaeffectcloudentity.setParticle(ParticleTypes.POOF);
            areaeffectcloudentity.setRadiusOnUse(0f);
            areaeffectcloudentity.setWaitTime(0);
            areaeffectcloudentity.setDuration(7);
            areaeffectcloudentity.setRadiusPerTick(2.0f);

            if(!list.isEmpty()){
                for(LivingEntity entity : list){
                    float angle = (float) Math.atan2((player.getX() - entity.getX()),-(player.getZ() - entity.getZ()));
                    entity.knockback(2, (double) MathHelper.sin(angle), (double)(-MathHelper.cos(angle)));
                }
            }

            player.level.addFreshEntity(areaeffectcloudentity);
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

package com.greenone.lostheroes.common.deity.abilities;

import com.greenone.lostheroes.common.player.capability.IMana;
import com.greenone.lostheroes.common.potion.LHEffects;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.MathHelper;

import java.util.List;

public class AresAbilities extends AbstractAbility {
    @Override
    public void majorAbility(PlayerEntity player, IMana manaCap) {
        if(!player.hasEffect(LHEffects.RAGE)){
            if(player.isCreative() || manaCap.consumeMana(majorManaReq(manaCap.getMaxMana()))){
                player.addEffect(new EffectInstance(LHEffects.RAGE, 900, 1, false, false, false, null));
            }
        }
    }

    @Override
    public void minorAbility(PlayerEntity player, IMana manaCap) {
        if (player.isCreative() || manaCap.consumeMana(minorManaReq(manaCap.getMaxMana()))) {
            List<LivingEntity> list = player.level.getEntitiesOfClass(LivingEntity.class, player.getBoundingBox().inflate(10.0d, 2.0D, 10.0d));
            AreaEffectCloudEntity areaeffectcloudentity = new AreaEffectCloudEntity(player.level, player.getX(), player.getY(), player.getZ());
            areaeffectcloudentity.setParticle(ParticleTypes.AMBIENT_ENTITY_EFFECT);
            areaeffectcloudentity.setRadiusOnUse(0f);
            areaeffectcloudentity.setWaitTime(0);
            areaeffectcloudentity.setDuration(7);
            areaeffectcloudentity.setRadiusPerTick(2.0f);

            if(!list.isEmpty()){
                for(LivingEntity entity : list){
                    if(entity != player) entity.addEffect(new EffectInstance(Effects.WEAKNESS, 300));
                }
            }

            player.level.addFreshEntity(areaeffectcloudentity);
        }
    }

    @Override
    public float majorManaReq(float maxMana) {
        return maxMana;
    }

    @Override
    public float minorManaReq(float maxMana) {
        return maxMana / 3;
    }
}

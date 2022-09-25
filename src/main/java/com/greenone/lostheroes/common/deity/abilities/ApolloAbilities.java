package com.greenone.lostheroes.common.deity.abilities;

import com.greenone.lostheroes.common.LHUtils;
import com.greenone.lostheroes.common.entity.projectile.LightRayProjectile;
import com.greenone.lostheroes.common.entity.projectile.WaterBallProjectile;
import com.greenone.lostheroes.common.player.capability.IMana;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;

import java.util.List;

public class ApolloAbilities extends AbstractAbility {
    @Override
    public void majorAbility(PlayerEntity player, IMana manaCap) {
        if(player.isCreative() || manaCap.consumeMana(majorManaReq(manaCap.getMaxMana()))) {
            Vector3d lookVec = LHUtils.getLookingAt(player, 24).getLocation();
            Vector3d eyeVec = player.getEyePosition(1.0f);
            double d0 = eyeVec.x();
            double d1 = eyeVec.y();
            double d2 = eyeVec.z();
            double d3 = lookVec.x() - d0;
            double d4 = lookVec.y() - d1;
            double d5 = lookVec.z() - d2;
            LightRayProjectile lightRay = new LightRayProjectile(player.level, player, d3, d4, d5);
            lightRay.setOwner(player);

            lightRay.setPosRaw(d0, d1, d2);
            player.level.addFreshEntity(lightRay);
        }
    }

    @Override
    public void minorAbility(PlayerEntity player, IMana manaCap) {
        if(player.isSteppingCarefully() && (player.isCreative() || manaCap.getMana()>0)){
            float healthDiff = player.getMaxHealth()-player.getHealth();
            if(player.isCreative() || manaCap.consumeMana(healthDiff/2)){
                player.setHealth(player.getMaxHealth());
            }else{
                player.setHealth(player.getHealth()+manaCap.getMana());
                manaCap.setMana(0);
            }
        }else if(player.isCreative() || manaCap.getMana()>0){
            Vector3d entityVec = LHUtils.getLookingAt(player, 0).getLocation();
            BlockPos entityPos = new BlockPos(entityVec.x, entityVec.y, entityVec.z);
            List<LivingEntity> list = player.level.getNearbyEntities(LivingEntity.class, new EntityPredicate().range(2), player, new AxisAlignedBB(entityPos).inflate(2));
            if(!list.isEmpty()){
                LivingEntity entity = list.get(0);
                float healthDiff = entity.getMaxHealth() - entity.getHealth();
                if(player.isCreative() || manaCap.consumeMana(healthDiff/2)){
                    entity.setHealth(entity.getMaxHealth());
                }else{
                    entity.setHealth(entity.getHealth()+manaCap.getMana());
                    manaCap.setMana(0);
                }
            }
        }
    }

    @Override
    public float majorManaReq(float maxMana) {
        return maxMana / 4;
    }

    @Override
    public float minorManaReq(float maxMana) {
        return 0;
    }
}

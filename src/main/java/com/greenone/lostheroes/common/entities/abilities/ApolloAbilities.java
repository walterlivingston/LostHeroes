package com.greenone.lostheroes.common.entities.abilities;

import com.greenone.lostheroes.common.capabilities.CapabilityRegistry;
import com.greenone.lostheroes.common.capabilities.IPlayerCap;
import com.greenone.lostheroes.common.util.LHUtils;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;

import java.util.List;

public class ApolloAbilities extends AbstractAbility{
    @Override
    public void mainAbility(PlayerEntity playerIn) {
        player = playerIn;
        playerCap = player.getCapability(CapabilityRegistry.PLAYERCAP, null).orElse(null);
        if(player.isSteppingCarefully() && (player.isCreative() || playerCap.getMana()>0)){
            float healthDiff = player.getMaxHealth()-player.getHealth();
            if(player.isCreative() || playerCap.consumeMana(healthDiff/2)){
                player.setHealth(player.getMaxHealth());
            }else{
                player.setHealth(player.getHealth()+playerCap.getMana());
                playerCap.setMana(0);
            }
            if(!player.isCreative()) success();
        }else if(player.isCreative() || playerCap.getMana()>0){
            Vector3d entityVec = LHUtils.getLookingAt(player, 0);
            BlockPos entityPos = new BlockPos(entityVec.x, entityVec.y, entityVec.z);
            List<LivingEntity> list = player.level.getNearbyEntities(LivingEntity.class, new EntityPredicate().range(2), player, new AxisAlignedBB(entityPos).inflate(2));
            if(!list.isEmpty()){
                LivingEntity entity = list.get(0);
                float healthDiff = entity.getMaxHealth() - entity.getHealth();
                if(player.isCreative() || playerCap.consumeMana(healthDiff/2)){
                    entity.setHealth(entity.getMaxHealth());
                }else{
                    entity.setHealth(entity.getHealth()+playerCap.getMana());
                    playerCap.setMana(0);
                }
                if(!player.isCreative()) success();
            }
        }
    }

    @Override
    public void minorAbility(PlayerEntity playerIn) {
        player = playerIn;
        playerCap = player.getCapability(CapabilityRegistry.PLAYERCAP, null).orElse(null);
    }

    @Override
    public float getMainManaReq() {
        return 0;
    }

    @Override
    public float getMinorManaReq() {
        return 0;
    }
}

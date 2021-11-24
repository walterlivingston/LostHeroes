package com.greenone.lostheroes.common.entities.abilities;

import com.greenone.lostheroes.common.capabilities.CapabilityRegistry;
import com.greenone.lostheroes.common.capabilities.IPlayerCap;
import com.greenone.lostheroes.common.util.LHUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class ApolloAbilities extends AbstractAbility{
    @Override
    public void mainAbility(Player playerIn) {
        player = playerIn;
        if(player.isSteppingCarefully() && playerCap().getMana()>0){
            float healthDiff = player.getMaxHealth()-player.getHealth();
            if(playerCap().consumeMana(healthDiff/2)){
                player.setHealth(player.getMaxHealth());
                success();
            }else{
                player.setHealth(player.getHealth()+playerCap().getMana());
                playerCap().setMana(0);
                success();
            }
        }else if(playerCap().getMana()>0){
            Vec3 entityVec = LHUtils.getLookingAt(player, 0);
            BlockPos entityPos = new BlockPos(entityVec.x, entityVec.y, entityVec.z);
            AABB aabb = (new AABB(player.blockPosition())).inflate(2);
            List<LivingEntity> list = player.level.getEntitiesOfClass(LivingEntity.class, aabb);
            if(!list.isEmpty()){
                LivingEntity entity = list.get(0);
                float healthDiff = entity.getMaxHealth() - entity.getHealth();
                if(playerCap().consumeMana(healthDiff/2)){
                    entity.setHealth(entity.getMaxHealth());
                    success();
                }else{
                    entity.setHealth(entity.getHealth()+playerCap().getMana());
                    playerCap().setMana(0);
                    success();
                }
            }
        }
    }

    @Override
    public void minorAbility(Player playerIn) {
        player = playerIn;
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

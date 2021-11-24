package com.greenone.lostheroes.common.entities.abilities;

import com.greenone.lostheroes.common.config.LHConfig;
import com.greenone.lostheroes.common.util.LHUtils;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public class ArtemisAbilities extends AbstractAbility{

    @Override
    public void mainAbility(Player playerIn) {
        player = playerIn;
        if(LHUtils.getLookingAt(player, 5)!=null && playerCap().consumeMana(getMainManaReq())){
            Vec3 spawnVec = LHUtils.getLookingAt(player, 5);
            Wolf wolf = new Wolf(EntityType.WOLF, player.level);
            wolf.tame(player);
            player.level.addFreshEntity(wolf);
            wolf.setPos(spawnVec.x, spawnVec.y, spawnVec.z);
            success();
        }
    }

    @Override
    public void minorAbility(Player playerIn) {
        player = playerIn;
    }

    @Override
    public float getMainManaReq() {
        return LHConfig.getBaseMaxMana();
    }

    @Override
    public float getMinorManaReq() {
        return 0;
    }
}

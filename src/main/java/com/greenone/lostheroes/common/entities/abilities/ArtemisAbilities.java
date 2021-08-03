package com.greenone.lostheroes.common.entities.abilities;

import com.greenone.lostheroes.common.capabilities.CapabilityRegistry;
import com.greenone.lostheroes.common.capabilities.IPlayerCap;
import com.greenone.lostheroes.common.config.LHConfig;
import com.greenone.lostheroes.common.util.LHUtils;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public class ArtemisAbilities extends AbstractAbility{
    private float mainManaReq= LHConfig.getMaxMana();

    @Override
    public void mainAbility(Player player) {
        IPlayerCap playerCap = player.getCapability(CapabilityRegistry.PLAYERCAP, null).orElse(null);
        if(LHUtils.getLookingAt(player, 5)!=null && playerCap.consumeMana(getMainManaReq(player))){
            Vec3 spawnVec = LHUtils.getLookingAt(player, 5);
            Wolf wolf = new Wolf(EntityType.WOLF, player.level);
            wolf.tame(player);
            player.level.addFreshEntity(wolf);
            wolf.setPos(spawnVec.x, spawnVec.y, spawnVec.z);
        }
    }

    @Override
    public void minorAbility(Player player) {

    }

    @Override
    public float getMainManaReq() {
        return mainManaReq;
    }

    public float getMainManaReq(Player player) {
        IPlayerCap playerCap = player.getCapability(CapabilityRegistry.PLAYERCAP, null).orElse(null);
        mainManaReq = playerCap.getMaxMana();
        return getMainManaReq();
    }

    @Override
    public float getMinorManaReq() {
        return 0;
    }
}

package com.greenone.lostheroes.common.entities.abilities;

import com.greenone.lostheroes.common.capabilities.CapabilityRegistry;
import com.greenone.lostheroes.common.capabilities.IPlayerCap;
import com.greenone.lostheroes.common.config.LHConfig;
import com.greenone.lostheroes.common.util.LHUtils;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.vector.Vector3d;

public class ArtemisAbilities extends AbstractAbility{
    private float mainManaReq= LHConfig.getMaxMana();

    @Override
    public void mainAbility(PlayerEntity player) {
        IPlayerCap playerCap = player.getCapability(CapabilityRegistry.PLAYERCAP, null).orElse(null);
        if(LHUtils.getLookingAt(player, 5)!=null && playerCap.consumeMana(getMainManaReq(player))){
            Vector3d spawnVec = LHUtils.getLookingAt(player, 5);
            WolfEntity wolf = new WolfEntity(EntityType.WOLF, player.level);
            wolf.tame(player);
            player.level.addFreshEntity(wolf);
            wolf.setPos(spawnVec.x, spawnVec.y, spawnVec.z);
        }
    }

    @Override
    public void minorAbility(PlayerEntity player) {

    }

    @Override
    public float getMainManaReq() {
        return mainManaReq;
    }

    public float getMainManaReq(PlayerEntity player) {
        IPlayerCap playerCap = player.getCapability(CapabilityRegistry.PLAYERCAP, null).orElse(null);
        mainManaReq = playerCap.getMaxMana();
        return getMainManaReq();
    }

    @Override
    public float getMinorManaReq() {
        return 0;
    }
}

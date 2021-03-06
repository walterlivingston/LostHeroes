package com.greenone.lostheroes.common.entities.abilities;

import com.greenone.lostheroes.common.config.LHConfig;
import com.greenone.lostheroes.common.init.LHEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class AphroditeAbilities extends AbstractAbility{
    @Override
    public void mainAbility(Player playerIn) {
        player = playerIn;
        AABB aabb = (new AABB(player.blockPosition())).inflate(10).expandTowards(0.0D, (double)player.level.getHeight(), 0.0D);
        List<Monster> list = player.level.getEntitiesOfClass(Monster.class, aabb);
        if(!list.isEmpty() && (list.get(0) instanceof Monster || list.get(0) instanceof Raider) && (player.isCreative() || playerCap().consumeMana(getMainManaReq()))){
            list.get(0).addEffect(new MobEffectInstance(LHEffects.APATHY, 1200));
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

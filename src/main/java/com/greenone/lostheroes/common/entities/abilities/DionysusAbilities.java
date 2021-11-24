package com.greenone.lostheroes.common.entities.abilities;

import com.greenone.lostheroes.common.capabilities.CapabilityRegistry;
import com.greenone.lostheroes.common.capabilities.IPlayerCap;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class DionysusAbilities extends AbstractAbility{
    @Override
    public void mainAbility(Player playerIn) {
        player = playerIn;
        if(player.isCreative() || playerCap().consumeMana(getMainManaReq())){
            AABB aabb = (new AABB(player.blockPosition())).inflate(5);
            List<LivingEntity> list = player.level.getEntitiesOfClass(LivingEntity.class, aabb);
            list.forEach(e -> {
                if(!e.equals(player)){
                    e.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 200));
                    if(e.getMobType() == MobType.UNDEAD) e.addEffect(new MobEffectInstance(MobEffects.WITHER, 200, 5));
                    else e.addEffect(new MobEffectInstance(MobEffects.POISON, 200, 5));
                    e.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 200));
                }
            });
            if(!list.isEmpty()) success();
        }
    }

    @Override
    public void minorAbility(Player playerIn) {
        player = playerIn;
    }

    @Override
    public float getMainManaReq() {
        return 2.5f;
    }

    @Override
    public float getMinorManaReq() {
        return 0;
    }
}

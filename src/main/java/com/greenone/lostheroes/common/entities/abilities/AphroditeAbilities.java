package com.greenone.lostheroes.common.entities.abilities;

import com.greenone.lostheroes.common.capabilities.CapabilityRegistry;
import com.greenone.lostheroes.common.capabilities.IPlayerCap;
import com.greenone.lostheroes.common.potions.LHEffects;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.PrioritizedGoal;
import net.minecraft.entity.monster.AbstractRaiderEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.math.AxisAlignedBB;

import java.util.ArrayList;
import java.util.List;

public class AphroditeAbilities extends AbstractAbility{
    @Override
    public void mainAbility(PlayerEntity player) {
        IPlayerCap playerCap = player.getCapability(CapabilityRegistry.PLAYERCAP, null).orElse(null);
        List<LivingEntity> list = player.level.getNearbyEntities(LivingEntity.class, new EntityPredicate().range(10), player, new AxisAlignedBB(player.blockPosition()).inflate(10));
        if(!list.isEmpty() && (list.get(0) instanceof MonsterEntity || list.get(0) instanceof AbstractRaiderEntity) && (player.isCreative() || playerCap.consumeMana(getMainManaReq()))){
            list.get(0).addEffect(new EffectInstance(LHEffects.APATHY, 1200));
        }
    }

    @Override
    public void minorAbility(PlayerEntity player) {

    }

    @Override
    public float getMainManaReq() {
        return 10;
    }

    @Override
    public float getMinorManaReq() {
        return 0;
    }
}

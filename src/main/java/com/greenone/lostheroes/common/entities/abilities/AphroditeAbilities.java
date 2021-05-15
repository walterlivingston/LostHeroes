package com.greenone.lostheroes.common.entities.abilities;

import com.greenone.lostheroes.common.capabilities.CapabilityRegistry;
import com.greenone.lostheroes.common.capabilities.IPlayerCap;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.PrioritizedGoal;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.AxisAlignedBB;

import java.util.ArrayList;
import java.util.List;

public class AphroditeAbilities extends AbstractAbility{
    @Override
    public void mainAbility(PlayerEntity player) {
        IPlayerCap playerCap = player.getCapability(CapabilityRegistry.PLAYERCAP, null).orElse(null);
        List<MonsterEntity> list = player.level.getNearbyEntities(MonsterEntity.class, new EntityPredicate().range(10), player, new AxisAlignedBB(player.blockPosition()).inflate(10));
        if(!list.isEmpty() && (player.isCreative() || playerCap.consumeMana(10))){
            List<PrioritizedGoal> meleeGoal = new ArrayList<>();
            List<PrioritizedGoal> natGoal = new ArrayList<>();
            list.get(0).goalSelector.getRunningGoals().filter(g -> g.getGoal() instanceof MeleeAttackGoal).forEach(meleeGoal::add);
            list.get(0).goalSelector.getRunningGoals().filter(g -> g.getGoal() instanceof NearestAttackableTargetGoal).forEach(natGoal::add);
            for(PrioritizedGoal goal : meleeGoal){
                list.get(0).goalSelector.removeGoal(goal.getGoal());
            }
            for(PrioritizedGoal goal : natGoal){
                list.get(0).goalSelector.removeGoal(goal.getGoal());
            }
        }
    }

    @Override
    public void minorAbility(PlayerEntity player) {

    }
}

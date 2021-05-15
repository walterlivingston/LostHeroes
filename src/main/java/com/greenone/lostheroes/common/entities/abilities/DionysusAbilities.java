package com.greenone.lostheroes.common.entities.abilities;

import com.greenone.lostheroes.common.capabilities.CapabilityRegistry;
import com.greenone.lostheroes.common.capabilities.IPlayerCap;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.AxisAlignedBB;

import java.util.List;

public class DionysusAbilities extends AbstractAbility{
    @Override
    public void mainAbility(PlayerEntity player) {
        IPlayerCap playerCap = player.getCapability(CapabilityRegistry.PLAYERCAP, null).orElse(null);
        if(player.isCreative() || playerCap.consumeMana(2.5F)){
            List<LivingEntity> list = player.level.getNearbyEntities(LivingEntity.class, new EntityPredicate().range(5), player, new AxisAlignedBB(player.blockPosition()).inflate(5));
            list.forEach(e -> {
                if(!e.equals(player)){
                    e.addEffect(new EffectInstance(Effects.CONFUSION, 200));
                    if(e.getMobType() == CreatureAttribute.UNDEAD) e.addEffect(new EffectInstance(Effects.WITHER, 200, 5));
                    else e.addEffect(new EffectInstance(Effects.POISON, 200, 5));
                    e.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 200));
                }
            });
        }
    }

    @Override
    public void minorAbility(PlayerEntity player) {

    }
}

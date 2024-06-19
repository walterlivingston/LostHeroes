package com.greenone.lostheroes.common.deity.abilities;

import com.greenone.lostheroes.common.LHUtils;
import com.greenone.lostheroes.common.player.capability.IMana;
import com.greenone.lostheroes.common.potion.LHEffects;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;

import java.util.List;
import java.util.Random;

public class AthenaAbilities extends AbstractAbility{
    // Aegis Aura
    @Override
    public void majorAbility(PlayerEntity player, IMana manaCap) {
        if(!player.hasEffect(LHEffects.AEGIS_AURA)){
            if(player.isCreative() || manaCap.consumeMana(majorManaReq(manaCap.getMaxMana()))){
                player.addEffect(new EffectInstance(LHEffects.AEGIS_AURA, 150, 1, false, false, false, null));
            }
        }
    }

    // Parry
    @Override
    public void minorAbility(PlayerEntity player, IMana manaCap) {
    if(player.isCreative() || player.isCreative() || manaCap.consumeMana(minorManaReq(manaCap.getMaxMana()))){
            Vector3d entityVec = LHUtils.getLookingAt(player, 3).getLocation();
            BlockPos entityPos = new BlockPos(entityVec.x, entityVec.y, entityVec.z);
            List<LivingEntity> list = player.level.getNearbyEntities(LivingEntity.class, new EntityPredicate().range(3), player, new AxisAlignedBB(entityPos).inflate(2));
            if(!list.isEmpty()){
                LivingEntity entity = list.get(0);
                if (entity.getMainHandItem().getItem() instanceof TieredItem || entity.getMainHandItem().getItem() instanceof BowItem || entity.getMainHandItem().getItem() instanceof CrossbowItem){
                    Random rand = new Random();
                    Item weapon = entity.getMainHandItem().getItem();
                    entity.getMainHandItem().shrink(entity.getMainHandItem().getCount());
                    entity.level.addFreshEntity(new ItemEntity(entity.level, entity.getX() + rand.nextDouble(), entity.getY() + rand.nextDouble(), entity.getZ() + rand.nextDouble(), new ItemStack(weapon)));
                }
            }
        }
    }

    @Override
    public float majorManaReq(float maxMana) {
        return maxMana;
    }

    @Override
    public float minorManaReq(float maxMana) {
        return maxMana / 2;
    }
}

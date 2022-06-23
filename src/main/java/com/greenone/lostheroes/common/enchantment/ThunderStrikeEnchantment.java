package com.greenone.lostheroes.common.enchantment;

import com.greenone.lostheroes.common.Deity;
import com.greenone.lostheroes.common.init.Deities;
import com.greenone.lostheroes.common.util.LHUtils;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;

import java.util.Random;

public class ThunderStrikeEnchantment extends GodlyEnchantment{
    public ThunderStrikeEnchantment(Rarity p_i46731_1_, EnchantmentType p_i46731_2_, EquipmentSlotType... p_i46731_3_) {
        super(p_i46731_1_, p_i46731_2_, Deities.ZEUS, p_i46731_3_);
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public void doPostAttack(LivingEntity player, Entity victim, int level) {
        if(player.level.isClientSide()){
            Random rand = new Random();
            if(rand.nextFloat() <= level*0.05f){
                Vector3d vec = victim.getPosition(0.1f);
                LightningBoltEntity lightningBoltEntity = EntityType.LIGHTNING_BOLT.create(player.level);
                lightningBoltEntity.moveTo(vec);
                lightningBoltEntity.setCause(player instanceof ServerPlayerEntity ? (ServerPlayerEntity) player : null);
                player.level.addFreshEntity(lightningBoltEntity);
            }
        }
    }
}

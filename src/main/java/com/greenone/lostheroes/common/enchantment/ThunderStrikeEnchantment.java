package com.greenone.lostheroes.common.enchantment;

import com.greenone.lostheroes.common.init.Deities;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.phys.Vec3;

import java.util.Random;

public class ThunderStrikeEnchantment extends GodlyEnchantment{
    public ThunderStrikeEnchantment(Rarity p_i46731_1_, EnchantmentCategory p_i46731_2_, EquipmentSlot... p_i46731_3_) {
        super(p_i46731_1_, p_i46731_2_, Deities.ZEUS, p_i46731_3_);
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public void doPostAttack(LivingEntity player, Entity victim, int level) {
        Random rand = new Random();
        if(rand.nextFloat() <= level*0.05f){
            Vec3 vec = victim.getPosition(0.1f);
            LightningBolt lightningBoltEntity = EntityType.LIGHTNING_BOLT.create(player.level);
            lightningBoltEntity.moveTo(vec);
            lightningBoltEntity.setCause(player instanceof ServerPlayer ? (ServerPlayer) player : null);
            player.level.addFreshEntity(lightningBoltEntity);
        }
    }
}

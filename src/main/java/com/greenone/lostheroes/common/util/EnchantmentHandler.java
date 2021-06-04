package com.greenone.lostheroes.common.util;

import com.greenone.lostheroes.LostHeroes;
import com.greenone.lostheroes.common.Blessing;
import com.greenone.lostheroes.common.enchantment.BrilliantRiposteEnchantment;
import com.greenone.lostheroes.common.enchantment.LHEnchants;
import com.greenone.lostheroes.common.potions.LHEffect;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import net.minecraftforge.event.entity.living.PotionEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Collectors;

@Mod.EventBusSubscriber(modid = LostHeroes.MOD_ID)
public class EnchantmentHandler {
    public static final EnchantmentHandler instance = new EnchantmentHandler();

    public static void enchantmentCheck(PlayerEntity player){
        if(player.getArmorValue() > 0){
            for(ItemStack stack : player.getArmorSlots()){
                if(EnchantmentHelper.getItemEnchantmentLevel(LHEnchants.REHYDRATION, stack) > 0 && player.isInWater()){
                    if(player.getHealth() < player.getMaxHealth()) player.heal(0.001F);
                }
                if(EnchantmentHelper.getItemEnchantmentLevel(LHEnchants.FLEET, stack) > 0){
                    player.addEffect(new EffectInstance(new LHEffect().addAttributeModifier(Attributes.MOVEMENT_SPEED, "91AEAA56-376B-4498-935B-2F7F68070635", (double)0.2F, AttributeModifier.Operation.MULTIPLY_TOTAL), 30, EnchantmentHelper.getItemEnchantmentLevel(LHEnchants.FLEET, stack), false, false, false, null));
                }
                if(EnchantmentHelper.getItemEnchantmentLevel(LHEnchants.DAEDALUS_ASPECT, stack) > 0){
                    player.addEffect(new EffectInstance(new LHEffect().addAttributeModifier(Attributes.ATTACK_DAMAGE, "648D7064-6A60-4F59-8ABE-C2C23A6DD7A9", 0.3D, AttributeModifier.Operation.ADDITION), 30, EnchantmentHelper.getItemEnchantmentLevel(LHEnchants.DAEDALUS_ASPECT, stack), false, false, false, null));
                }
            }
        }
    }

    @SubscribeEvent
    public void onSetAttackTarget(final LivingSetAttackTargetEvent event) {
        if (event.getTarget() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) event.getTarget();
            player.getArmorSlots().forEach((armor) -> {
                if (EnchantmentHelper.getItemEnchantmentLevel(LHEnchants.UNDEAD_PRESENCE, armor) > 0 && event.getEntityLiving().getMobType() == CreatureAttribute.UNDEAD) {
                    ((MonsterEntity) event.getEntityLiving()).setTarget(null);
                }
            });
        }
    }

    @SubscribeEvent
    public void onCriticalHit(final CriticalHitEvent event) {
        if(EnchantmentHelper.getItemEnchantmentLevel(LHEnchants.PRECISION, event.getPlayer().getMainHandItem()) > 0){
            Random random = new Random();
            if(random.nextFloat() < 0.1*EnchantmentHelper.getItemEnchantmentLevel(LHEnchants.PRECISION, event.getPlayer().getMainHandItem())){
                event.setDamageModifier(EnchantmentHelper.getItemEnchantmentLevel(LHEnchants.PRECISION, event.getPlayer().getMainHandItem()));
                event.setResult(Event.Result.ALLOW);
            }
        }
    }

    @SubscribeEvent
    public void onAttackEntity(final AttackEntityEvent event) {
        if (EnchantmentHelper.getItemEnchantmentLevel(LHEnchants.UNREQUITED, event.getPlayer().getMainHandItem()) > 0) {
            Random random = new Random();
            if(random.nextFloat() < 0.1*EnchantmentHelper.getItemEnchantmentLevel(LHEnchants.UNREQUITED, event.getPlayer().getMainHandItem())){
                event.getPlayer().heal(5);
            }
        }
        if (EnchantmentHelper.getItemEnchantmentLevel(LHEnchants.BRILLIANT_RIPOSTE, event.getPlayer().getMainHandItem()) > 0) {
            if(event.getPlayer().tickCount-event.getPlayer().getLastHurtByMobTimestamp() < 20){
                EnchantmentHelper.getEnchantments(event.getPlayer().getMainHandItem()).computeIfPresent(LHEnchants.BRILLIANT_RIPOSTE, (e, l) -> {
                    ((BrilliantRiposteEnchantment)e).setDamageBonus(l);
                    return l;
                });
            }
        }
    }

    @SubscribeEvent
    public void onPotionAdded(final PotionEvent.PotionApplicableEvent event) {
        if(event.getEntityLiving() instanceof PlayerEntity && !(event.getPotionEffect().getEffect() instanceof Blessing)){
            PlayerEntity player = (PlayerEntity) event.getEntityLiving();
            player.getArmorSlots().forEach((a) -> {
                if(EnchantmentHelper.getItemEnchantmentLevel(LHEnchants.VINTAGE, a) > 0){
                    int level = EnchantmentHelper.getItemEnchantmentLevel(LHEnchants.VINTAGE, a);
                    event.getPotionEffect().update(new EffectInstance(event.getPotionEffect().getEffect(), event.getPotionEffect().getDuration()*(3/2 * level), event.getPotionEffect().getAmplifier() + level - 1));
                }
            });
        }
    }

    @SubscribeEvent
    public void onFarmlandTrample(final BlockEvent.FarmlandTrampleEvent event) {
        if(event.getEntity() instanceof PlayerEntity){
            PlayerEntity player = (PlayerEntity) event.getEntity();
            player.getArmorSlots().forEach((a) -> {
                if(EnchantmentHelper.getItemEnchantmentLevel(LHEnchants.LEAF_STEP, a) > 0){
                    event.setCanceled(true);
                }
            });
        }
    }

    @SubscribeEvent
    public void onProjectileImpact(final ProjectileImpactEvent event) {
        if(event.getEntity() instanceof AbstractArrowEntity){
            AbstractArrowEntity arrow = (AbstractArrowEntity) event.getEntity();
            if(event.getRayTraceResult().getType() == RayTraceResult.Type.ENTITY){
                EntityRayTraceResult entityRay = (EntityRayTraceResult) event.getRayTraceResult();
                if(entityRay.getEntity() instanceof LivingEntity){
                    LivingEntity living = (LivingEntity) entityRay.getEntity();
                    float chance = 0.0f;
                    if(arrow.getTags().contains("poison_volley1")){
                        chance = 1*0.15f;
                    }
                    if(arrow.getTags().contains("poison_volley2")){
                        chance = 2*0.15f;
                    }
                    if(arrow.getTags().contains("poison_volley3")){
                        chance = 3*0.15f;
                    }
                    if(chance!=0.0f && new Random().nextFloat() < chance ){
                        ArrayList<Effect> bad_effects = (ArrayList<Effect>) Registry.MOB_EFFECT.stream().filter((e) -> e.getCategory() == EffectType.HARMFUL).collect(Collectors.toList());
                        living.addEffect(new EffectInstance(bad_effects.get(new Random().nextInt(bad_effects.size())), 600));
                        System.out.println(living.getActiveEffects());
                    }
                }
            }
        }
    }
}

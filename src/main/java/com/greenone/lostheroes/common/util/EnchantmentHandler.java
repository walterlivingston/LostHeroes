package com.greenone.lostheroes.common.util;

import com.greenone.lostheroes.LostHeroes;
import com.greenone.lostheroes.common.Blessing;
import com.greenone.lostheroes.common.enchantment.BrilliantRiposteEnchantment;
import com.greenone.lostheroes.common.init.LHEnchants;
import com.greenone.lostheroes.common.potions.LHEffect;
import net.minecraft.core.Registry;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
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

    public static void enchantmentCheck(Player player){
        if(player.getArmorValue() > 0){
            for(ItemStack stack : player.getArmorSlots()){
                if(EnchantmentHelper.getItemEnchantmentLevel(LHEnchants.REHYDRATION, stack) > 0 && player.isInWater()){
                    if(player.getHealth() < player.getMaxHealth()) player.heal(0.001F);
                }
                if(EnchantmentHelper.getItemEnchantmentLevel(LHEnchants.FLEET, stack) > 0){
                    player.addEffect(new MobEffectInstance(new LHEffect().addAttributeModifier(Attributes.MOVEMENT_SPEED, "91AEAA56-376B-4498-935B-2F7F68070635", (double)0.2F, AttributeModifier.Operation.MULTIPLY_TOTAL), 30, EnchantmentHelper.getItemEnchantmentLevel(LHEnchants.FLEET, stack), false, false, false, null));
                }
                if(EnchantmentHelper.getItemEnchantmentLevel(LHEnchants.BRUTISH, stack) > 0){
                    player.addEffect(new MobEffectInstance(new LHEffect().addAttributeModifier(Attributes.ATTACK_DAMAGE, "648D7064-6A60-4F59-8ABE-C2C23A6DD7A9", 0.3D, AttributeModifier.Operation.ADDITION), 30, EnchantmentHelper.getItemEnchantmentLevel(LHEnchants.BRUTISH, stack), false, false, false, null));
                }
            }
        }
    }

    @SubscribeEvent
    public void onSetAttackTarget(final LivingSetAttackTargetEvent event) {
        if (event.getTarget() instanceof Player) {
            Player player = (Player) event.getTarget();
            player.getArmorSlots().forEach((armor) -> {
                if (EnchantmentHelper.getItemEnchantmentLevel(LHEnchants.UNDEAD_PRESENCE, armor) > 0 && event.getEntityLiving().getMobType() == MobType.UNDEAD && event.getTarget()!=null) {
                    ((Monster) event.getEntityLiving()).setTarget(null);
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
        if(event.getEntityLiving() instanceof Player && !(event.getPotionEffect().getEffect() instanceof Blessing)){
            Player player = (Player) event.getEntityLiving();
            player.getArmorSlots().forEach((a) -> {
                if(EnchantmentHelper.getItemEnchantmentLevel(LHEnchants.VINTAGE, a) > 0){
                    int level = EnchantmentHelper.getItemEnchantmentLevel(LHEnchants.VINTAGE, a);
                    event.getPotionEffect().update(new MobEffectInstance(event.getPotionEffect().getEffect(), event.getPotionEffect().getDuration()*(3/2 * level), event.getPotionEffect().getAmplifier() + level - 1));
                }
            });
        }
    }

    @SubscribeEvent
    public void onFarmlandTrample(final BlockEvent.FarmlandTrampleEvent event) {
        if(event.getEntity() instanceof Player){
            Player player = (Player) event.getEntity();
            player.getArmorSlots().forEach((a) -> {
                if(EnchantmentHelper.getItemEnchantmentLevel(LHEnchants.LEAF_STEP, a) > 0){
                    event.setCanceled(true);
                }
            });
        }
    }

    @SubscribeEvent
    public void onProjectileImpact(final ProjectileImpactEvent event) {
        if(event.getEntity() instanceof AbstractArrow){
            AbstractArrow arrow = (AbstractArrow) event.getEntity();
            if(event.getRayTraceResult().getType() == BlockHitResult.Type.ENTITY){
                EntityHitResult entityRay = (EntityHitResult) event.getRayTraceResult();
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
                        ArrayList<MobEffect> bad_effects = (ArrayList<MobEffect>) Registry.MOB_EFFECT.stream().filter((e) -> e.getCategory() == MobEffectCategory.HARMFUL).collect(Collectors.toList());
                        living.addEffect(new MobEffectInstance(bad_effects.get(new Random().nextInt(bad_effects.size())), 600));
                        System.out.println(living.getActiveEffects());
                    }
                }
            }
        }
    }
}

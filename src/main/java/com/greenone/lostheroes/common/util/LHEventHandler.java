package com.greenone.lostheroes.common.util;

import com.greenone.lostheroes.LostHeroes;
import com.greenone.lostheroes.common.Deity;
import com.greenone.lostheroes.common.capabilities.CapabilityRegistry;
import com.greenone.lostheroes.common.capabilities.IPlayerCap;
import com.greenone.lostheroes.common.capabilities.PlayerCap;
import com.greenone.lostheroes.common.init.LHCommands;
import com.greenone.lostheroes.common.init.Blessings;
import com.greenone.lostheroes.common.init.Deities;
import com.greenone.lostheroes.common.potions.LHEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.AbstractRaiderEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Random;

@Mod.EventBusSubscriber(modid = LostHeroes.MOD_ID)
public class LHEventHandler {
    public static final LHEventHandler instance = new LHEventHandler();

    @SubscribeEvent
    public void onPlayerJoined(final PlayerEvent.PlayerLoggedInEvent event) {
        IPlayerCap playerCap = event.getPlayer().getCapability(CapabilityRegistry.PLAYERCAP, null).orElseThrow(() -> new IllegalArgumentException("No Capability at Login"));
        if (playerCap.getParent() == null) {
            Random rand = new Random();
            Object[] values = Deities.list.values().toArray();
            playerCap.setParent((Deity) values[rand.nextInt(values.length)]);
            event.getPlayer().sendMessage(new StringTextComponent("You have been claimed by " + playerCap.getParent().getFormattedName()), event.getPlayer().getUUID());
            playerCap.sync(event.getPlayer());
        }
    }

    @SubscribeEvent
    public void attachCapabilities(final AttachCapabilitiesEvent event) {
        if ((event.getObject()) == null) return;
        if (event.getObject() instanceof PlayerEntity) event.addCapability(new ResourceLocation(LostHeroes.MOD_ID, "player_cap"), new PlayerCap());
    }

    @SubscribeEvent
    public static void registerCommands(final RegisterCommandsEvent event) {
        LHCommands.register(event.getDispatcher());
    }

    @SubscribeEvent
    public void onPlayerTick(final TickEvent.PlayerTickEvent event) {
        PlayerEntity player = event.player;
        IPlayerCap playerCap = player.getCapability(CapabilityRegistry.PLAYERCAP, null).orElse(null);
        if (!player.isDeadOrDying()) {
            if (playerCap.getMana() < playerCap.getMaxMana()) playerCap.setMana(playerCap.getMana() + 0.00001F);
            if (playerCap != null && playerCap.getParent() != null) {
                if (playerCap.getHadesCooldown() > 0) playerCap.decreaseHadesCooldown();
                if (!player.hasEffect(Blessings.ZEUS) && !player.isCreative() && player.abilities.mayfly) {
                    player.abilities.mayfly = false;
                    player.abilities.flying = false;
                    player.onUpdateAbilities();
                }
                LHUtils.abilityCheck(player, playerCap);
                playerCap.sync(player);
            }
        }
        EnchantmentHandler.enchantmentCheck(player);
    }

    @SubscribeEvent
    public void onPlayerClone(final PlayerEvent.Clone event) {
        PlayerEntity originalPlayer = event.getOriginal();
        PlayerEntity newPlayer = event.getPlayer();
        IPlayerCap oPlayerCap = originalPlayer.getCapability(CapabilityRegistry.PLAYERCAP, null).orElse(null);
        IPlayerCap nPlayerCap = newPlayer.getCapability(CapabilityRegistry.PLAYERCAP, null).orElse(null);
        nPlayerCap.setParent(oPlayerCap.getParent());
        nPlayerCap.setMana(oPlayerCap.getMana());
        nPlayerCap.setMaxMana(oPlayerCap.getMaxMana());
        nPlayerCap.setHadesCooldown(oPlayerCap.getHadesCooldown());
    }

    @SubscribeEvent
    public void onPlayerDeath(final LivingDeathEvent event) {
        if (event.getEntityLiving() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) event.getEntityLiving();
            IPlayerCap playerCap = player.getCapability(CapabilityRegistry.PLAYERCAP, null).orElse(null);
            boolean flag1 = playerCap.getParent() == Deities.HADES && playerCap.getMana() == playerCap.getMaxMana();
            boolean flag2 = playerCap.getParent() != Deities.HADES && player.hasEffect(Blessings.HADES);

            if (playerCap.getHadesCooldown() == 0 && (flag1 || flag2)) {
                event.setCanceled(true);
                player.setHealth(player.getMaxHealth() / 2);
                player.removeAllEffects();
                if (flag1) playerCap.setMana(0);
                playerCap.resetHadesCooldown();
            }
        }
    }

    @SubscribeEvent
    public void onPlayerWake(final PlayerWakeUpEvent event) {
        PlayerEntity player = event.getPlayer();
        IPlayerCap playerCap = player.getCapability(CapabilityRegistry.PLAYERCAP, null).orElse(null);
        playerCap.setMana(playerCap.getMaxMana() / 2);
    }

    @SubscribeEvent
    public void onSetAttackTarget(final LivingSetAttackTargetEvent event) {
        if(event.getEntityLiving().hasEffect(LHEffects.APATHY)){
            if(event.getEntityLiving() instanceof MonsterEntity && event.getTarget()!=null) ((MonsterEntity)event.getEntityLiving()).setTarget((LivingEntity) null);
            if(event.getEntityLiving() instanceof AbstractRaiderEntity && event.getTarget()!=null) ((AbstractRaiderEntity)event.getEntityLiving()).setTarget((LivingEntity) null);
        }
    }
}

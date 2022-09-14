package com.greenone.lostheroes.common;

import com.greenone.lostheroes.LostHeroes;
import com.greenone.lostheroes.common.command.LHCommands;
import com.greenone.lostheroes.common.deity.Blessings;
import com.greenone.lostheroes.common.deity.Deities;
import com.greenone.lostheroes.common.deity.Deity;
import com.greenone.lostheroes.common.player.capability.*;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Random;

@Mod.EventBusSubscriber(modid = LostHeroes.MODID)
public class LHEventHandler {

    @SubscribeEvent
    public void onPlayerJoined(final PlayerEvent.PlayerLoggedInEvent event) {
        IParent parentCap = event.getPlayer().getCapability(PlayerCapabilities.PARENT_CAPABILITY).orElseThrow(() -> new IllegalArgumentException("No Capability at Login"));
        if (parentCap.getParent() == null) {
            Random rand = new Random();
            Object[] values = Deities.list.toArray();
            parentCap.setParent((Deity) values[rand.nextInt(values.length)]);
            event.getPlayer().sendMessage(new StringTextComponent("You have been claimed by " + parentCap.getParent().getFormattedName()), event.getPlayer().getUUID());
//            playerCap.sync(event.getPlayer());
        }
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event){
        PlayerEntity player = event.player;
        IMana manaCap = player.getCapability(PlayerCapabilities.MANA_CAPABILITY).orElse(null);
        IParent parentCap = player.getCapability(PlayerCapabilities.PARENT_CAPABILITY).orElse(null);

        abilityCheck(player, parentCap, manaCap);

        if (!player.hasEffect(Blessings.ZEUS) && !player.isCreative() && !player.isSpectator() && player.abilities.mayfly) {
            player.abilities.mayfly = false;
            player.abilities.flying = false;
            player.onUpdateAbilities();
        }
    }

    @SubscribeEvent
    public void onPlayerClone(final PlayerEvent.Clone event) {
        PlayerEntity originalPlayer = event.getOriginal();
        PlayerEntity newPlayer = event.getPlayer();

        IParent oldParent = originalPlayer.getCapability(PlayerCapabilities.PARENT_CAPABILITY).orElse(null);
        IParent newParent = newPlayer.getCapability(PlayerCapabilities.PARENT_CAPABILITY).orElse(null);
        IMana oldMana = originalPlayer.getCapability(PlayerCapabilities.MANA_CAPABILITY).orElse(null);
        IMana newMana = newPlayer.getCapability(PlayerCapabilities.MANA_CAPABILITY).orElse(null);

        newParent.copy(oldParent);
        newMana.copy(oldMana);
    }

    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent event) {
        LHCommands.register(event.getDispatcher());
    }

    @SubscribeEvent
    public static void onSetAttackTarget(LivingSetAttackTargetEvent event){
        if(event.getTarget() != null && event.getTarget().hasEffect(Blessings.HADES))
            if (event.getEntityLiving().getMobType() == CreatureAttribute.UNDEAD)
                ((MobEntity) event.getEntityLiving()).setTarget(null);
    }

    private void abilityCheck(PlayerEntity player, IParent parentCap, IMana manaCap){
        if(player != null && parentCap != null && manaCap != null && parentCap.getParent() != null){
            parentCap.getParent().applyAttributeModifiers(player, player.getAttributes(), 1);
            player.addEffect(new EffectInstance(parentCap.getParent().getBlessing(), 1));
            switch(parentCap.getParent().getName()){
                case "zeus":
                    break;
                case "poseidon":
                    break;
                case "hades":
                    break;
                default:
                    break;
            }
        }
    }
}

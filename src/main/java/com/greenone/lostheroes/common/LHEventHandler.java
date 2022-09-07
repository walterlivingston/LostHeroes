package com.greenone.lostheroes.common;

import com.greenone.lostheroes.LostHeroes;
import com.greenone.lostheroes.common.player.capability.Mana;
import com.greenone.lostheroes.common.player.capability.PlayerCapabilities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class LHEventHandler {
    @SubscribeEvent
    public void onCapabilitiesAttachEntity(AttachCapabilitiesEvent<Entity> event)
    {
        if(event.getObject() instanceof PlayerEntity)
            event.addCapability(new ResourceLocation("mana", LostHeroes.MODID),
                    new Mana(10.0f));
    }
}

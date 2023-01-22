package com.greenone.lostheroes.common;

import com.greenone.lostheroes.common.deity.Blessings;
import com.greenone.lostheroes.common.deity.Deities;
import com.greenone.lostheroes.common.entity.LHEntities;
import com.greenone.lostheroes.common.network.LHNetworkHandler;
import com.greenone.lostheroes.common.player.capability.PlayerCapabilities;
import com.greenone.lostheroes.common.potion.LHEffects;
import com.greenone.lostheroes.common.potion.LHPotions;
import com.greenone.lostheroes.common.world.LHDimensions;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.ParallelDispatchEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class LHContent {

    public static void modConstruction(){
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        LHNetworkHandler.registerMessages();

        LHEffects.register();
        Blessings.register();
        LHPotions.register(eventBus);

        LHEntities.register(eventBus);
    }

    public static void init(ParallelDispatchEvent event){
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        Deities.init();
        Blessings.init();
        LHDimensions.init();

        PlayerCapabilities.register();
    }
}

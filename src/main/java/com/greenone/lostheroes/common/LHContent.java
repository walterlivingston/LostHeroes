package com.greenone.lostheroes.common;

import com.greenone.lostheroes.common.deity.Blessings;
import com.greenone.lostheroes.common.deity.Deities;
import com.greenone.lostheroes.common.potion.LHPotions;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.ParallelDispatchEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class LHContent {

    public static void modConstruction(){
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        Blessings.register();
        Deities.register();
        LHPotions.register(eventBus);
    }

    public static void init(ParallelDispatchEvent event){

    }
}

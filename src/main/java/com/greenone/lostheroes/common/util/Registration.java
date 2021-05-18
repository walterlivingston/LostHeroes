package com.greenone.lostheroes.common.util;

import com.greenone.lostheroes.common.blocks.LHBlocks;
import com.greenone.lostheroes.common.entities.LHEntities;
import com.greenone.lostheroes.common.init.Deities;
import com.greenone.lostheroes.common.init.LHTags;
import com.greenone.lostheroes.common.inventory.container.LHContainers;
import com.greenone.lostheroes.common.items.LHItems;
import com.greenone.lostheroes.common.potions.LHPotions;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class Registration {

    public static void register(){
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        LHBlocks.register(eventBus);
        LHItems.register(eventBus);
        LHEntities.register(eventBus);
        LHContainers.register(eventBus);
        LHPotions.register(eventBus);
        Deities.init();
        LHTags.register();
    }
}

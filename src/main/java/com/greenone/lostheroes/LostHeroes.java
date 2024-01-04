package com.greenone.lostheroes;

import com.greenone.lostheroes.block.LHBlocks;
import com.greenone.lostheroes.datagen.DataGenerators;
import com.greenone.lostheroes.item.LHItems;
import com.greenone.lostheroes.item.LHTabs;
import com.greenone.lostheroes.material.LHMaterials;
import com.mojang.logging.LogUtils;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(LostHeroes.MOD_ID)
public class LostHeroes {

    public static final String MOD_ID = "lostheroes";
    private static final Logger LOGGER = LogUtils.getLogger();

    public LostHeroes() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::commonSetup);
        MinecraftForge.EVENT_BUS.register(this);

//        LHMaterials.init();
        LHItems.register(modEventBus);
        LHBlocks.register(modEventBus);

        modEventBus.addListener(this::addCreative);
    }

    private void addCreative(CreativeModeTabEvent.BuildContents event) {
        if(event.getTab() == CreativeModeTabs.INGREDIENTS){
//            event.accept(LHItems.BRONZE);
        }

        if(event.getTab() == LHTabs.ITEMS_TAB){
//            event.accept(LHItems.BRONZE);
        }
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();


    }

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {

        }
    }
}

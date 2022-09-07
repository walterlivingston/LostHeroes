package com.greenone.lostheroes;

import com.greenone.lostheroes.common.LHContent;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.EventBus;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.stream.Collectors;

/*
* A lot of this mod setup and organization is based on BluSunrize's Immersive Engineering
* Github: https://github.com/BluSunrize/ImmersiveEngineering/tree/1.16.5
* */

@Mod(LostHeroes.MODID)
public class LostHeroes
{
    public static final String MODID = "lostheroes";
    public static final Logger LOGGER = LogManager.getLogger();

    public LostHeroes() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);

        LHContent.modConstruction();

        MinecraftForge.EVENT_BUS.register(this);
    }

    public void setup(FMLCommonSetupEvent event){
        LHContent.init(event);
    }
}

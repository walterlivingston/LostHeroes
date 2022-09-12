package com.greenone.lostheroes;

import com.greenone.lostheroes.common.LHContent;
import com.greenone.lostheroes.common.LHEventHandler;
import com.greenone.lostheroes.common.config.Config;
import com.greenone.lostheroes.common.player.capability.PlayerCapabilities;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.EventBus;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
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

        // register configs
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Config.client_config);
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, Config.server_config);

        // load configs
        Config.loadConfig(Config.client_config, FMLPaths.CONFIGDIR.get().resolve(MODID + "-client.toml").toString());
        Config.loadConfig(Config.server_config, FMLPaths.CONFIGDIR.get().resolve(MODID + "-server.toml").toString());

        LHContent.modConstruction();

        MinecraftForge.EVENT_BUS.register(this);
    }

    public void setup(FMLCommonSetupEvent event){
        MinecraftForge.EVENT_BUS.register(new LHEventHandler());
        MinecraftForge.EVENT_BUS.register(new PlayerCapabilities());

        LHContent.init(event);
    }
}

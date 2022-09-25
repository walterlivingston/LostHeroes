package com.greenone.lostheroes;

import com.greenone.lostheroes.client.renderer.entity.LHSpriteRenderer;
import com.greenone.lostheroes.client.renderer.entity.LHWitherSkeletonRenderer;
import com.greenone.lostheroes.client.util.LHKeybindings;
import com.greenone.lostheroes.common.LHContent;
import com.greenone.lostheroes.common.LHEventHandler;
import com.greenone.lostheroes.common.config.Config;
import com.greenone.lostheroes.common.entity.LHEntities;
import com.greenone.lostheroes.common.entity.monster.WitherSkeletonWarrior;
import com.greenone.lostheroes.common.player.capability.PlayerCapabilities;
import net.minecraft.client.renderer.entity.WitherSkeletonRenderer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
        // register configs
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Config.client_config);
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, Config.server_config);

        // load configs
        Config.loadConfig(Config.client_config, FMLPaths.CONFIGDIR.get().resolve(MODID + "-client.toml").toString());
        Config.loadConfig(Config.server_config, FMLPaths.CONFIGDIR.get().resolve(MODID + "-server.toml").toString());

        MinecraftForge.EVENT_BUS.register(this);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(LHEntities::onEntityAttributeCreation);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);

        LHContent.modConstruction();
    }

    public void commonSetup(FMLCommonSetupEvent event){
        MinecraftForge.EVENT_BUS.register(new PlayerCapabilities());
        MinecraftForge.EVENT_BUS.register(new LHEventHandler());

        LHContent.init(event);
    }

    public void clientSetup(FMLClientSetupEvent event){
        RenderingRegistry.registerEntityRenderingHandler(LHEntities.WATER_BALL, new LHSpriteRenderer.RenderFactory<>());
        RenderingRegistry.registerEntityRenderingHandler(LHEntities.LIGHT_RAY, new LHSpriteRenderer.RenderFactory<>());
        RenderingRegistry.registerEntityRenderingHandler(LHEntities.WITHER_WARRIOR, LHWitherSkeletonRenderer::new);

        MinecraftForge.EVENT_BUS.register(new LHKeybindings());
        LHKeybindings.register();
    }

}

package com.greenone.lostheroes;

import com.greenone.lostheroes.client.render.LHEnchantTileEntityRenderer;
import com.greenone.lostheroes.client.render.SpearRenderer;
import com.greenone.lostheroes.client.screen.ForgeScreen;
import com.greenone.lostheroes.client.screen.LHEnchantScreen;
import com.greenone.lostheroes.client.utils.LHClientUtils;
import com.greenone.lostheroes.client.utils.LHKeybinds;
import com.greenone.lostheroes.common.IProxy;
import com.greenone.lostheroes.common.blocks.tiles.LHTileEntities;
import com.greenone.lostheroes.common.capabilities.CapabilityRegistry;
import com.greenone.lostheroes.common.config.Config;
import com.greenone.lostheroes.common.init.LHBlocks;
import com.greenone.lostheroes.common.init.LHEntities;
import com.greenone.lostheroes.common.inventory.container.LHContainers;
import com.greenone.lostheroes.common.items.LHItemModelProperties;
import com.greenone.lostheroes.common.network.LHNetworkHandler;
import com.greenone.lostheroes.common.util.EnchantmentHandler;
import com.greenone.lostheroes.common.util.LHEventHandler;
import com.greenone.lostheroes.common.init.Registration;
import com.greenone.lostheroes.common.world.LHOreGen;
import com.greenone.lostheroes.data.DataGenerators;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLDedicatedServerSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartedEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.event.server.FMLServerStoppingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;

import javax.annotation.Nullable;

import static com.greenone.lostheroes.LostHeroes.MOD_ID;

public class SideProxy implements IProxy {
    private static MinecraftServer server;

    SideProxy(){
        FMLJavaModLoadingContext.get().getModEventBus().addListener(DataGenerators::gatherData);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(SideProxy::commonSetup);
        MinecraftForge.EVENT_BUS.register(LHEventHandler.instance);
        MinecraftForge.EVENT_BUS.register(EnchantmentHandler.instance);

        // register configs
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Config.client_config);
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, Config.server_config);

        // load configs
        Config.loadConfig(Config.client_config, FMLPaths.CONFIGDIR.get().resolve(MOD_ID+"-client.toml").toString());
        Config.loadConfig(Config.server_config, FMLPaths.CONFIGDIR.get().resolve(MOD_ID+"-server.toml").toString());
        Registration.register();
    }

    public static void commonSetup(final FMLCommonSetupEvent event) {
        CapabilityRegistry.registerCapabilities();
        LHNetworkHandler.registerMessages();
        LHOreGen.initOres();
        LHOreGen.setupOres();
    }

    private static void serverStarted(FMLServerStartedEvent event) {
        LostHeroes.LOGGER.info("Server Started");
        server = event.getServer();
    }

    private static void serverStarting(FMLServerStartingEvent event) {
        MinecraftForge.EVENT_BUS.addListener(LHEventHandler::registerCommands);
        LHNetworkHandler.registerMessages();
    }

    private static void serverStopping(FMLServerStoppingEvent event) {
        LostHeroes.LOGGER.info("Server Stopped");
        server = null;
    }

    @Nullable
    @Override
    public PlayerEntity getClientPlayer() {
        return null;
    }

    @Nullable
    @Override
    public MinecraftServer getServer() {
        return server;
    }

    public static class Client extends SideProxy{
        public Client(){
            FMLJavaModLoadingContext.get().getModEventBus().addListener(Client::clientSetup);
        }

        private static void clientSetup(FMLClientSetupEvent event){
            //ClientRegistry.bindTileEntityRenderer(LHTileEntities.ALTAR.get(), AltarTileRenderer::new);
            ClientRegistry.bindTileEntityRenderer(LHTileEntities.ENCHANT, LHEnchantTileEntityRenderer::new);
            RenderTypeLookup.setRenderLayer(LHBlocks.greek_fire, RenderType.cutout());
            DeferredWorkQueue.runLater(LHItemModelProperties::registerProperties);
            ScreenManager.register(LHContainers.ENCHANTING, LHEnchantScreen::new);
            ScreenManager.register(LHContainers.FORGE, ForgeScreen::new);
            LHKeybinds.register();
            MinecraftForge.EVENT_BUS.register(LHClientUtils.instance);
            RenderingRegistry.registerEntityRenderingHandler(LHEntities.SPEAR, new SpearRenderer.RenderFactory());
        }

    }

    public static class Common extends SideProxy{
        public Common() {
            FMLJavaModLoadingContext.get().getModEventBus().addListener(this::serverSetup);
        }

        private void serverSetup(FMLDedicatedServerSetupEvent event) {}
    }
}

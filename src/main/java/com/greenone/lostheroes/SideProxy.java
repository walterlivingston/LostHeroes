package com.greenone.lostheroes;

import com.greenone.lostheroes.client.render.LHEnchantTileEntityRenderer;
import com.greenone.lostheroes.client.render.SpearRenderer;
import com.greenone.lostheroes.client.screen.ForgeScreen;
import com.greenone.lostheroes.client.screen.LHEnchantScreen;
import com.greenone.lostheroes.client.utils.LHClientUtils;
import com.greenone.lostheroes.client.utils.LHKeybinds;
import com.greenone.lostheroes.common.IProxy;
import com.greenone.lostheroes.common.blocks.entity.LHBlockEntities;
import com.greenone.lostheroes.common.capabilities.CapabilityRegistry;
import com.greenone.lostheroes.common.config.Config;
import com.greenone.lostheroes.common.entities.LHEntities;
import com.greenone.lostheroes.common.init.LHMenus;
import com.greenone.lostheroes.common.init.Registration;
import com.greenone.lostheroes.common.items.LHItemModelProperties;
import com.greenone.lostheroes.common.network.LHNetworkHandler;
import com.greenone.lostheroes.common.util.EnchantmentHandler;
import com.greenone.lostheroes.common.util.LHEventHandler;
import com.greenone.lostheroes.common.world.LHFeatures;
import com.greenone.lostheroes.data.DataGenerators;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLDedicatedServerSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.fmlserverevents.FMLServerStartedEvent;
import net.minecraftforge.fmlserverevents.FMLServerStartingEvent;
import net.minecraftforge.fmlserverevents.FMLServerStoppingEvent;

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
        LHFeatures.initFeatures();
        LHFeatures.setupFeatures();
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
    public Player getClientPlayer() {
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
            BlockEntityRenderers.register(LHBlockEntities.ENCHANT, LHEnchantTileEntityRenderer::new);
            //RenderTypeLookup.setRenderLayer(LHBlocks.greek_fire, RenderType.cutout());
            LHItemModelProperties.registerProperties();
            MenuScreens.register(LHMenus.ENCHANTING, LHEnchantScreen::new);
            MenuScreens.register(LHMenus.FORGE, ForgeScreen::new);
            LHKeybinds.register();
            MinecraftForge.EVENT_BUS.register(LHClientUtils.instance);
            EntityRenderers.register(LHEntities.SPEAR, SpearRenderer::new);
        }

        @Nullable
        @Override
        public Player getClientPlayer() {
            return Minecraft.getInstance().player;
        }
    }

    public static class Common extends SideProxy{
        public Common() {
            FMLJavaModLoadingContext.get().getModEventBus().addListener(this::serverSetup);
        }

        private void serverSetup(FMLDedicatedServerSetupEvent event) {}
    }
}

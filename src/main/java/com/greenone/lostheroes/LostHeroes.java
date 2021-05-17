package com.greenone.lostheroes;

import com.greenone.lostheroes.client.utils.LHClientUtils;
import com.greenone.lostheroes.client.utils.LHKeybinds;
import com.greenone.lostheroes.common.capabilities.CapabilityRegistry;
import com.greenone.lostheroes.common.enums.Metal;
import com.greenone.lostheroes.common.inventory.container.LHContainers;
import com.greenone.lostheroes.common.items.LHItems;
import com.greenone.lostheroes.common.network.LHNetworkHandler;
import com.greenone.lostheroes.common.util.LHEventHandler;
import com.greenone.lostheroes.common.util.RegistryHandler;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(LostHeroes.MOD_ID)
public class LostHeroes
{
    public static final String MOD_ID = "lostheroes";
    public static final ItemGroup lh_group = new ItemGroup(12, LostHeroes.MOD_ID) {
        @Override
        public ItemStack makeIcon() {
        return new ItemStack(LHItems.ingots.get(Metal.CELESTIAL_BRONZE));
        }
    };
    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();

    public LostHeroes() {
        RegistryHandler.register();
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // Register the doClientStuff method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(LHEventHandler.instance);
        //DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> MinecraftForge.EVENT_BUS.register(LHClientUtils.instance));
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        CapabilityRegistry.registerCapabilities();
        LHNetworkHandler.registerMessages();
        // some preinit code
        LOGGER.info("HELLO FROM PREINIT");
        LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        LHKeybinds.register();
        MinecraftForge.EVENT_BUS.register(LHClientUtils.instance);
        // do something that can only be done on the client
        LOGGER.info("Got game settings {}", event.getMinecraftSupplier().get().options);
    }
    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
        // do something when the server starts
        LOGGER.info("HELLO from server starting");
    }
}

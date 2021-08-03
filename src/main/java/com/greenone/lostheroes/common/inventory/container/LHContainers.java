package com.greenone.lostheroes.common.inventory.container;

import com.greenone.lostheroes.LostHeroes;
import net.minecraft.client.Minecraft;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class LHContainers {
    public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, LostHeroes.MOD_ID);

    public static final MenuType<PCContainer> PORTABLE_CRAFTER = new MenuType<>((syncId, playerInv) -> new PCContainer(syncId, playerInv, ContainerLevelAccess.create(playerInv.player.level, playerInv.player.blockPosition())));
    public static final MenuType<LHEnchantContainer> ENCHANTING = IForgeContainerType.create((id, inv, data) -> new LHEnchantContainer(id, Minecraft.getInstance().player.getInventory()));
    public static final MenuType<ForgeContainer> FORGE = IForgeContainerType.create(ForgeContainer::new);

    public static void register(IEventBus eventBus){
        CONTAINERS.register("portable_crafting", () -> PORTABLE_CRAFTER);
        CONTAINERS.register("enchanting", () -> ENCHANTING);
        CONTAINERS.register("forge", () -> FORGE);

        CONTAINERS.register(eventBus);
    }
}

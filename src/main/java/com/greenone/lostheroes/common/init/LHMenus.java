package com.greenone.lostheroes.common.init;

import com.greenone.lostheroes.LostHeroes;
import com.greenone.lostheroes.common.inventory.menu.ForgeMenu;
import com.greenone.lostheroes.common.inventory.menu.LHEnchantMenu;
import com.greenone.lostheroes.common.inventory.menu.PCMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class LHMenus {
    public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, LostHeroes.MOD_ID);

    public static final MenuType<PCMenu> PORTABLE_CRAFTER = new MenuType<>((syncId, playerInv) -> new PCMenu(syncId, playerInv, ContainerLevelAccess.create(playerInv.player.level, playerInv.player.blockPosition())));
    public static final MenuType<LHEnchantMenu> ENCHANTING = new MenuType<>((syncId, playerInv) -> new LHEnchantMenu(syncId, playerInv, ContainerLevelAccess.create(playerInv.player.level, playerInv.player.blockPosition())));
    public static final MenuType<ForgeMenu> FORGE = new MenuType<>(ForgeMenu::new);
    //public static final MenuType<LHEnchantMenu> ENCHANTING = IForgeContainerType.create((id, inv, data) -> new LHEnchantMenu(id, Minecraft.getInstance().player.getInventory()));
    //public static final MenuType<ForgeMenu> FORGE = IForgeContainerType.create(ForgeMenu::new);

    public static void register(IEventBus eventBus){
        CONTAINERS.register("portable_crafting", () -> PORTABLE_CRAFTER);
        CONTAINERS.register("enchanting", () -> ENCHANTING);
        CONTAINERS.register("forge", () -> FORGE);

        CONTAINERS.register(eventBus);
    }
}

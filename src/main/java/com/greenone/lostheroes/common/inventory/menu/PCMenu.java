package com.greenone.lostheroes.common.inventory.menu;

import com.greenone.lostheroes.common.init.LHMenus;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.CraftingMenu;

import javax.annotation.Nullable;

public class PCMenu extends CraftingMenu {
    public PCMenu(int id, Inventory playerInv){ super(id, playerInv); }

    public PCMenu(int id, Inventory playerInv, ContainerLevelAccess world) {
        super(id, playerInv, world);
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    public static class Provider implements MenuProvider {
        @Override
        public Component getDisplayName() {
            return new TextComponent("Athena's Crafting");
        }

        @Nullable
        @Override
        public AbstractContainerMenu createMenu(int id, Inventory inv, Player player) {
            return LHMenus.PORTABLE_CRAFTER.create(id, inv);
        }
    }
}

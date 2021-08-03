package com.greenone.lostheroes.common.inventory.container;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.CraftingMenu;

public class PCContainer extends CraftingMenu {
    public PCContainer(int id, Inventory playerInv){ super(id, playerInv); }

    public PCContainer(int id, Inventory playerInv, ContainerLevelAccess world) {
        super(id, playerInv, world);
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }
}

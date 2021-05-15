package com.greenone.lostheroes.common.inventory.container;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.WorkbenchContainer;
import net.minecraft.util.IWorldPosCallable;

public class PCContainer extends WorkbenchContainer {
    public PCContainer(int id, PlayerInventory playerInv){ super(id, playerInv); }

    public PCContainer(int id, PlayerInventory playerInv, IWorldPosCallable world) {
        super(id, playerInv, world);
    }

    @Override
    public boolean stillValid(PlayerEntity player) {
        return true;
    }
}

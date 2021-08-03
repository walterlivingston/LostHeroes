package com.greenone.lostheroes.common.inventory.container;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;

import javax.annotation.Nullable;

public class PCContainerProvider implements MenuProvider {
    @Override
    public Component getDisplayName() {
        return new TextComponent("Athena's Crafting");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inv, Player player) {
        return LHContainers.PORTABLE_CRAFTER.create(id, inv);
    }
}

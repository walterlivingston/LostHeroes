package com.greenone.lostheroes.common.inventory.container;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

import javax.annotation.Nullable;

public class PCContainerProvider implements INamedContainerProvider {
    @Override
    public ITextComponent getDisplayName() {
        return new StringTextComponent("Athena's Crafting");
    }

    @Nullable
    @Override
    public Container createMenu(int id, PlayerInventory inv, PlayerEntity player) {
        return LHContainers.PORTABLE_CRAFTER.create(id, inv);
    }
}

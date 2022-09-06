package com.greenone.lostheroes.common.inventory;

import com.greenone.lostheroes.common.inventory.container.ForgeContainer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class ForgeFuelSlot extends Slot {
    private final ForgeContainer container;
    public ForgeFuelSlot(ForgeContainer containerIn, IInventory forgeInv, int index, int x, int y) {
        super(forgeInv, index, x, y);
        this.container = containerIn;
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return this.container.isFuel(stack) || isBucket(stack);
    }

    @Override
    public int getMaxStackSize(ItemStack stack) {
        return isBucket(stack) ? 1 : super.getMaxStackSize(stack);
    }

    public static boolean isBucket(ItemStack stack) {
        return stack.getItem() == Items.BUCKET;
    }
}

package com.greenone.lostheroes.common.inventory;

import com.greenone.lostheroes.common.blocks.tiles.ForgeTile;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.AbstractFurnaceTileEntity;

public class ForgeResultSlot extends Slot {
    private final PlayerEntity player;
    private int removeCount;

    public ForgeResultSlot(PlayerEntity player, IInventory forgeInvIn, int index, int x, int y) {
        super(forgeInvIn, index, x, y);
        this.player = player;
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return false;
    }

    @Override
    public ItemStack remove(int p_75209_1_) {
        if (this.hasItem()) {
            this.removeCount += Math.min(p_75209_1_, this.getItem().getCount());
        }

        return super.remove(p_75209_1_);
    }

    @Override
    public ItemStack onTake(PlayerEntity player, ItemStack stack) {
        this.checkTakeAchievements(stack);
        super.onTake(player, stack);
        return stack;
    }

    @Override
    protected void onQuickCraft(ItemStack stack, int amount) {
        this.removeCount += amount;
        this.checkTakeAchievements(stack);
    }

    @Override
    protected void checkTakeAchievements(ItemStack stack) {
        stack.onCraftedBy(this.player.level, this.player, this.removeCount);
        if (!this.player.level.isClientSide && this.container instanceof ForgeTile) {
            ((ForgeTile)this.container).awardUsedRecipesAndPopExperience(this.player);
        }

        this.removeCount = 0;
        net.minecraftforge.fml.hooks.BasicEventHooks.firePlayerSmeltedEvent(this.player, stack);
    }
}

package com.greenone.lostheroes.common.inventory;

import com.greenone.lostheroes.common.blocks.entity.ForgeBlockEntity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class ForgeResultSlot extends Slot {
    private final Player player;
    private int removeCount;

    public ForgeResultSlot(Player player, Container forgeInvIn, int index, int x, int y) {
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
    public void onTake(Player player, ItemStack stack) {
        this.checkTakeAchievements(stack);
        super.onTake(player, stack);
    }

    @Override
    protected void onQuickCraft(ItemStack stack, int amount) {
        this.removeCount += amount;
        this.checkTakeAchievements(stack);
    }

    @Override
    protected void checkTakeAchievements(ItemStack stack) {
        stack.onCraftedBy(this.player.level, this.player, this.removeCount);
        if (!this.player.level.isClientSide && this.container instanceof ForgeBlockEntity) {
            ((ForgeBlockEntity)this.container).awardUsedRecipesAndPopExperience((ServerPlayer) this.player);
        }

        this.removeCount = 0;
        net.minecraftforge.fmllegacy.hooks.BasicEventHooks.firePlayerSmeltedEvent(this.player, stack);
    }
}

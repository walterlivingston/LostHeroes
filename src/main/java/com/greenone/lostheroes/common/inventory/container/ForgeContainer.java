package com.greenone.lostheroes.common.inventory.container;

import com.greenone.lostheroes.common.blocks.tiles.ForgeTile;
import com.greenone.lostheroes.common.init.LHRecipes;
import com.greenone.lostheroes.common.inventory.ForgeFuelSlot;
import com.greenone.lostheroes.common.inventory.ForgeResultSlot;
import com.greenone.lostheroes.common.items.crafting.ForgeRecipe;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.IRecipeHelperPopulator;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.*;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.AbstractFurnaceTileEntity;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ForgeContainer extends RecipeBookContainer<IInventory> {
    private final IInventory forgeInv;
    private final IIntArray forgeData;
    protected final World level;
    private final IRecipeType<? extends ForgeRecipe> recipeType;
    //private final RecipeBookCategory recipeBookType;

    public ForgeContainer(int id, PlayerInventory playerInv, PacketBuffer buffer) {
        this(id, playerInv, new Inventory(4), new IntArray(4));
    }

    public ForgeContainer(int idIn, PlayerInventory playerInvIn, IInventory forgeInvIn, IIntArray forgeDataIn) {
        super(LHContainers.FORGE, idIn);
        this.recipeType = LHRecipes.Types.ALLOYING;
        checkContainerSize(forgeInvIn, 4);
        checkContainerDataCount(forgeDataIn, 4);
        this.forgeInv = forgeInvIn;
        this.forgeData = forgeDataIn;
        this.level = playerInvIn.player.level;
        this.addSlot(new Slot(forgeInvIn, 0, 40, 16));
        this.addSlot(new Slot(forgeInvIn, 1, 71, 16));
        this.addSlot(new ForgeFuelSlot(this, forgeInvIn, 2, 56, 53));
        this.addSlot(new ForgeResultSlot(playerInvIn.player, forgeInvIn, 3, 116, 35));

        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInvIn, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for(int k = 0; k < 9; ++k) {
            this.addSlot(new Slot(playerInvIn, k, 8 + k * 18, 142));
        }

        this.addDataSlots(forgeDataIn);
    }

    @Override
    public void fillCraftSlotsStackedContents(RecipeItemHelper itemHelper) {
        if(this.forgeInv instanceof IRecipeHelperPopulator){
            ((IRecipeHelperPopulator)this.forgeInv).fillStackedContents(itemHelper);
        }
    }

    @Override
    public void clearCraftingContent() {
        this.forgeInv.clearContent();
    }

    @Override
    public boolean recipeMatches(IRecipe<? super IInventory> recipe) {
        return recipe.matches(this.forgeInv, this.level);
    }

    @Override
    public int getResultSlotIndex() {
        return 3;
    }

    @Override
    public int getGridWidth() {
        return 2;
    }

    @Override
    public int getGridHeight() {
        return 1;
    }

    @Override
    public int getSize() {
        return 4;
    }

    @Override
    public RecipeBookCategory getRecipeBookType() {
        return null;
    }

    @Override
    public boolean stillValid(PlayerEntity player) {
        return this.forgeInv.stillValid(player);
    }

    @Override
    public ItemStack quickMoveStack(PlayerEntity player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (index == 3) {
                if (!this.moveItemStackTo(itemstack1, 4, 40, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onQuickCraft(itemstack1, itemstack);
            } else if (index != 1 && index != 0 && index != 2) {
                if (this.canSmelt(itemstack1)) {
                    if (!this.moveItemStackTo(itemstack1, 0, 2, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (this.isFuel(itemstack1)) {
                    if (!this.moveItemStackTo(itemstack1, 2, 3, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index >= 4 && index < 31) {
                    if (!this.moveItemStackTo(itemstack1, 31, 40, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index >= 31 && index < 40 && !this.moveItemStackTo(itemstack1, 4, 31, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemstack1, 4, 40, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, itemstack1);
        }

        return itemstack;
    }

    protected boolean canSmelt(ItemStack stack) {
        return this.level.getRecipeManager().getRecipeFor((IRecipeType)this.recipeType, new Inventory(stack), this.level).isPresent();
    }

    public boolean isFuel(ItemStack stack) {
        return ForgeTile.isFuel(stack);
    }

    @OnlyIn(Dist.CLIENT)
    public int getBurnProgress() {
        int i = this.forgeData.get(2);
        int j = this.forgeData.get(3);
        return j != 0 && i != 0 ? i * 24 / j : 0;
    }

    @OnlyIn(Dist.CLIENT)
    public int getLitProgress() {
        int i = this.forgeData.get(1);
        if (i == 0) {
            i = 200;
        }

        return this.forgeData.get(0) * 13 / i;
    }

    @OnlyIn(Dist.CLIENT)
    public boolean isLit() {
        return this.forgeData.get(0) > 0;
    }
}

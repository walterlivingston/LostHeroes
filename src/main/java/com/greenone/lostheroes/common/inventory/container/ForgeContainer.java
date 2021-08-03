package com.greenone.lostheroes.common.inventory.container;

import com.greenone.lostheroes.common.init.LHRecipes;
import com.greenone.lostheroes.common.inventory.ForgeFuelSlot;
import com.greenone.lostheroes.common.inventory.ForgeResultSlot;
import com.greenone.lostheroes.common.items.crafting.ForgeRecipe;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public class ForgeContainer extends RecipeBookMenu<Container> {
    public static final int[] INGREDIENT_SLOT = new int[]{0,1};
    public static final int FUEL_SLOT = 2;
    public static final int RESULT_SLOT = 3;
    public static final int SLOT_COUNT = 4;
    public static final int DATA_COUNT = 4;
    private static final int INV_SLOT_START = 3;
    private static final int INV_SLOT_END = 30;
    private static final int USE_ROW_SLOT_START = 30;
    private static final int USE_ROW_SLOT_END = 39;
    private final Container container;
    private final ContainerData data;
    protected final Level level;
    private final RecipeType<? extends ForgeRecipe> recipeType;
    private final RecipeBookType recipeBookType;

    public ForgeContainer(int id, Inventory inv, FriendlyByteBuf buf) {
        this(LHContainers.FORGE, LHRecipes.Types.ALLOYING, null, id, inv);
    }

    public ForgeContainer(int id, Inventory inv, Container container, ContainerData containerData) {
        this(LHContainers.FORGE, LHRecipes.Types.ALLOYING, null, id, inv, container, containerData);
    }

    protected ForgeContainer(MenuType<?> menuType, RecipeType<? extends ForgeRecipe> recipeType, RecipeBookType recipeBookType, int id, Inventory inv) {
        this(menuType, recipeType, recipeBookType, id, inv, new SimpleContainer(4), new SimpleContainerData(4));
    }

    protected ForgeContainer(MenuType<?> menuType, RecipeType<? extends ForgeRecipe> recipeType, RecipeBookType recipeBookType, int id, Inventory inv, Container containerIn, ContainerData containerDataIn) {
        super(menuType, id);
        this.recipeType = recipeType;
        this.recipeBookType = recipeBookType;
        checkContainerSize(containerIn, 4);
        checkContainerDataCount(containerDataIn, 4);
        this.container = containerIn;
        this.data = containerDataIn;
        this.level = inv.player.level;
        this.addSlot(new Slot(containerIn, 0, 40, 16));
        this.addSlot(new Slot(containerIn, 1, 71, 16));
        this.addSlot(new ForgeFuelSlot(this, containerIn, 2, 56, 53));
        this.addSlot(new ForgeResultSlot(inv.player, containerIn, 3, 116, 35));

        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(inv, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for(int k = 0; k < 9; ++k) {
            this.addSlot(new Slot(inv, k, 8 + k * 18, 142));
        }

        this.addDataSlots(containerDataIn);
    }

    @Override
    public void fillCraftSlotsStackedContents(StackedContents stackedContents) {
        if (this.container instanceof StackedContentsCompatible) {
            ((StackedContentsCompatible)this.container).fillStackedContents(stackedContents);
        }

    }

    @Override
    public void clearCraftingContent() {
        this.getSlot(0).set(ItemStack.EMPTY);
        this.getSlot(1).set(ItemStack.EMPTY);
        this.getSlot(3).set(ItemStack.EMPTY);
    }

    @Override
    public boolean recipeMatches(Recipe<? super Container> recipe) {
        return recipe.matches(this.container, this.level);
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
    public boolean stillValid(Player player) {
        return this.container.stillValid(player);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (index == 3) {
                if (!this.moveItemStackTo(itemstack1, 4, 39, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onQuickCraft(itemstack1, itemstack);
            } else if (index != 2 && index != 1 && index != 0) {
                if (this.canSmelt(itemstack1)) {
                    if (!this.moveItemStackTo(itemstack1, 0, 2, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (this.isFuel(itemstack1)) {
                    if (!this.moveItemStackTo(itemstack1, 2, 3, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index >= 4 && index < 30) {
                    if (!this.moveItemStackTo(itemstack1, 31, 39, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index >= 31 && index < 39 && !this.moveItemStackTo(itemstack1, 4, 31, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemstack1, 4, 39, false)) {
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
        return this.level.getRecipeManager().getRecipeFor((RecipeType<ForgeRecipe>)this.recipeType, new SimpleContainer(stack), this.level).isPresent();
    }

    public boolean isFuel(ItemStack stack) {
        return net.minecraftforge.common.ForgeHooks.getBurnTime(stack, this.recipeType) > 0;
    }

    public int getBurnProgress() {
        int i = this.data.get(2);
        int j = this.data.get(3);
        return j != 0 && i != 0 ? i * 24 / j : 0;
    }

    public int getLitProgress() {
        int i = this.data.get(1);
        if (i == 0) {
            i = 200;
        }

        return this.data.get(0) * 13 / i;
    }

    public boolean isLit() {
        return this.data.get(0) > 0;
    }

    @Override
    public RecipeBookType getRecipeBookType() {
        return null;
    }

    @Override
    public boolean shouldMoveToInventory(int index) {
        return index != 2;
    }
}

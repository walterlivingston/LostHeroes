package com.greenone.lostheroes.common.items.crafting;

import com.greenone.lostheroes.common.blocks.LHBlocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ForgeRecipe implements IRecipe<IInventory> {
    protected final IRecipeType<?> type;
    protected final ResourceLocation id;
    protected final String group;
    protected final Ingredient ing1;
    protected final Ingredient ing2;
    protected final ItemStack result;
    protected final float experience;
    protected final int cookTime;

    public static final IRecipeType<ForgeRecipe> FORGE = new IRecipeType<ForgeRecipe>() {
        @Override
        public String toString() {
            return "lostheroes:alloying";
        }
    };

    public ForgeRecipe(ResourceLocation idIn, String groupIn, Ingredient ing1In, Ingredient ing2In, ItemStack resultIn, float experienceIn, int cookTimeIn) {
        this.type = FORGE;
        this.id = idIn;
        this.group = groupIn;
        this.ing1 = ing1In;
        this.ing2 = ing2In;
        this.result = resultIn;
        this.experience = experienceIn;
        this.cookTime = cookTimeIn;
    }

    @Override
    public ItemStack getToastSymbol() {
        return new ItemStack(LHBlocks.forge);
    }

    @Override
    public boolean matches(IInventory inv, World world) {
        return this.ing1.test(inv.getItem(0)) && this.ing2.test(inv.getItem(3));
    }

    @Override
    public ItemStack assemble(IInventory p_77572_1_) {
        return this.result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int p_194133_1_, int p_194133_2_) {
        return true;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> nonNullList = NonNullList.create();
        nonNullList.add(this.ing1);
        nonNullList.add(this.ing2);
        return nonNullList;
    }
    public float getExperience(){
        return this.experience;
    }

    @Override
    public ItemStack getResultItem() {
        return this.result;
    }

    @Override
    public String getGroup() {
        return group;
    }

    public int getCookTime() {
        return cookTime;
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return LHRecipeSerializers.FORGE;
    }

    @Override
    public IRecipeType<?> getType() {
        return this.type;
    }
}

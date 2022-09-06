package com.greenone.lostheroes.common.items.crafting;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.greenone.lostheroes.common.init.LHRecipes;
import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class ForgeRecipe implements IRecipe<IInventory> {
    private final ResourceLocation id;
    private final String group;
    private final ItemStack result;
    private final NonNullList<Ingredient> ingredients;
    //private final boolean isSimple;
    protected final float experience;
    protected final int cookingTime;

    public ForgeRecipe(ResourceLocation idIn, String groupIn, ItemStack resultIn, NonNullList<Ingredient> ingredientsIn, float experienceIn, int cookingTimeIn) {
        this.id = idIn;
        this.group = groupIn;
        this.result = resultIn;
        this.ingredients = ingredientsIn;
        //this.isSimple = ingredientsIn.stream().allMatch(Ingredient::isSimple);
        this.experience = experienceIn;
        this.cookingTime = cookingTimeIn;
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return LHRecipes.Serializers.ALLOYING;
    }

    @Override
    public IRecipeType<?> getType() {
        return LHRecipes.Types.ALLOYING;
    }

    @Override
    public String getGroup() {
        return this.group;
    }

    @Override
    public ItemStack getResultItem() {
        return this.result;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return this.ingredients;
    }

    public float getExperience() {
        return this.experience;
    }

    public int getCookingTime() {
        return this.cookingTime;
    }

    @Override
    public boolean matches(IInventory p_77569_1_, World p_77569_2_) {
        RecipeItemHelper recipeitemhelper = new RecipeItemHelper();
        java.util.List<ItemStack> inputs = new java.util.ArrayList<>();
        int i = 0;

        for(int j = 0; j < 2; ++j) {
            ItemStack itemstack = p_77569_1_.getItem(j);
            if (!itemstack.isEmpty()) {
                ++i;
                inputs.add(itemstack);
            }
        }

        return i == this.ingredients.size() && (net.minecraftforge.common.util.RecipeMatcher.findMatches(inputs,  this.ingredients) != null);
    }

    @Override
    public ItemStack assemble(IInventory p_77572_1_) {
        return this.result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int p_194133_1_, int p_194133_2_) {
        return p_194133_1_ * p_194133_2_ >= this.ingredients.size();
    }

    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<ForgeRecipe> {
        @Override
        public ForgeRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            String s = JSONUtils.getAsString(json, "group", "");
            NonNullList<Ingredient> nonnulllist = itemsFromJson(JSONUtils.getAsJsonArray(json, "ingredients"));
            if (nonnulllist.isEmpty()) {
                throw new JsonParseException("No ingredients for forge recipe");
            } else if (nonnulllist.size() > 2 * 1) {
                throw new JsonParseException("Too many ingredients for forge recipe the max is " + (2 * 1));
            }
            if (!json.has("result")) throw new com.google.gson.JsonSyntaxException("Missing result, expected to find a string or object");
            ItemStack itemstack;
            if (json.get("result").isJsonObject()) itemstack = ShapedRecipe.itemFromJson(JSONUtils.getAsJsonObject(json, "result"));
            else {
                String s1 = JSONUtils.getAsString(json, "result");
                ResourceLocation resourcelocation = new ResourceLocation(s1);
                itemstack = new ItemStack(Registry.ITEM.getOptional(resourcelocation).orElseThrow(() -> {
                    return new IllegalStateException("Item: " + s1 + " does not exist");
                }));
            }
            float f = JSONUtils.getAsFloat(json, "experience", 0.0F);
            int i = JSONUtils.getAsInt(json, "cookingtime", 200);
            return new ForgeRecipe(recipeId, s, itemstack, nonnulllist, f, i);
        }

        private static NonNullList<Ingredient> itemsFromJson(JsonArray p_199568_0_) {
            NonNullList<Ingredient> nonnulllist = NonNullList.create();

            for (int i = 0; i < p_199568_0_.size(); ++i) {
                Ingredient ingredient = Ingredient.fromJson(p_199568_0_.get(i));
                if (!ingredient.isEmpty()) {
                    nonnulllist.add(ingredient);
                }
            }

            return nonnulllist;
        }

        @Override
        public ForgeRecipe fromNetwork(ResourceLocation recipeId, PacketBuffer p_199426_2_) {
            String s = p_199426_2_.readUtf(32767);
            NonNullList<Ingredient> nonnulllist = NonNullList.withSize(2, Ingredient.EMPTY);

            for (int j = 0; j < nonnulllist.size(); ++j) {
                nonnulllist.set(j, Ingredient.fromNetwork(p_199426_2_));
            }
            ItemStack itemstack = p_199426_2_.readItem();
            float f = p_199426_2_.readFloat();
            int i = p_199426_2_.readVarInt();
            return new ForgeRecipe(recipeId, s, itemstack, nonnulllist, f, i);
        }

        @Override
        public void toNetwork(PacketBuffer p_199427_1_, ForgeRecipe p_199427_2_) {
            p_199427_1_.writeUtf(p_199427_2_.group);

            for (Ingredient ingredient : p_199427_2_.ingredients) {
                ingredient.toNetwork(p_199427_1_);
            }

            p_199427_1_.writeItem(p_199427_2_.result);
            p_199427_1_.writeFloat(p_199427_2_.experience);
            p_199427_1_.writeVarInt(p_199427_2_.cookingTime);
        }
    }
}

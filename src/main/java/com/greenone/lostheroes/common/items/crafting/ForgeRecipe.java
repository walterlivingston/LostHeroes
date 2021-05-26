package com.greenone.lostheroes.common.items.crafting;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.greenone.lostheroes.LostHeroes;
import com.greenone.lostheroes.common.blocks.LHBlocks;
import com.greenone.lostheroes.common.init.LHRecipes;
import it.unimi.dsi.fastutil.ints.IntList;
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

import javax.annotation.Nullable;

public class ForgeRecipe implements IRecipe<IInventory> {
    protected final IRecipeType<?> type;
    protected final ResourceLocation id;
    protected final String group;
    private final NonNullList<Ingredient> ingredients;
    protected final ItemStack result;
    protected final float experience;
    protected final int cookTime;

    public ForgeRecipe(ResourceLocation idIn, NonNullList<Ingredient> ingredientsIn, ItemStack resultIn, float experienceIn, int cookTimeIn) {
        this.type = LHRecipes.Types.ALLOYING;
        this.id = idIn;
        this.group = "";
        this.ingredients = ingredientsIn;
        this.result = resultIn;
        this.experience = experienceIn;
        this.cookTime = cookTimeIn;
    }

    @Override
    public boolean matches(IInventory inv, World world) {
        RecipeItemHelper recipeitemhelper = new RecipeItemHelper();
        java.util.List<ItemStack> inputs = new java.util.ArrayList<>();
        int i = 0;

        for(int j = 0; j < inv.getContainerSize(); ++j) {
            ItemStack itemstack = inv.getItem(j);
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
        return true;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return this.ingredients;
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
        return LHRecipes.Serializers.ALLOYING;
    }

    @Override
    public IRecipeType<?> getType() {
        return this.type;
    }

    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<ForgeRecipe> {
        private final Serializer.IFactory<ForgeRecipe> factory;

        public Serializer(Serializer.IFactory<ForgeRecipe> factoryIn) {
            this.factory = factoryIn;
        }

        @Override
        public ForgeRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            NonNullList<Ingredient> nonNullList = itemsFromJson(JSONUtils.getAsJsonArray(json,"ingredients"));
            if (nonNullList.isEmpty()) {
                throw new JsonParseException("No ingredients for shapeless recipe");
            } else if (nonNullList.size() > 2 * 1) {
                throw new JsonParseException("Too many ingredients for shapeless recipe the max is " + (2 * 1));
            }

            ItemStack itemStack;
            if (!json.has("result")) {
                throw new JsonSyntaxException("Missing result, expected to find a string or object");
            }
            if (json.get("result").isJsonObject()) {
                itemStack = ShapedRecipe.itemFromJson(JSONUtils.getAsJsonObject(json, "result"));
            } else {
                String s1 = JSONUtils.getAsString(json, "result");
                ResourceLocation resourceLocation = new ResourceLocation(s1);
                itemStack = new ItemStack(Registry.ITEM.getOptional(resourceLocation).orElseThrow(() -> new IllegalStateException("Item: " + s1 + " does not exist")));
            }
            float f = JSONUtils.getAsFloat(json, "experience", 0.0F);
            int i = JSONUtils.getAsInt(json, "cookingtime", 200);

            return this.factory.create(recipeId, nonNullList, itemStack, f, i);
        }

        @Nullable
        @Override
        public ForgeRecipe fromNetwork(ResourceLocation recipeId, PacketBuffer buffer) {
            NonNullList<Ingredient> nonnulllist = NonNullList.withSize(2, Ingredient.EMPTY);

            for(int j = 0; j < nonnulllist.size(); ++j) {
                nonnulllist.set(j, Ingredient.fromNetwork(buffer));
            }
            ItemStack itemStack = buffer.readItem();
            float f = buffer.readFloat();
            int i = buffer.readInt();

            return this.factory.create(recipeId, nonnulllist, itemStack, f, i);
        }

        @Override
        public void toNetwork(PacketBuffer buffer, ForgeRecipe recipe) {
            buffer.writeUtf(recipe.group);
            for(Ingredient ingredient : recipe.ingredients) {
                ingredient.toNetwork(buffer);
            }
            buffer.writeItem(recipe.result);
            buffer.writeFloat(recipe.experience);
            buffer.writeVarInt(recipe.cookTime);
        }

        private static NonNullList<Ingredient> itemsFromJson(JsonArray p_199568_0_) {
            NonNullList<Ingredient> nonnulllist = NonNullList.create();

            for(int i = 0; i < p_199568_0_.size(); ++i) {
                Ingredient ingredient = Ingredient.fromJson(p_199568_0_.get(i));
                if (!ingredient.isEmpty()) {
                    nonnulllist.add(ingredient);
                }
            }

            return nonnulllist;
        }

        public interface IFactory<ForgeRecipe> {
            ForgeRecipe create(ResourceLocation resourceLocation, NonNullList<Ingredient> ingredients, ItemStack result, float experience, int cookTime);
        }
    }
}

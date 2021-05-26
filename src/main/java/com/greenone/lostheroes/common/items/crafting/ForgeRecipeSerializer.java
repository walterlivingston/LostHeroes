package com.greenone.lostheroes.common.items.crafting;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;

public class ForgeRecipeSerializer<T extends ForgeRecipe> extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<T> {
    private final ForgeRecipeSerializer.IFactory<T> factory;
    public ForgeRecipeSerializer(ForgeRecipeSerializer.IFactory<T> factoryIn) {
        this.factory = factoryIn;
    }

    @Override
    public T fromJson(ResourceLocation recipeId, JsonObject json) {
        String s = JSONUtils.getAsString(json, "group", "");
        Ingredient ing1;
        Ingredient ing2;
        if(json.get("ing_one").isJsonObject() && json.get("ing_two").isJsonObject()){
            ing1 = Ingredient.fromJson(JSONUtils.getAsJsonObject(json, "ing_one"));
            ing2 = Ingredient.fromJson(JSONUtils.getAsJsonObject(json, "ing_two"));
        }else{
            String i1 = JSONUtils.getAsString(json, "ing_one");
            String i2 = JSONUtils.getAsString(json, "ing_two");
            ResourceLocation resourceLocation1 = new ResourceLocation(i1);
            ResourceLocation resourceLocation2 = new ResourceLocation(i2);
            ing1 = Ingredient.of(Registry.ITEM.getOptional(resourceLocation1).orElseThrow(() -> new IllegalStateException("Item: " + i1 + " does not exist")));
            ing2 = Ingredient.of(Registry.ITEM.getOptional(resourceLocation2).orElseThrow(() -> new IllegalStateException("Item: " + i2 + " does not exist")));
        }
        ItemStack itemStack;
        if(!json.has("result")){
            throw new JsonSyntaxException("Missing result, expected to find a string or object");
        }
        if(json.get("result").isJsonObject()){
            itemStack = ShapedRecipe.itemFromJson(JSONUtils.getAsJsonObject(json, "result"));
        }else{
            String s1 = JSONUtils.getAsString(json, "result");
            ResourceLocation resourceLocation = new ResourceLocation(s1);
            itemStack = new ItemStack(Registry.ITEM.getOptional(resourceLocation).orElseThrow(() -> new IllegalStateException("Item: " + s1 + " does not exist")));
        }
        float f = JSONUtils.getAsFloat(json, "experience", 0.0F);
        int i = JSONUtils.getAsInt(json, "cookingtime", 200);

        return this.factory.create(recipeId, s, ing1, ing2, itemStack, f, i);
    }

    @Nullable
    @Override
    public T fromNetwork(ResourceLocation recipeId, PacketBuffer buffer) {
        String s = buffer.readUtf(32767);
        Ingredient ing1 = Ingredient.fromNetwork(buffer);
        Ingredient ing2 = Ingredient.fromNetwork(buffer);
        ItemStack itemStack = buffer.readItem();
        float f = buffer.readFloat();
        int i = buffer.readInt();

        return this.factory.create(recipeId, s, ing1, ing2, itemStack, f, i);
    }

    @Override
    public void toNetwork(PacketBuffer buffer, T recipe) {
        buffer.writeUtf(recipe.group);
        recipe.ing1.toNetwork(buffer);
        recipe.ing2.toNetwork(buffer);
        buffer.writeItem(recipe.result);
        buffer.writeFloat(recipe.experience);
        buffer.writeVarInt(recipe.cookTime);
    }

    public interface IFactory<T extends ForgeRecipe>{
        T create(ResourceLocation resourceLocation, String group, Ingredient ing1, Ingredient ing2, ItemStack result, float experience, int cookTime);
    }
}

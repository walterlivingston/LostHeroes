package com.greenone.lostheroes.data;

import com.google.gson.JsonObject;
import com.greenone.lostheroes.common.items.crafting.ForgeRecipe;
import com.greenone.lostheroes.common.items.crafting.ForgeRecipeSerializer;
import com.greenone.lostheroes.common.items.crafting.LHRecipeSerializers;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.ICriterionInstance;
import net.minecraft.advancements.IRequirementsStrategy;
import net.minecraft.advancements.criterion.RecipeUnlockedTrigger;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class ForgeRecipeBuilder {
    private final Item result;
    private final Ingredient ing1;
    private final Ingredient ing2;
    private final float experience;
    private final int cookingTime;
    private final Advancement.Builder advancement = Advancement.Builder.advancement();
    private String group;
    private final ForgeRecipeSerializer<?> serializer;

    private ForgeRecipeBuilder(IItemProvider p_i50788_1_, Ingredient ing1In, Ingredient ing2In, float p_i50788_3_, int p_i50788_4_, ForgeRecipeSerializer<?> p_i50788_5_) {
        this.result = p_i50788_1_.asItem();
        this.ing1 = ing1In;
        this.ing2 = ing2In;
        this.experience = p_i50788_3_;
        this.cookingTime = p_i50788_4_;
        this.serializer = p_i50788_5_;
    }

    public static ForgeRecipeBuilder forge(Item ing1, Item ing2, IItemProvider result, float experience, int cookingTimeIn) {
        Ingredient i1 = Ingredient.of(new ItemStack(ing1));
        Ingredient i2 = Ingredient.of(new ItemStack(ing2));
        return new ForgeRecipeBuilder(result, i1, i2, experience, cookingTimeIn, LHRecipeSerializers.FORGE);
    }

    public ForgeRecipeBuilder unlockedBy(String p_218628_1_, ICriterionInstance p_218628_2_) {
        this.advancement.addCriterion(p_218628_1_, p_218628_2_);
        return this;
    }

    public void save(Consumer<IFinishedRecipe> p_218630_1_) {
        this.save(p_218630_1_, Registry.ITEM.getKey(this.result));
    }

    public void save(Consumer<IFinishedRecipe> p_218632_1_, String p_218632_2_) {
        ResourceLocation resourcelocation = Registry.ITEM.getKey(this.result);
        ResourceLocation resourcelocation1 = new ResourceLocation(p_218632_2_);
        if (resourcelocation1.equals(resourcelocation)) {
            throw new IllegalStateException("Recipe " + resourcelocation1 + " should remove its 'save' argument");
        } else {
            this.save(p_218632_1_, resourcelocation1);
        }
    }

    public void save(Consumer<IFinishedRecipe> consumerIn, ResourceLocation id) {
        this.ensureValid(id);
        this.advancement.parent(new ResourceLocation("recipes/root")).addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id)).rewards(AdvancementRewards.Builder.recipe(id)).requirements(IRequirementsStrategy.OR);
        consumerIn.accept(new ForgeRecipeBuilder.Result(id, this.group == null ? "" : this.group, this.ing1, this.ing2, this.result, this.experience, this.cookingTime, this.advancement, new ResourceLocation(id.getNamespace(), "recipes/" + this.result.getItemCategory().getRecipeFolderName() + "/" + id.getPath()), this.serializer));
    }

    private void ensureValid(ResourceLocation p_218634_1_) {
        if (this.advancement.getCriteria().isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + p_218634_1_);
        }
    }

    public static class Result implements IFinishedRecipe {
        private final ResourceLocation id;
        private final String group;
        private final Ingredient ing1;
        private final Ingredient ing2;
        private final Item result;
        private final float experience;
        private final int cookingTime;
        private final Advancement.Builder advancement;
        private final ResourceLocation advancementId;
        private final IRecipeSerializer<? extends ForgeRecipe> serializer;

        public Result(ResourceLocation p_i50605_1_, String p_i50605_2_, Ingredient ing1In, Ingredient ing2In, Item p_i50605_4_, float p_i50605_5_, int p_i50605_6_, Advancement.Builder p_i50605_7_, ResourceLocation p_i50605_8_, IRecipeSerializer<? extends ForgeRecipe> p_i50605_9_) {
            this.id = p_i50605_1_;
            this.group = p_i50605_2_;
            this.ing1 = ing1In;
            this.ing2 = ing2In;
            this.result = p_i50605_4_;
            this.experience = p_i50605_5_;
            this.cookingTime = p_i50605_6_;
            this.advancement = p_i50605_7_;
            this.advancementId = p_i50605_8_;
            this.serializer = p_i50605_9_;
        }

        public void serializeRecipeData(JsonObject json) {
            if (!this.group.isEmpty()) {
                json.addProperty("group", this.group);
            }

            json.add("ing1", this.ing1.toJson());
            json.add("ing2", this.ing2.toJson());
            json.addProperty("result", Registry.ITEM.getKey(this.result).toString());
            json.addProperty("experience", this.experience);
            json.addProperty("cookingtime", this.cookingTime);
        }

        public IRecipeSerializer<?> getType() {
            return this.serializer;
        }

        public ResourceLocation getId() {
            return this.id;
        }

        @Nullable
        public JsonObject serializeAdvancement() {
            return this.advancement.serializeToJson();
        }

        @Nullable
        public ResourceLocation getAdvancementId() {
            return this.advancementId;
        }
    }
}
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
    private final Item ing1;
    private final Item ing2;
    private final float experience;
    private final int cookingTime;
    private final Advancement.Builder advancement = Advancement.Builder.advancement();
    private String group;
    private final ForgeRecipeSerializer<?> serializer;

    private ForgeRecipeBuilder(IItemProvider resultIn, Item ing1In, Item ing2In, float experienceIn, int cookingTimeIn, ForgeRecipeSerializer<?> serializerIn) {
        this.result = resultIn.asItem();
        this.ing1 = ing1In;
        this.ing2 = ing2In;
        this.experience = experienceIn;
        this.cookingTime = cookingTimeIn;
        this.serializer = serializerIn;
    }

    public static ForgeRecipeBuilder forge(Item ing1, Item ing2, IItemProvider result, float experience, int cookingTime) {
        return new ForgeRecipeBuilder(result, ing1, ing2, experience, cookingTime, LHRecipeSerializers.FORGE);
    }

    public ForgeRecipeBuilder unlockedBy(String p_218628_1_, ICriterionInstance p_218628_2_) {
        this.advancement.addCriterion(p_218628_1_, p_218628_2_);
        return this;
    }

    public void save(Consumer<IFinishedRecipe> consumer) {
        this.save(consumer, Registry.ITEM.getKey(this.result));
    }

    public void save(Consumer<IFinishedRecipe> consumer, ResourceLocation resultResLoc) {
        this.ensureValid(resultResLoc);
        this.advancement.parent(new ResourceLocation("recipes/root")).addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(resultResLoc)).rewards(AdvancementRewards.Builder.recipe(resultResLoc)).requirements(IRequirementsStrategy.OR);
        consumer.accept(new ForgeRecipeBuilder.Result(resultResLoc, this.group == null ? "" : this.group, Ingredient.of(new ItemStack(this.ing1)), Ingredient.of(new ItemStack(this.ing2)), this.result, this.experience, this.cookingTime, this.advancement, new ResourceLocation(resultResLoc.getNamespace(), "recipes/" + this.result.getItemCategory().getRecipeFolderName() + "/" + resultResLoc.getPath()), this.serializer));
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

        public Result(ResourceLocation idIn, String groupIn, Ingredient ing1In, Ingredient ing2In, Item resultIn, float experienceIn, int cookingTimeIn, Advancement.Builder advancementIn, ResourceLocation advancementIdIn, IRecipeSerializer<? extends ForgeRecipe> recipeSerializerIn) {
            this.id = idIn;
            this.group = groupIn;
            this.ing1 = ing1In;
            this.ing2 = ing2In;
            this.result = resultIn;
            this.experience = experienceIn;
            this.cookingTime = cookingTimeIn;
            this.advancement = advancementIn;
            this.advancementId = advancementIdIn;
            this.serializer = recipeSerializerIn;
        }

        public void serializeRecipeData(JsonObject p_218610_1_) {
            if (!this.group.isEmpty()) {
                p_218610_1_.addProperty("group", this.group);
            }

            p_218610_1_.add("ing_one", this.ing1.toJson());
            p_218610_1_.add("ing_two", this.ing2.toJson());
            p_218610_1_.addProperty("result", Registry.ITEM.getKey(this.result).toString());
            p_218610_1_.addProperty("experience", this.experience);
            p_218610_1_.addProperty("cookingtime", this.cookingTime);
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
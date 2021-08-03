package com.greenone.lostheroes.data;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.greenone.lostheroes.common.init.LHRecipes;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.Registry;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Consumer;

public class ForgeRecipeBuilder {
    private static final Logger LOGGER = LogManager.getLogger();
    private final Item result;
    private final int count;
    private final float experience;
    private final int cookingTime;
    private final List<Ingredient> ingredients = Lists.newArrayList();
    private final Advancement.Builder advancement = Advancement.Builder.advancement();
    private String group;

    public ForgeRecipeBuilder(ItemLike resultIn, int countIn, float experienceIn, int cookingTimeIn) {
        this.result = resultIn.asItem();
        this.count = countIn;
        this.experience = experienceIn;
        this.cookingTime = cookingTimeIn;
    }

    public static ForgeRecipeBuilder forge(ItemLike result, float experience, int cookingTime) {
        return new ForgeRecipeBuilder(result, 1, experience, cookingTime);
    }

    public ForgeRecipeBuilder requires(Tag<Item> p_203221_1_) {
        return this.requires(Ingredient.of(p_203221_1_));
    }

    public ForgeRecipeBuilder requires(ItemLike p_200487_1_) {
        return this.requires(p_200487_1_, 1);
    }

    public ForgeRecipeBuilder requires(ItemLike p_200491_1_, int p_200491_2_) {
        for(int i = 0; i < p_200491_2_; ++i) {
            this.requires(Ingredient.of(p_200491_1_));
        }

        return this;
    }

    public ForgeRecipeBuilder requires(Ingredient p_200489_1_) {
        return this.requires(p_200489_1_, 1);
    }

    public ForgeRecipeBuilder requires(Ingredient p_200492_1_, int p_200492_2_) {
        for(int i = 0; i < p_200492_2_; ++i) {
            this.ingredients.add(p_200492_1_);
        }

        return this;
    }

    public ForgeRecipeBuilder unlockedBy(String p_200483_1_, CriterionTriggerInstance p_200483_2_) {
        this.advancement.addCriterion(p_200483_1_, p_200483_2_);
        return this;
    }

    public ForgeRecipeBuilder group(String p_200490_1_) {
        this.group = p_200490_1_;
        return this;
    }

    public void save(Consumer<FinishedRecipe> p_200482_1_) {
        this.save(p_200482_1_, Registry.ITEM.getKey(this.result));
    }

    public void save(Consumer<FinishedRecipe> p_200484_1_, String p_200484_2_) {
        ResourceLocation resourcelocation = Registry.ITEM.getKey(this.result);
        if ((new ResourceLocation(p_200484_2_)).equals(resourcelocation)) {
            throw new IllegalStateException("Forge Recipe " + p_200484_2_ + " should remove its 'save' argument");
        } else {
            this.save(p_200484_1_, new ResourceLocation(p_200484_2_));
        }
    }

    public void save(Consumer<FinishedRecipe> p_200485_1_, ResourceLocation p_200485_2_) {
        this.ensureValid(p_200485_2_);
        this.advancement.parent(new ResourceLocation("recipes/root")).addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(p_200485_2_)).rewards(AdvancementRewards.Builder.recipe(p_200485_2_)).requirements(RequirementsStrategy.OR);
        p_200485_1_.accept(new ForgeRecipeBuilder.Result(p_200485_2_, this.result, this.count, this.experience, this.cookingTime,this.group == null ? "" : this.group, this.ingredients, this.advancement, new ResourceLocation(p_200485_2_.getNamespace(), "recipes/" + this.result.getItemCategory().getRecipeFolderName() + "/" + p_200485_2_.getPath())));
    }

    private void ensureValid(ResourceLocation p_200481_1_) {
        if (this.advancement.getCriteria().isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + p_200481_1_);
        }
    }

    public static class Result implements FinishedRecipe {
        private final ResourceLocation id;
        private final Item result;
        private final int count;
        private final float experience;
        private final int cookingTime;
        private final String group;
        private final List<Ingredient> ingredients;
        private final Advancement.Builder advancement;
        private final ResourceLocation advancementId;

        public Result(ResourceLocation idIn, Item resultIn, int countIn, float experienceIn, int cookingTimeIn, String groupIn, List<Ingredient> ingredientsIn, Advancement.Builder advancementIn, ResourceLocation advancementIdIn) {
            this.id = idIn;
            this.result = resultIn;
            this.count = countIn;
            this.experience = experienceIn;
            this.cookingTime = cookingTimeIn;
            this.group = groupIn;
            this.ingredients = ingredientsIn;
            this.advancement = advancementIn;
            this.advancementId = advancementIdIn;
        }

        @Override
        public void serializeRecipeData(JsonObject p_218610_1_) {
            if (!this.group.isEmpty()) {
                p_218610_1_.addProperty("group", this.group);
            }

            JsonArray jsonarray = new JsonArray();

            for(Ingredient ingredient : this.ingredients) {
                jsonarray.add(ingredient.toJson());
            }

            p_218610_1_.add("ingredients", jsonarray);
            JsonObject jsonobject = new JsonObject();
            jsonobject.addProperty("item", Registry.ITEM.getKey(this.result).toString());
            if (this.count > 1) {
                jsonobject.addProperty("count", this.count);
            }

            p_218610_1_.add("result", jsonobject);
            p_218610_1_.addProperty("experience", this.experience);
            p_218610_1_.addProperty("cookingtime", this.cookingTime);
        }

        @Override
        public RecipeSerializer<?> getType() {
            return LHRecipes.Serializers.ALLOYING;
        }

        @Override
        public ResourceLocation getId() {
            return this.id;
        }

        @Override
        @Nullable
        public JsonObject serializeAdvancement() {
            return this.advancement.serializeToJson();
        }

        @Override
        @Nullable
        public ResourceLocation getAdvancementId() {
            return this.advancementId;
        }
    }
}
package com.greenone.lostheroes.client.screen;

import com.greenone.lostheroes.LostHeroes;
import com.greenone.lostheroes.common.inventory.menu.ForgeMenu;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.client.gui.screens.recipebook.RecipeUpdateListener;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class ForgeScreen extends AbstractContainerScreen<ForgeMenu> implements RecipeUpdateListener {
    private static final ResourceLocation texture = new ResourceLocation(LostHeroes.MOD_ID, "textures/gui/container/forge.png");
    private static final ResourceLocation RECIPE_BUTTON_LOCATION = new ResourceLocation("textures/gui/recipe_button.png");
    //public final AbstractRecipeBookGui recipeBookComponent;
    private boolean widthTooNarrow;

    public ForgeScreen(ForgeMenu container, Inventory inv, Component titleIn) {
        super(container, inv, titleIn);
        //this.recipeBookComponent = new FurnaceRecipeGui();
    }

    @Override
    public void init() {
        super.init();
        this.widthTooNarrow = this.width < 379;
        /*this.recipeBookComponent.init(this.width, this.height, this.minecraft, this.widthTooNarrow, this.menu);
        this.leftPos = this.recipeBookComponent.updateScreenPosition(this.widthTooNarrow, this.width, this.imageWidth);
        this.addButton(new ImageButton(this.leftPos + 20, this.height / 2 - 49, 20, 18, 0, 0, 19, RECIPE_BUTTON_LOCATION, (p_214087_1_) -> {
            this.recipeBookComponent.initVisuals(this.widthTooNarrow);
            this.recipeBookComponent.toggleVisibility();
            this.leftPos = this.recipeBookComponent.updateScreenPosition(this.widthTooNarrow, this.width, this.imageWidth);
            ((ImageButton)p_214087_1_).setPosition(this.leftPos + 20, this.height / 2 - 49);
        }));*/
        this.titleLabelX = (this.imageWidth - this.font.width(this.title)) / 2;
    }

    /*@Override
    public void render(PoseStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
        this.renderBackground(p_230430_1_);
        if (this.recipeBookComponent.isVisible() && this.widthTooNarrow) {
            this.renderBg(p_230430_1_, p_230430_4_, p_230430_2_, p_230430_3_);
            this.recipeBookComponent.render(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
        } else {
            this.recipeBookComponent.render(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
            super.render(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
            this.recipeBookComponent.renderGhostRecipe(p_230430_1_, this.leftPos, this.topPos, true, p_230430_4_);
        }

        this.renderTooltip(p_230430_1_, p_230430_2_, p_230430_3_);
        this.recipeBookComponent.renderTooltip(p_230430_1_, this.leftPos, this.topPos, p_230430_2_, p_230430_3_);
    }*/

    @Override
    protected void renderBg(PoseStack p_230450_1_, float p_230450_2_, int p_230450_3_, int p_230450_4_) {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, this.texture);
        int i = this.leftPos;
        int j = this.topPos;
        this.blit(p_230450_1_, i, j, 0, 0, this.imageWidth, this.imageHeight);
        if (this.menu.isLit()) {
            int k = this.menu.getLitProgress();
            this.blit(p_230450_1_, i + 56, j + 36 + 12 - k, 176, 12 - k, 14, k + 1);
        }

        int l = this.menu.getBurnProgress();
        this.blit(p_230450_1_, i + 79, j + 34, 176, 14, l + 1, 16);
    }

    @Override
    public void recipesUpdated() {

    }

    @Override
    public RecipeBookComponent getRecipeBookComponent() {
        return null;
    }

    /*@Override
    public boolean mouseClicked(double p_231044_1_, double p_231044_3_, int p_231044_5_) {
        if (this.recipeBookComponent.mouseClicked(p_231044_1_, p_231044_3_, p_231044_5_)) {
            return true;
        } else {
            return this.widthTooNarrow && this.recipeBookComponent.isVisible() ? true : super.mouseClicked(p_231044_1_, p_231044_3_, p_231044_5_);
        }
    }*/

    /*@Override
    protected void slotClicked(Slot p_184098_1_, int p_184098_2_, int p_184098_3_, ClickType p_184098_4_) {
        super.slotClicked(p_184098_1_, p_184098_2_, p_184098_3_, p_184098_4_);
        this.recipeBookComponent.slotClicked(p_184098_1_);
    }*/

    /*@Override
    public boolean keyPressed(int p_231046_1_, int p_231046_2_, int p_231046_3_) {
        return this.recipeBookComponent.keyPressed(p_231046_1_, p_231046_2_, p_231046_3_) ? false : super.keyPressed(p_231046_1_, p_231046_2_, p_231046_3_);
    }*/

    /*@Override
    protected boolean hasClickedOutside(double p_195361_1_, double p_195361_3_, int p_195361_5_, int p_195361_6_, int p_195361_7_) {
        boolean flag = p_195361_1_ < (double)p_195361_5_ || p_195361_3_ < (double)p_195361_6_ || p_195361_1_ >= (double)(p_195361_5_ + this.imageWidth) || p_195361_3_ >= (double)(p_195361_6_ + this.imageHeight);
        return this.recipeBookComponent.hasClickedOutside(p_195361_1_, p_195361_3_, this.leftPos, this.topPos, this.imageWidth, this.imageHeight, p_195361_7_) && flag;
    }*/

    /*@Override
    public boolean charTyped(char p_231042_1_, int p_231042_2_) {
        return this.recipeBookComponent.charTyped(p_231042_1_, p_231042_2_) ? true : super.charTyped(p_231042_1_, p_231042_2_);
    }

    @Override
    public void recipesUpdated() {
        this.recipeBookComponent.recipesUpdated();
    }

    @Override
    public RecipeBookGui getRecipeBookComponent() {
        return this.recipeBookComponent;
    }

    @Override
    public void removed() {
        this.recipeBookComponent.removed();
        super.removed();
    }*/
}

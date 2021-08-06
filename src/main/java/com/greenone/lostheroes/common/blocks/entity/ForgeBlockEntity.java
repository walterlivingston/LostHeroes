package com.greenone.lostheroes.common.blocks.entity;

import com.google.common.collect.Lists;
import com.greenone.lostheroes.common.blocks.ForgeBlock;
import com.greenone.lostheroes.common.init.LHRecipes;
import com.greenone.lostheroes.common.inventory.menu.ForgeMenu;
import com.greenone.lostheroes.common.items.crafting.ForgeRecipe;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.RecipeHolder;
import net.minecraft.world.inventory.StackedContentsCompatible;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.List;

public class ForgeBlockEntity extends BaseContainerBlockEntity implements WorldlyContainer, RecipeHolder, StackedContentsCompatible {
    protected static final int[] SLOT_INPUT = new int[]{0,1};
    protected static final int SLOT_FUEL = 2;
    protected static final int SLOT_RESULT = 3;
    public static final int DATA_LIT_TIME = 0;
    private static final int[] SLOTS_FOR_UP = new int[]{0,1};
    private static final int[] SLOTS_FOR_DOWN = new int[]{3,2};
    private static final int[] SLOTS_FOR_SIDES = new int[]{2};
    public static final int DATA_LIT_DURATION = 1;
    public static final int DATA_COOKING_PROGRESS = 2;
    public static final int DATA_COOKING_TOTAL_TIME = 3;
    public static final int NUM_DATA_VALUES = 4;
    public static final int BURN_TIME_STANDARD = 200;
    public static final int BURN_COOL_SPEED = 2;
    protected NonNullList<ItemStack> items = NonNullList.withSize(4, ItemStack.EMPTY);
    int litTime;
    int litDuration;
    int cookingProgress;
    int cookingTotalTime;
    protected final ContainerData dataAccess = new ContainerData() {
        public int get(int index) {
            switch(index) {
                case 0:
                    return ForgeBlockEntity.this.litTime;
                case 1:
                    return ForgeBlockEntity.this.litDuration;
                case 2:
                    return ForgeBlockEntity.this.cookingProgress;
                case 3:
                    return ForgeBlockEntity.this.cookingTotalTime;
                default:
                    return 0;
            }
        }

        public void set(int index, int value) {
            switch(index) {
                case 0:
                    ForgeBlockEntity.this.litTime = value;
                    break;
                case 1:
                    ForgeBlockEntity.this.litDuration = value;
                    break;
                case 2:
                    ForgeBlockEntity.this.cookingProgress = value;
                    break;
                case 3:
                    ForgeBlockEntity.this.cookingTotalTime = value;
            }

        }

        public int getCount() {
            return 4;
        }
    };
    private final Object2IntOpenHashMap<ResourceLocation> recipesUsed = new Object2IntOpenHashMap<>();
    private final RecipeType<? extends ForgeRecipe> recipeType;

    public ForgeBlockEntity(BlockPos pos, BlockState state) {
        super(LHBlockEntities.FORGE, pos, state);
        this.recipeType = LHRecipes.Types.ALLOYING;
    }

    private boolean isLit() {
        return this.litTime > 0;
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(tag, this.items);
        this.litTime = tag.getInt("BurnTime");
        this.cookingProgress = tag.getInt("CookTime");
        this.cookingTotalTime = tag.getInt("CookTimeTotal");
        this.litDuration = this.getBurnDuration(this.items.get(2));
        CompoundTag compoundtag = tag.getCompound("RecipesUsed");

        for(String s : compoundtag.getAllKeys()) {
            this.recipesUsed.put(new ResourceLocation(s), compoundtag.getInt(s));
        }

    }

    @Override
    public CompoundTag save(CompoundTag tag) {
        super.save(tag);
        tag.putInt("BurnTime", this.litTime);
        tag.putInt("CookTime", this.cookingProgress);
        tag.putInt("CookTimeTotal", this.cookingTotalTime);
        ContainerHelper.saveAllItems(tag, this.items);
        CompoundTag compoundtag = new CompoundTag();
        this.recipesUsed.forEach((p_58382_, p_58383_) -> {
            compoundtag.putInt(p_58382_.toString(), p_58383_);
        });
        tag.put("RecipesUsed", compoundtag);
        return tag;
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, ForgeBlockEntity tile) {
        boolean flag = tile.isLit();
        boolean flag1 = false;
        if (tile.isLit()) {
            --tile.litTime;
        }

        ItemStack itemstack = tile.items.get(2);
        if (tile.isLit() || !itemstack.isEmpty() && !tile.items.get(0).isEmpty()) {
            Recipe<?> recipe = level.getRecipeManager().getRecipeFor((RecipeType<ForgeRecipe>)tile.recipeType, tile, level).orElse(null);
            int i = tile.getMaxStackSize();
            if (!tile.isLit() && tile.canBurn(recipe, tile.items, i)) {
                tile.litTime = tile.getBurnDuration(itemstack);
                tile.litDuration = tile.litTime;
                if (tile.isLit()) {
                    flag1 = true;
                    if (itemstack.hasContainerItem())
                        tile.items.set(2, itemstack.getContainerItem());
                    else
                    if (!itemstack.isEmpty()) {
                        Item item = itemstack.getItem();
                        itemstack.shrink(1);
                        if (itemstack.isEmpty()) {
                            tile.items.set(2, itemstack.getContainerItem());
                        }
                    }
                }
            }

            if (tile.isLit() && tile.canBurn(recipe, tile.items, i)) {
                ++tile.cookingProgress;
                if (tile.cookingProgress == tile.cookingTotalTime) {
                    tile.cookingProgress = 0;
                    tile.cookingTotalTime = getTotalCookTime(level, tile.recipeType, tile);
                    if (tile.burn(recipe, tile.items, i)) {
                        tile.setRecipeUsed(recipe);
                    }

                    flag1 = true;
                }
            } else {
                tile.cookingProgress = 0;
            }
        } else if (!tile.isLit() && tile.cookingProgress > 0) {
            tile.cookingProgress = Mth.clamp(tile.cookingProgress - 2, 0, tile.cookingTotalTime);
        }

        if (flag != tile.isLit()) {
            flag1 = true;
            state = state.setValue(ForgeBlock.LIT, Boolean.valueOf(tile.isLit()));
            level.setBlock(pos, state, 3);
        }

        if (flag1) {
            setChanged(level, pos, state);
        }

    }

    private boolean canBurn(@Nullable Recipe<?> recipe, NonNullList<ItemStack> itemsIn, int count) {
        if (!itemsIn.get(0).isEmpty() && !itemsIn.get(1).isEmpty() && recipe != null) {
            ItemStack itemstack = ((Recipe<WorldlyContainer>) recipe).assemble(this);
            if (itemstack.isEmpty()) {
                return false;
            } else {
                ItemStack itemstack1 = itemsIn.get(3);
                if (itemstack1.isEmpty()) {
                    return true;
                } else if (!itemstack1.sameItem(itemstack)) {
                    return false;
                } else if (itemstack1.getCount() + itemstack.getCount() <= count && itemstack1.getCount() + itemstack.getCount() <= itemstack1.getMaxStackSize()) { // Forge fix: make furnace respect stack sizes in furnace recipes
                    return true;
                } else {
                    return itemstack1.getCount() + itemstack.getCount() <= itemstack.getMaxStackSize(); // Forge fix: make furnace respect stack sizes in furnace recipes
                }
            }
        } else {
            return false;
        }
    }

    private boolean burn(@Nullable Recipe<?> recipe, NonNullList<ItemStack> itemsIn, int index) {
        if (recipe != null && this.canBurn(recipe, itemsIn, index)) {
            ItemStack i1 = itemsIn.get(0);
            ItemStack i2 = itemsIn.get(1);
            ItemStack itemstack1 = ((Recipe<WorldlyContainer>) recipe).assemble(this);
            ItemStack itemstack2 = itemsIn.get(3);
            if (itemstack2.isEmpty()) {
                itemsIn.set(3, itemstack1.copy());
            } else if (itemstack2.is(itemstack1.getItem())) {
                itemstack2.grow(itemstack1.getCount());
            }

            i1.shrink(1);
            i2.shrink(1);
            return true;
        } else {
            return false;
        }
    }

    protected int getBurnDuration(ItemStack stack) {
        if (stack.isEmpty()) {
            return 0;
        } else {
            Item item = stack.getItem();
            return net.minecraftforge.common.ForgeHooks.getBurnTime(stack, this.recipeType);
        }
    }

    private static int getTotalCookTime(Level level, RecipeType<? extends ForgeRecipe> recipeType, Container container) {
        return level.getRecipeManager().getRecipeFor((RecipeType<ForgeRecipe>)recipeType, container, level).map(ForgeRecipe::getCookingTime).orElse(200);
    }

    public static boolean isFuel(ItemStack stack) {
        return net.minecraftforge.common.ForgeHooks.getBurnTime(stack, null) > 0;
    }

    @Override
    public int[] getSlotsForFace(Direction direction) {
        if (direction == Direction.DOWN) {
            return SLOTS_FOR_DOWN;
        } else {
            return direction == Direction.UP ? SLOTS_FOR_UP : SLOTS_FOR_SIDES;
        }
    }

    @Override
    public boolean canPlaceItemThroughFace(int index, ItemStack stack, @Nullable Direction direction) {
        return this.canPlaceItem(index, stack);
    }

    @Override
    public boolean canTakeItemThroughFace(int index, ItemStack stack, Direction direction) {
        if (direction == Direction.DOWN && index == 2) {
            return stack.is(Items.WATER_BUCKET) || stack.is(Items.BUCKET);
        } else {
            return true;
        }
    }

    @Override
    public int getContainerSize() {
        return this.items.size();
    }

    @Override
    public boolean isEmpty() {
        for(ItemStack itemstack : this.items) {
            if (!itemstack.isEmpty()) {
                return false;
            }
        }

        return true;
    }

    @Override
    public ItemStack getItem(int index) {
        return this.items.get(index);
    }

    @Override
    public ItemStack removeItem(int index, int count) {
        return ContainerHelper.removeItem(this.items, index, count);
    }

    @Override
    public ItemStack removeItemNoUpdate(int index) {
        return ContainerHelper.takeItem(this.items, index);
    }

    @Override
    public void setItem(int index, ItemStack stack) {
        ItemStack itemstack = this.items.get(index);
        boolean flag = !stack.isEmpty() && stack.sameItem(itemstack) && ItemStack.tagMatches(stack, itemstack);
        this.items.set(index, stack);
        if (stack.getCount() > this.getMaxStackSize()) {
            stack.setCount(this.getMaxStackSize());
        }

        if (index == 0 && !flag) {
            this.cookingTotalTime = getTotalCookTime(this.level, this.recipeType, this);
            this.cookingProgress = 0;
            this.setChanged();
        }

    }

    @Override
    public boolean stillValid(Player player) {
        if (this.level.getBlockEntity(this.worldPosition) != this) {
            return false;
        } else {
            return player.distanceToSqr((double)this.worldPosition.getX() + 0.5D, (double)this.worldPosition.getY() + 0.5D, (double)this.worldPosition.getZ() + 0.5D) <= 64.0D;
        }
    }

    @Override
    public boolean canPlaceItem(int index, ItemStack stack) {
        if (index == 3) {
            return false;
        } else if (index != 2) {
            return true;
        } else {
            ItemStack itemstack = this.items.get(2);
            return net.minecraftforge.common.ForgeHooks.getBurnTime(stack, this.recipeType) > 0 || stack.is(Items.BUCKET) && !itemstack.is(Items.BUCKET);
        }
    }

    @Override
    public void clearContent() {
        this.items.clear();
    }

    @Override
    public void setRecipeUsed(@Nullable Recipe<?> recipe) {
        if (recipe != null) {
            ResourceLocation resourcelocation = recipe.getId();
            this.recipesUsed.addTo(resourcelocation, 1);
        }

    }

    @Nullable
    @Override
    public Recipe<?> getRecipeUsed() {
        return null;
    }

    @Override
    public void awardUsedRecipes(Player player) {
    }

    public void awardUsedRecipesAndPopExperience(ServerPlayer sPlayer) {
        List<Recipe<?>> list = this.getRecipesToAwardAndPopExperience(sPlayer.getLevel(), sPlayer.position());
        sPlayer.awardRecipes(list);
        this.recipesUsed.clear();
    }

    public List<Recipe<?>> getRecipesToAwardAndPopExperience(ServerLevel sLevel, Vec3 vec) {
        List<Recipe<?>> list = Lists.newArrayList();

        for(Object2IntMap.Entry<ResourceLocation> entry : this.recipesUsed.object2IntEntrySet()) {
            sLevel.getRecipeManager().byKey(entry.getKey()).ifPresent((p_155023_) -> {
                list.add(p_155023_);
                createExperience(sLevel, vec, entry.getIntValue(), ((ForgeRecipe)p_155023_).getExperience());
            });
        }

        return list;
    }

    private static void createExperience(ServerLevel sLevel, Vec3 vec, int p_155001_, float p_155002_) {
        int i = Mth.floor((float)p_155001_ * p_155002_);
        float f = Mth.frac((float)p_155001_ * p_155002_);
        if (f != 0.0F && Math.random() < (double)f) {
            ++i;
        }

        ExperienceOrb.award(sLevel, vec, i);
    }

    @Override
    public void fillStackedContents(StackedContents stackedContents) {
        for(ItemStack itemstack : this.items) {
            stackedContents.accountStack(itemstack);
        }

    }

    net.minecraftforge.common.util.LazyOptional<? extends net.minecraftforge.items.IItemHandler>[] handlers =
            net.minecraftforge.items.wrapper.SidedInvWrapper.create(this, Direction.UP, Direction.DOWN, Direction.NORTH);

    @Override
    public <T> net.minecraftforge.common.util.LazyOptional<T> getCapability(net.minecraftforge.common.capabilities.Capability<T> capability, @Nullable Direction facing) {
        if (!this.remove && facing != null && capability == net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            if (facing == Direction.UP)
                return handlers[0].cast();
            else if (facing == Direction.DOWN)
                return handlers[1].cast();
            else
                return handlers[2].cast();
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        for (int x = 0; x < handlers.length; x++)
            handlers[x].invalidate();
    }

    @Override
    protected Component getDefaultName() {
        return new TranslatableComponent("container.forge");
    }

    @Override
    protected AbstractContainerMenu createMenu(int id, Inventory inv) {
        return new ForgeMenu(id, inv, this, this.dataAccess);
    }
}

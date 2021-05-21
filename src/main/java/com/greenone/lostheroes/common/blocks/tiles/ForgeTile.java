package com.greenone.lostheroes.common.blocks.tiles;

import com.google.common.collect.Lists;
import com.greenone.lostheroes.common.blocks.ForgeBlock;
import com.greenone.lostheroes.common.inventory.container.ForgeContainer;
import com.greenone.lostheroes.common.items.crafting.ForgeRecipe;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.block.BlockState;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IRecipeHelperPopulator;
import net.minecraft.inventory.IRecipeHolder;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeItemHelper;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.LockableTileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class ForgeTile extends LockableTileEntity implements ISidedInventory, IRecipeHolder, IRecipeHelperPopulator, ITickableTileEntity {
    private static final int[] SLOTS_FOR_UP = new int[]{0};
    private static final int[] SLOTS_FOR_DOWN = new int[]{2, 1};
    private static final int[] SLOTS_FOR_SIDES = new int[]{1};
    protected NonNullList<ItemStack> items = NonNullList.withSize(4, ItemStack.EMPTY);
    private int litTime;
    private int litDuration;
    private int cookingProgress;
    private int cookingTotalTime;
    protected final IIntArray forgeData = new IIntArray() {
        public int get(int p_221476_1_) {
            switch(p_221476_1_) {
                case 0:
                    return ForgeTile.this.litTime;
                case 1:
                    return ForgeTile.this.litDuration;
                case 2:
                    return ForgeTile.this.cookingProgress;
                case 3:
                    return ForgeTile.this.cookingTotalTime;
                default:
                    return 0;
            }
        }

        public void set(int p_221477_1_, int p_221477_2_) {
            switch(p_221477_1_) {
                case 0:
                    ForgeTile.this.litTime = p_221477_2_;
                    break;
                case 1:
                    ForgeTile.this.litDuration = p_221477_2_;
                    break;
                case 2:
                    ForgeTile.this.cookingProgress = p_221477_2_;
                    break;
                case 3:
                    ForgeTile.this.cookingTotalTime = p_221477_2_;
            }

        }

        public int getCount() {
            return 4;
        }
    };
    private final Object2IntOpenHashMap<ResourceLocation> recipesUsed = new Object2IntOpenHashMap<>();
    protected final IRecipeType<? extends ForgeRecipe> recipeType;
    public ForgeTile() {
        super(LHTileEntities.FORGE);
        this.recipeType = ForgeRecipe.FORGE;
    }

    private boolean isLit() {
        return this.litTime > 0;
    }

    @Override
    public void load(BlockState p_230337_1_, CompoundNBT p_230337_2_) {
        super.load(p_230337_1_, p_230337_2_);
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(p_230337_2_, this.items);
        this.litTime = p_230337_2_.getInt("BurnTime");
        this.cookingProgress = p_230337_2_.getInt("CookTime");
        this.cookingTotalTime = p_230337_2_.getInt("CookTimeTotal");
        this.litDuration = this.getBurnDuration(this.items.get(1));
        CompoundNBT compoundnbt = p_230337_2_.getCompound("RecipesUsed");

        for(String s : compoundnbt.getAllKeys()) {
            this.recipesUsed.put(new ResourceLocation(s), compoundnbt.getInt(s));
        }

    }

    @Override
    public CompoundNBT save(CompoundNBT p_189515_1_) {
        super.save(p_189515_1_);
        p_189515_1_.putInt("BurnTime", this.litTime);
        p_189515_1_.putInt("CookTime", this.cookingProgress);
        p_189515_1_.putInt("CookTimeTotal", this.cookingTotalTime);
        ItemStackHelper.saveAllItems(p_189515_1_, this.items);
        CompoundNBT compoundnbt = new CompoundNBT();
        this.recipesUsed.forEach((p_235643_1_, p_235643_2_) -> {
            compoundnbt.putInt(p_235643_1_.toString(), p_235643_2_);
        });
        p_189515_1_.put("RecipesUsed", compoundnbt);
        return p_189515_1_;
    }

    @Override
    public void tick() {
        boolean flag = this.isLit();
        boolean flag1 = false;
        if (this.isLit()) {
            --this.litTime;
        }

        if (!this.level.isClientSide) {
            ItemStack itemstack = this.items.get(2);
            if (this.isLit() || !itemstack.isEmpty() && !this.items.get(0).isEmpty()) {
                IRecipe<?> irecipe = this.level.getRecipeManager().getRecipeFor((IRecipeType<ForgeRecipe>)this.recipeType, this, this.level).orElse(null);
                if (!this.isLit() && this.canBurn(irecipe)) {
                    this.litTime = this.getBurnDuration(itemstack);
                    this.litDuration = this.litTime;
                    if (this.isLit()) {
                        flag1 = true;
                        if (itemstack.hasContainerItem())
                            this.items.set(2, itemstack.getContainerItem());
                        else
                        if (!itemstack.isEmpty()) {
                            Item item = itemstack.getItem();
                            itemstack.shrink(1);
                            if (itemstack.isEmpty()) {
                                this.items.set(2, itemstack.getContainerItem());
                            }
                        }
                    }
                }

                if (this.isLit() && this.canBurn(irecipe)) {
                    ++this.cookingProgress;
                    if (this.cookingProgress == this.cookingTotalTime) {
                        this.cookingProgress = 0;
                        this.cookingTotalTime = this.getTotalCookTime();
                        this.burn(irecipe);
                        flag1 = true;
                    }
                } else {
                    this.cookingProgress = 0;
                }
            } else if (!this.isLit() && this.cookingProgress > 0) {
                this.cookingProgress = MathHelper.clamp(this.cookingProgress - 2, 0, this.cookingTotalTime);
            }

            if (flag != this.isLit()) {
                flag1 = true;
                this.level.setBlock(this.worldPosition, this.level.getBlockState(this.worldPosition).setValue(ForgeBlock.LIT, Boolean.valueOf(this.isLit())), 3);
            }
        }

        if (flag1) {
            this.setChanged();
        }

    }

    protected boolean canBurn(@Nullable IRecipe<?> recipe) {
        if (!this.items.get(0).isEmpty() && !this.items.get(1).isEmpty() && recipe != null) {
            ItemStack itemstack = recipe.getResultItem();
            if (itemstack.isEmpty()) {
                return false;
            } else {
                ItemStack itemstack1 = this.items.get(3);
                if (itemstack1.isEmpty()) {
                    return true;
                } else if (!itemstack1.sameItem(itemstack)) {
                    return false;
                } else if (itemstack1.getCount() + itemstack.getCount() <= this.getMaxStackSize() && itemstack1.getCount() + itemstack.getCount() <= itemstack1.getMaxStackSize()) { // Forge fix: make furnace respect stack sizes in furnace recipes
                    return true;
                } else {
                    return itemstack1.getCount() + itemstack.getCount() <= itemstack.getMaxStackSize(); // Forge fix: make furnace respect stack sizes in furnace recipes
                }
            }
        } else {
            return false;
        }
    }

    private void burn(@Nullable IRecipe<?> recipe) {
        if (recipe != null && this.canBurn(recipe)) {
            ItemStack i1 = this.items.get(0);
            ItemStack i2 = this.items.get(1);
            ItemStack itemstack1 = recipe.getResultItem();
            ItemStack itemstack2 = this.items.get(3);
            if (itemstack2.isEmpty()) {
                this.items.set(3, itemstack1.copy());
            } else if (itemstack2.getItem() == itemstack1.getItem()) {
                itemstack2.grow(itemstack1.getCount());
            }

            if (!this.level.isClientSide) {
                this.setRecipeUsed(recipe);
            }

            i1.shrink(1);
            i2.shrink(1);
        }
    }

    protected int getBurnDuration(ItemStack stack) {
        if (stack.isEmpty()) {
            return 0;
        } else {
            Item item = stack.getItem();
            return net.minecraftforge.common.ForgeHooks.getBurnTime(stack);
        }
    }

    protected int getTotalCookTime() {
        return this.level.getRecipeManager().getRecipeFor((IRecipeType<ForgeRecipe>)this.recipeType, this, this.level).map(ForgeRecipe::getCookTime).orElse(200);
    }

    public static boolean isFuel(ItemStack stack) {
        return net.minecraftforge.common.ForgeHooks.getBurnTime(stack) > 0;
    }

    @Override
    public int[] getSlotsForFace(Direction side) {
        if(side == Direction.DOWN){
            return SLOTS_FOR_DOWN;
        }else{
            return side == Direction.UP ? SLOTS_FOR_UP : SLOTS_FOR_SIDES;
        }
    }

    @Override
    public boolean canPlaceItemThroughFace(int index, ItemStack stack, @Nullable Direction direction) {
        return this.canPlaceItem(index, stack);
    }

    public boolean canPlaceItem(int index, ItemStack stack) {
        if (index == 3) {
            return false;
        } else if (index != 2) {
            return true;
        } else {
            ItemStack itemstack = this.items.get(1);
            return isFuel(stack) || stack.getItem() == Items.BUCKET && itemstack.getItem() != Items.BUCKET;
        }
    }

    @Override
    public boolean canTakeItemThroughFace(int index, ItemStack stack, Direction direction) {
        if (direction == Direction.DOWN && index == 2) {
            Item item = stack.getItem();
            if (item != Items.WATER_BUCKET && item != Items.BUCKET) {
                return false;
            }
        }
        return true;
    }

    @Override
    protected ITextComponent getDefaultName() {
        return new TranslationTextComponent("container.forge");
    }

    @Override
    protected Container createMenu(int id, PlayerInventory player) {
        return new ForgeContainer(id, player, this, this.forgeData);
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
        return ItemStackHelper.removeItem(items, index, count);
    }

    @Override
    public ItemStack removeItemNoUpdate(int index) {
        return ItemStackHelper.takeItem(items, index);
    }

    @Override
    public void setItem(int index, ItemStack stack) {
        ItemStack itemstack = this.items.get(index);
        boolean flag = !stack.isEmpty() && stack.sameItem(itemstack) && ItemStack.tagMatches(stack, itemstack);
        this.items.set(index, stack);
        if (stack.getCount() > this.getMaxStackSize()) {
            stack.setCount(this.getMaxStackSize());
        }

        if ((index==0 || index==1) && !flag) {
            this.cookingTotalTime = this.getTotalCookTime();
            this.cookingProgress = 0;
            this.setChanged();
        }
    }

    @Override
    public boolean stillValid(PlayerEntity player) {
        if (this.level.getBlockEntity(this.worldPosition) != this) {
            return false;
        } else {
            return player.distanceToSqr((double)this.worldPosition.getX() + 0.5D, (double)this.worldPosition.getY() + 0.5D, (double)this.worldPosition.getZ() + 0.5D) <= 64.0D;
        }
    }

    @Override
    public void clearContent() {
        this.items.clear();
    }

    @Override
    public void fillStackedContents(RecipeItemHelper itemHelper) {
        for(ItemStack itemstack : this.items) {
            itemHelper.accountStack(itemstack);
        }
    }

    @Override
    public void setRecipeUsed(@Nullable IRecipe<?> recipe) {
        if (recipe != null) {
            ResourceLocation resourcelocation = recipe.getId();
            this.recipesUsed.addTo(resourcelocation, 1);
        }
    }

    @Nullable
    @Override
    public IRecipe<?> getRecipeUsed() {
        return null;
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
    protected void invalidateCaps() {
        super.invalidateCaps();
        for (int x = 0; x < handlers.length; x++)
            handlers[x].invalidate();
    }

    public void awardUsedRecipesAndPopExperience(PlayerEntity player) {
        List<IRecipe<?>> list = this.getRecipesToAwardAndPopExperience(player.level, player.position());
        player.awardRecipes(list);
        this.recipesUsed.clear();
    }

    public List<IRecipe<?>> getRecipesToAwardAndPopExperience(World level, Vector3d position) {
        List<IRecipe<?>> list = Lists.newArrayList();

        for(Object2IntMap.Entry<ResourceLocation> entry : this.recipesUsed.object2IntEntrySet()) {
            level.getRecipeManager().byKey(entry.getKey()).ifPresent((p_235642_4_) -> {
                list.add(p_235642_4_);
                createExperience(level, position, entry.getIntValue(), ((ForgeRecipe)p_235642_4_).getExperience());
            });
        }

        return list;
    }

    private void createExperience(World world, Vector3d vec, int p_235641_2_, float experience) {
        int i = MathHelper.floor((float)p_235641_2_ * experience);
        float f = MathHelper.frac((float)p_235641_2_ * experience);
        if (f != 0.0F && Math.random() < (double)f) {
            ++i;
        }

        while(i > 0) {
            int j = ExperienceOrbEntity.getExperienceValue(i);
            i -= j;
            world.addFreshEntity(new ExperienceOrbEntity(world, vec.x, vec.y, vec.z, j));
        }

    }
}

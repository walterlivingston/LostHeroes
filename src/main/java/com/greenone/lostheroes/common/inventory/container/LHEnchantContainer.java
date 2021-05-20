package com.greenone.lostheroes.common.inventory.container;

import com.greenone.lostheroes.common.blocks.LHBlocks;
import com.greenone.lostheroes.common.enums.Metal;
import com.greenone.lostheroes.common.items.LHItem;
import com.greenone.lostheroes.common.items.LHItems;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.stats.Stats;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.IntReferenceHolder;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;
import java.util.Random;

public class LHEnchantContainer extends Container {
    private final IInventory enchantSlots = new Inventory(2) {
        public void setChanged() {
            super.setChanged();
            LHEnchantContainer.this.slotsChanged(this);
        }
    };
    private final IWorldPosCallable access;
    private final Random random = new Random();
    private final IntReferenceHolder enchantmentSeed = IntReferenceHolder.standalone();
    public final int[] costs = new int[3];
    public final int[] enchantClue = new int[]{-1, -1, -1};
    public final int[] levelClue = new int[]{-1, -1, -1};

    public LHEnchantContainer(int p_i50085_1_, PlayerInventory p_i50085_2_) {
        this(p_i50085_1_, p_i50085_2_, IWorldPosCallable.NULL);
    }

    public LHEnchantContainer(int p_i50086_1_, PlayerInventory p_i50086_2_, IWorldPosCallable p_i50086_3_) {
        super(LHContainers.ENCHANTING, p_i50086_1_);
        this.access = p_i50086_3_;
        this.addSlot(new Slot(this.enchantSlots, 0, 15, 47) {
            public boolean mayPlace(ItemStack p_75214_1_) {
                return true;
            }

            public int getMaxStackSize() {
                return 1;
            }
        });
        this.addSlot(new Slot(this.enchantSlots, 1, 35, 47) {
            public boolean mayPlace(ItemStack p_75214_1_) {
                return net.minecraftforge.common.Tags.Items.GEMS_LAPIS.contains(p_75214_1_.getItem());
            }
        });

        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(p_i50086_2_, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for(int k = 0; k < 9; ++k) {
            this.addSlot(new Slot(p_i50086_2_, k, 8 + k * 18, 142));
        }

        this.addDataSlot(IntReferenceHolder.shared(this.costs, 0));
        this.addDataSlot(IntReferenceHolder.shared(this.costs, 1));
        this.addDataSlot(IntReferenceHolder.shared(this.costs, 2));
        this.addDataSlot(this.enchantmentSeed).set(p_i50086_2_.player.getEnchantmentSeed());
        this.addDataSlot(IntReferenceHolder.shared(this.enchantClue, 0));
        this.addDataSlot(IntReferenceHolder.shared(this.enchantClue, 1));
        this.addDataSlot(IntReferenceHolder.shared(this.enchantClue, 2));
        this.addDataSlot(IntReferenceHolder.shared(this.levelClue, 0));
        this.addDataSlot(IntReferenceHolder.shared(this.levelClue, 1));
        this.addDataSlot(IntReferenceHolder.shared(this.levelClue, 2));
    }

    private float getPower(net.minecraft.world.World world, net.minecraft.util.math.BlockPos pos) {
        return world.getBlockState(pos).getEnchantPowerBonus(world, pos);
    }

    public void slotsChanged(IInventory p_75130_1_) {
        if (p_75130_1_ == this.enchantSlots) {
            ItemStack itemstack = p_75130_1_.getItem(0);
            if (!itemstack.isEmpty() && itemstack.isEnchantable()) {
                this.access.execute((p_217002_2_, p_217002_3_) -> {
                    int power = 0;

                    for(int k = -1; k <= 1; ++k) {
                        for(int l = -1; l <= 1; ++l) {
                            if ((k != 0 || l != 0) && p_217002_2_.isEmptyBlock(p_217002_3_.offset(l, 0, k)) && p_217002_2_.isEmptyBlock(p_217002_3_.offset(l, 1, k))) {
                                power += getPower(p_217002_2_, p_217002_3_.offset(l * 2, 0, k * 2));
                                power += getPower(p_217002_2_, p_217002_3_.offset(l * 2, 1, k * 2));

                                if (l != 0 && k != 0) {
                                    power += getPower(p_217002_2_, p_217002_3_.offset(l * 2, 0, k));
                                    power += getPower(p_217002_2_, p_217002_3_.offset(l * 2, 1, k));
                                    power += getPower(p_217002_2_, p_217002_3_.offset(l, 0, k * 2));
                                    power += getPower(p_217002_2_, p_217002_3_.offset(l, 1, k * 2));
                                }
                            }
                        }
                    }

                    this.random.setSeed((long)this.enchantmentSeed.get());

                    for(int i1 = 0; i1 < 3; ++i1) {
                        this.costs[i1] = EnchantmentHelper.getEnchantmentCost(this.random, i1, (int)power, itemstack);
                        this.enchantClue[i1] = -1;
                        this.levelClue[i1] = -1;
                        if (this.costs[i1] < i1 + 1) {
                            this.costs[i1] = 0;
                        }
                        this.costs[i1] = net.minecraftforge.event.ForgeEventFactory.onEnchantmentLevelSet(p_217002_2_, p_217002_3_, i1, (int)power, itemstack, costs[i1]);
                    }

                    for(int j1 = 0; j1 < 3; ++j1) {
                        if (this.costs[j1] > 0) {
                            List<EnchantmentData> list = this.getEnchantmentList(itemstack, j1, this.costs[j1]);
                            if (list != null && !list.isEmpty()) {
                                EnchantmentData enchantmentdata = list.get(this.random.nextInt(list.size()));
                                this.enchantClue[j1] = Registry.ENCHANTMENT.getId(enchantmentdata.enchantment);
                                this.levelClue[j1] = enchantmentdata.level;
                            }
                        }
                    }

                    this.broadcastChanges();
                });
            } else {
                for(int i = 0; i < 3; ++i) {
                    this.costs[i] = 0;
                    this.enchantClue[i] = -1;
                    this.levelClue[i] = -1;
                }
            }
        }

    }

    public boolean clickMenuButton(PlayerEntity p_75140_1_, int p_75140_2_) {
        ItemStack itemstack = this.enchantSlots.getItem(0);
        ItemStack itemstack1 = this.enchantSlots.getItem(1);
        int i = p_75140_2_ + 1;
        if ((itemstack1.isEmpty() || itemstack1.getCount() < i) && !p_75140_1_.abilities.instabuild) {
            return false;
        } else if (this.costs[p_75140_2_] <= 0 || itemstack.isEmpty() || (p_75140_1_.experienceLevel < i || p_75140_1_.experienceLevel < this.costs[p_75140_2_]) && !p_75140_1_.abilities.instabuild) {
            return false;
        } else {
            this.access.execute((p_217003_6_, p_217003_7_) -> {
                ItemStack itemstack2 = itemstack;
                List<EnchantmentData> list = this.getEnchantmentList(itemstack, p_75140_2_, this.costs[p_75140_2_]);
                if (!list.isEmpty()) {
                    p_75140_1_.onEnchantmentPerformed(itemstack, i);
                    boolean flag = itemstack.getItem() == Items.BOOK;
                    boolean flag1 = itemstack.getItem() == LHItems.ingots.get(Metal.BRONZE);
                    boolean flag2 = itemstack.getItem() == LHItems.ingots.get(Metal.GOLD);
                    boolean flag3 = itemstack.getItem() == LHItems.nuggets.get(Metal.BRONZE);
                    boolean flag4 = itemstack.getItem() == LHItems.nuggets.get(Metal.GOLD);
                    boolean flag5 = itemstack.getItem() == LHBlocks.storageBlocks.get(Metal.BRONZE).asItem();
                    boolean flag6 = itemstack.getItem() == LHBlocks.storageBlocks.get(Metal.GOLD).asItem();
                    boolean flag7 = itemstack.getItem() == LHItems.adamantine_ingot_dull;
                    if (flag) {
                        itemstack2 = new ItemStack(Items.ENCHANTED_BOOK);
                        CompoundNBT compoundnbt = itemstack.getTag();
                        if (compoundnbt != null) itemstack2.setTag(compoundnbt.copy());
                        this.enchantSlots.setItem(0, itemstack2);
                    }
                    if(flag1) {
                        itemstack2 = new ItemStack(LHItems.ingots.get(Metal.CELESTIAL_BRONZE));
                        this.enchantSlots.setItem(0, new ItemStack(LHItems.ingots.get(Metal.CELESTIAL_BRONZE)));
                        System.out.println("MADE IT");
                    }
                    if(flag2) {
                        itemstack2 = new ItemStack(LHItems.ingots.get(Metal.IMPERIAL_GOLD));
                        this.enchantSlots.setItem(0, new ItemStack(LHItems.ingots.get(Metal.IMPERIAL_GOLD)));
                    }
                    if(flag3) {
                        itemstack2 = new ItemStack(LHItems.nuggets.get(Metal.CELESTIAL_BRONZE));
                        this.enchantSlots.setItem(0, new ItemStack(LHItems.nuggets.get(Metal.CELESTIAL_BRONZE)));
                    }
                    if(flag4) {
                        itemstack2 = new ItemStack(LHItems.nuggets.get(Metal.IMPERIAL_GOLD));
                        this.enchantSlots.setItem(0, new ItemStack(LHItems.nuggets.get(Metal.IMPERIAL_GOLD)));
                    }
                    if(flag5) {
                        itemstack2 = new ItemStack(LHBlocks.storageBlocks.get(Metal.CELESTIAL_BRONZE));
                        this.enchantSlots.setItem(0, new ItemStack(LHBlocks.storageBlocks.get(Metal.CELESTIAL_BRONZE)));
                    }
                    if(flag6) {
                        itemstack2 = new ItemStack(LHBlocks.storageBlocks.get(Metal.IMPERIAL_GOLD));
                        this.enchantSlots.setItem(0, new ItemStack(LHBlocks.storageBlocks.get(Metal.IMPERIAL_GOLD)));
                    }
                    if(flag7) {
                        itemstack2 = new ItemStack(LHItems.ingots.get(Metal.ADAMANTINE));
                        this.enchantSlots.setItem(0, new ItemStack(LHItems.ingots.get(Metal.ADAMANTINE)));
                    }

                    for (EnchantmentData enchantmentdata : list) {
                        if (flag) {
                            EnchantedBookItem.addEnchantment(itemstack2, enchantmentdata);
                        } else if (flag1 || flag2 || flag3 || flag4 || flag5 || flag6 || flag7) {
                            System.out.println("MADE IT FURTHER");
                            break;
                        } else {
                            itemstack2.enchant(enchantmentdata.enchantment, enchantmentdata.level);
                            System.out.println("I'm an idiot");
                        }
                    }

                    if (!p_75140_1_.abilities.instabuild) {
                        itemstack1.shrink(i);
                        if (itemstack1.isEmpty()) {
                            this.enchantSlots.setItem(1, ItemStack.EMPTY);
                        }
                    }

                    p_75140_1_.awardStat(Stats.ENCHANT_ITEM);
                    if (p_75140_1_ instanceof ServerPlayerEntity) {
                        CriteriaTriggers.ENCHANTED_ITEM.trigger((ServerPlayerEntity)p_75140_1_, itemstack2, i);
                    }

                    this.enchantSlots.setChanged();
                    this.enchantmentSeed.set(p_75140_1_.getEnchantmentSeed());
                    this.slotsChanged(this.enchantSlots);
                    p_217003_6_.playSound((PlayerEntity)null, p_217003_7_, SoundEvents.ENCHANTMENT_TABLE_USE, SoundCategory.BLOCKS, 1.0F, p_217003_6_.random.nextFloat() * 0.1F + 0.9F);
                }

            });
            return true;
        }
    }

    private List<EnchantmentData> getEnchantmentList(ItemStack p_178148_1_, int p_178148_2_, int p_178148_3_) {
        this.random.setSeed((long)(this.enchantmentSeed.get() + p_178148_2_));
        List<EnchantmentData> list = EnchantmentHelper.selectEnchantment(this.random, p_178148_1_, p_178148_3_, false);
        if (p_178148_1_.getItem() == Items.BOOK && list.size() > 1) {
            list.remove(this.random.nextInt(list.size()));
        }

        return list;
    }

    @OnlyIn(Dist.CLIENT)
    public int getGoldCount() {
        ItemStack itemstack = this.enchantSlots.getItem(1);
        return itemstack.isEmpty() ? 0 : itemstack.getCount();
    }

    @OnlyIn(Dist.CLIENT)
    public int getEnchantmentSeed() {
        return this.enchantmentSeed.get();
    }

    public void removed(PlayerEntity p_75134_1_) {
        super.removed(p_75134_1_);
        this.access.execute((p_217004_2_, p_217004_3_) -> {
            this.clearContainer(p_75134_1_, p_75134_1_.level, this.enchantSlots);
        });
    }

    public boolean stillValid(PlayerEntity p_75145_1_) {
        return stillValid(this.access, p_75145_1_, LHBlocks.enchanting_table);
    }

    public ItemStack quickMoveStack(PlayerEntity p_82846_1_, int p_82846_2_) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(p_82846_2_);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (p_82846_2_ == 0) {
                if (!this.moveItemStackTo(itemstack1, 2, 38, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (p_82846_2_ == 1) {
                if (!this.moveItemStackTo(itemstack1, 2, 38, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (itemstack1.getItem() == Items.LAPIS_LAZULI) {
                if (!this.moveItemStackTo(itemstack1, 1, 2, true)) {
                    return ItemStack.EMPTY;
                }
            } else {
                if (this.slots.get(0).hasItem() || !this.slots.get(0).mayPlace(itemstack1)) {
                    return ItemStack.EMPTY;
                }

                ItemStack itemstack2 = itemstack1.copy();
                itemstack2.setCount(1);
                itemstack1.shrink(1);
                this.slots.get(0).set(itemstack2);
            }

            if (itemstack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(p_82846_1_, itemstack1);
        }

        return itemstack;
    }
}

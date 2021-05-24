package com.greenone.lostheroes.common.items.tools;

import com.greenone.lostheroes.LostHeroes;
import com.greenone.lostheroes.common.capabilities.CapabilityRegistry;
import com.greenone.lostheroes.common.capabilities.IPlayerCap;
import com.greenone.lostheroes.common.enums.Metal;
import com.greenone.lostheroes.common.init.Deities;
import com.greenone.lostheroes.common.items.LHItemTier;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.*;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

public class LHBow extends BowItem {
    private final IItemTier tier;

    public LHBow(Metal metal, Properties properties) {
        this(metal.getTier(), properties);
    }

    public LHBow(IItemTier itemTier, Properties properties) {
        super(properties.defaultDurability(itemTier.getUses()));
        this.tier = itemTier;
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        boolean flag = !player.getProjectile(itemstack).isEmpty();
        IPlayerCap playerCap = player.getCapability(CapabilityRegistry.PLAYERCAP, null).orElse(null);
        boolean flag2 = playerCap.getParent() == Deities.APOLLO || playerCap.getParent() == Deities.ARTEMIS;

        ActionResult<ItemStack> ret = net.minecraftforge.event.ForgeEventFactory.onArrowNock(itemstack, world, player, hand, flag);
        if (ret != null) return ret;

        if (!player.abilities.instabuild && !flag && !flag2) {
            return ActionResult.fail(itemstack);
        } else {
            player.startUsingItem(hand);
            return ActionResult.consume(itemstack);
        }
    }

    @Override
    public void releaseUsing(ItemStack stack, World world, LivingEntity entityLiving, int p_77615_4_) {
        if (entityLiving instanceof PlayerEntity) {
            PlayerEntity playerentity = (PlayerEntity)entityLiving;
            boolean flag = playerentity.abilities.instabuild || EnchantmentHelper.getItemEnchantmentLevel(Enchantments.INFINITY_ARROWS, stack) > 0;
            IPlayerCap playerCap = playerentity.getCapability(CapabilityRegistry.PLAYERCAP, null).orElse(null);
            boolean flag2 = playerCap.getParent() == Deities.APOLLO || playerCap.getParent() == Deities.ARTEMIS;
            ItemStack itemstack = playerentity.getProjectile(stack);

            int i = this.getUseDuration(stack) - p_77615_4_;
            i = net.minecraftforge.event.ForgeEventFactory.onArrowLoose(stack, world, playerentity, i, !itemstack.isEmpty() || flag);
            if (i < 0) return;

            if (!itemstack.isEmpty() || flag || flag2) {
                if (itemstack.isEmpty()) {
                    itemstack = new ItemStack(Items.ARROW);
                }

                float f = getPowerForTime(i);
                if (!((double)f < 0.1D)) {
                    boolean flag1 = playerentity.abilities.instabuild || (itemstack.getItem() instanceof ArrowItem && ((ArrowItem)itemstack.getItem()).isInfinite(itemstack, stack, playerentity));
                    if (!world.isClientSide) {
                        ArrowItem arrowitem = (ArrowItem)(itemstack.getItem() instanceof ArrowItem ? itemstack.getItem() : Items.ARROW);
                        AbstractArrowEntity abstractarrowentity = arrowitem.createArrow(world, itemstack, playerentity);
                        abstractarrowentity = customArrow(abstractarrowentity);
                        abstractarrowentity.shootFromRotation(playerentity, playerentity.xRot, playerentity.yRot, 0.0F, f * 3.0F, 1.0F);
                        if (f == 1.0F) {
                            abstractarrowentity.setCritArrow(true);
                        }

                        int j = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.POWER_ARROWS, stack);
                        if (j > 0) {
                            abstractarrowentity.setBaseDamage(abstractarrowentity.getBaseDamage() + getDamageModifier() * 0.25D + (double)j * 0.5D + 0.5D);
                        }else{
                            abstractarrowentity.setBaseDamage(abstractarrowentity.getBaseDamage() + getDamageModifier() * 0.25D);
                        }

                        int k = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.PUNCH_ARROWS, stack);
                        if (k > 0) {
                            abstractarrowentity.setKnockback(k);
                        }

                        if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.FLAMING_ARROWS, stack) > 0) {
                            abstractarrowentity.setSecondsOnFire(100);
                        }

                        stack.hurtAndBreak(1, playerentity, (p_220009_1_) -> {
                            p_220009_1_.broadcastBreakEvent(playerentity.getUsedItemHand());
                        });
                        if (flag1 || playerentity.abilities.instabuild && (itemstack.getItem() == Items.SPECTRAL_ARROW || itemstack.getItem() == Items.TIPPED_ARROW)) {
                            abstractarrowentity.pickup = AbstractArrowEntity.PickupStatus.CREATIVE_ONLY;
                        }

                        world.addFreshEntity(abstractarrowentity);
                    }

                    world.playSound((PlayerEntity)null, playerentity.getX(), playerentity.getY(), playerentity.getZ(), SoundEvents.ARROW_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F / (random.nextFloat() * 0.4F + 1.2F) + f * 0.5F);
                    if (!flag1 && !playerentity.abilities.instabuild) {
                        itemstack.shrink(1);
                        if (itemstack.isEmpty()) {
                            playerentity.inventory.removeItem(itemstack);
                        }
                    }

                    playerentity.awardStat(Stats.ITEM_USED.get(this));
                }
            }
        }
    }

    public IItemTier getTier() {
        return tier;
    }

    public float getDamageModifier() { return tier.getAttackDamageBonus(); }

    @Override
    public int getItemEnchantability(ItemStack stack) {
        return tier.getEnchantmentValue();
    }

    @Override
    public boolean isRepairable(ItemStack stack) {
        return tier.getRepairIngredient().test(stack) || super.isRepairable(stack);
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        if(tier != null && tier instanceof LHItemTier){
            return ((LHItemTier)tier).hasEffect() || super.isFoil(stack);
        }
        return super.isFoil(stack);
    }
}

package com.greenone.lostheroes.common.items.tools;

import com.google.common.collect.Lists;
import com.greenone.lostheroes.common.capabilities.CapabilityRegistry;
import com.greenone.lostheroes.common.capabilities.IPlayerCap;
import com.greenone.lostheroes.common.enums.Metal;
import com.greenone.lostheroes.common.init.Deities;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.CrossbowAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

public class LHCrossbow extends CrossbowItem {
    private static Tier tier = null;
    private boolean startSoundPlayed = false;
    private boolean midLoadSoundPlayed = false;

    public LHCrossbow(Metal metal, Properties properties) {
        this(metal.getTier(), properties);
    }

    public LHCrossbow(Tier itemTier, Properties properties) {
        super(properties.defaultDurability(itemTier.getUses()));
        this.tier = itemTier;
    }

    public static Tier getTier() {
        return tier;
    }

    public static boolean hasInfinity(Player player){
        IPlayerCap playerCap = player.getCapability(CapabilityRegistry.PLAYERCAP, null).orElse(null);
        return playerCap.getParent() == Deities.APOLLO || playerCap.getParent() == Deities.ARTEMIS;
    }

    @Override
    public Predicate<ItemStack> getSupportedHeldProjectiles() {
        return ARROW_OR_FIREWORK;
    }

    @Override
    public Predicate<ItemStack> getAllSupportedProjectiles() {
        return ARROW_ONLY;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (isCharged(itemstack)) {
            performShooting(world, player, hand, itemstack, getShootingPower(itemstack), 1.0F);
            setCharged(itemstack, false);
            return InteractionResultHolder.consume(itemstack);
        } else if (!player.getProjectile(itemstack).isEmpty() || hasInfinity(player)) {
            if (!isCharged(itemstack)) {
                this.startSoundPlayed = false;
                this.midLoadSoundPlayed = false;
                player.startUsingItem(hand);
            }

            return InteractionResultHolder.consume(itemstack);
        } else {
            return InteractionResultHolder.fail(itemstack);
        }
    }

    @Override
    public void releaseUsing(ItemStack stack, Level world, LivingEntity livingEntity, int p_77615_4_) {
        int i = this.getUseDuration(stack) - p_77615_4_;
        float f = getPowerForTime(i, stack);
        if (f >= 1.0F && !isCharged(stack) && tryLoadProjectiles(livingEntity, stack)) {
            setCharged(stack, true);
            SoundSource soundcategory = livingEntity instanceof Player ? SoundSource.PLAYERS : SoundSource.HOSTILE;
            world.playSound((Player)null, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), SoundEvents.CROSSBOW_LOADING_END, soundcategory, 1.0F, 1.0F / (new Random().nextFloat() * 0.5F + 1.0F) + 0.2F);
        }

    }

    private static boolean tryLoadProjectiles(LivingEntity livingEntity, ItemStack stack) {
        int i = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.MULTISHOT, stack);
        int j = i == 0 ? 1 : 3;
        boolean flag = livingEntity instanceof Player && (((Player)livingEntity).getAbilities().instabuild || hasInfinity((Player) livingEntity));
        ItemStack itemstack = livingEntity.getProjectile(stack);
        ItemStack itemstack1 = itemstack.copy();

        for(int k = 0; k < j; ++k) {
            if (k > 0) {
                itemstack = itemstack1.copy();
            }

            if (itemstack.isEmpty() && flag) {
                itemstack = new ItemStack(Items.ARROW);
                itemstack1 = itemstack.copy();
            }

            if (!loadProjectile(livingEntity, stack, itemstack, k > 0, flag)) {
                return false;
            }
        }

        return true;
    }

    private static boolean loadProjectile(LivingEntity livingEntity, ItemStack stack, ItemStack projectile, boolean hasMultishot, boolean isCreative) {
        if (projectile.isEmpty()) {
            return false;
        } else {
            boolean flag = isCreative && projectile.getItem() instanceof ArrowItem;
            ItemStack itemstack;
            if (!flag && !isCreative && !hasMultishot && !hasInfinity((Player) livingEntity)) {
                itemstack = projectile.split(1);
                if (projectile.isEmpty() && livingEntity instanceof Player) {
                    ((Player)livingEntity).getInventory().removeItem(projectile);
                }
            } else {
                itemstack = projectile.copy();
            }

            addChargedProjectile(stack, itemstack);
            return true;
        }
    }

    public static boolean isCharged(ItemStack stack) {
        CompoundTag compoundnbt = stack.getTag();
        return compoundnbt != null && compoundnbt.getBoolean("Charged");
    }

    public static void setCharged(ItemStack stack, boolean isCharged) {
        CompoundTag compoundnbt = stack.getOrCreateTag();
        compoundnbt.putBoolean("Charged", isCharged);
    }

    private static void addChargedProjectile(ItemStack stack, ItemStack projectile) {
        CompoundTag compoundnbt = stack.getOrCreateTag();
        ListTag listnbt;
        if (compoundnbt.contains("ChargedProjectiles", 9)) {
            listnbt = compoundnbt.getList("ChargedProjectiles", 10);
        } else {
            listnbt = new ListTag();
        }

        CompoundTag compoundnbt1 = new CompoundTag();
        projectile.save(compoundnbt1);
        listnbt.add(compoundnbt1);
        compoundnbt.put("ChargedProjectiles", listnbt);
    }

    private static List<ItemStack> getChargedProjectiles(ItemStack stack) {
        List<ItemStack> list = Lists.newArrayList();
        CompoundTag compoundnbt = stack.getTag();
        if (compoundnbt != null && compoundnbt.contains("ChargedProjectiles", 9)) {
            ListTag listnbt = compoundnbt.getList("ChargedProjectiles", 10);
            if (listnbt != null) {
                for(int i = 0; i < listnbt.size(); ++i) {
                    CompoundTag compoundnbt1 = listnbt.getCompound(i);
                    list.add(ItemStack.of(compoundnbt1));
                }
            }
        }

        return list;
    }

    private static void clearChargedProjectiles(ItemStack stack) {
        CompoundTag compoundnbt = stack.getTag();
        if (compoundnbt != null) {
            ListTag listnbt = compoundnbt.getList("ChargedProjectiles", 9);
            listnbt.clear();
            compoundnbt.put("ChargedProjectiles", listnbt);
        }

    }

    public static boolean containsChargedProjectile(ItemStack stack, Item projectile) {
        return getChargedProjectiles(stack).stream().anyMatch((p_220010_1_) -> {
            return p_220010_1_.getItem() == projectile;
        });
    }

    private static void shootProjectile(Level world, LivingEntity livingEntity, InteractionHand hand, ItemStack stack, ItemStack projectile, float pitch, boolean flagIn, float power, float p_220016_8_, float p_220016_9_) {
        if (!world.isClientSide) {
            boolean flag = projectile.getItem() == Items.FIREWORK_ROCKET;
            Projectile projectileentity;
            if (flag) {
                projectileentity = new FireworkRocketEntity(world, projectile, livingEntity, livingEntity.getX(), livingEntity.getEyeY() - (double)0.15F, livingEntity.getZ(), true);
            } else {
                projectileentity = getArrow(world, livingEntity, stack, projectile);
                if (flag || p_220016_9_ != 0.0F) {
                    ((AbstractArrow)projectileentity).pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
                }
            }

            if (livingEntity instanceof CrossbowAttackMob) {
                CrossbowAttackMob icrossbowuser = (CrossbowAttackMob)livingEntity;
                icrossbowuser.shootCrossbowProjectile(icrossbowuser.getTarget(), stack, projectileentity, p_220016_9_);
            } else {
                Vec3 vector3d1 = livingEntity.getUpVector(1.0F);
                Quaternion quaternion = new Quaternion(new Vector3f(vector3d1), p_220016_9_, true);
                Vec3 vector3d = livingEntity.getViewVector(1.0F);
                Vector3f vector3f = new Vector3f(vector3d);
                vector3f.transform(quaternion);
                projectileentity.shoot((double)vector3f.x(), (double)vector3f.y(), (double)vector3f.z(), power, p_220016_8_);
            }

            stack.hurtAndBreak(flag ? 3 : 1, livingEntity, (p_220017_1_) -> {
                p_220017_1_.broadcastBreakEvent(hand);
            });
            world.addFreshEntity(projectileentity);
            world.playSound((Player)null, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), SoundEvents.CROSSBOW_SHOOT, SoundSource.PLAYERS, 1.0F, pitch);
        }
    }

    private static AbstractArrow getArrow(Level world, LivingEntity livingEntity, ItemStack stack, ItemStack projectile) {
        ArrowItem arrowitem = (ArrowItem)(projectile.getItem() instanceof ArrowItem ? projectile.getItem() : Items.ARROW);
        AbstractArrow abstractarrowentity = arrowitem.createArrow(world, projectile, livingEntity);
        if (livingEntity instanceof Player) {
            abstractarrowentity.setCritArrow(true);
        }

        abstractarrowentity.setSoundEvent(SoundEvents.CROSSBOW_HIT);
        abstractarrowentity.setShotFromCrossbow(true);
        int i = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.PIERCING, stack);
        if (i > 0) {
            abstractarrowentity.setPierceLevel((byte)i);
        }

        return abstractarrowentity;
    }

    public static void performShooting(Level world, LivingEntity livingEntity, InteractionHand hand, ItemStack stack, float power, float p_220014_5_) {
        List<ItemStack> list = getChargedProjectiles(stack);
        float[] afloat = getShotPitches(livingEntity.getRandom());

        for(int i = 0; i < list.size(); ++i) {
            ItemStack itemstack = list.get(i);
            boolean flag = livingEntity instanceof Player && ((Player)livingEntity).getAbilities().instabuild;
            if (!itemstack.isEmpty()) {
                if (i == 0) {
                    shootProjectile(world, livingEntity, hand, stack, itemstack, afloat[i], flag, power, p_220014_5_, 0.0F);
                } else if (i == 1) {
                    shootProjectile(world, livingEntity, hand, stack, itemstack, afloat[i], flag, power, p_220014_5_, -10.0F);
                } else if (i == 2) {
                    shootProjectile(world, livingEntity, hand, stack, itemstack, afloat[i], flag, power, p_220014_5_, 10.0F);
                }
            }
        }

        onCrossbowShot(world, livingEntity, stack);
    }

    private static float[] getShotPitches(Random random) {
        boolean flag = random.nextBoolean();
        return new float[]{1.0F, getRandomShotPitch(flag), getRandomShotPitch(!flag)};
    }

    private static float getRandomShotPitch(boolean flag) {
        float f = flag ? 0.63F : 0.43F;
        return 1.0F / (new Random().nextFloat() * 0.5F + 1.8F) + f;
    }

    private static void onCrossbowShot(Level world, LivingEntity livingEntity, ItemStack stack) {
        if (livingEntity instanceof ServerPlayer) {
            ServerPlayer serverplayerentity = (ServerPlayer)livingEntity;
            if (!world.isClientSide) {
                CriteriaTriggers.SHOT_CROSSBOW.trigger(serverplayerentity, stack);
            }

            serverplayerentity.awardStat(Stats.ITEM_USED.get(stack.getItem()));
        }

        clearChargedProjectiles(stack);
    }

    @Override
    public void onUseTick(Level world, LivingEntity livingEntity, ItemStack stack, int p_219972_4_) {
        if (!world.isClientSide) {
            int i = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.QUICK_CHARGE, stack);
            SoundEvent soundevent = this.getStartSound(i);
            SoundEvent soundevent1 = i == 0 ? SoundEvents.CROSSBOW_LOADING_MIDDLE : null;
            float f = (float)(stack.getUseDuration() - p_219972_4_) / (float)getChargeDuration(stack);
            if (f < 0.2F) {
                this.startSoundPlayed = false;
                this.midLoadSoundPlayed = false;
            }

            if (f >= 0.2F && !this.startSoundPlayed) {
                this.startSoundPlayed = true;
                world.playSound((Player)null, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), soundevent, SoundSource.PLAYERS, 0.5F, 1.0F);
            }

            if (f >= 0.5F && soundevent1 != null && !this.midLoadSoundPlayed) {
                this.midLoadSoundPlayed = true;
                world.playSound((Player)null, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), soundevent1, SoundSource.PLAYERS, 0.5F, 1.0F);
            }
        }

    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return getChargeDuration(stack) + 3;
    }

    public static int getChargeDuration(ItemStack stack) {
        int i = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.QUICK_CHARGE, stack);
        return i == 0 ? (int) (25 - getTier().getSpeed()) : (int) (25 - getTier().getSpeed()) - 5 * i;
    }

    private SoundEvent getStartSound(int quickChargeLevel) {
        switch(quickChargeLevel) {
            case 1:
                return SoundEvents.CROSSBOW_QUICK_CHARGE_1;
            case 2:
                return SoundEvents.CROSSBOW_QUICK_CHARGE_2;
            case 3:
                return SoundEvents.CROSSBOW_QUICK_CHARGE_3;
            default:
                return SoundEvents.CROSSBOW_LOADING_START;
        }
    }

    private float getPowerForTime(int time, ItemStack stack) {
        float f = (float) time / (float)getChargeDuration(stack);
        if (f > 1.0F) {
            f = 1.0F;
        }

        return f;
    }

    private static float getShootingPower(ItemStack stack) {
        return stack.getItem() == Items.CROSSBOW && containsChargedProjectile(stack, Items.FIREWORK_ROCKET) ? 1.6F : 3.15F;
    }

    @Override
    public int getDefaultProjectileRange() {
        return (int) (8 + getTier().getAttackDamageBonus() * 2);
    }
}

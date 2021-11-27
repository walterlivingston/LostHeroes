package com.greenone.lostheroes.data.loot;

import com.google.common.collect.ImmutableSet;
import com.greenone.lostheroes.LostHeroes;
import com.greenone.lostheroes.common.enums.Metal;
import com.greenone.lostheroes.common.enums.Stone;
import com.greenone.lostheroes.common.enums.Wood;
import com.greenone.lostheroes.common.init.LHBlocks;
import com.greenone.lostheroes.common.init.LHItems;
import com.greenone.lostheroes.common.items.LHItem;
import net.minecraft.advancements.critereon.EnchantmentPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.BonusLevelTableCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LHBlockLootTables extends BlockLoot {
    private static final LootItemCondition.Builder HAS_SILK_TOUCH = MatchTool.toolMatches(ItemPredicate.Builder.item().hasEnchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.Ints.atLeast(1))));
    private static final LootItemCondition.Builder HAS_NO_SILK_TOUCH = HAS_SILK_TOUCH.invert();
    private static final LootItemCondition.Builder HAS_SHEARS = MatchTool.toolMatches(ItemPredicate.Builder.item().of(Items.SHEARS));
    private static final LootItemCondition.Builder HAS_SHEARS_OR_SILK_TOUCH = HAS_SHEARS.or(HAS_SILK_TOUCH);
    private static final LootItemCondition.Builder HAS_NO_SHEARS_OR_SILK_TOUCH = HAS_SHEARS_OR_SILK_TOUCH.invert();
    private static final Set<Item> EXPLOSION_RESISTANT = Stream.of(Blocks.DRAGON_EGG, Blocks.BEACON, Blocks.CONDUIT, Blocks.SKELETON_SKULL, Blocks.WITHER_SKELETON_SKULL, Blocks.PLAYER_HEAD, Blocks.ZOMBIE_HEAD, Blocks.CREEPER_HEAD, Blocks.DRAGON_HEAD, Blocks.SHULKER_BOX, Blocks.BLACK_SHULKER_BOX, Blocks.BLUE_SHULKER_BOX, Blocks.BROWN_SHULKER_BOX, Blocks.CYAN_SHULKER_BOX, Blocks.GRAY_SHULKER_BOX, Blocks.GREEN_SHULKER_BOX, Blocks.LIGHT_BLUE_SHULKER_BOX, Blocks.LIGHT_GRAY_SHULKER_BOX, Blocks.LIME_SHULKER_BOX, Blocks.MAGENTA_SHULKER_BOX, Blocks.ORANGE_SHULKER_BOX, Blocks.PINK_SHULKER_BOX, Blocks.PURPLE_SHULKER_BOX, Blocks.RED_SHULKER_BOX, Blocks.WHITE_SHULKER_BOX, Blocks.YELLOW_SHULKER_BOX).map(ItemLike::asItem).collect(ImmutableSet.toImmutableSet());
    private static final float[] NORMAL_LEAVES_SAPLING_CHANCES = new float[]{0.05F, 0.0625F, 0.083333336F, 0.1F};

    @Override
    protected void addTables() {
        for(Metal m : Metal.values()){
            if(m.isVanilla()){

            }else {
                dropSelf(LHBlocks.storageBlocks.get(m));
                if (m.generateOre()) {
                    dropSelf(LHBlocks.ores.get(m));
                }
            }
        }
        for(Stone s : Stone.values()){
            dropSelf(LHBlocks.stoneBlocks.get(s));
            dropSelf(LHBlocks.stoneSlabs.get(s));
            dropSelf(LHBlocks.stoneStairs.get(s));
            dropSelf(LHBlocks.stoneBricks.get(s));
            dropSelf(LHBlocks.stoneBrickSlabs.get(s));
            dropSelf(LHBlocks.stoneBrickStairs.get(s));
            dropSelf(LHBlocks.pillars.get(s));
        }
        for(Wood w : Wood.values()){
            dropSelf(LHBlocks.logs.get(w));
            dropSelf(LHBlocks.stripped_logs.get(w));
            dropSelf(LHBlocks.planks.get(w));
            dropSelf(LHBlocks.wooden_buttons.get(w));
            dropSelf(LHBlocks.wooden_doors.get(w));
            dropSelf(LHBlocks.wooden_stairs.get(w));
            dropSelf(LHBlocks.wooden_slabs.get(w));
            dropSelf(LHBlocks.fence.get(w));
            dropSelf(LHBlocks.fence_gates.get(w));
            add(LHBlocks.leaves.get(w), (b) -> specialLeaveDrops(b,LHBlocks.saplings.get(w), LHItems.pomegranate, NORMAL_LEAVES_SAPLING_CHANCES));
            dropSelf(LHBlocks.saplings.get(w));
        }
        dropSelf(LHBlocks.forge);
        //dropSelf(LHBlocks.cask);
        //dropSelf(LHBlocks.lotus_flower);
        //add(LHBlocks.grape_vine, BlockLoot::createShearsOnlyDrop);
        add(LHBlocks.greek_fire, noDrop());
    }

    protected static LootTable.Builder specialLeaveDrops(Block block, Block sapling, Item additionalDrop,float... sapling_chance) {
        return createLeavesDrops(block, sapling, sapling_chance).withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).when(HAS_NO_SHEARS_OR_SILK_TOUCH).add(applyExplosionCondition(block, LootItem.lootTableItem(additionalDrop)).when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, 0.005F, 0.0055555557F, 0.00625F, 0.008333334F, 0.025F))));
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ForgeRegistries.BLOCKS.getValues().stream()
                .filter(block -> LostHeroes.MOD_ID.equals(block.getRegistryName().getNamespace()))
                .collect(Collectors.toSet());
    }
}

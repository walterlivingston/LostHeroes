package com.greenone.lostheroes.common.init;

import com.greenone.lostheroes.LostHeroes;
import com.greenone.lostheroes.common.blocks.ForgeBlock;
import com.greenone.lostheroes.common.blocks.GreekFireBlock;
import com.greenone.lostheroes.common.blocks.LHEnchantmentTable;
import com.greenone.lostheroes.common.blocks.PillarBlock;
import com.greenone.lostheroes.common.enums.Metal;
import com.greenone.lostheroes.common.enums.Stone;
import com.greenone.lostheroes.common.items.LHItemBlock;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.Map;

public class LHBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, LostHeroes.MOD_ID);
    public static final DeferredRegister<Block> VANILLA_BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, "minecraft");

    public static final AbstractBlock.Properties metal_prop = AbstractBlock.Properties.of(Material.HEAVY_METAL).requiresCorrectToolForDrops().strength(5, 10);
    public static final AbstractBlock.Properties stone_prop = AbstractBlock.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(3.0F, 3.0F);

    public static Map<Metal, Block> storageBlocks = new HashMap<>();
    public static Map<Metal, Block> ores = new HashMap<>();
    public static Map<Stone, Block> stoneBlocks = new HashMap<>();
    public static Map<Stone, Block> stoneSlabs = new HashMap<>();
    public static Map<Stone, Block> stoneStairs = new HashMap<>();
    public static Map<Stone, Block> stoneBricks = new HashMap<>();
    public static Map<Stone, Block> stoneBrickSlabs = new HashMap<>();
    public static Map<Stone, Block> stoneBrickStairs = new HashMap<>();
    public static Map<Stone, Block> pillars = new HashMap<>();

    public static final Block enchanting_table = registerVanilla("enchanting_table", new LHEnchantmentTable(AbstractBlock.Properties.of(Material.STONE, MaterialColor.COLOR_RED).requiresCorrectToolForDrops().strength(5.0F, 1200.0F)), ItemGroup.TAB_DECORATIONS);
    public static final Block forge = register("forge", new ForgeBlock(stone_prop));

    public static final Block greek_fire = registerNoItem("greek_fire", new GreekFireBlock(AbstractBlock.Properties.of(Material.FIRE, MaterialColor.COLOR_LIGHT_GREEN).noCollission().instabreak().lightLevel((p_235468_0_) -> 15).sound(SoundType.WOOL)));

    public static void register(IEventBus eventBus) {
        for(Metal m : Metal.values()){
            if(m.isVanilla()){
                storageBlocks.put(m, registerVanilla(m.tagName()+"_block", new Block(metal_prop), ItemGroup.TAB_BUILDING_BLOCKS, m));
            }else{
                storageBlocks.put(m, register(m.tagName()+"_block", new Block(metal_prop), m));
                if(m.generateOre()) ores.put(m, register(m.tagName()+"_ore", new OreBlock(stone_prop)));
            }
        }
        for(Stone s : Stone.values()){
            stoneBlocks.put(s, register(s.tagName(), new Block(stone_prop)));
            stoneSlabs.put(s, register(s.tagName()+"_slab", new SlabBlock(stone_prop)));
            stoneStairs.put(s, register(s.tagName()+"_stair", new StairsBlock(() -> stoneBlocks.get(s).defaultBlockState(), stone_prop)));
            stoneBricks.put(s, register(s.tagName()+"_brick", new Block(stone_prop)));
            stoneBrickSlabs.put(s, register(s.tagName()+"_brick_slab", new SlabBlock(stone_prop)));
            stoneBrickStairs.put(s, register(s.tagName()+"_brick_stair",  new StairsBlock(() -> stoneBricks.get(s).defaultBlockState(), stone_prop)));
            pillars.put(s, register(s.tagName()+"_pillar", new PillarBlock(stone_prop)));
        }

        VANILLA_BLOCKS.register(eventBus);
        BLOCKS.register(eventBus);
    }

    public static Block registerNoItem(String name, Block block){
        BLOCKS.register(name, () -> block);
        return block;
    }
    public static Block register(String name, Block block){
        BLOCKS.register(name, () -> block);
        LHItems.ITEMS.register(name, () -> new LHItemBlock(block, new Item.Properties().tab(ItemGroup.TAB_BUILDING_BLOCKS)));
        return block;
    }
    public static Block register(String name, Block block, Metal metal){
        LHItems.ITEMS.register(name, () -> new LHItemBlock(block, new Item.Properties().tab(ItemGroup.TAB_BUILDING_BLOCKS), metal));
        return registerNoItem(name, block);
    }
    private static Block registerVanilla(String name, Block block, ItemGroup group, Metal m) {
        VANILLA_BLOCKS.register(name, () -> block);
        LHItems.VANILLA_ITEMS.register(name, () -> new LHItemBlock(block, new Item.Properties().tab(group), m));
        return block;
    }
    private static Block registerVanilla(String name, Block block, ItemGroup group) {
        VANILLA_BLOCKS.register(name, () -> block);
        LHItems.VANILLA_ITEMS.register(name, () -> new LHItemBlock(block, new Item.Properties().tab(group)));
        return block;
    }
}

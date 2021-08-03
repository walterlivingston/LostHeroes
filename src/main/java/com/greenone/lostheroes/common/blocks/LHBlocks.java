package com.greenone.lostheroes.common.blocks;

import com.greenone.lostheroes.LostHeroes;
import com.greenone.lostheroes.common.enums.Metal;
import com.greenone.lostheroes.common.enums.Stone;
import com.greenone.lostheroes.common.items.LHItemBlock;
import com.greenone.lostheroes.common.items.LHItems;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class LHBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, LostHeroes.MOD_ID);
    public static final DeferredRegister<Block> VANILLA_BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, "minecraft");

    public static final BlockBehaviour.Properties metal_prop = BlockBehaviour.Properties.of(Material.HEAVY_METAL).requiresCorrectToolForDrops().strength(5, 10);
    public static final BlockBehaviour.Properties stone_prop = BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(3.0F, 3.0F);

    public static Map<Metal, Block> storageBlocks = new HashMap<>();
    public static Map<Metal, Block> ores = new HashMap<>();
    public static Map<Stone, Block> stoneBlocks = new HashMap<>();
    public static Map<Stone, Block> stoneSlabs = new HashMap<>();
    public static Map<Stone, Block> stoneStairs = new HashMap<>();
    public static Map<Stone, Block> stoneBricks = new HashMap<>();
    public static Map<Stone, Block> stoneBrickSlabs = new HashMap<>();
    public static Map<Stone, Block> stoneBrickStairs = new HashMap<>();
    public static Map<Stone, Block> pillars = new HashMap<>();

    public static final Block enchanting_table = registerVanilla("enchanting_table", new LHEnchantmentTable(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_RED).requiresCorrectToolForDrops().strength(5.0F, 1200.0F)), CreativeModeTab.TAB_DECORATIONS);
    public static final Block forge = register("forge", new ForgeBlock(stone_prop));

    public static final Block cask = register("cask", new CaskBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(0.6F).sound(SoundType.WOOD)));
    public static final Block lotus_flower = register("lotus_flower", new LotusFlowerBlock(MobEffects.LEVITATION, 5, BlockBehaviour.Properties.of(Material.PLANT).noCollission().instabreak().sound(SoundType.GRASS)));

    //public static final Block greek_fire = registerNoItem("greek_fire", new GreekFireBlock(BlockBehaviour.Properties.of(Material.FIRE, MaterialColor.COLOR_LIGHT_GREEN).noCollission().instabreak().lightLevel((p_235468_0_) -> 15).sound(SoundType.WOOL)));

    public static void register(IEventBus eventBus) {
        for(Metal m : Metal.values()){
            if(m.isVanilla()){
                storageBlocks.put(m, registerVanilla(m.tagName()+"_block", new Block(metal_prop), CreativeModeTab.TAB_BUILDING_BLOCKS, m));
            }else{
                storageBlocks.put(m, register(m.tagName()+"_block", new Block(metal_prop), m));
                if(m.generateOre()) ores.put(m, register(m.tagName()+"_ore", new OreBlock(stone_prop)));
            }
        }
        for(Stone s : Stone.values()){
            stoneBlocks.put(s, register(s.tagName(), new Block(stone_prop)));
            stoneSlabs.put(s, register(s.tagName()+"_slab", new SlabBlock(stone_prop)));
            stoneStairs.put(s, register(s.tagName()+"_stair", new StairBlock(() -> stoneBlocks.get(s).defaultBlockState(), stone_prop)));
            stoneBricks.put(s, register(s.tagName()+"_brick", new Block(stone_prop)));
            stoneBrickSlabs.put(s, register(s.tagName()+"_brick_slab", new SlabBlock(stone_prop)));
            stoneBrickStairs.put(s, register(s.tagName()+"_brick_stair",  new StairBlock(() -> stoneBricks.get(s).defaultBlockState(), stone_prop)));
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
        LHItems.ITEMS.register(name, () -> new LHItemBlock(block, new Item.Properties().tab(LostHeroes.lh_group)));
        return block;
    }
    public static Block register(String name, Block block, Metal metal){
        LHItems.ITEMS.register(name, () -> new LHItemBlock(block, new Item.Properties().tab(LostHeroes.lh_group), metal));
        return registerNoItem(name, block);
    }
    private static Block registerVanilla(String name, Block block, CreativeModeTab group, Metal m) {
        VANILLA_BLOCKS.register(name, () -> block);
        LHItems.VANILLA_ITEMS.register(name, () -> new LHItemBlock(block, new Item.Properties().tab(group), m));
        return block;
    }
    private static Block registerVanilla(String name, Block block, CreativeModeTab group) {
        VANILLA_BLOCKS.register(name, () -> block);
        LHItems.VANILLA_ITEMS.register(name, () -> new LHItemBlock(block, new Item.Properties().tab(group)));
        return block;
    }
}

package com.greenone.lostheroes.common.blocks;

import com.greenone.lostheroes.LostHeroes;
import com.greenone.lostheroes.common.enums.Metal;
import com.greenone.lostheroes.common.items.LHItemBlock;
import com.greenone.lostheroes.common.items.LHItems;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class LHBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, LostHeroes.MOD_ID);

    //public static final Block greek_fire = registerNoItem("greek_fire", new GreekFireBlock(AbstractBlock.Properties.of(Material.FIRE, MaterialColor.COLOR_LIGHT_GREEN).noCollission().instabreak().lightLevel((p_235468_0_) -> 15).sound(SoundType.WOOL)));

    public static void register(IEventBus eventBus) {
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
}

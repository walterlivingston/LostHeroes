package com.greenone.lostheroes.common.blocks;

import com.greenone.lostheroes.LostHeroes;
import com.greenone.lostheroes.common.items.LHItems;
import com.greenone.lostheroes.common.util.RegistryHandler;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class LHBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, LostHeroes.MOD_ID);

    public static final RegistryObject<Block> TEST_BLOCK = register("test_block", () ->
            new Block(AbstractBlock.Properties.of(Material.STONE)));

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }

    public static <T extends Block> RegistryObject<T> registerNoItem(String name, Supplier<T> block){
        return BLOCKS.register(name, block);
    }

    public static <T extends Block> RegistryObject<T> register(String name, Supplier<T> block){
        RegistryObject<T> ret = registerNoItem(name, block);
        LHItems.ITEMS.register(name, () -> new BlockItem(ret.get(), new Item.Properties().tab(LostHeroes.lh_group)));
        return ret;
    }
}

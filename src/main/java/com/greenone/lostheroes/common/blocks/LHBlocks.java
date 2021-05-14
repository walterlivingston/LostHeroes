package com.greenone.lostheroes.common.blocks;

import com.greenone.lostheroes.LostHeroes;
import com.greenone.lostheroes.common.util.RegistryHandler;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;

import java.util.function.Supplier;

public class LHBlocks {
    public static final RegistryObject<Block> TEST_BLOCK = register("test_block", () ->
            new Block(AbstractBlock.Properties.of(Material.STONE)));

    public static void register(IEventBus eventBus) {
        RegistryHandler.BLOCKS.register(eventBus);
    }

    public static <T extends Block> RegistryObject<T> registerNoItem(String name, Supplier<T> block){
        return RegistryHandler.BLOCKS.register(name, block);
    }

    public static <T extends Block> RegistryObject<T> register(String name, Supplier<T> block){
        RegistryObject<T> ret = registerNoItem(name, block);
        RegistryHandler.ITEMS.register(name, () -> new BlockItem(ret.get(), new Item.Properties().tab(LostHeroes.lh_group)));
        return ret;
    }
}

package com.greenone.lostheroes.common.blocks;

import com.greenone.lostheroes.LostHeroes;
import com.greenone.lostheroes.common.enums.Metal;
import com.greenone.lostheroes.common.items.LHItemBlock;
import com.greenone.lostheroes.common.items.LHItems;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class LHBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, LostHeroes.MOD_ID);

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }

    public static <T extends Block> RegistryObject<T> registerNoItem(String name, Supplier<T> block){
        return BLOCKS.register(name, block);
    }

    public static <T extends Block> RegistryObject<T> register(String name, Supplier<T> block){
        RegistryObject<T> ret = registerNoItem(name, block);
        LHItems.ITEMS.register(name, () -> new LHItemBlock(ret.get(), new Item.Properties().tab(LostHeroes.lh_group)));
        return ret;
    }

    public static <T extends Block> RegistryObject<T> register(String name, Supplier<T> block, Metal metal){
        RegistryObject<T> ret = registerNoItem(name, block);
        LHItems.ITEMS.register(name, () -> new LHItemBlock(ret.get(), new Item.Properties().tab(LostHeroes.lh_group), metal));
        return ret;
    }
}

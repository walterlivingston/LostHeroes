package com.greenone.lostheroes.common.items;

import com.greenone.lostheroes.LostHeroes;
import com.greenone.lostheroes.common.util.RegistryHandler;
import net.minecraft.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class LHItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, LostHeroes.MOD_ID);

    public static final RegistryObject<Item> TEST_ITEM = ITEMS.register("test_item", () ->
            new Item(new Item.Properties().tab(LostHeroes.lh_group)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}

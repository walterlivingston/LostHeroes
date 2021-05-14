package com.greenone.lostheroes.common.items;

import com.greenone.lostheroes.LostHeroes;
import com.greenone.lostheroes.common.util.RegistryHandler;
import net.minecraft.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;

public class LHItems {
    public static final RegistryObject<Item> TEST_ITEM = RegistryHandler.ITEMS.register("test_item", () ->
            new Item(new Item.Properties().tab(LostHeroes.lh_group)));

    public static void register(IEventBus eventBus) {
        RegistryHandler.ITEMS.register(eventBus);
    }
}

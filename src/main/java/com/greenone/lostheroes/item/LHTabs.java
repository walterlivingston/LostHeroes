package com.greenone.lostheroes.item;

import com.greenone.lostheroes.LostHeroes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = LostHeroes.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class LHTabs {
    public static CreativeModeTab ITEMS_TAB;

    @SubscribeEvent
    public static void registerTabs(CreativeModeTabEvent.Register event){
        ITEMS_TAB = register(event, "lh_items_tab", Items.IRON_INGOT);
    }

    private static CreativeModeTab register(CreativeModeTabEvent.Register event, String name, Item icon){
        return event.registerCreativeModeTab(new ResourceLocation(LostHeroes.MOD_ID, name),
                builder -> builder.icon(() -> new ItemStack(icon))
                        .title(Component.translatable("createivemodetab." + name)));
    }
}

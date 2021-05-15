package com.greenone.lostheroes.common.items;

import com.greenone.lostheroes.LostHeroes;
import com.greenone.lostheroes.common.enums.Metal;
import com.greenone.lostheroes.common.util.RegistryHandler;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.Map;

public class LHItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, LostHeroes.MOD_ID);
    public static final DeferredRegister<Item> VANILLA_ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, "minecraft");

    public static Map<Metal, Item> ingots = new HashMap<>();
    public static Map<Metal, Item> nuggets = new HashMap<>();

    public static Map<Metal, Item> swords = new HashMap<>();
    public static Map<Metal, Item> picks = new HashMap<>();
    public static Map<Metal, Item> axes = new HashMap<>();
    public static Map<Metal, Item> shovels = new HashMap<>();
    public static Map<Metal, Item> hoes = new HashMap<>();
    public static Map<Metal, Item> bows = new HashMap<>();
    public static Map<Metal, Item> spears = new HashMap<>();
    public static Map<Metal, Item> shields = new HashMap<>();

    public static Map<Metal, Item> helmets = new HashMap<>();
    public static Map<Metal, Item> chestplates = new HashMap<>();
    public static Map<Metal, Item> leggings = new HashMap<>();
    public static Map<Metal, Item> boots = new HashMap<>();


    public static void register(IEventBus eventBus) {
        for(Metal m : Metal.values()){
            if(m.isVanilla()){
                ingots.put(m, registerVanillaItem(m.tagName()+"_ingot", ItemGroup.TAB_MATERIALS, m));
                nuggets.put(m, registerVanillaItem(m.tagName()+"_nugget", ItemGroup.TAB_MATERIALS, m));
            }else{
                ingots.put(m, registerItem(m.tagName()+"_ingot", m));
                nuggets.put(m, registerItem(m.tagName()+"_nugget", m));
            }
        }

        ITEMS.register(eventBus);
    }

    private static Item registerItem(String name){
        Item item = new LHItem(new Item.Properties().tab(LostHeroes.lh_group));
        ITEMS.register(name, () -> item);
        return item;
    }
    private static Item registerItem(String name, Metal metal){
        Item item = new LHItem(metal, new Item.Properties().tab(LostHeroes.lh_group));
        ITEMS.register(name, () -> item);
        return item;
    }
    private static Item registerItem(String name, Item item){
        ITEMS.register(name, () -> item);
        return item;
    }
    private static Item registerVanillaItem(String name, ItemGroup group){
        Item item = new LHItem(new Item.Properties().tab(group));
        VANILLA_ITEMS.register(name, () -> item);
        return item;
    }
    private static Item registerVanillaItem(String name, ItemGroup group, Metal metal){
        Item item = new LHItem(metal, new Item.Properties().tab(group));
        VANILLA_ITEMS.register(name, () -> item);
        return item;
    }
}

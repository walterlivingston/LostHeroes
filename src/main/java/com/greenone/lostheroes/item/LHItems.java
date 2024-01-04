package com.greenone.lostheroes.item;

import com.greenone.lostheroes.LostHeroes;
import com.greenone.lostheroes.material.LHMaterial;
import com.greenone.lostheroes.material.LHMaterials;
import net.minecraft.world.item.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.function.Supplier;

public class LHItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, LostHeroes.MOD_ID);
    public static final HashMap<String, RegistryObject<Item>> raw = new HashMap<>();
    public static final HashMap<String, RegistryObject<Item>> ingots = new HashMap<>();
    public static final HashMap<String, RegistryObject<Item>> pickaxes = new HashMap<>();
    public static final HashMap<String, RegistryObject<Item>> axes = new HashMap<>();
    public static final HashMap<String, RegistryObject<Item>> shovels = new HashMap<>();
    public static final HashMap<String, RegistryObject<Item>> swords = new HashMap<>();
    public static final HashMap<String, RegistryObject<Item>> hoes = new HashMap<>();
    public static final HashMap<String, RegistryObject<Item>> helmets = new HashMap<>();
    public static final HashMap<String, RegistryObject<Item>> chestplates = new HashMap<>();
    public static final HashMap<String, RegistryObject<Item>> leggings = new HashMap<>();
    public static final HashMap<String, RegistryObject<Item>> boots = new HashMap<>();

    public static void init(){
        for(LHMaterial mat : LHMaterials.materials.values()){
            if(!mat.isVanilla() || mat.isCopper()){
                if(mat.isMetal()){
                    if(!mat.isCopper()){
                        // Ingots, Raw
//                        raw.put(mat.getTagName(), registerItem("raw_" + mat.getTagName(), () -> new Item(new Item.Properties())));
                        ingots.put(mat.getTagName(), registerItem(mat.getTagName() + "_ingot", () -> new Item(new Item.Properties())));
                    }
                    // Pickaxes, Axes, Shovels, Swords, Hoes
                    pickaxes.put(mat.getTagName(), registerItem(mat.getTagName() + "_pickaxe", () -> new PickaxeItem(mat.getTier(), 1, -2.8F, new Item.Properties())));
                    axes.put(mat.getTagName(), registerItem(mat.getTagName() + "_axe", () -> new AxeItem(mat.getTier(), 6.0F, -3.1F, new Item.Properties())));
                    shovels.put(mat.getTagName(), registerItem(mat.getTagName() + "_shovel", () -> new ShovelItem(mat.getTier(), 1.5F, -3.0F, new Item.Properties())));
                    swords.put(mat.getTagName(), registerItem(mat.getTagName() + "_sword", () -> new SwordItem(mat.getTier(), 3, -2.4F, new Item.Properties())));
                    hoes.put(mat.getTagName(), registerItem(mat.getTagName() + "_hoe", () -> new HoeItem(mat.getTier(), -2, -1.0F, new Item.Properties())));

                    // Armor
                    helmets.put(mat.getTagName(), registerItem(mat.getTagName() + "_helmet", () -> new ArmorItem(mat.getArmorMaterial(), ArmorItem.Type.HELMET, new Item.Properties())));
                    chestplates.put(mat.getTagName(), registerItem(mat.getTagName() + "_chestplate", () -> new ArmorItem(mat.getArmorMaterial(), ArmorItem.Type.CHESTPLATE, new Item.Properties())));
                    leggings.put(mat.getTagName(), registerItem(mat.getTagName() + "_leggings", () -> new ArmorItem(mat.getArmorMaterial(), ArmorItem.Type.LEGGINGS, new Item.Properties())));
                    boots.put(mat.getTagName(), registerItem(mat.getTagName() + "_boots", () -> new ArmorItem(mat.getArmorMaterial(), ArmorItem.Type.BOOTS, new Item.Properties())));
                }else{
                    // Misc
                }
            }else{
                if(mat.isMetal()){
                    // Modded Weapons
                }
            }
        }
    }

    private static <T extends Item> RegistryObject<T> registerItem(String name, Supplier<T> item){
        return ITEMS.register(name, item);
    }

    public static void register(IEventBus eventBus){
        init();
        ITEMS.register(eventBus);
    }
}

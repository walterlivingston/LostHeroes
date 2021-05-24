package com.greenone.lostheroes.common.items;

import com.greenone.lostheroes.LostHeroes;
import com.greenone.lostheroes.client.render.ShieldRenderer;
import com.greenone.lostheroes.common.enums.Metal;
import com.greenone.lostheroes.common.items.tools.*;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraftforge.eventbus.api.IEventBus;
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
    public static Item invisibility_cap = registerArmor("invisibility_cap", LHArmorMaterial.LEATHER, EquipmentSlotType.HEAD);

    public static Item adamantine_ingot_dull = registerItem("adamantine_ingot_dull");
    public static Item ivlivs_coin = registerItem("ivlivs_coin", new LHItem(Metal.IMPERIAL_GOLD, new Item.Properties().tab(LostHeroes.lh_group).stacksTo(1)));
    public static Item ivlivs_sword = registerItem("ivlivs_sword", new LHSword(LHItemTier.IMPERIAL_GOLD, 3, -2.4F, new Item.Properties(), Metal.IMPERIAL_GOLD));
    public static Item ivlivs_spear = registerItem("ivlivs_spear", new LHSpear(Metal.IMPERIAL_GOLD, new Item.Properties()));
    public static Item anaklusmos_pen = registerItem("anaklusmos_pen", new LHItem(new Item.Properties().tab(LostHeroes.lh_group).stacksTo(1)));
    public static Item anaklusmos_sword = registerItem("anaklusmos_sword", new LHSword(LHItemTier.CELESTIAL_BRONZE, 3, -2.4F, new Item.Properties(), Metal.CELESTIAL_BRONZE));
    public static Item pearl_of_persephone = registerItem("pearl_of_persephone");
    public static Item aegis = registerShield("aegis", Metal.IMPERIAL_GOLD);
    public static Item katoptris = registerKnife("katoptris", LHItemTier.CELESTIAL_BRONZE, 3, -2.4F, Metal.CELESTIAL_BRONZE);
    public static Item backbiter = registerSword("backbiter", LHItemTier.CELESTIAL_BRONZE, 3, -2.4F, Metal.CELESTIAL_BRONZE);

    public static Item ambrosia = registerFood("ambrosia", LHFoods.AMBROSIA, true);
    public static Item nectar = registerItem("nectar", new LHFood(new Item.Properties().tab(LostHeroes.lh_group).food(LHFoods.NECTAR), true));

    public static Item vanilla_bow = registerVanillaItem("bow", new LHBow(ItemTier.WOOD, new Item.Properties().tab(ItemGroup.TAB_COMBAT)));

    //public static Item greek_fire = registerItem("greek_fire", new GreekFireItem(new Item.Properties().tab(LostHeroes.lh_group).stacksTo(1)));

    public static void register(IEventBus eventBus) {
        for(Metal m : Metal.values()){
            if(m.isVanilla()){
                ingots.put(m, registerVanillaItem(m.tagName()+"_ingot", ItemGroup.TAB_MATERIALS, m));
                nuggets.put(m, registerVanillaItem(m.tagName()+"_nugget", ItemGroup.TAB_MATERIALS, m));
            }else{
                ingots.put(m, registerItem(m.tagName()+"_ingot", m));
                nuggets.put(m, registerItem(m.tagName()+"_nugget", m));
                swords.put(m, registerSword(m.tagName()+"_sword", m.getTier(), 3, -2.4F, m));
                picks.put(m, registerPick(m.tagName()+"_pickaxe", m.getTier(),1, -2.8F, m));
                axes.put(m, registerAxe(m.tagName()+"_axe", m.getTier(),6, -3.2F, m));
                shovels.put(m, registerShovel(m.tagName()+"_shovel", m.getTier(),1.5F, -3F, m));
                hoes.put(m, registerHoe(m.tagName()+"_hoe", m.getTier(),-2, -1.0F, m));
                bows.put(m, registerBow(m.tagName()+"_bow", m));
                spears.put(m, registerSpear(m.tagName()+"_spear", m));
                shields.put(m, registerShield(m.tagName()+"_shield", m));
                helmets.put(m, registerArmor(m.tagName()+"_helmet", m.getArmor(), EquipmentSlotType.HEAD, m));
                chestplates.put(m, registerArmor(m.tagName()+"_chestplate", m.getArmor(), EquipmentSlotType.CHEST, m));
                leggings.put(m, registerArmor(m.tagName()+"_leggings", m.getArmor(), EquipmentSlotType.LEGS, m));
                boots.put(m, registerArmor(m.tagName()+"_boots", m.getArmor(), EquipmentSlotType.FEET, m));
            }
        }
        ITEMS.register(eventBus);
        VANILLA_ITEMS.register(eventBus);
    }

    private static Item registerItem(String name, Item item){
        ITEMS.register(name, () -> item);
        return item;
    }
    private static Item registerItem(String name){
        Item item = new LHItem(new Item.Properties().tab(LostHeroes.lh_group));
        return registerItem(name, item);
    }
    private static Item registerItem(String name, Metal metal){
        Item item = new LHItem(metal, new Item.Properties().tab(LostHeroes.lh_group));
        return registerItem(name, item);
    }
    private static Item registerFood(String name, Food food, boolean isGodly){
        Item item = new LHFood(food, isGodly);
        return registerItem(name, item);
    }
    private static Item registerVanillaItem(String name, Item item){
        VANILLA_ITEMS.register(name, () -> item);
        return item;
    }
    private static Item registerVanillaItem(String name, ItemGroup group, Metal metal){
        Item item = new LHItem(metal, new Item.Properties().tab(group));
        return registerVanillaItem(name, item);
    }
    private static Item registerSword(String name, IItemTier tier, int damage, float speed, Metal metal) {
        Item item = new LHSword(tier, damage, speed, new Item.Properties().tab(LostHeroes.lh_group), metal);
        return registerItem(name, item);
    }
    private static Item registerPick(String name, IItemTier tier, int damage, float speed, Metal metal) {
        Item item = new LHPick(tier, damage, speed, new Item.Properties().tab(LostHeroes.lh_group), metal);
        return registerItem(name, item);
    }
    private static Item registerAxe(String name, IItemTier tier, float damage, float speed, Metal metal) {
        Item item = new LHAxe(tier, damage, speed, new Item.Properties().tab(LostHeroes.lh_group), metal);
        return registerItem(name, item);
    }
    private static Item registerShovel(String name, IItemTier tier, float damage, float speed, Metal metal) {
        Item item = new LHShovel(tier, damage, speed, new Item.Properties().tab(LostHeroes.lh_group), metal);
        return registerItem(name, item);
    }
    private static Item registerHoe(String name, IItemTier tier, int damage, float speed, Metal metal) {
        Item item = new LHHoe(tier, damage, speed, new Item.Properties().tab(LostHeroes.lh_group), metal);
        return registerItem(name, item);
    }
    private static Item registerBow(String name, Metal metal) {
        Item item = new LHBow(metal, new Item.Properties().tab(LostHeroes.lh_group));
        return registerItem(name, item);
    }
    private static Item registerSpear(String name, Metal metal) {
        Item item = new LHSpear(metal, new Item.Properties().tab(LostHeroes.lh_group));
        return registerItem(name, item);
    }
    private static Item registerShield(String name, Metal metal) {
        Item item = new LHShield(metal, new Item.Properties().tab(LostHeroes.lh_group).setISTER(()-> ShieldRenderer::new));
        return registerItem(name, item);
    }
    private static Item registerKnife(String name, IItemTier tier, int damage, float speed, Metal metal) {
        Item item = new LHKnife(tier, damage, speed, new Item.Properties().tab(LostHeroes.lh_group), metal);
        return registerItem(name, item);
    }
    private static Item registerArmor(String name, IArmorMaterial material, EquipmentSlotType slot, Metal metal) {
        Item item = new LHArmorItem(material, slot, new Item.Properties().tab(LostHeroes.lh_group), metal);
        return registerItem(name, item);
    }
    private static Item registerArmor(String name, IArmorMaterial material, EquipmentSlotType slot) {
        Item item = new LHArmorItem(material, slot, new Item.Properties().tab(LostHeroes.lh_group));
        return registerItem(name, item);
    }
}

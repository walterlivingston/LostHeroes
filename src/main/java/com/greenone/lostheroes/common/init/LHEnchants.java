package com.greenone.lostheroes.common.init;

import com.greenone.lostheroes.LostHeroes;
import com.greenone.lostheroes.common.enchantment.*;
import com.greenone.lostheroes.common.enums.Metal;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class LHEnchants {
    private static final DeferredRegister<Enchantment> ENCHANTMENT = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, LostHeroes.MOD_ID);
    private static final EquipmentSlot[] ARMOR_SLOTS = new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};
    public static final EnchantmentCategory METAL = EnchantmentCategory.create("metal", (item) -> {
        if(item!=null){
            if(item == LHItems.ingots.get(Metal.BRONZE) || item==LHItems.ingots.get(Metal.GOLD) || item==LHItems.adamantine_ingot_dull)
                return true;
            if(item == LHBlocks.storageBlocks.get(Metal.BRONZE).asItem() || item==LHBlocks.storageBlocks.get(Metal.GOLD).asItem())
                return true;
            if(item == LHItems.nuggets.get(Metal.BRONZE) || item==LHItems.nuggets.get(Metal.GOLD))
                return true;
        }
        return false;
    });

    public static final Enchantment BLESSING = register("blessing", new BlessingEnchant());

    public static final Enchantment THUNDER_STRIKE = register("thunder_strike", new ThunderStrikeEnchantment(Enchantment.Rarity.RARE, EnchantmentCategory.WEAPON, EquipmentSlot.MAINHAND));
    public static final Enchantment REHYDRATION = register("rehydration", new RehydrationEnchantment(Enchantment.Rarity.RARE, EnchantmentCategory.ARMOR_HEAD, EquipmentSlot.HEAD));
    public static final Enchantment UNDEAD_PRESENCE = register("undead_presence", new UndeadPresenceEnchantment(Enchantment.Rarity.RARE, EnchantmentCategory.ARMOR_CHEST, EquipmentSlot.CHEST));
    public static final Enchantment BRILLIANT_RIPOSTE = register("brilliant_riposte", new BrilliantRiposteEnchantment(Enchantment.Rarity.RARE, EnchantmentCategory.WEAPON, EquipmentSlot.MAINHAND));
    public static final Enchantment BRUTISH = register("brutish", new BrutishEnchantment(Enchantment.Rarity.RARE, EnchantmentCategory.WEAPON, EquipmentSlot.MAINHAND));
    public static final Enchantment UNREQUITED = register("unrequited", new UnrequitedEnchantment(Enchantment.Rarity.RARE, EnchantmentCategory.BOW, EquipmentSlot.MAINHAND));
    public static final Enchantment POISON_VOLLEY = register("poison_volley", new PoisonVolleyEnchantment(Enchantment.Rarity.RARE, EnchantmentCategory.BOW, EquipmentSlot.MAINHAND));
    public static final Enchantment PRECISION = register("precision", new PrecisionEnchantment(Enchantment.Rarity.RARE, EnchantmentCategory.BOW, EquipmentSlot.MAINHAND));
    public static final Enchantment LEAF_STEP = register("leaf_step", new LeafStepEnchantment(Enchantment.Rarity.RARE, EnchantmentCategory.ARMOR_FEET, EquipmentSlot.FEET));
    public static final Enchantment VINTAGE = register("vintage", new VintageEnchantment(Enchantment.Rarity.RARE, EnchantmentCategory.ARMOR_HEAD, EquipmentSlot.HEAD));
    public static final Enchantment FLEET = register("fleet", new FleetEnchantment(Enchantment.Rarity.RARE, EnchantmentCategory.ARMOR_FEET, EquipmentSlot.FEET));
    public static final Enchantment DAEDALUS_ASPECT = register("daedalus_aspect", new DaedalusAspectEnchantment(Enchantment.Rarity.RARE, EnchantmentCategory.ARMOR, ARMOR_SLOTS));

    public static void register(IEventBus eventBus){
        ENCHANTMENT.register(eventBus);
    }

    private static Enchantment register(String name, Enchantment enchant){
        ENCHANTMENT.register(name, () -> enchant);
        return enchant;
    }
}

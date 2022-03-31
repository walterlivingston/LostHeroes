package com.greenone.lostheroes.common.enchantment;

import com.greenone.lostheroes.LostHeroes;
import com.greenone.lostheroes.common.blocks.LHBlocks;
import com.greenone.lostheroes.common.enums.Metal;
import com.greenone.lostheroes.common.items.LHItems;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class LHEnchants {
    private static final DeferredRegister<Enchantment> ENCHANTMENT = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, LostHeroes.MOD_ID);
    private static final EquipmentSlotType[] ARMOR_SLOTS = new EquipmentSlotType[]{EquipmentSlotType.HEAD, EquipmentSlotType.CHEST, EquipmentSlotType.LEGS, EquipmentSlotType.FEET};
    public static final EnchantmentType METAL = EnchantmentType.create("metal", (item) -> {
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

    public static final Enchantment THUNDER_STRIKE = register("thunder_strike", new ThunderStrikeEnchantment(Enchantment.Rarity.VERY_RARE, EnchantmentType.WEAPON, EquipmentSlotType.MAINHAND));
    public static final Enchantment REHYDRATION = register("rehydration", new RehydrationEnchantment(Enchantment.Rarity.VERY_RARE, EnchantmentType.ARMOR_HEAD, EquipmentSlotType.HEAD));
    public static final Enchantment UNDEAD_PRESENCE = register("undead_presence", new UndeadPresenceEnchantment(Enchantment.Rarity.VERY_RARE, EnchantmentType.ARMOR_CHEST, EquipmentSlotType.CHEST));
    public static final Enchantment BRILLIANT_RIPOSTE = register("brilliant_riposte", new BrilliantRiposteEnchantment(Enchantment.Rarity.VERY_RARE, EnchantmentType.WEAPON, EquipmentSlotType.MAINHAND));
    public static final Enchantment BRUTISH = register("brutish", new BrutishEnchantment(Enchantment.Rarity.VERY_RARE, EnchantmentType.WEAPON, EquipmentSlotType.MAINHAND));
    public static final Enchantment UNREQUITED = register("unrequited", new UnrequitedEnchantment(Enchantment.Rarity.VERY_RARE, EnchantmentType.BOW, EquipmentSlotType.MAINHAND));
    public static final Enchantment POISON_VOLLEY = register("poison_volley", new PoisonVolleyEnchantment(Enchantment.Rarity.VERY_RARE, EnchantmentType.BOW, EquipmentSlotType.MAINHAND));
    public static final Enchantment PRECISION = register("precision", new PrecisionEnchantment(Enchantment.Rarity.VERY_RARE, EnchantmentType.BOW, EquipmentSlotType.MAINHAND));
    public static final Enchantment LEAF_STEP = register("leaf_step", new LeafStepEnchantment(Enchantment.Rarity.VERY_RARE, EnchantmentType.ARMOR_FEET, EquipmentSlotType.FEET));
    public static final Enchantment VINTAGE = register("vintage", new VintageEnchantment(Enchantment.Rarity.VERY_RARE, EnchantmentType.ARMOR_HEAD, EquipmentSlotType.HEAD));
    public static final Enchantment FLEET = register("fleet", new FleetEnchantment(Enchantment.Rarity.VERY_RARE, EnchantmentType.ARMOR_FEET, EquipmentSlotType.FEET));
    public static final Enchantment DAEDALUS_ASPECT = register("daedalus_aspect", new DaedalusAspectEnchantment(Enchantment.Rarity.VERY_RARE, EnchantmentType.ARMOR, ARMOR_SLOTS));

    public static void register(IEventBus eventBus){
        ENCHANTMENT.register(eventBus);
    }

    private static Enchantment register(String name, Enchantment enchant){
        ENCHANTMENT.register(name, () -> enchant);
        return enchant;
    }
}

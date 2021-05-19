package com.greenone.lostheroes.common.enchantment;

import com.greenone.lostheroes.LostHeroes;
import com.greenone.lostheroes.common.blocks.LHBlocks;
import com.greenone.lostheroes.common.enums.Metal;
import com.greenone.lostheroes.common.items.LHItems;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class LHEnchants {
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



    public static final DeferredRegister<Enchantment> ENCHANTMENT = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, LostHeroes.MOD_ID);

    public static final Enchantment BLESSING = register("blessing", new BlessingEnchant());

    public static void register(IEventBus eventBus){
        ENCHANTMENT.register(eventBus);
    }

    private static Enchantment register(String name, Enchantment enchant){
        ENCHANTMENT.register(name, () -> enchant);
        return enchant;
    }
}

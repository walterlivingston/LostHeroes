package com.greenone.lostheroes.common.entities.villager;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.greenone.lostheroes.LostHeroes;
import com.greenone.lostheroes.common.blocks.LHBlocks;
import com.greenone.lostheroes.common.blocks.PillarBlock;
import com.greenone.lostheroes.common.enums.Metal;
import com.greenone.lostheroes.common.enums.Stone;
import com.greenone.lostheroes.common.items.LHItems;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.MerchantOffer;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.village.PointOfInterestType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class VillagerInit {
    public static final DeferredRegister<PointOfInterestType> POI_TYPES = DeferredRegister.create(ForgeRegistries.POI_TYPES, LostHeroes.MOD_ID);
    public static final DeferredRegister<VillagerProfession> PROFESSIONS = DeferredRegister.create(ForgeRegistries.PROFESSIONS, LostHeroes.MOD_ID);

    public static final BlockState ALTAR_STATE_1 = LHBlocks.pillars.get(Stone.MARBLE).defaultBlockState().setValue(PillarBlock.IS_ALTAR, true);
    public static final BlockState ALTAR_STATE_2 = LHBlocks.pillars.get(Stone.BLACK_MARBLE).defaultBlockState().setValue(PillarBlock.IS_ALTAR, true);
    public static final Set<BlockState> ALTAR_STATES = ImmutableSet.copyOf(new BlockState[]{ALTAR_STATE_1, ALTAR_STATE_2});

    public static final PointOfInterestType BLACKSMITH_POI = new PointOfInterestType("blacksmith", PointOfInterestType.getBlockStates(Blocks.ANVIL),1 ,1);
    public static final PointOfInterestType PRIEST_POI = new PointOfInterestType("priest", ALTAR_STATES,1 ,1);
    public static final PointOfInterestType WINEMAKER_POI = new PointOfInterestType("oracle", PointOfInterestType.getBlockStates(LHBlocks.cask),1 ,1);


    public static final VillagerProfession BLACKSMITH = registerProf("blacksmith", BLACKSMITH_POI, SoundEvents.VILLAGER_WORK_TOOLSMITH);
    public static final VillagerProfession PRIEST = registerProf("priest", PRIEST_POI, SoundEvents.VILLAGER_WORK_LIBRARIAN);
    public static final VillagerProfession WINEMAKER = registerProf("winemaker", WINEMAKER_POI, SoundEvents.VILLAGER_WORK_CLERIC);

    public static void register(IEventBus eventBus){
        POI_TYPES.register(eventBus);
        fillTradeData();
        PROFESSIONS.register(eventBus);
    }

    private static VillagerProfession registerProf(String name, PointOfInterestType poi, SoundEvent soundEvent){
        VillagerProfession prof = new VillagerProfession(name, poi, ImmutableSet.of(), ImmutableSet.of(), soundEvent);
        POI_TYPES.register(name, () -> poi);
        PROFESSIONS.register(name, () -> prof);
        return prof;
    }

    public static void fillTradeData(){
        VillagerTrades.TRADES.put(BLACKSMITH, toIntMap(
                ImmutableMap.of(1, new VillagerTrades.ITrade[]{
                        new MultiItemForDrachmasTrade(ImmutableList.of(Items.GOLD_INGOT, LHItems.ingots.get(Metal.LEAD), LHItems.ingots.get(Metal.SILVER)), ImmutableList.of(10, 10, 10), ImmutableList.of(1,1,1), 3, 2),
                        new DrachmasforMultiItemsTrade(ImmutableList.of(Items.GOLD_INGOT, LHItems.ingots.get(Metal.BRONZE)), ImmutableList.of(3, 2), ImmutableList.of(1,1), 3, 5),
                    }, 2, new VillagerTrades.ITrade[]{
                        new MultiItemForDrachmasTrade(ImmutableList.of(Items.IRON_INGOT, LHItems.ingots.get(Metal.COPPER), LHItems.ingots.get(Metal.TIN)), ImmutableList.of(5, 5, 5), ImmutableList.of(1,1,1), 2, 2),
                })));
    }

    private static Int2ObjectMap<VillagerTrades.ITrade[]> toIntMap(ImmutableMap<Integer, VillagerTrades.ITrade[]> p_221238_0_) {
        return new Int2ObjectOpenHashMap<>(p_221238_0_);
    }

    public static class MultiItemForDrachmasTrade implements VillagerTrades.ITrade{
        private final List<Item> items;
        private final List<Integer> amountOfItems;
        private final List<Integer> amountOfDrachmas;
        private final int uses;
        private final int villagerExp;

        public MultiItemForDrachmasTrade(List<Item> itemsIn, List<Integer> amountOfItemsIn, List<Integer> amountOfDrachmasIn, int usesIn, int villagerExpIn){
            this.items = itemsIn;
            this.amountOfItems = amountOfItemsIn;
            this.amountOfDrachmas = amountOfDrachmasIn;
            this.uses = usesIn;
            this.villagerExp = villagerExpIn;
        }

        @Nullable
        @Override
        public MerchantOffer getOffer(Entity entity, Random random) {
            int choose = (int) (random.nextFloat() * items.size());
            return new MerchantOffer(new ItemStack(items.get(choose), this.amountOfItems.get(choose)), new ItemStack(LHItems.drachma, amountOfDrachmas.get(choose)), this.uses, this.villagerExp, 0.05f);
        }
    }

    public static class DrachmasforMultiItemsTrade implements VillagerTrades.ITrade{
        private final List<Item> items;
        private final List<Integer> amountOfItems;
        private final List<Integer> amountOfDrachmas;
        private final int uses;
        private final int villagerExp;

        public DrachmasforMultiItemsTrade(List<Item> itemsIn, List<Integer> amountOfDrachmasIn, List<Integer> amountOfItemsIn, int usesIn, int villagerExpIn){
            this.items = itemsIn;
            this.amountOfItems = amountOfItemsIn;
            this.amountOfDrachmas = amountOfDrachmasIn;
            this.uses = usesIn;
            this.villagerExp = villagerExpIn;
        }

        @Nullable
        @Override
        public MerchantOffer getOffer(Entity entity, Random random) {
            int choose = (int) (random.nextFloat() * items.size());
            return new MerchantOffer(new ItemStack(LHItems.drachma, amountOfDrachmas.get(choose)), new ItemStack(items.get(choose), this.amountOfItems.get(choose)), this.uses, this.villagerExp, 0.05f);
        }
    }
}

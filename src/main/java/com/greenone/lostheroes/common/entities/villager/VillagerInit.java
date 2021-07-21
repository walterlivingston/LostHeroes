package com.greenone.lostheroes.common.entities.villager;

import com.google.common.collect.ImmutableSet;
import com.greenone.lostheroes.LostHeroes;
import com.greenone.lostheroes.common.blocks.LHBlocks;
import com.greenone.lostheroes.common.blocks.PillarBlock;
import com.greenone.lostheroes.common.enums.Stone;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.util.SoundEvents;
import net.minecraft.village.PointOfInterestType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Set;

public class VillagerInit {
    public static final DeferredRegister<PointOfInterestType> POI_TYPES = DeferredRegister.create(ForgeRegistries.POI_TYPES, LostHeroes.MOD_ID);
    public static final DeferredRegister<VillagerProfession> PROFESSIONS = DeferredRegister.create(ForgeRegistries.PROFESSIONS, LostHeroes.MOD_ID);

    public static final BlockState ALTAR_STATE_1 = LHBlocks.pillars.get(Stone.MARBLE).defaultBlockState().setValue(PillarBlock.IS_ALTAR, true);
    public static final BlockState ALTAR_STATE_2 = LHBlocks.pillars.get(Stone.BLACK_MARBLE).defaultBlockState().setValue(PillarBlock.IS_ALTAR, true);
    public static final Set<BlockState> ALTAR_STATES = ImmutableSet.copyOf(new BlockState[]{ALTAR_STATE_1, ALTAR_STATE_2});

    public static final PointOfInterestType BLACKSMITH_POI = new PointOfInterestType("blacksmith", PointOfInterestType.getBlockStates(Blocks.ANVIL),1 ,1);
    public static final PointOfInterestType PRIEST_POI = new PointOfInterestType("priest", ALTAR_STATES,1 ,1);

    public static final VillagerProfession BLACKSMITH = new VillagerProfession("blacksmith", BLACKSMITH_POI, ImmutableSet.of(), ImmutableSet.of(), SoundEvents.VILLAGER_WORK_TOOLSMITH);
    public static final VillagerProfession PRIEST = new VillagerProfession("priest", PRIEST_POI, ImmutableSet.of(), ImmutableSet.of(), SoundEvents.VILLAGER_WORK_CLERIC);

    public static void register(IEventBus eventBus){
        POI_TYPES.register("blacksmith", () -> BLACKSMITH_POI);
        POI_TYPES.register("priest", () -> PRIEST_POI);

        PROFESSIONS.register("blacksmith", () -> BLACKSMITH);
        PROFESSIONS.register("priest", () -> PRIEST);

        POI_TYPES.register(eventBus);
        PROFESSIONS.register(eventBus);
    }
}

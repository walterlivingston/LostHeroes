package com.greenone.lostheroes.datagen;

import com.greenone.lostheroes.LostHeroes;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = LostHeroes.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event){
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        generator.addProvider(true, new LHRecipeProvider(packOutput));
        generator.addProvider(true, new LHLangProvider(packOutput, "en_US"));
        generator.addProvider(true, LHLootTableProvider.create(packOutput));
        generator.addProvider(true, new LHBlockStateProvider(packOutput, existingFileHelper));
        generator.addProvider(true, new LHItemModelProvider(packOutput, existingFileHelper));
    }
}

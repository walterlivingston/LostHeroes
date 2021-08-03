package com.greenone.lostheroes.data;

import com.greenone.lostheroes.LostHeroes;
import com.greenone.lostheroes.data.client.LHBlockModelProvider;
import com.greenone.lostheroes.data.client.LHBlockStateProvider;
import com.greenone.lostheroes.data.client.LHItemModelProvider;
import com.greenone.lostheroes.data.client.LHLangProvider;
import com.greenone.lostheroes.data.loot.LHLootTableProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = LostHeroes.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class DataGenerators {
    private DataGenerators(){ }

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event){
        DataGenerator gen = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        gen.addProvider(new LHBlockModelProvider(gen, existingFileHelper));
        gen.addProvider(new LHItemModelProvider(gen, existingFileHelper));
        gen.addProvider(new LHBlockStateProvider(gen, existingFileHelper));

        gen.addProvider(new LHLootTableProvider(gen));

        LHBlockTagsProvider blockTags = new LHBlockTagsProvider(gen, existingFileHelper);
        gen.addProvider(new LHItemTagsProvider(gen, blockTags, existingFileHelper));

        gen.addProvider(new LHRecipeProvider(gen));
        gen.addProvider(new LHLangProvider(gen, "en_us"));
    }
}

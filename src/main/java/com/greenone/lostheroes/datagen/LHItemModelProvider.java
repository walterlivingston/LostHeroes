package com.greenone.lostheroes.datagen;

import com.greenone.lostheroes.LostHeroes;
import com.greenone.lostheroes.item.LHItems;
import com.greenone.lostheroes.material.LHMaterial;
import com.greenone.lostheroes.material.LHMaterials;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class LHItemModelProvider extends ItemModelProvider {
    public LHItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, LostHeroes.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        for(LHMaterial mat : LHMaterials.materials.values()){
            System.out.println(mat.getTagName());
            if(!mat.isVanilla() || mat.isCopper()){
                if(mat.isMetal()){
                    if(!mat.isCopper()){
                        // Ingots, Raw
//                        simpleItem(LHItems.raw.get(mat.getTagName()));
                        simpleItem(LHItems.ingots.get(mat.getTagName()));
                    }
                    // Pickaxes, Axes, Shovels, Swords, Hoes
                    handheldItem(LHItems.pickaxes.get(mat.getTagName()));
                    handheldItem(LHItems.axes.get(mat.getTagName()));
                    handheldItem(LHItems.shovels.get(mat.getTagName()));
                    handheldItem(LHItems.swords.get(mat.getTagName()));
                    handheldItem(LHItems.hoes.get(mat.getTagName()));

                    // Armor
                    simpleItem(LHItems.helmets.get(mat.getTagName()));
                    simpleItem(LHItems.chestplates.get(mat.getTagName()));
                    simpleItem(LHItems.leggings.get(mat.getTagName()));
                    simpleItem(LHItems.boots.get(mat.getTagName()));

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

    private ItemModelBuilder simpleItem(RegistryObject<Item> item){
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(LostHeroes.MOD_ID, "item/" + item.getId().getPath()));
    }

    private ItemModelBuilder handheldItem(RegistryObject<Item> item){
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/handheld")).texture("layer0",
                new ResourceLocation(LostHeroes.MOD_ID, "item/" + item.getId().getPath()));
    }
}

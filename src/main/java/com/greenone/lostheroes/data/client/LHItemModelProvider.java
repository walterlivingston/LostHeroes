package com.greenone.lostheroes.data.client;

import com.greenone.lostheroes.LostHeroes;
import com.greenone.lostheroes.common.enums.Metal;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nonnull;

public class LHItemModelProvider extends ItemModelProvider {
    public LHItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, LostHeroes.MOD_ID, existingFileHelper);
    }

    @Nonnull
    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    protected void registerModels() {
        // withExistingParent("test_block", modLoc("block/test_block"));

        ModelFile itemGenerated = getExistingFile(mcLoc("item/generated"));

        for(Metal m : Metal.values()) {
            if(m.isVanilla()){

            }else{
                builder(itemGenerated, m.tagName()+"_ingot");
                builder(itemGenerated, m.tagName()+"_nugget");
                builder(itemGenerated, m.tagName()+"_sword");
                builder(itemGenerated, m.tagName()+"_pickaxe");
                builder(itemGenerated, m.tagName()+"_axe");
                builder(itemGenerated, m.tagName()+"_shovel");
                builder(itemGenerated, m.tagName()+"_hoe");
                builder(itemGenerated, m.tagName()+"_spear");
                builder(itemGenerated, m.tagName()+"_helmet");
                builder(itemGenerated, m.tagName()+"_chestplate");
                builder(itemGenerated, m.tagName()+"_leggings");
                builder(itemGenerated, m.tagName()+"_boots");
            }
        }

        builder(itemGenerated, "adamantine_ingot_dull");
        builder(itemGenerated, "invisibility_cap");
        builder(itemGenerated, "anaklusmos_pen");
        builder(itemGenerated, "anaklusmos_sword");
        builder(itemGenerated, "ivlivs_coin");
        builder(itemGenerated, "ivlivs_sword");
        builder(itemGenerated, "ivlivs_spear");
        builder(itemGenerated, "backbiter");
        builder(itemGenerated, "katoptris");
        builder(itemGenerated, "ambrosia");
        builder(itemGenerated, "nectar");
        builder(itemGenerated, "pearl_of_persephone");
        builder(itemGenerated, "greek_fire");
    }

    private ItemModelBuilder builder(ModelFile itemGenerated, String name) {
        return getBuilder(name).parent(itemGenerated).texture("layer0", "item/" + name);
    }
}

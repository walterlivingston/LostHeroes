package com.greenone.lostheroes.data.client;

import com.greenone.lostheroes.LostHeroes;
import com.greenone.lostheroes.common.enums.Metal;
import com.greenone.lostheroes.common.enums.Stone;
import com.greenone.lostheroes.common.enums.Wood;
import net.minecraft.core.Direction;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.BlockModelProvider;
import net.minecraftforge.client.model.generators.ModelBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;

public class LHBlockModelProvider extends BlockModelProvider {

    public LHBlockModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, LostHeroes.MOD_ID, existingFileHelper);
    }

    @Override
    public String getName() {
        return "LostHeroes - Block Models";
    }

    @Override
    protected void registerModels() {
        for(Metal m : Metal.values()){
            if(m.isVanilla()){

            }else {
                singleTexture(m.tagName() + "_block", modLoc("block/" + m.tagName() + "_block"), modLoc("block/" + m.tagName() + "_block"));
                if (m.generateOre()) {
                    singleTexture(m.tagName() + "_ore", modLoc("block/" + m.tagName() + "_ore"), modLoc("block/" + m.tagName() + "_ore"));
                }
            }
        }
        for(Stone s : Stone.values()){
            singleTexture(s.tagName(), modLoc("block/" + s.tagName()), modLoc("block/" + s.tagName()));
            slab(s.tagName()+"_slab", modLoc("block/"+s.tagName()), modLoc("block/"+s.tagName()), modLoc("block/"+s.tagName()));
            stairs(s.tagName()+"_stair", modLoc("block/"+s.tagName()), modLoc("block/"+s.tagName()), modLoc("block/"+s.tagName()));
            cubeAll(s.tagName()+"_brick", modLoc("block/"+s.tagName()+"_brick"));
            slab(s.tagName()+"_brick_slab", modLoc("block/"+s.tagName()+"_brick"), modLoc("block/"+s.tagName()+"_brick"), modLoc("block/"+s.tagName()+"_brick"));
            stairs(s.tagName()+"_brick_stair", modLoc("block/"+s.tagName()+"_brick"), modLoc("block/"+s.tagName()+"_brick"), modLoc("block/"+s.tagName()+"_brick"));
            pillar(s.tagName()+"_pillar");
            pillarTop(s.tagName()+"_pillar");
            pillarMid(s.tagName()+"_pillar");
            pillarBottom(s.tagName()+"_pillar");
        }
        for(Wood w : Wood.values()){
            cubeColumn(w.tagName()+"_log", modLoc("block/" + w.tagName() + "_log"), modLoc("block/" + w.tagName() + "_log_top"));
            cubeColumn("stripped_" + w.tagName()+"_log", modLoc("block/stripped_" + w.tagName() + "_log"), modLoc("block/stripped_" + w.tagName() + "_log_top"));
            cubeAll(w.tagName()+"_planks", modLoc("block/" + w.tagName() + "_planks"));
            button(w.tagName()+"_button", modLoc("block/"+w.tagName()+"_planks"));
            buttonInventory(w.tagName()+"_button_inventory", modLoc("block/"+w.tagName()+"_planks"));
            doorBottomLeft(w.tagName()+"_door", modLoc("block/"+w.tagName()+"_door_bottom"), modLoc("block/"+w.tagName()+"_door_top"));
            doorBottomRight(w.tagName()+"_door", modLoc("block/"+w.tagName()+"_door_bottom"), modLoc("block/"+w.tagName()+"_door_top"));
            doorTopLeft(w.tagName()+"_door", modLoc("block/"+w.tagName()+"_door_bottom"), modLoc("block/"+w.tagName()+"_door_top"));
            doorTopRight(w.tagName()+"_door", modLoc("block/"+w.tagName()+"_door_bottom"), modLoc("block/"+w.tagName()+"_door_top"));
            stairs(w.tagName()+"_stairs", modLoc("block/"+w.tagName()+"_planks"), modLoc("block/"+w.tagName()+"_planks"), modLoc("block/"+w.tagName()+"_planks"));
            slab(w.tagName()+"_slab", modLoc("block/"+w.tagName()+"_planks"), modLoc("block/"+w.tagName()+"_planks"), modLoc("block/"+w.tagName()+"_planks"));
            fencePost(w.tagName()+"_fence", modLoc("block/"+w.tagName()+"_planks"));
            fenceGate(w.tagName()+"_fence_gate", modLoc("block/"+w.tagName()+"_planks"));
            fenceGateOpen(w.tagName()+"_fence_gate", modLoc("block/"+w.tagName()+"_planks"));
            fenceGateWall(w.tagName()+"_fence_gate", modLoc("block/"+w.tagName()+"_planks"));
            fenceGateWallOpen(w.tagName()+"_fence_gate", modLoc("block/"+w.tagName()+"_planks"));
            singleTexture(w.tagName() + "_leaves", mcLoc("block/leaves"), modLoc("block/" + w.tagName() + "_leaves"));
            cross(w.tagName() + "_sapling", modLoc("block/" + w.tagName() + "_sapling"));
        }
        orientable("forge", modLoc(BLOCK_FOLDER+"/forge_top"),modLoc(BLOCK_FOLDER+"/forge_front"),modLoc(BLOCK_FOLDER+"/forge_side"));
        orientable("forge_on", modLoc(BLOCK_FOLDER+"/forge_top"),modLoc(BLOCK_FOLDER+"/forge_front_on"),modLoc(BLOCK_FOLDER+"/forge_side"));
    }

    public ModelBuilder pillar(String name) {
        return getBuilder(name)
                .texture("0", modLoc(BLOCK_FOLDER+"/"+name))
                .texture("1", modLoc(BLOCK_FOLDER+"/"+name+"_bottom"))
                .texture("2", modLoc(BLOCK_FOLDER+"/"+name+"_inner"))
                .texture("3", modLoc(BLOCK_FOLDER+"/"+name+"_updown"))
                .texture("particle", modLoc(BLOCK_FOLDER+"/"+name))
                .element().from(4,1,4).to(12,15,12)
                .face(Direction.NORTH).uvs(2,0,14,16).texture("#0").end()
                .face(Direction.EAST).uvs(2,0,14,16).texture("#0").end()
                .face(Direction.SOUTH).uvs(2,0,14,16).texture("#0").end()
                .face(Direction.WEST).uvs(2,0,14,16).texture("#0").end()
                .face(Direction.UP).uvs(2,0,14,16).texture("#2").end()
                .face(Direction.DOWN).uvs(2,2.125F,14,14.125F).texture("#2").end().end()
                .element().from(2,0,2).to(14,2,14)
                .face(Direction.NORTH).uvs(0,12,16,16).texture("#1").end()
                .face(Direction.EAST).uvs(0,12,16,16).texture("#1").end()
                .face(Direction.SOUTH).uvs(0,12,16,16).texture("#1").end()
                .face(Direction.WEST).uvs(0,12,16,16).texture("#1").end()
                .face(Direction.UP).uvs(0,0,16,16).texture("#3").end()
                .face(Direction.DOWN).uvs(0,0,16,16).texture("#3").end().end()
                .element().from(2,14,2).to(14,16,14)
                .face(Direction.NORTH).uvs(0,12,16,16).texture("#1").end()
                .face(Direction.EAST).uvs(0,12,16,16).texture("#1").end()
                .face(Direction.SOUTH).uvs(0,12,16,16).texture("#1").end()
                .face(Direction.WEST).uvs(0,12,16,16).texture("#1").end()
                .face(Direction.UP).uvs(0,0,16,16).texture("#3").end()
                .face(Direction.DOWN).uvs(0,0,16,16).texture("#3").end().end();
    }

    public ModelBuilder pillarTop(String name) {
        return getBuilder(name+"_top")
                .texture("0", modLoc(BLOCK_FOLDER+"/"+name))
                .texture("1", modLoc(BLOCK_FOLDER+"/"+name+"_bottom"))
                .texture("2", modLoc(BLOCK_FOLDER+"/"+name+"_inner"))
                .texture("3", modLoc(BLOCK_FOLDER+"/"+name+"_updown"))
                .texture("particle", modLoc(BLOCK_FOLDER+"/"+name))
                .element().from(4,0,4).to(12,15,12)
                .face(Direction.NORTH).uvs(2,0,14,16).texture("#0").end()
                .face(Direction.EAST).uvs(2,0,14,16).texture("#0").end()
                .face(Direction.SOUTH).uvs(2,0,14,16).texture("#0").end()
                .face(Direction.WEST).uvs(2,0,14,16).texture("#0").end()
                .face(Direction.UP).uvs(2,2,14,14).texture("#2").end()
                .face(Direction.DOWN).uvs(2,2.125F,14,14.125F).texture("#2").end().end()
                .element().from(2,14,2).to(14,16,14)
                .face(Direction.NORTH).uvs(0,12,16,16).texture("#1").end()
                .face(Direction.EAST).uvs(0,12,16,16).texture("#1").end()
                .face(Direction.SOUTH).uvs(0,12,16,16).texture("#1").end()
                .face(Direction.WEST).uvs(0,12,16,16).texture("#1").end()
                .face(Direction.UP).uvs(0,0,16,16).texture("#3").end()
                .face(Direction.DOWN).uvs(0,0,16,16).texture("#3").end()
                .end();
    }

    public ModelBuilder pillarMid(String name) {
        return getBuilder(name+"_mid")
                .texture("0", modLoc(BLOCK_FOLDER+"/"+name))
                .texture("1", modLoc(BLOCK_FOLDER+"/"+name+"_bottom"))
                .texture("2", modLoc(BLOCK_FOLDER+"/"+name+"_inner"))
                .texture("3", modLoc(BLOCK_FOLDER+"/"+name+"_updown"))
                .texture("particle", modLoc(BLOCK_FOLDER+"/"+name))
                .element().from(4,0,4).to(12,16,12)
                .face(Direction.NORTH).uvs(2,0,14,16).texture("#0").end()
                .face(Direction.EAST).uvs(2,0,14,16).texture("#0").end()
                .face(Direction.SOUTH).uvs(2,0,14,16).texture("#0").end()
                .face(Direction.WEST).uvs(2,0,14,16).texture("#0").end()
                .face(Direction.UP).uvs(2,2,14,14).texture("#2").end()
                .face(Direction.DOWN).uvs(2,2.125F,14,14.125F).texture("#2").end().end();
    }

    public ModelBuilder pillarBottom(String name) {
        return getBuilder(name+"_bottom")
                .texture("0", modLoc(BLOCK_FOLDER+"/"+name))
                .texture("1", modLoc(BLOCK_FOLDER+"/"+name+"_bottom"))
                .texture("2", modLoc(BLOCK_FOLDER+"/"+name+"_inner"))
                .texture("3", modLoc(BLOCK_FOLDER+"/"+name+"_updown"))
                .texture("particle", modLoc(BLOCK_FOLDER+"/"+name))
                .element().from(4,1,4).to(12,16,12)
                .face(Direction.NORTH).uvs(2,0,14,16).texture("#0").end()
                .face(Direction.EAST).uvs(2,0,14,16).texture("#0").end()
                .face(Direction.SOUTH).uvs(2,0,14,16).texture("#0").end()
                .face(Direction.WEST).uvs(2,0,14,16).texture("#0").end()
                .face(Direction.UP).uvs(2,2,14,14).texture("#2").end()
                .face(Direction.DOWN).uvs(2,2.125F,14,14.125F).texture("#2").end().end()
                .element().from(2,0,2).to(14,2,14)
                .face(Direction.NORTH).uvs(0,12,16,16).texture("#1").end()
                .face(Direction.EAST).uvs(0,12,16,16).texture("#1").end()
                .face(Direction.SOUTH).uvs(0,12,16,16).texture("#1").end()
                .face(Direction.WEST).uvs(0,12,16,16).texture("#1").end()
                .face(Direction.UP).uvs(0,0,16,16).texture("#3").end()
                .face(Direction.DOWN).uvs(0,0,16,16).texture("#3").end().end();
    }
}

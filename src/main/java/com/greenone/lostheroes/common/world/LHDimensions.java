package com.greenone.lostheroes.common.world;

import net.minecraft.util.RegistryKey;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class LHDimensions {
    public static final List<RegistryKey<World>> dims = new ArrayList<>();
    public static void init(){
        dims.add(World.END);
        dims.add(World.NETHER);
        dims.add(World.OVERWORLD);
    }
}

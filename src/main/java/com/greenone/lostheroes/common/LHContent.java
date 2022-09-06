package com.greenone.lostheroes.common;

import com.greenone.lostheroes.common.deity.Deities;
import net.minecraftforge.fml.event.lifecycle.ParallelDispatchEvent;

public class LHContent {
    public static void init(ParallelDispatchEvent eventBus){
        Deities.register();
    }
}

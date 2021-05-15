package com.greenone.lostheroes.common.inventory.container;

import com.greenone.lostheroes.LostHeroes;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.util.IWorldPosCallable;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class LHContainers {
    public static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, LostHeroes.MOD_ID);

    public static final ContainerType<PCContainer> PORTABLE_CRAFTER = new ContainerType<>((syncId, playerInv) -> new PCContainer(syncId, playerInv, IWorldPosCallable.create(playerInv.player.level, playerInv.player.blockPosition())));

    public static void register(IEventBus eventBus){
        CONTAINERS.register("portable_crafting", () -> PORTABLE_CRAFTER);

        CONTAINERS.register(eventBus);
    }
}

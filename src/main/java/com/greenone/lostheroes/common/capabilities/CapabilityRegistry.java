package com.greenone.lostheroes.common.capabilities;

import com.greenone.lostheroes.common.init.Deities;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

import javax.annotation.Nullable;

public class CapabilityRegistry {
    @CapabilityInject(IPlayerCap.class)
    public static Capability<IPlayerCap> PLAYERCAP = null;

    public static void registerCapabilities(){
        CapabilityManager.INSTANCE.register(IPlayerCap.class, new PlayerCapStorage(), PlayerCap::new);
    }

    private static class PlayerCapStorage implements Capability.IStorage<IPlayerCap>{
        @Nullable
        @Override
        public INBT writeNBT(Capability<IPlayerCap> capability, IPlayerCap instance, Direction side) {
            CompoundNBT nbt = new CompoundNBT();
            if(instance!=null){
                nbt.putFloat("mana", instance.getMana());
                nbt.putFloat("maxMana", instance.getMaxMana());
                nbt.putString("parent", instance.getParent().getName());
                nbt.putInt("hadesCooldown", instance.getHadesCooldown());
            }
            return nbt;
        }

        @Override
        public void readNBT(Capability<IPlayerCap> capability, IPlayerCap instance, Direction side, INBT nbt) {
            CompoundNBT tag = (CompoundNBT) nbt;

            instance.setMana(tag.getFloat("mana"));
            instance.setMaxMana(tag.getFloat("maxMana"));
            instance.setParent(Deities.list.get(tag.getString("parent")));
            instance.setHadesCooldown(tag.getInt("hadesCooldown"));
        }
    }
}

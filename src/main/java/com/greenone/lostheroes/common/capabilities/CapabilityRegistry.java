package com.greenone.lostheroes.common.capabilities;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class CapabilityRegistry {
    //@CapabilityInject(IPlayerCap.class)
    public static Capability<IPlayerCap> PLAYERCAP = null;

    /*public static void registerCapabilities(){
        CapabilityManager.INSTANCE.register(IPlayerCap.class);
    }*/

    /*private static class PlayerCapStorage implements Capability.IStorage<IPlayerCap>{
        @Nullable
        @Override
        public Tag writeNBT(Capability<IPlayerCap> capability, IPlayerCap instance, Direction side) {
            CompoundTag nbt = new CompoundTag();
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
            CompoundTag tag = (CompoundTag) nbt;

            instance.setMana(tag.getFloat("mana"));
            instance.setMaxMana(tag.getFloat("maxMana"));
            instance.setParent(Deities.list.get(tag.getString("parent")));
            instance.setHadesCooldown(tag.getInt("hadesCooldown"));
        }
    }*/
}

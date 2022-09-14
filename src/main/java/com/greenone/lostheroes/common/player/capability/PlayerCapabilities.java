package com.greenone.lostheroes.common.player.capability;

import com.greenone.lostheroes.LostHeroes;
import com.greenone.lostheroes.common.config.LHConfig;
import com.greenone.lostheroes.common.deity.Deities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = LostHeroes.MODID)
public class PlayerCapabilities {
    @CapabilityInject(IMana.class)
    public static Capability<IMana> MANA_CAPABILITY = null;

    @CapabilityInject(IParent.class)
    public static Capability<IParent> PARENT_CAPABILITY = null;

    public static void register() {
        CapabilityManager.INSTANCE.register(IMana.class, new DefaultManaStorage<>(), Mana::new);
        CapabilityManager.INSTANCE.register(IParent.class, new DefaultParentStorage<>(), Parent::new);
    }

    @SubscribeEvent
    public void onCapabilitiesAttachEntity(AttachCapabilitiesEvent<Entity> event) {
        if(event.getObject() == null) return;
        if(event.getObject() instanceof PlayerEntity) {
            event.addCapability(new ResourceLocation(LostHeroes.MODID, "mana"),
                    new Mana());
            event.addCapability(new ResourceLocation(LostHeroes.MODID, "parent"),
                    new Parent());
        }
    }

    private static class DefaultManaStorage<T extends IMana> implements Capability.IStorage<T> {
        @Override
        public INBT writeNBT(Capability<T> capability, T instance, Direction side) {
            if (!(instance instanceof Mana))
                throw new RuntimeException("Cannot serialize to an instance that isn't the default implementation");
            CompoundNBT nbt = new CompoundNBT();
            Mana manaCap = (Mana) instance;
            nbt.putFloat("mana", manaCap.getMana());
            nbt.putFloat("maxMana", manaCap.getMaxMana());
            return nbt;
        }

        @Override
        public void readNBT(Capability<T> capability, T instance, Direction side, INBT nbt) {
            if (!(instance instanceof Mana))
                throw new RuntimeException("Cannot deserialize to an instance that isn't the default implementation");
            CompoundNBT tags = (CompoundNBT) nbt;
            Mana manaCap = (Mana) instance;
            manaCap.setMana(tags.getFloat("mana"));
        }
    }

    private static class DefaultParentStorage<T extends IParent> implements Capability.IStorage<T> {
        @Override
        public INBT writeNBT(Capability<T> capability, T instance, Direction side) {
            if (!(instance instanceof Parent))
                throw new RuntimeException("Cannot serialize to an instance that isn't the default implementation");
            CompoundNBT nbt = new CompoundNBT();
            Parent parentCap = (Parent) instance;
            nbt.putString("parent", parentCap.getParent().getName());
            return nbt;
        }

        @Override
        public void readNBT(Capability<T> capability, T instance, Direction side, INBT nbt) {
            if (!(instance instanceof Parent))
                throw new RuntimeException("Cannot deserialize to an instance that isn't the default implementation");
            CompoundNBT tags = (CompoundNBT) nbt;
            Parent parentCap = (Parent) instance;
            parentCap.setParent(Deities.getDeity(tags.getString("parent")));
        }
    }
}

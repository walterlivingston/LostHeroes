package com.greenone.lostheroes.common;

import com.google.common.collect.Maps;
import com.greenone.lostheroes.common.entities.abilities.AbstractAbility;
import com.greenone.lostheroes.common.network.PacketAbility;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

import java.util.Map;
import java.util.UUID;

public class Deity {
    private final String name;
    private final Item sacrifice;
    private final MobEffect blessing;
    private final AbstractAbility abilities;
    private Map<Attribute, AttributeModifier> attributeModifiers = Maps.newHashMap();

    public Deity(String nameIn, Item sacrificeIn, MobEffect blessingIn, AbstractAbility abilitiesIn) {
        this.name = nameIn;
        this.sacrifice = sacrificeIn;
        this.blessing = blessingIn;
        this.abilities = abilitiesIn;
    }

    public Deity(String nameIn, Item sacrificeIn, MobEffect blessingIn, AbstractAbility abilitiesIn, Map<Attribute, AttributeModifier> attributeModifierMapIn) {
        this.name = nameIn;
        this.sacrifice = sacrificeIn;
        this.blessing = blessingIn;
        this.abilities = abilitiesIn;
    }

    public String getName() {
        return name;
    }

    public String getFormattedName(){
        return this.getName().substring(0,1).toUpperCase() + this.getName().substring(1);
    }

    public Item getSacrifice() {
        return sacrifice;
    }

    public MobEffect getBlessing() {
        return blessing;
    }

    public void getAbilities(PacketAbility.Type type, Player player) {
        if(type == PacketAbility.Type.MAIN) abilities.mainAbility(player);
        else if(type == PacketAbility.Type.MINOR) abilities.minorAbility(player);
    }

    public AbstractAbility getAbilities(){
        return abilities;
    }

    public Deity addAttributeModifier(Attribute attribute, String uuid, double amplifier, AttributeModifier.Operation operation) {
        AttributeModifier attributemodifier = new AttributeModifier(UUID.fromString(uuid), attribute::getDescriptionId, amplifier, operation);
        this.attributeModifiers.put(attribute, attributemodifier);
        return this;
    }

    public Map<Attribute, AttributeModifier> getAttributeModifiers() {
        return this.attributeModifiers;
    }

    public void removeAttributeModifiers(LivingEntity entity, AttributeMap attMap, int p_19471_) {
        for(Map.Entry<Attribute, AttributeModifier> entry : this.attributeModifiers.entrySet()) {
            AttributeInstance attributeinstance = attMap.getInstance(entry.getKey());
            if (attributeinstance != null) {
                attributeinstance.removeModifier(entry.getValue());
            }
        }

    }

    public void applyAttributesModifiersToEntity(LivingEntity p_19478_, AttributeMap p_19479_, int amplifier) {
        for(Map.Entry<Attribute, AttributeModifier> entry : this.attributeModifiers.entrySet()) {
            AttributeInstance attributeinstance = p_19479_.getInstance(entry.getKey());
            if (attributeinstance != null) {
                AttributeModifier attributemodifier = entry.getValue();
                attributeinstance.removeModifier(attributemodifier);
                attributeinstance.addPermanentModifier(new AttributeModifier(attributemodifier.getId(), attributemodifier.getName() + " " + amplifier, this.getAttributeModifierAmount(amplifier, attributemodifier), attributemodifier.getOperation()));
            }
        }

    }

    public double getAttributeModifierAmount(int amplifier, AttributeModifier modifier){
        return modifier.getAmount() * (double)(amplifier + 1);
    }
}

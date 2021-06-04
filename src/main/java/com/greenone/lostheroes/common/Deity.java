package com.greenone.lostheroes.common;

import com.google.common.collect.Maps;
import com.greenone.lostheroes.common.entities.abilities.AbstractAbility;
import com.greenone.lostheroes.common.network.PacketAbility;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.AttributeModifierManager;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.potion.Effect;

import java.util.Map;
import java.util.UUID;

public class Deity {
    private final String name;
    private final Item sacrifice;
    private final Effect blessing;
    private final AbstractAbility abilities;
    private Map<Attribute, AttributeModifier> attributeModifiers = Maps.newHashMap();

    public Deity(String nameIn, Item sacrificeIn, Effect blessingIn, AbstractAbility abilitiesIn) {
        this.name = nameIn;
        this.sacrifice = sacrificeIn;
        this.blessing = blessingIn;
        this.abilities = abilitiesIn;
    }

    public Deity(String nameIn, Item sacrificeIn, Effect blessingIn, AbstractAbility abilitiesIn, Map<Attribute, AttributeModifier> attributeModifierMapIn) {
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

    public Effect getBlessing() {
        return blessing;
    }

    public void getAbilities(PacketAbility.Type type, PlayerEntity player) {
        if(type == PacketAbility.Type.MAIN) abilities.mainAbility(player);
        else if(type == PacketAbility.Type.MINOR) abilities.minorAbility(player);
    }

    public AbstractAbility getAbilities(){
        return abilities;
    }

    public Deity addAttributeModifier(Attribute attribute, String uuid, double amount, AttributeModifier.Operation operation) {
        AttributeModifier attributemodifier = new AttributeModifier(UUID.fromString(uuid), attribute.getDescriptionId(), amount, operation);
        this.attributeModifiers.put(attribute, attributemodifier);
        return this;
    }

    public Map<Attribute, AttributeModifier> getAttributeModifiers() {
        return this.attributeModifiers;
    }

    public void removeAttributeModifiers(LivingEntity entityLivingBaseIn, AttributeModifierManager attributeMapIn, int amplifier) {
        for(Map.Entry<Attribute, AttributeModifier> entry : this.attributeModifiers.entrySet()) {
            ModifiableAttributeInstance modifiableattributeinstance = attributeMapIn.getInstance(entry.getKey());
            if (modifiableattributeinstance != null) {
                modifiableattributeinstance.removeModifier(entry.getValue());
            }
        }
    }

    public void applyAttributesModifiersToEntity(LivingEntity entityLivingBaseIn, AttributeModifierManager attributeMapIn, int amplifier) {
        for(Map.Entry<Attribute, AttributeModifier> entry : this.attributeModifiers.entrySet()) {
            ModifiableAttributeInstance modifiableattributeinstance = attributeMapIn.getInstance(entry.getKey());
            if (modifiableattributeinstance != null) {
                AttributeModifier attributeModifier = entry.getValue();
                modifiableattributeinstance.removeModifier(attributeModifier);
                modifiableattributeinstance.addPermanentModifier(new AttributeModifier(attributeModifier.getId(), entry.getValue().getName() + " " + amplifier, this.getAttributeModifierAmount(amplifier, attributeModifier), attributeModifier.getOperation()));
            }
        }
    }

    public double getAttributeModifierAmount(int amplifier, AttributeModifier modifier){
        return modifier.getAmount() * (double)(amplifier + 1);
    }
}

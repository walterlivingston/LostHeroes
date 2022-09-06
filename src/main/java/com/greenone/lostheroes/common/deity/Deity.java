package com.greenone.lostheroes.common.deity;

import com.google.common.collect.Maps;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.AttributeModifierManager;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.item.Item;
import net.minecraft.potion.Effect;

import java.util.Map;
import java.util.UUID;

public class Deity {
    private static String name;
    private static Item sacrifice;
    private static Effect blessing;
    private final Map<Attribute, AttributeModifier> attributeModifiers = Maps.newHashMap();

    Deity(String name_, Item sacrifice_, Effect blessing_){
        name = name_;
        sacrifice = sacrifice_;
        blessing = blessing_;
    }

    public static String getName() {
        return name;
    }

    public String getFormattedName(){
        return getName().substring(0,1).toUpperCase() + getName().substring(1);
    }

    public static Item getSacrifice() {
        return sacrifice;
    }

    public static Effect getBlessing() {
        return blessing;
    }

    public Deity addAttributeModifier(Attribute attribute, String uuid, double amount, AttributeModifier.Operation operation) {
        AttributeModifier attributemodifier = new AttributeModifier(UUID.fromString(uuid), attribute.getDescriptionId(), amount, operation);
        this.attributeModifiers.put(attribute, attributemodifier);
        return this;
    }

    public Map<Attribute, AttributeModifier> getAttributeModifiers() {
        return this.attributeModifiers;
    }

    public void removeAttributeModifiers(LivingEntity entity, AttributeModifierManager attModManager) {
        for(Map.Entry<Attribute, AttributeModifier> entry : this.attributeModifiers.entrySet()) {
            ModifiableAttributeInstance modifiableattributeinstance = attModManager.getInstance(entry.getKey());
            if (modifiableattributeinstance != null)
                modifiableattributeinstance.removeModifier(entry.getValue());
        }
    }

    // (TODO) Handle Apollo and Artemis passives here
    public double getAttributeModifierValue(int p_111183_1_, AttributeModifier p_111183_2_) {
        return p_111183_2_.getAmount() * (double)(p_111183_1_ + 1);
    }

    public void applyAttributeModifiers(LivingEntity p_111185_1_, AttributeModifierManager attModManager, int amount) {
        for(Map.Entry<Attribute, AttributeModifier> entry : this.attributeModifiers.entrySet()) {
            ModifiableAttributeInstance modifiableattributeinstance = attModManager.getInstance(entry.getKey());
            if (modifiableattributeinstance != null) {
                AttributeModifier attributemodifier = entry.getValue();
                modifiableattributeinstance.removeModifier(attributemodifier);
                modifiableattributeinstance.addPermanentModifier(new AttributeModifier(attributemodifier.getId(), entry.getValue().getId() + " " + amount, this.getAttributeModifierValue(amount, attributemodifier), attributemodifier.getOperation()));
            }
        }
    }
}

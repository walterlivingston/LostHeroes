package com.greenone.lostheroes.material;

import java.util.HashMap;

public class LHMaterials {
    public static HashMap<String, LHMaterial> materials = new HashMap<>();

    public static final LHMaterial COPPER = registerMaterial("COPPER",
            new LHMaterial.Properties()
                    .metal(LHTiers.COPPER, LHArmorMaterials.COPPER).copper().vanilla());

    public static final LHMaterial TIN = registerMaterial("TIN",
            new LHMaterial.Properties()
                    .metal(LHTiers.TIN, LHArmorMaterials.TIN));

    private static LHMaterial registerMaterial(String name, LHMaterial.Properties props){
        LHMaterial mat = new LHMaterial(name, props);
        materials.put(name, mat);
        return mat;
    }
}
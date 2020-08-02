package com.eosproject.redrealms.file.settings;

import com.eosproject.redrealms.RedRealms;

import java.io.File;
import java.util.HashMap;

public class MaterialsFile extends SettingsFile {

    HashMap<String, Integer> customMaterials = new HashMap<String, Integer>();

    // Constructor
    public MaterialsFile(String YMLFileName) {
        super(YMLFileName);

        /*for (hui.ego.znaet : hui.ego.znaet.noMnogo) {
            customMaterials.put(NID, NBT);
        }*/
    }

    public void materialReading() {

    }
}

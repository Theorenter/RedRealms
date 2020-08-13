package com.eosproject.redrealms.config;

import com.eosproject.redrealms.materials.MaterialCustom;
import com.eosproject.redrealms.materials.MaterialRemover;

import java.util.List;
import java.util.Map;

public class ConfigMaterialManager extends ConfigAbstractSetup {

    public ConfigMaterialManager(String ymlFileName) {
        super(ymlFileName);

        // Loading from config all materials
        List<Map<?, ?>> cfgMaterials = getCustomConfig().getMapList("materials.workbench");
        // Creating materials from materials.yml file
        for (Map<?, ?> m : cfgMaterials) { MaterialCustom materialCustom = new MaterialCustom(m); }

        MaterialRemover.removeMaterials();
    }
}

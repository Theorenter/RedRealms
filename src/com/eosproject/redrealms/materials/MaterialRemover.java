package com.eosproject.redrealms.materials;

import com.eosproject.redrealms.config.ConfigMaterialManager;
import com.eosproject.redrealms.main.RedRealms;
import org.bukkit.NamespacedKey;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class MaterialRemover {
    private static final RedRealms plugin = RedRealms.getPlugin(RedRealms.class);

    public static void removeMaterials() {
        ArrayList<LinkedHashMap> materialsForDelete = (ArrayList<LinkedHashMap>) ConfigMaterialManager.getCustomConfig().get("removed_recipes.keys");
        materialsForDelete.forEach((recipe) -> plugin.getServer().removeRecipe(NamespacedKey.minecraft(recipe.get("key").toString())));
    }

}

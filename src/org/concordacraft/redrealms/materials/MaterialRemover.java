package org.concordacraft.redrealms.materials;

import org.concordacraft.redrealms.config.ConfigMaterialManager;
import org.concordacraft.redrealms.main.RedRealms;
import org.bukkit.NamespacedKey;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class MaterialRemover {

    public static void removeMaterials() {
        ArrayList<LinkedHashMap> materialsForDelete = (ArrayList<LinkedHashMap>) ConfigMaterialManager.getCustomConfig().get("removed_recipes.keys");
        materialsForDelete.forEach((recipe) -> RedRealms.getPlugin().getServer().removeRecipe(NamespacedKey.minecraft(recipe.get("key").toString())));
    }
}

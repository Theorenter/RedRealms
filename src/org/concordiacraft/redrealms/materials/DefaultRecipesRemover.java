package org.concordiacraft.redrealms.materials;

import org.bukkit.Bukkit;
import org.concordiacraft.redrealms.config.ConfigMaterialManager;
import org.bukkit.NamespacedKey;

import java.util.List;

public class DefaultRecipesRemover {

    public static void removeMaterials() {
        List<String> materialsForDelete = (List<String>) ConfigMaterialManager.getCustomConfig().get("removed-recipes.items");
        for (String item : materialsForDelete) {
            Bukkit.removeRecipe(NamespacedKey.minecraft(item));
        }
    }
}

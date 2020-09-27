package org.concordacraft.redrealms.materials;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.concordacraft.redrealms.config.ConfigMaterialManager;
import org.concordacraft.redrealms.main.RedLog;
import org.concordacraft.redrealms.main.RedRealms;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MaterialRecipes {
    public static void createCustomShapedRecipe(Map c) {
        NamespacedKey recipeKey = new NamespacedKey(RedRealms.getPlugin(), (String) c.get("recipe_key"));
        String itemID = (String) c.get("redRealms_ID");

        for (MaterialCustom customMat : ConfigMaterialManager.customMaterials) {
            if (customMat.getRedRealms_ID().equals(itemID)) {
                ItemStack customItem = customMat.getItemStack();
                ShapedRecipe shapedRecipe = new ShapedRecipe(recipeKey, customItem);

                List<Character> craftableCharacters = new ArrayList<>();
                for (int i = 1; i < 10; i++) { craftableCharacters.add((char) i); }
                Map<Integer, String> ingredients = (Map<Integer, String>) c.get("ingredients");
                List<String> recipeShape = (List<String>) c.get("shape");
                shapedRecipe.shape(
                        recipeShape.get(0),
                        recipeShape.get(1),
                        recipeShape.get(2));
                ingredients.forEach((charID, materialNID) -> shapedRecipe.setIngredient(charID.toString().charAt(0), Material.getMaterial(materialNID)));
                Bukkit.addRecipe(shapedRecipe);
            }
        }
    }
    public static void createCustomShapelessRecipe(Map c) {

    }
    public static void createCustomBlastingRecipe(Map c) {

    }
    public static void createCustomCampfireRecipe(Map c) {

    }
    public static void createCustomCookingRecipe(Map c) {

    }
    public static void createCustomSmithingRecipe(Map c) {

    }
    public static void createCustomSmokingRecipe(Map c) {

    }
    public static void createCustomStonecuttingRecipe(Map c) {

    }
}

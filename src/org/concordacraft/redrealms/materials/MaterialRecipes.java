package org.concordacraft.redrealms.materials;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.concordacraft.redrealms.config.ConfigMaterialManager;
import org.concordacraft.redrealms.main.RedLog;
import org.concordacraft.redrealms.main.RedRealms;

import java.util.List;
import java.util.Map;

public class MaterialRecipes {
    public static void createCustomShapedRecipe(Map c) {
        String stringRecipeKey = (String) c.get("recipe_key");
        NamespacedKey recipeKey = new NamespacedKey(RedRealms.getPlugin(), stringRecipeKey);
        String itemID = (String) c.get("redRealms_ID");
        Boolean vReverse = (Boolean) c.get("hasVerticalReverse");

        for (MaterialCustom customMat : ConfigMaterialManager.customMaterials) {
            if (customMat.getRedRealms_ID().equals(itemID)) {

                Map<Integer, String> ingredients = (Map<Integer, String>) c.get("ingredients");
                List<String> recipeShape = (List<String>) c.get("shape");

                ItemStack customItem = customMat.getItemStack();
                ShapedRecipe shapedRecipe = new ShapedRecipe(recipeKey, customItem);
                shapedRecipe.shape(
                        recipeShape.get(0),
                        recipeShape.get(1),
                        recipeShape.get(2));
                ingredients.forEach((k, v) -> shapedRecipe.setIngredient(k.toString().charAt(0), Material.getMaterial(v)));
                Bukkit.addRecipe(shapedRecipe);

                if (vReverse) {
                    NamespacedKey recipeKey_vReverse = new NamespacedKey(RedRealms.getPlugin(), stringRecipeKey + "_vReversed");
                    ShapedRecipe shapedRecipe_vReverse = new ShapedRecipe(recipeKey_vReverse, customItem);
                    shapedRecipe_vReverse.shape(
                            recipeShape.get(2),
                            recipeShape.get(1),
                            recipeShape.get(0));
                    ingredients.forEach((k, v) -> shapedRecipe_vReverse.setIngredient(k.toString().charAt(0), Material.getMaterial(v)));
                    Bukkit.addRecipe(shapedRecipe_vReverse);
                }
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

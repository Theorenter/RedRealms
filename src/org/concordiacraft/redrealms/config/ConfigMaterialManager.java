package org.concordiacraft.redrealms.config;

import org.bukkit.inventory.ItemStack;
import org.concordiacraft.redrealms.main.RedLog;
import org.concordiacraft.redrealms.main.RedRealms;
import org.concordiacraft.redrealms.materials.MaterialRemover;

import java.util.ArrayList;
import java.util.List;

public class ConfigMaterialManager extends ConfigAbstractSetup {

    // List of all RedRealms custom materials
    public static List<ItemStack> customMaterials = new ArrayList<>();

    public ConfigMaterialManager(RedRealms plugin, String ymlFileName) {
        super(plugin, ymlFileName);

        /*// Loading from config all custom materials
        List<Map<?, ?>> cfgCustomMaterials = getCustomConfig().getMapList("materials.items");
        // Creating materials from materials.yml file
        for (Map<?, ?> m : cfgCustomMaterials) {
            MaterialCustom materialCustom = new MaterialCustom(m);
            ConfigMaterialManager.customMaterials.add(materialCustom.getItemStack());
        }

        // Initializing custom recipes
        List<Map<?, ?>> customShapedRecipes = getCustomConfig().getMapList("materials.recipes.shaped-recipes");
        for (Map <?, ?> c : customShapedRecipes) { MaterialRecipes.createCustomShapedRecipe(c); }
        List<Map<?, ?>> customShapelessRecipes = getCustomConfig().getMapList("materials.recipes.shapeless-recipes");
        for (Map <?, ?> c : customShapelessRecipes) { MaterialRecipes.createCustomShapelessRecipe(c); }
        List<Map<?, ?>> customFurnaceRecipes = getCustomConfig().getMapList("materials.recipes.furnace-recipes");
        for (Map <?, ?> c : customFurnaceRecipes) { MaterialRecipes.createCustomFurnaceRecipe(c); }
        List<Map<?, ?>> customBlastingRecipes = getCustomConfig().getMapList("materials.recipes.blasting-recipes");
        for (Map <?, ?> c : customBlastingRecipes) { MaterialRecipes.createCustomBlastingRecipe(c); }
        List<Map<?, ?>> customCampfireRecipes = getCustomConfig().getMapList("materials.recipes.campfire-recipes");
        for (Map <?, ?> c : customCampfireRecipes) { MaterialRecipes.createCustomCampfireRecipe(c); }
        List<Map<?, ?>> customCookingRecipes = getCustomConfig().getMapList("materials.recipes.cooking-recipes");
        for (Map <?, ?> c : customCookingRecipes) { MaterialRecipes.createCustomCookingRecipe(c); }
        List<Map<?, ?>> customSmithingRecipes = getCustomConfig().getMapList("materials.recipes.smithing-recipes");
        for (Map <?, ?> c : customSmithingRecipes) { MaterialRecipes.createCustomSmithingRecipe(c); }
        List<Map<?, ?>> customSmokingRecipes = getCustomConfig().getMapList("materials.recipes.smoking-recipes");
        for (Map <?, ?> c : customSmokingRecipes) { MaterialRecipes.createCustomSmokingRecipe(c); }
        List<Map<?, ?>> customStonecuttingRecipes = getCustomConfig().getMapList("materials.recipes.stonecutting-recipes");
        for (Map <?, ?> c : customStonecuttingRecipes) { MaterialRecipes.createCustomStonecuttingRecipe(c); }*/

        MaterialRemover.removeMaterials();

        RedLog.info("All custom items and recipes were added");
    }
}

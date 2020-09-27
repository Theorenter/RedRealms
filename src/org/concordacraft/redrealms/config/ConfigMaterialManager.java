package org.concordacraft.redrealms.config;

import org.concordacraft.redrealms.main.RedLog;
import org.concordacraft.redrealms.main.RedRealms;
import org.concordacraft.redrealms.materials.MaterialCustom;
import org.concordacraft.redrealms.materials.MaterialRecipes;
import org.concordacraft.redrealms.materials.MaterialRemover;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReferenceArray;

public class ConfigMaterialManager extends ConfigAbstractSetup {

    // List of all RedRealms custom materials
    public static List<MaterialCustom> customMaterials = new ArrayList<>();

    public ConfigMaterialManager(RedRealms plugin, String ymlFileName) {
        super(plugin, ymlFileName);

        // Loading from config all custom materials
        List<Map<?, ?>> cfgCustomMaterials = getCustomConfig().getMapList("materials.items");
        // Creating materials from materials.yml file
        for (Map<?, ?> m : cfgCustomMaterials) {
            MaterialCustom materialCustom = new MaterialCustom(m);
            ConfigMaterialManager.customMaterials.add(materialCustom);
        }

        // Initializing custom recipes
        List<Map<?, ?>> customShapedRecipes = getCustomConfig().getMapList("materials.recipes.shapedRecipes");
        for (Map <?, ?> c : customShapedRecipes) { MaterialRecipes.createCustomShapedRecipe(c); }
        List<Map<?, ?>> customShapelessRecipes = getCustomConfig().getMapList("materials.recipes.shapelessRecipes");
        for (Map <?, ?> c : customShapelessRecipes) { MaterialRecipes.createCustomShapelessRecipe(c); }
        List<Map<?, ?>> customBlastingRecipes = getCustomConfig().getMapList("materials.recipes.blastingRecipes");
        for (Map <?, ?> c : customBlastingRecipes) { MaterialRecipes.createCustomBlastingRecipe(c); }
        List<Map<?, ?>> customCampfireRecipes = getCustomConfig().getMapList("materials.recipes.campfireRecipes");
        for (Map <?, ?> c : customCampfireRecipes) { MaterialRecipes.createCustomCampfireRecipe(c); }
        List<Map<?, ?>> customCookingRecipes = getCustomConfig().getMapList("materials.recipes.cookingRecipes");
        for (Map <?, ?> c : customCookingRecipes) { MaterialRecipes.createCustomCookingRecipe(c); }
        List<Map<?, ?>> customSmithingRecipes = getCustomConfig().getMapList("materials.recipes.smithingRecipes");
        for (Map <?, ?> c : customSmithingRecipes) { MaterialRecipes.createCustomSmithingRecipe(c); }
        List<Map<?, ?>> customSmokingRecipes = getCustomConfig().getMapList("materials.recipes.smokingRecipes");
        for (Map <?, ?> c : customSmokingRecipes) { MaterialRecipes.createCustomSmokingRecipe(c); }
        List<Map<?, ?>> customStonecuttingRecipes = getCustomConfig().getMapList("materials.recipes.stonecuttingRecipes");
        for (Map <?, ?> c : customStonecuttingRecipes) { MaterialRecipes.createCustomStonecuttingRecipe(c); }

        MaterialRemover.removeMaterials();

        RedLog.info("All custom materials and recipes were added successfully");
    }
}

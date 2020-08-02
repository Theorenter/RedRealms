package com.eosproject.redrealms.materials;

import com.eosproject.redrealms.RedRealms;
import net.minecraft.server.v1_16_R1.ShapelessRecipes;
import org.apache.logging.log4j.core.pattern.AbstractStyleNameConverter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public abstract class CustomMaterialManager {

    // Main class
    protected static RedRealms plugin = RedRealms.getPlugin(RedRealms.class);

    // Custom ID for material
    protected static String customNID;

    protected static Material minecraftMaterial;

    protected static String displayName;
    protected static ItemMeta materialMeta;

    protected static Boolean craftable;
    protected static Boolean shaped;
    protected static List<String> recipeShape;

    // Constructor
    public CustomMaterialManager() {
        ItemStack material = new ItemStack(minecraftMaterial);
        NamespacedKey key = new NamespacedKey(plugin, customNID);

        materialMeta = material.getItemMeta();
        materialMeta.setDisplayName(displayName);

        if (craftable) {
            if (shaped)
                Bukkit.addRecipe(createShapedRecipe(key, material));
            else {
                // IDK -> createShapelessRecipe(key, material);
            }
        }

    }
    protected static ShapedRecipe createShapedRecipe(NamespacedKey key, ItemStack material) {
        ShapedRecipe shapedRecipe = new ShapedRecipe(key, material);

        // JUST FOR TEST
        shapedRecipe.shape("III", "III", "III");
        shapedRecipe.setIngredient('I', Material.IRON_BLOCK);

        return shapedRecipe;
    }

    protected static ShapelessRecipes createShapelessRecipe(NamespacedKey key, ItemStack material) {
        ShapelessRecipe shapelessRecipe = new ShapelessRecipe(key, material);

        return null;
    }
}

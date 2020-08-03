package com.eosproject.redrealms.file.settings;

import com.eosproject.redrealms.RedRealms;
import net.minecraft.server.v1_16_R1.ShapelessRecipes;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MaterialsFile extends SettingsFile {

    protected static RedRealms plugin = RedRealms.getPlugin(RedRealms.class);

    // Required fields
    private String customNID;
    private String materialName;
    private String displayName;
    private ItemMeta materialMeta;

    // Optional fields
    private Boolean craftable;
    private Boolean shaped;
    private List<String> recipeShape;
    private Character craftChar;

    // Constructor
    public MaterialsFile(String YMLFileName) {
        super(YMLFileName);

        List<Map<?, ?>> cfgMaterials = customConfig.getMapList("materials");
        for (Map<?, ?> m : cfgMaterials) addMaterial(m);
    }

    public void addMaterial (Map m) {

        this.customNID = (String) m.get("nid");
        plugin.log.info(customNID);
        this.materialName = (String) m.get("minecraft_nid");
        plugin.log.info(materialName);
        this.displayName = (String) m.get("name");
        plugin.log.info(displayName);
        this.craftable = (Boolean) m.get("craftable");
        plugin.log.info(craftable.toString());
        this.shaped = (Boolean) m.get("shaped");
        plugin.log.info(shaped.toString());

        ItemStack material = new ItemStack(Material.getMaterial(materialName));
        NamespacedKey key = new NamespacedKey(plugin, customNID);

        materialMeta = material.getItemMeta();
        materialMeta.setDisplayName(displayName);

        if (craftable) {
            List<Character> craftableCharacters = new ArrayList<>();

            craftableCharacters.add('1');
            craftableCharacters.add('2');
            craftableCharacters.add('3');
            craftableCharacters.add('4');
            craftableCharacters.add('5');
            craftableCharacters.add('6');
            craftableCharacters.add('7');
            craftableCharacters.add('8');
            craftableCharacters.add('9');

            if (shaped)
                Bukkit.addRecipe(createShapedRecipe(key, material, m));
            else {
                // IDK -> createShapelessRecipe(key, material);
            }
        }
    }

    private ShapedRecipe createShapedRecipe (NamespacedKey key, ItemStack material, Map m) {
        ShapedRecipe shapedRecipe = new ShapedRecipe(key, material);
        Map <Integer, String> ingredients = (Map<Integer, String>) m.get("ingredients");
        recipeShape = (List<String>) m.get("shape");
        shapedRecipe.shape(
                recipeShape.get(0),
                recipeShape.get(1),
                recipeShape.get(2));
        ingredients.forEach((charID, materialNID) -> shapedRecipe.setIngredient(charID.toString().charAt(0), Material.getMaterial(materialNID)));
        return shapedRecipe;
    }

    private ShapelessRecipes createShapelessRecipe (NamespacedKey key, ItemStack material) {
        ShapelessRecipe shapelessRecipe = new ShapelessRecipe(key, material);

        return null;
    }
}

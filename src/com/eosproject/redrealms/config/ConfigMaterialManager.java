package com.eosproject.redrealms.config;

import com.eosproject.redrealms.main.RedColors;
import com.eosproject.redrealms.main.RedLog;
import com.eosproject.redrealms.main.RedRealms;
import net.minecraft.server.v1_16_R1.ShapelessRecipes;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class ConfigMaterialManager extends ConfigAbstractSetup {

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

    // Constructor
    public ConfigMaterialManager(String YMLFileName) {
        super(YMLFileName);

        List<Map<?, ?>> cfgMaterials = customConfig.getMapList("materials.workbench_or_inventory");
        for (Map<?, ?> m : cfgMaterials) addMaterial(m);
    }

    public void addMaterial (Map m) {

        this.customNID = (String) m.get("nid");
        this.materialName = (String) m.get("minecraft_nid");
        this.displayName = (String) m.get("name");
        this.craftable = (Boolean) m.get("craftable");
        this.shaped = (Boolean) m.get("shaped");

        ItemStack itemStack = new ItemStack(Material.getMaterial(materialName));
        NamespacedKey key = new NamespacedKey(plugin, customNID);

        materialMeta = itemStack.getItemMeta();
        materialMeta.setDisplayName(RedColors.RESET + displayName);

        itemStack.setItemMeta(materialMeta);

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
                Bukkit.addRecipe(createShapedRecipe(key, itemStack, m));
            else {
                Bukkit.addRecipe(createShapelessRecipe(key, itemStack, m));
            }
        }
    }

    private ShapedRecipe createShapedRecipe (NamespacedKey key, ItemStack itemStack, Map m) {
        ShapedRecipe shapedRecipe = new ShapedRecipe(key, itemStack);
        Map <Integer, String> ingredients = (Map<Integer, String>) m.get("ingredients");
        recipeShape = (List<String>) m.get("shape");
        shapedRecipe.shape(
                recipeShape.get(0),
                recipeShape.get(1),
                recipeShape.get(2));
        ingredients.forEach((charID, materialNID) -> shapedRecipe.setIngredient(charID.toString().charAt(0), Material.getMaterial(materialNID)));
        return shapedRecipe;
    }

    private ShapelessRecipe createShapelessRecipe (NamespacedKey key, ItemStack itemStack, Map m) {
        ShapelessRecipe shapelessRecipe = new ShapelessRecipe(key, itemStack);
        ArrayList<LinkedHashMap> ingredients = (ArrayList<LinkedHashMap>) m.get("ingredients");
        ingredients.forEach((elements) -> shapelessRecipe.addIngredient((Integer) elements.get("count"), Material.getMaterial(elements.get("material").toString())));
        return shapelessRecipe;
    }
}

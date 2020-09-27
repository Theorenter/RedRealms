package org.concordacraft.redrealms.materials;

import org.concordacraft.redrealms.config.ConfigMaterialManager;
import org.concordacraft.redrealms.main.RedLog;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.concordacraft.redrealms.utilits.NBTEditor;

import java.util.*;

public class MaterialCustom {

    // Required fields
    private String redRealms_ID;
    private String minecraft_ID;

    private ItemStack itemStack;

    // Optional fields
    private String displayName;
    private List<String> itemLore;
    private List<String> recipeShape;

    // Constructor
    public MaterialCustom(Map m) {

        // Getting the value of key fields
        this.redRealms_ID = (String) m.get("redRealms_ID");
        this.minecraft_ID = (String) m.get("minecraft_ID");

        if (redRealms_ID == null || minecraft_ID == null ) {
            RedLog.error("The material was not created due to missing key fields (redRealms_ID, minecraft_ID): ");
            RedLog.error("{");
            m.forEach((k, v)
                    -> RedLog.error("  " + k + ": " + v));
            RedLog.error("};");
            return;
        }
        // Creating an itemStack based on key fields
        this.itemStack = new ItemStack(Material.getMaterial(minecraft_ID));

        // Set NBT tag
        this.itemStack = NBTEditor.set(itemStack, this.redRealms_ID, "customID");

        // Get itemMeta for further work
        ItemMeta itemMeta = itemStack.getItemMeta();

        // Set displayName
        if (m.containsKey("displayName")) {
            this.displayName = (String) m.get("displayName");
            itemMeta.setDisplayName(this.displayName);
        }

        // Set lore
        if (m.containsKey("lore")) {
            this.itemLore = (List<String>) m.get("lore");
            itemMeta.setLore(itemLore);
        }

        // Back meta
        itemStack.setItemMeta(itemMeta);

        /*if (craftable) {
            List<Character> craftableCharacters = new ArrayList<>();

            for (int i = 1; i < 10; i++) { craftableCharacters.add((char) i); }

            if (shaped)
                Bukkit.addRecipe(createShapedRecipe(key, itemStack, m));
            else {
                Bukkit.addRecipe(createShapelessRecipe(key, itemStack, m));
            }
        }*/
    }
    // Getters
    public ItemStack getItemStack() {
        return this.itemStack;
    }

    public String getRedRealms_ID() {
        return this.redRealms_ID;
    }

    // Recipes methods
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
    //endregion
}

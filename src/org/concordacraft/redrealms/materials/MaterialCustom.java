package org.concordacraft.redrealms.materials;

import org.concordacraft.redrealms.main.RedLog;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.concordacraft.redrealms.utilits.NBTEditor;

import java.util.*;

public class MaterialCustom {

    // Required fields
    private String redRealmsID;
    private String minecraftID;

    private ItemStack itemStack;

    // Optional fields
    private String displayName;
    private List<String> itemLore;
    private List<String> recipeShape;

    // Constructor
    public MaterialCustom(Map m) {

        // Getting the value of key fields
        this.redRealmsID = (String) m.get("redRealmsID");
        this.minecraftID = (String) m.get("minecraftID");

        if (redRealmsID == null || minecraftID == null ) {
            RedLog.error("The material was not created due to missing key fields (redRealmsID, minecraftID): ");
            RedLog.error("{");
            m.forEach((k, v)
                    -> RedLog.error("  " + k + ": " + v));
            RedLog.error("};");
            return;
        }
        // Creating an itemStack based on key fields
        this.itemStack = new ItemStack(Material.getMaterial(minecraftID));

        // Set NBT tag
        this.itemStack = NBTEditor.set(itemStack, this.redRealmsID, "customID");

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

    }
    // Getters
    public ItemStack getItemStack() {
        return this.itemStack;
    }

    public String getRedRealmsID() {
        return this.redRealmsID;
    }
}

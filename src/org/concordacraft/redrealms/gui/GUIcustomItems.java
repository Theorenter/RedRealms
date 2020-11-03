package org.concordacraft.redrealms.gui;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

public class GUIcustomItems {
    private final Inventory inventory;

    public GUIcustomItems() {
        inventory = Bukkit.createInventory(null, 27, "Custom Items inventory");
    }
}

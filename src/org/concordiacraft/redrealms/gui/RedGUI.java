package org.concordiacraft.redrealms.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.concordiacraft.redrealms.main.RedRealms;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Theorenter
 * Universal abstract class for GUIs
 */
public abstract class RedGUI implements Listener {
    private static ArrayList<Inventory> inventories = new ArrayList<>();
    protected Inventory inv;

    abstract void initializeItems();

    protected RedGUI(InventoryHolder owner, int invSlots) {
        inv = Bukkit.createInventory(owner, invSlots);

        inventories.add(inv);
    }

    protected RedGUI(InventoryHolder owner, int invSlots, String invName) {
        inv = Bukkit.createInventory(owner, invSlots, invName);

        inventories.add(inv);
    }

    protected ItemStack getGUIItemStack(Material material, int customModelData, String name, String... lore) {
        ItemStack i = new ItemStack(material);
        ItemMeta meta = i.getItemMeta();

        meta.setDisplayName(name);
        meta.setLore(Arrays.asList(lore));
        meta.setCustomModelData(customModelData);

        i.setItemMeta(meta);
        return i;
    }

    protected ItemStack getFunctionalGUIItemStack(Material material, int customModelData, String name, String tagKey, String tagValue, String... lore) {
        ItemStack i = new ItemStack(material);
        ItemMeta meta = i.getItemMeta();

        meta.setDisplayName(name);
        meta.setLore(Arrays.asList(lore));
        meta.setCustomModelData(customModelData);
        PersistentDataContainer pdc = meta.getPersistentDataContainer();
        NamespacedKey nsk = new NamespacedKey(RedRealms.getPlugin(), tagKey);
        pdc.set(nsk, PersistentDataType.STRING, tagValue);

        i.setItemMeta(meta);
        return i;
    }

    public void openInventory(final HumanEntity ent) {
        ent.openInventory(inv);
    }

    public static ArrayList<Inventory> getCustomInventoryList() { return inventories; }
    public static void removeCustomInventory(Inventory inv) { inventories.remove(inv);
    }

}

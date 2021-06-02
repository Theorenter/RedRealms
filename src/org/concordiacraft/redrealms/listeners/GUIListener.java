package org.concordiacraft.redrealms.listeners;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.persistence.PersistentDataType;
import org.concordiacraft.redrealms.gui.RedGUI;
import org.concordiacraft.redrealms.main.RedRealms;

public class GUIListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (!RedGUI.getCustomInventoryList().contains(e.getInventory())) return;
        e.setCancelled(true);
        NamespacedKey nsk = new NamespacedKey(RedRealms.getPlugin(), "UNQ-GUI-COMMAND");
        if ((e.getCurrentItem() == null) || !(e.getCurrentItem().getItemMeta().getPersistentDataContainer().has(nsk, PersistentDataType.STRING))) return;
        String commandStr = e.getCurrentItem().getItemMeta().getPersistentDataContainer().get(
                new NamespacedKey(RedRealms.getPlugin(), "UNQ-GUI-COMMAND"), PersistentDataType.STRING);
        ((Player) e.getWhoClicked()).performCommand(commandStr);
    }

    @EventHandler
    public void onInventoryMoveItem(InventoryDragEvent e) {
        if (!RedGUI.getCustomInventoryList().contains(e.getInventory())) return;
        e.setCancelled(true);
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        if (!RedGUI.getCustomInventoryList().contains(e.getInventory())) return;
        RedGUI.removeCustomInventory(e.getInventory());
    }
}

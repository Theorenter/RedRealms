package org.concordiacraft.redrealms.listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;

/**
 * @author Theorenter
 * Listener for all rules that relate directly to Minecraft
 */
public class RuleListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onIronItemsCraft(PrepareItemCraftEvent e) {

    }
}

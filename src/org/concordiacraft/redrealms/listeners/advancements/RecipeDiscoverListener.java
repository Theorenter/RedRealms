package org.concordiacraft.redrealms.listeners.advancements;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRecipeDiscoverEvent;

public class RecipeDiscoverListener implements Listener {

    @EventHandler(priority = EventPriority.NORMAL)
    public void onRecipeDiscover(PlayerRecipeDiscoverEvent e) {
        e.setCancelled(true);
    }

}

package org.concordiacraft.redrealms.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.concordiacraft.redrealms.data.PromptData;
import org.concordiacraft.redrealms.main.RedRealms;
import org.concordiacraft.redutils.utils.RedFormatter;

public class PlayerInteractListener implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void blockWhenPrompt(PlayerInteractEvent e) {
        if (PromptData.getPromptMap().containsKey(e.getPlayer().getUniqueId())) {
            e.setCancelled(true);
            e.getPlayer().sendMessage(RedFormatter.format(RedRealms.getLocalization().getRawString("messages.errors.player-in-prompt")));
        }
    }
}

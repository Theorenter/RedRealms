package org.concordiacraft.redrealms.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.concordiacraft.redrealms.data.RedData;
import org.concordiacraft.redrealms.data.RedPlayer;
import org.concordiacraft.redrealms.main.RedRealms;

/**
 * @author Theorenter
 * Listener for all RedRules.
 */
public class RuleListener implements Listener {

    @EventHandler(priority = EventPriority.NORMAL)
    public void onItemCraft(PrepareItemCraftEvent e) {
        if (!(e.getInventory().getHolder() instanceof Player)) return;
        Player p = (Player) e.getInventory().getHolder();
        RedPlayer rp = RedData.loadPlayer(p);

        if (!rp.hasRuleToCraft(e.getInventory().getResult())) {
            p.sendRawMessage("messages.errors.dont't-have-tech-to-craft");
            p.playSound(p.getLocation(), RedRealms.getDefaultConfig().getErrorSoundName(), RedRealms.getDefaultConfig().getErrorSoundVolume(), RedRealms.getDefaultConfig().getErrorSoundPitch());
        }
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onItemUse(PlayerInteractEvent e) {
        if (e.getItem().getType().equals(Material.AIR)) return;
        Player p = e.getPlayer();
        RedPlayer rp = RedData.loadPlayer(p);

        if (!rp.hasRuleToUse(e.getItem())) {
            p.sendRawMessage("messages.errors.dont't-have-tech-to-use");
            p.playSound(p.getLocation(), RedRealms.getDefaultConfig().getErrorSoundName(), RedRealms.getDefaultConfig().getErrorSoundVolume(), RedRealms.getDefaultConfig().getErrorSoundPitch());
        }
    }
}

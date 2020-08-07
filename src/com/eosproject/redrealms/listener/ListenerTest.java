package com.eosproject.redrealms.listener;

import com.eosproject.redrealms.main.RedLog;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class ListenerTest implements Listener {

    @EventHandler
    public void eventUseItem(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        String Name = p.getName();

        ItemStack itemStack = p.getInventory().getItemInMainHand();
        p.sendMessage(Name + " - " + itemStack.getItemMeta().toString());
        RedLog.debug("a");
    }
}

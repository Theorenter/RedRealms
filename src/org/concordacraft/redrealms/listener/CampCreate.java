package org.concordacraft.redrealms.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class CampCreate implements Listener {

    @EventHandler(priority = EventPriority.NORMAL)
    public void createCamp(BlockPlaceEvent e) {
/*        if (e.getPlayer().getInventory()) {
            Player player = e.getPlayer();
            RedMessenger.getManager().msg(player, RedMessenger.MessageType.NOTIFY, "Вы уверены, что хотите основать на выбранном регионе новый лагерь?");
        }*/
    }
}

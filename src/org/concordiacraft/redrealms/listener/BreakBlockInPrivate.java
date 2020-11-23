package org.concordiacraft.redrealms.listener;

import org.bukkit.Chunk;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.concordiacraft.redrealms.data.DataRegions;

public class BreakBlockInPrivate implements Listener {
    @EventHandler(priority = EventPriority.NORMAL)
    public void Playerinteract(PlayerInteractEvent e) {
        if (!e.hasBlock()) return;
        Block block = e.getClickedBlock();
        DataRegions chunk = new DataRegions();
        chunk.setChunk(block.getChunk());
        chunk.setWorld(block.getWorld());
        chunk.readFile();
        if (chunk.getOwner()!=e.getPlayer().getDisplayName())
        {
            e.setCancelled(true);
            e.getPlayer().sendMessage("Вам не позволено использовать предметы на чужой территории.");
        }
/*        if (e.getPlayer().getInventory()) {
            Player player = e.getPlayer();
            RedMessenger.getManager().msg(player, RedMessenger.MessageType.NOTIFY, "Вы уверены, что хотите основать на выбранном регионе новый лагерь?");
        }*/
    }
}

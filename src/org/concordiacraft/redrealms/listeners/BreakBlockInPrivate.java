package org.concordiacraft.redrealms.listeners;

import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.concordiacraft.redrealms.data.DataChunks;

public class BreakBlockInPrivate implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void PlayerInteractWithBlock(PlayerInteractEvent e) {
        if (!e.hasBlock()) return;
        Block block = e.getClickedBlock();
        DataChunks chunk = new DataChunks();
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

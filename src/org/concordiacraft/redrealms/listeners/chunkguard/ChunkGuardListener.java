package org.concordiacraft.redrealms.listeners.chunkguard;

import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.concordiacraft.redrealms.config.ConfigLocalization;
import org.concordiacraft.redrealms.data.DataChunk;
import org.concordiacraft.redrealms.utilits.ChunkWork;

public class ChunkGuardListener implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void PlayerInteractTownBlock(PlayerInteractEvent e) {
        if (!e.hasBlock()) return;
        Block block = e.getClickedBlock();
        DataChunk chunk = new DataChunk(block.getChunk());
        chunk.readFile();
        if (!ChunkWork.canInteract(block.getChunk(),e.getPlayer()))
        {
            e.setCancelled(true);
            e.getPlayer().sendMessage(ConfigLocalization.getFormatString("msg_error_private_territory_break"));
        }
    }
    @EventHandler(priority = EventPriority.HIGHEST)
    public void PlayerBreakTownBlock(BlockBreakEvent e) {
        Block block = e.getBlock();
        DataChunk chunk = new DataChunk(block.getChunk());
        chunk.readFile();
        if (!ChunkWork.canInteract(block.getChunk(),e.getPlayer()))
        {
            e.setCancelled(true);
            e.getPlayer().sendMessage(ConfigLocalization.getFormatString("msg_error_private_territory_break"));
        }
    }
    @EventHandler(priority = EventPriority.HIGHEST)
    public void TownBoom(EntityExplodeEvent e) {
        DataChunk chunk = new DataChunk(e.getLocation().getChunk());
        if (chunk.getOwner()!=null) {
            e.setCancelled(true);
        }
    }
}

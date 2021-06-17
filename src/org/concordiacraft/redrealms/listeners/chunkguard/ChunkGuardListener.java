package org.concordiacraft.redrealms.listeners.chunkguard;

import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.concordiacraft.redrealms.data.RedChunk;
import org.concordiacraft.redrealms.data.RedData;
import org.concordiacraft.redrealms.main.RedRealms;
import org.concordiacraft.redrealms.utilits.ChunkWork;

import java.util.ArrayList;
import java.util.List;

public class ChunkGuardListener implements Listener {
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void playerInteractTownBlock(PlayerInteractEvent e) {
        if (!e.hasBlock()) return;
        Block block = e.getClickedBlock();
        RedData.loadChunk(block.getChunk());

        if ((!ChunkWork.canInteract(block.getChunk(), e.getPlayer()) && (!e.getPlayer().isOp()))) {
            e.setCancelled(true);
            e.getPlayer().sendRawMessage(RedRealms.getLocalization().getString("messages.errors.can-not-break-there"));
        }
    }
    @EventHandler(priority = EventPriority.HIGHEST)
    public void playerBreakTownBlock(BlockBreakEvent e) {
        Block block = e.getBlock();
        RedData.loadChunk(block.getChunk());

        if ((!ChunkWork.canInteract(block.getChunk(),e.getPlayer())) && (!e.getPlayer().isOp())) {
            e.setCancelled(true);
            e.getPlayer().sendRawMessage(RedRealms.getLocalization().getString("messages.errors.can-not-break-there"));
        }
    }
    @EventHandler(priority = EventPriority.HIGHEST)
    public void townExplode(EntityExplodeEvent e) {
        RedChunk chunk = RedData.loadChunk(e.getLocation().getChunk());
        if (chunk.getTownOwner() != null) {
            e.setCancelled(true);
        }
    }
    @EventHandler(priority = EventPriority.HIGHEST)
    public void inTownFluidFlow(BlockFromToEvent e){
        RedChunk chunkFrom = RedData.loadChunk(e.getBlock().getChunk());
        RedChunk chunkTo = RedData.loadChunk(e.getToBlock().getChunk());
        if (chunkTo.getTownOwner()==null) { return; }
        if (!chunkTo.getTownOwner().equals(chunkFrom.getTownOwner())) {
            e.setCancelled(true);
        }
    }
    @EventHandler(priority = EventPriority.HIGH)
    public void closeToTownExplode(EntityExplodeEvent e) {
        RedChunk chunk = RedData.loadChunk(e.getLocation().getChunk());
        List<Block> blockList = new ArrayList<>();
        if (chunk.getTownOwner()==null) {
            for (Block b:e.blockList()) {
                chunk = RedData.loadChunk(b.getChunk());
                if (chunk.getTownOwner()!=null) {
                    e.blockList().remove(b);
                }
            }

        }
    }
}

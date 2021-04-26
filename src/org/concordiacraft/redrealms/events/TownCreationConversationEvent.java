package org.concordiacraft.redrealms.events;

import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

public class TownCreationConversationEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    private Player townFounder;
    private ItemStack townBanner;
    private Chunk townChunk;
    private boolean cancelled;

    public TownCreationConversationEvent(Player townFounder, ItemStack townBanner, Chunk townChunk) {
        this.townFounder = townFounder;
        this.townBanner = townBanner;
        this.townChunk = townChunk;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public Player getTownFounder() {
        return townFounder;
    }

    public ItemStack getTownBanner() { return townBanner; }

    public Chunk getTownChunk() { return townChunk; }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    @Override
    public void setCancelled(boolean cancel) {
        cancelled = cancel;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }
}

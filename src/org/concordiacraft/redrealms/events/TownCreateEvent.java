package org.concordiacraft.redrealms.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;
import org.concordiacraft.redrealms.data.DataTown;

public class TownCreateEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    private Player townFounder;
    private ItemStack townBanner;
    private DataTown newTown;
    private boolean cancelled;

    public TownCreateEvent(Player townFounder, DataTown newTown) {
        this.townFounder = townFounder;
        this.newTown = newTown;
    }
    public static HandlerList getHandlerList() {
        return handlers;
    }

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

    public Player getTownFounder() {
        return townFounder;
    }

    public DataTown getNewTown() {
        return newTown;
    }

    public ItemStack getTownBanner() { return townBanner; }
}

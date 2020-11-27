package org.concordiacraft.redrealms.listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.concordiacraft.redrealms.main.RedRealms;

public class CustomItemRiderModifier {
    RedRealms plugin;

    public CustomItemRiderModifier(RedRealms plugin) { this.plugin = plugin; }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerAttackEntityWhenRide(EntityDamageByEntityEvent e) {

    }
}
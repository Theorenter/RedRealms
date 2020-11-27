package org.concordiacraft.redrealms.listener;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer; // PROKLYATO
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.concordiacraft.redrealms.main.RedLog;
import org.concordiacraft.redrealms.main.RedRealms;

public class CustomItemShieldBreaker implements Listener {
    RedRealms plugin;
    public CustomItemShieldBreaker(RedRealms plugin) { this.plugin = plugin; }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerAttackShield(EntityDamageByEntityEvent e) {
        if (!((e.getDamager() instanceof Player) && (e.getEntity() instanceof Player))) { return; }
        Player pAttacker = (Player) e.getDamager();
        Player pDamaged = (Player) e.getEntity();
        if (!pAttacker.getInventory().getItemInMainHand().hasItemMeta()) { return; }
        ItemStack itemBreaker = pAttacker.getInventory().getItemInMainHand();
        PersistentDataContainer data = itemBreaker.getItemMeta().getPersistentDataContainer();
        if (data.isEmpty()) { return; }
        NamespacedKey nSK = new NamespacedKey(plugin, "UNQ-SHIELD-BREAKER");
        if (!data.has(nSK, PersistentDataType.INTEGER_ARRAY)) { return; }
        if (pDamaged.isBlocking()) {
            int[] valS = data.get(nSK, PersistentDataType.INTEGER_ARRAY);
            if (valS[1] < 100) {
                int randValue = (int) (Math.random() * 100);
                if (randValue <= valS[1]) {
                    // Sorry... Spigot doesn't have at the moment #clearActiveItem()
                    ((CraftPlayer) pDamaged).getHandle().clearActiveItem();
                }
            } else {
                pDamaged.setCooldown(Material.SHIELD, valS[0]);
                ((CraftPlayer) pDamaged).getHandle().clearActiveItem();
            }
        }
    }
}
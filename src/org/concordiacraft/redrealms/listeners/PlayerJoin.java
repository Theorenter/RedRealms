package org.concordiacraft.redrealms.listeners;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.concordiacraft.redrealms.data.RedData;
import org.concordiacraft.redrealms.data.RedPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.concordiacraft.redrealms.data.PromptData;
import org.concordiacraft.redrealms.main.RedRealms;
import org.concordiacraft.redutils.utils.RedFormatter;

import java.util.UUID;

public class PlayerJoin implements Listener {

    @EventHandler(priority = EventPriority.NORMAL)
    public void firstPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        RedPlayer redPlayer = RedData.createPlayer(p);
    }
    @EventHandler(priority = EventPriority.LOWEST)
    public void interruptedConversationCheck(PlayerJoinEvent e) {
        UUID uuid = e.getPlayer().getUniqueId();
        if (!PromptData.getPromptMap().containsKey(uuid)) return;
        Player p = e.getPlayer();
        ItemStack i = (ItemStack) PromptData.getPromptMap().get(uuid);
        PlayerInventory pi = p.getInventory();
        String s;
        // - 1 means inventory is full

        String logS;

        if (pi.firstEmpty() == -1) {
            p.getWorld().dropItem(p.getLocation(), i);
            s = String.format(RedFormatter.format(RedRealms.getLocalization().getRawString("messages.notifications.prompt-data-item-back"))
                    + RedFormatter.format(RedRealms.getLocalization().getRawString("messages.notifications.prompt-data-item-back-inventory-full")), i.getItemMeta().getDisplayName());
            logS = "Игроку " + e.getPlayer().getName() + " [" + uuid+ "]" + " был возврашён следующий предмет: " + i.getItemMeta().getDisplayName() + " (Заспаунился рядом с игроком)";
        } else {
            pi.addItem(i);
            s = String.format(RedFormatter.format(RedRealms.getLocalization().getRawString("messages.notifications.prompt-data-item-back")), i.getItemMeta().getDisplayName());
            logS = "Игроку " + e.getPlayer().getName() + " [" + uuid+ "]" + " был возврашён следующий предмет: " + i.getItemMeta().getDisplayName();
        }
        p.sendMessage(s);
        PromptData.removeFromPromptMap(uuid);
        RedRealms.getPlugin().getRedLogger().info(logS);
    }
}

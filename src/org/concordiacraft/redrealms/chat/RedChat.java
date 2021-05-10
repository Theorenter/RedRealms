package org.concordiacraft.redrealms.chat;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class RedChat implements Listener {

    private static String chatFormat = "%1$s → %2$s";

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        e.setFormat(chatFormat);
    }
}

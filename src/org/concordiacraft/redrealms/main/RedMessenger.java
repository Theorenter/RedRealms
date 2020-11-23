package org.concordiacraft.redrealms.main;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class RedMessenger {
    RedMessenger() {}

    private static RedMessenger manager = new RedMessenger();

    public static RedMessenger getManager() {
        return manager;
    }
    public enum MessageType {
        NOTIFY(ChatColor.BLUE),
        SUCCESS(ChatColor.GREEN),
        WARNING(ChatColor.GOLD),
        FAILURE(ChatColor.RED);

        private ChatColor color;

        MessageType (ChatColor color) {
            this.color = color;
        }
        public ChatColor getColor() {
            return color;
        }
    }
    private String messageChar = "⚠";

    public void msg (CommandSender sender, MessageType type, String... msgs) {
        for (String msg : msgs) {
            sender.sendMessage(type.getColor() + messageChar + ChatColor.RESET + " " + msg);
        }
    }
}

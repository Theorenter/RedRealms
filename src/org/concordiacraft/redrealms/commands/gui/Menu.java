package org.concordiacraft.redrealms.commands.gui;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.concordiacraft.redrealms.config.ConfigLocalization;
import org.concordiacraft.redrealms.gui.GUIMenu;
import org.concordiacraft.redrealms.main.RedRealms;

public class Menu implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if (!(sender instanceof Player)) {
            RedRealms.getPlugin().getRedLogger().info(ConfigLocalization.getRawString("messages.errors.only-for-players"));
            return true;
        }
        GUIMenu gui = new GUIMenu();
        gui.openInventory((Player) sender);
        return true;
    }
}

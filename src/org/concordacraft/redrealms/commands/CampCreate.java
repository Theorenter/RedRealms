package org.concordacraft.redrealms.commands;

import org.concordacraft.redrealms.main.RedRealms;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CampCreate implements CommandExecutor {

    RedRealms plugin;
    public CampCreate(RedRealms plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("Данная команда доступна только для игроков");
            return true;
        }

        if (strings.length != 1)
            return false;

        Player playerSender = (Player) commandSender;
        playerSender.sendMessage(playerSender.getUniqueId().toString());

        return true;
    }

}

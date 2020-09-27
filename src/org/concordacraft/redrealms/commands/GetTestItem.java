package org.concordacraft.redrealms.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.concordacraft.redrealms.main.RedRealms;
import org.concordacraft.redrealms.utilits.TestItem;

public class GetTestItem implements CommandExecutor {

    RedRealms plugin;
    public GetTestItem(RedRealms plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("Данная команда доступна только для игроков");
            return true;
        }

        // действие
        ItemStack testItem = TestItem.create();
        ((Player) commandSender).getInventory().addItem(testItem);
        return true;
    }
}

package org.concordacraft.redrealms.commands;

import org.concordacraft.redrealms.data.DataCamps;
import org.concordacraft.redrealms.data.DataPlayer;
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
        DataCamps camp = new DataCamps(strings[0]);
        if (camp.readFile()) {
            commandSender.sendMessage("Лагерь с таким названием уже существует!");
            return true;
        }
        //region adds data in camp file
        camp.setPlayerName(playerSender.getName());
        camp.setChunkCoords(playerSender.getLocation().getChunk().getX(),playerSender.getLocation().getChunk().getZ());
        camp.addChunk(playerSender.getLocation().getChunk().getX(),playerSender.getLocation().getChunk().getZ());
        camp.updateFile();
        //endregion
        //region adds data in player file
        DataPlayer dataPlayer = new DataPlayer(playerSender);
        dataPlayer.setRealm(strings[0]);
        dataPlayer.updateFile();
        //endregion
        playerSender.sendMessage("Лагерь "+ strings[0]+ " успешно создан!");

        return true;
    }

}

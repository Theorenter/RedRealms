package org.concordacraft.redrealms.commands;

import org.bukkit.Chunk;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.concordacraft.redrealms.data.DataCamps;
import org.concordacraft.redrealms.data.DataPlayer;
import org.concordacraft.redrealms.utilits.ChunkWork;
import org.concordacraft.redrealms.main.RedRealms;

import java.util.ArrayList;

public class AddChunk implements CommandExecutor {
    RedRealms plugin;
    public AddChunk(RedRealms plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("Данная команда доступна только для игроков");
            return true;
        }
        Player playerSender = (Player) commandSender;
        Chunk chunk = playerSender.getLocation().getChunk();
        DataPlayer dataPlayer = new DataPlayer(playerSender);
        dataPlayer.readFile();
        String Realm = dataPlayer.getRealm();
        DataCamps camp = new DataCamps(Realm);
        camp.readFile();

         // using abstract utility class, we convert Chunk to ArrayList, and look for him in camp file
        if (ChunkWork.ChunksContains(camp.getChunks(),chunk)) {
            commandSender.sendMessage("Данный чанк уже запривачен");
            return true;
        }
        camp.addChunk(chunk);
        camp.updateFile();
        commandSender.sendMessage("Чанк с координатами X=" +playerSender.getLocation().getChunk().getX() +" Z = " + playerSender.getLocation().getChunk().getZ()+"Добавлен в лагерь" + Realm);
        return true;
    }

}

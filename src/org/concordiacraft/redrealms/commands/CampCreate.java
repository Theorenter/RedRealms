package org.concordiacraft.redrealms.commands;

import org.bukkit.Chunk;
import org.concordiacraft.redrealms.data.DataCamps;
import org.concordiacraft.redrealms.data.DataPlayer;
import org.concordiacraft.redrealms.data.DataRegions;
import org.concordiacraft.redrealms.main.RedRealms;
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
        Chunk chunk = playerSender.getLocation().getChunk();
        DataCamps camp = new DataCamps(strings[0]);
        if (camp.readFile()) {
            commandSender.sendMessage("Лагерь с таким названием уже существует!");
            return true;
        }
        //add data in camp file
        camp.setPlayerName(playerSender.getName());
        camp.setChunkCoords(chunk.getX(),chunk.getZ());
        camp.addChunk(chunk.getX(),chunk.getZ());
        camp.updateFile();
        //creating region file
        DataRegions region = new DataRegions();


        region.setWorld(chunk.getWorld());
        region.setChunk(chunk);
        region.readFile();
        if (region.getOwner()!=null){
            commandSender.sendMessage("Лагерь на этой территории уже существует!");
            return true;
        }
        region.setOwner(strings[0]);
        region.updateFile();
        //add data in player file
        DataPlayer dataPlayer = new DataPlayer(playerSender);
        dataPlayer.setRealm(strings[0]);
        dataPlayer.updateFile();
        playerSender.sendMessage("Лагерь "+ strings[0]+ " успешно создан!");

        return true;
    }

}

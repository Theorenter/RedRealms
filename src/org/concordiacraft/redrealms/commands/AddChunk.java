package org.concordiacraft.redrealms.commands;

import org.bukkit.Chunk;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.concordiacraft.redrealms.data.DataTowns;
import org.concordiacraft.redrealms.data.DataPlayer;
import org.concordiacraft.redrealms.data.DataChunks;
import org.concordiacraft.redrealms.main.RedRealms;

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
        DataTowns town = new DataTowns(Realm);
        town.readFile();

         // using abstract utility class, we convert Chunk to ArrayList, and look for him in camp file
        DataChunks region = new DataChunks();
        region.setWorld(chunk.getWorld());
        region.setChunk(chunk);
        region.readFile();
        if (region.getOwner()!=null){
            commandSender.sendMessage("Это территория города " + region.getOwner());
            return true;
        }
        region.setOwner(dataPlayer.getRealm());
        region.updateFile();
        town.addChunk(chunk);

        town.updateFile();
        commandSender.sendMessage("Чанк с координатами X=" +playerSender.getLocation().getChunk().getX() +" Z = " + playerSender.getLocation().getChunk().getZ()+" Добавлен во владения города " + Realm);
        return true;
    }

}

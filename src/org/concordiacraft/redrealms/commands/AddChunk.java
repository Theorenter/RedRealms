package org.concordiacraft.redrealms.commands;

import org.bukkit.Chunk;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.concordiacraft.redrealms.data.RedData;
import org.concordiacraft.redrealms.data.RedTown;
import org.concordiacraft.redrealms.data.RedPlayer;
import org.concordiacraft.redrealms.data.RedChunk;
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
        RedPlayer redPlayer = RedData.loadPlayer(playerSender);
        redPlayer.readFile();

        String Realm = redPlayer.getRealmName();
        RedTown town = RedData.loadTown(Realm);
        town.readFile();

         // using abstract utility class, we convert Chunk to ArrayList, and look for him in camp file
        RedChunk region = RedData.loadChunk(chunk);
        region.readFile();
        if (region.getOwner() != null){
            commandSender.sendMessage("Это территория города " + region.getOwner());
            return true;
        }
        region.setOwner(redPlayer.getRealmName());
        region.updateFile();
        town.addChunk(chunk);

        town.updateFile();
        commandSender.sendMessage("Чанк с координатами X=" +playerSender.getLocation().getChunk().getX() +" Z = " + playerSender.getLocation().getChunk().getZ()+" Добавлен во владения города " + Realm);
        return true;
    }

}

package org.concordiacraft.redrealms.commands.town;

import org.bukkit.Chunk;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.concordiacraft.redrealms.data.DataChunk;
import org.concordiacraft.redrealms.data.DataPlayer;
import org.concordiacraft.redrealms.data.DataRegion;
import org.concordiacraft.redrealms.main.RedRealms;
import org.concordiacraft.redrealms.utilits.ChunkWork;

public class RegionMgmt implements CommandExecutor {
    RedRealms plugin;
    public RegionMgmt (RedRealms plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("Данная команда доступна только для игроков");
            return true;
        }
        Player player = (Player) commandSender;
        if (strings[0].equalsIgnoreCase("create")){
            if (strings.length!=2) {player.sendMessage("Введите название региона"); return true;}
            DataPlayer dataPlayer = new DataPlayer(player);
            DataRegion region = new DataRegion(strings[1],dataPlayer.getRealm());
            if (region.getFile().exists()) {player.sendMessage("Регион с таким названием уже существует"); return true;}
            Chunk gameChunk = player.getLocation().getChunk();
            DataChunk chunk = new DataChunk(gameChunk);

            if (chunk.getTownRegion()!=null||chunk.getOwner()!=null) {player.sendMessage("Этот чанк уже занят другим регионом или не находится в городе"); return true;}
            chunk.setTownRegion(strings[1]);
            region.addChunk(gameChunk);
            chunk.updateFile();
            region.updateFile();
            commandSender.sendMessage("Вы успешно создали регион " + strings[0]);
            return true;
        }
        if (strings[0].equalsIgnoreCase("add")) {
            if (strings.length!=2) {commandSender.sendMessage("Введите название региона"); return true;}
            DataPlayer dataPlayer = new DataPlayer(player);
            if (dataPlayer.getRealm()==null) {commandSender.sendMessage("Вы даже не гражданин"); return true;}
            DataRegion region = new DataRegion(strings[1],dataPlayer.getRealm());
            if (!region.readFile()) {commandSender.sendMessage("Данного региона не существует"); return true;}
            DataChunk chunk = new DataChunk(player.getLocation().getChunk());
            if (chunk.getTownRegion()!=null) {commandSender.sendMessage("Данный чанк принадлежит другому региону!"); return true;}
            if (!chunk.getOwner().equalsIgnoreCase(dataPlayer.getRealm())){commandSender.sendMessage("Данный регион не принадлежит вашему городу"); return true;}
            chunk.setTownRegion(strings[1]);
            region.addChunk(player.getLocation().getChunk());
            chunk.updateFile();
            region.updateFile();
        }
        if (strings[0].equalsIgnoreCase("remove")) {
            if (strings.length!=2) {commandSender.sendMessage("Введите название региона"); return true;}
            DataPlayer dataPlayer = new DataPlayer(player);
            if (dataPlayer.getRealm()==null) {commandSender.sendMessage("Вы даже не гражданин"); return true;}
            DataRegion region = new DataRegion(strings[1],dataPlayer.getRealm());
            if (!region.readFile()) {commandSender.sendMessage("Данный чанк не занят регионом"); return true;}
            if (region.getChunks().contains(ChunkWork.chunkCreate(((Player) commandSender).getLocation().getChunk()))) {
               region.removeChunk(((Player) commandSender).getLocation().getChunk());
                commandSender.sendMessage("Данный чанк удален из региона"+region.getName()); return true;
            }
            commandSender.sendMessage("Данный чанк не занят регионом"); return true;
        }
        if (strings[0].equalsIgnoreCase("set")) {
            if (strings.length>3) {return false;}
            DataPlayer playerSender = new DataPlayer(player);
            DataRegion region = new DataRegion(strings[1],playerSender.getRealm());
            if (!region.readFile()) {
                commandSender.sendMessage("Region does not exists");
            }
            //todo set chunkProfs/free
        }
        return false;
    }
}

package org.concordiacraft.redrealms.commands.town;

import org.bukkit.Chunk;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.concordiacraft.redrealms.data.RedChunk;
import org.concordiacraft.redrealms.data.RedPlayer;
import org.concordiacraft.redrealms.data.RedRegion;
import org.concordiacraft.redrealms.main.RedRealms;
import org.concordiacraft.redrealms.utilits.ChunkWork;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RegionManagament implements CommandExecutor {
    RedRealms plugin;
    public RegionManagament(RedRealms plugin) {
        this.plugin = plugin;
    }
    private final List<String> chunkProfs = new ArrayList<>(Arrays.asList("bank","farm","market","jail"));
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("Данная команда доступна только для игроков");
            return true;
        }
        Player player = (Player) commandSender;
        if (strings[0].equalsIgnoreCase("create")){
            if (strings.length!=2) {player.sendMessage("Введите название региона"); return true;}
            RedPlayer redPlayer = new RedPlayer(player);
            RedRegion region = new RedRegion(strings[1], redPlayer.getRealm());
            if (region.getFile().exists()) {player.sendMessage("Регион с таким названием уже существует"); return true;}
            Chunk gameChunk = player.getLocation().getChunk();
            RedChunk chunk = new RedChunk(gameChunk);

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
            RedPlayer redPlayer = new RedPlayer(player);
            if (redPlayer.getRealm()==null) {commandSender.sendMessage("Вы даже не гражданин"); return true;}
            RedRegion region = new RedRegion(strings[1], redPlayer.getRealm());
            if (!region.readFile()) {commandSender.sendMessage("Данного региона не существует"); return true;}
            RedChunk chunk = new RedChunk(player.getLocation().getChunk());
            if (chunk.getTownRegion()!=null) {commandSender.sendMessage("Данный чанк принадлежит другому региону!"); return true;}
            if (!chunk.getOwner().equalsIgnoreCase(redPlayer.getRealm())){commandSender.sendMessage("Данный регион не принадлежит вашему городу"); return true;}
            chunk.setTownRegion(strings[1]);
            region.addChunk(player.getLocation().getChunk());
            chunk.updateFile();
            region.updateFile();
        }
        if (strings[0].equalsIgnoreCase("remove")) {
            if (strings.length!=2) {commandSender.sendMessage("Введите название региона"); return true;}
            RedPlayer redPlayer = new RedPlayer(player);
            if (redPlayer.getRealm()==null) {commandSender.sendMessage("Вы даже не гражданин"); return true;}
            RedRegion region = new RedRegion(strings[1], redPlayer.getRealm());
            if (!region.readFile()) {commandSender.sendMessage("Данный чанк не занят регионом"); return true;}
            if (region.getChunks().contains(ChunkWork.chunkCreate(((Player) commandSender).getLocation().getChunk()))) {
               region.removeChunk(((Player) commandSender).getLocation().getChunk());
                commandSender.sendMessage("Данный чанк удален из региона"+region.getName()); return true;
            }
            commandSender.sendMessage("Данный чанк не занят регионом"); return true;
        }
        if (strings[0].equalsIgnoreCase("set")) {
            if (strings.length>3) {return false;}
            RedPlayer playerSender = new RedPlayer(player);
            RedRegion region = new RedRegion(strings[1],playerSender.getRealm());
            if (!region.readFile()) {
                commandSender.sendMessage("Region does not exists");
                return true;
            }
            //todo set chunkProfs/free
            if(!chunkProfs.contains(strings[2])){commandSender.sendMessage("Данной специализации региона не существует");return true;}
            region.getChunks().forEach(chunk -> {
                RedChunk mChunk = new RedChunk(chunk);
                mChunk.setChunkProf(strings[2]);
            });
            commandSender.sendMessage("Специализация чанков успешно изменена!");
            return true;
        }
        return false;
    }
}

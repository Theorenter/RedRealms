package org.concordiacraft.redrealms.commands.town;

import org.bukkit.Chunk;
import org.concordiacraft.redrealms.config.ConfigLocalization;
import org.concordiacraft.redrealms.data.RedTown;
import org.concordiacraft.redrealms.data.RedPlayer;
import org.concordiacraft.redrealms.data.RedChunk;
import org.concordiacraft.redrealms.main.RedRealms;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Deprecated
public class TownCreate implements CommandExecutor {

    RedRealms plugin;
    public TownCreate(RedRealms plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(ConfigLocalization.getRawString("msg_error_only_for_players"));
            return true;
        }

        if (strings.length != 1)
            return false;

        Player playerSender = (Player) commandSender;
        Chunk chunk = playerSender.getLocation().getChunk();
        RedTown town = new RedTown(strings[0]);
        if (town.readFile()) {
            commandSender.sendMessage(ConfigLocalization.getString("msg_error_this_name_already"));
            return true;
        }
        //add data in town file
        town.setMayorID(playerSender.getUniqueId().toString());
        town.addResident(playerSender.getUniqueId().toString());
        town.setChunkCoords(chunk.getX(),chunk.getZ());
        town.addChunk(chunk.getX(),chunk.getZ());
        town.updateFile();
        //creating region file
        RedChunk region = new RedChunk(chunk);
        region.readFile();
        if (region.getOwner() != null){
            commandSender.sendMessage(ConfigLocalization.getString("msg_error_this_territory_already"));
            return true;
        }
        region.setOwner(strings[0]);
        region.setTownRegion("capital");
        region.updateFile();
        //add data in player file
        RedPlayer redPlayer = new RedPlayer(playerSender);
        redPlayer.setRealm(strings[0]);
        redPlayer.updateFile();
        playerSender.sendMessage("Лагерь "+ strings[0]+ " успешно создан!");

        return true;
    }

}

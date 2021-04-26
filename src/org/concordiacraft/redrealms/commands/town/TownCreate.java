package org.concordiacraft.redrealms.commands.town;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.concordiacraft.redrealms.config.ConfigLocalization;
import org.concordiacraft.redrealms.data.DataTown;
import org.concordiacraft.redrealms.data.DataPlayer;
import org.concordiacraft.redrealms.data.DataChunk;
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
            commandSender.sendMessage(ConfigLocalization.getString("msg_error_only_for_players"));
            return true;
        }

        if (strings.length != 1)
            return false;

        Player playerSender = (Player) commandSender;
        Chunk chunk = playerSender.getLocation().getChunk();
        DataTown town = new DataTown(strings[0]);
        if (town.readFile()) {
            commandSender.sendMessage(ConfigLocalization.getFormatString("msg_error_this_name_already"));
            return true;
        }
        //add data in town file
        town.setPlayerID(playerSender.getUniqueId().toString());
        town.addResident(playerSender.getUniqueId().toString());
        town.setChunkCoords(chunk.getX(),chunk.getZ());
        town.addChunk(chunk.getX(),chunk.getZ());
        town.updateFile();
        //creating region file
        DataChunk region = new DataChunk(chunk);
        region.readFile();
        if (region.getOwner() != null){
            commandSender.sendMessage(ConfigLocalization.getFormatString("msg_error_this_territory_already"));
            return true;
        }
        region.setOwner(strings[0]);
        region.setTownRegion("capital");
        region.updateFile();
        //add data in player file
        DataPlayer dataPlayer = new DataPlayer(playerSender);
        dataPlayer.setRealm(strings[0]);
        dataPlayer.updateFile();
        playerSender.sendMessage("Лагерь "+ strings[0]+ " успешно создан!");

        return true;
    }

}

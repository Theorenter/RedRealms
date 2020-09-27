package org.concordacraft.redrealms.listener;

import org.concordacraft.redrealms.data.DataPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.File;

public class FirstJoinPlayer implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void firstPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();

        File dataPlayerFile = new File(DataPlayer.getDataPath() + File.separator + p.getUniqueId().toString() + ".yml");
        if (!dataPlayerFile.exists()) { DataPlayer dataPlayer = new DataPlayer(p, dataPlayerFile); }
    }
}

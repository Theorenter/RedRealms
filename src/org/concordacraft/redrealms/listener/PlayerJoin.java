package org.concordacraft.redrealms.listener;

import org.concordacraft.redrealms.config.ConfigDefault;
import org.concordacraft.redrealms.data.DataPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.concordacraft.redrealms.main.RedLog;

public class PlayerJoin implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void firstPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        p.setResourcePack(ConfigDefault.getResourcePackLink());

        DataPlayer dataPlayer = new DataPlayer(p);
        if (!dataPlayer.getFile().exists()) { dataPlayer.updateFile(); }
    }
}

package org.concordacraft.redrealms.main;

import org.concordacraft.redrealms.commands.AddChunk;
import org.concordacraft.redrealms.commands.CampCreate;
import org.concordacraft.redrealms.config.RedRealmsSettings;
import org.concordacraft.redrealms.data.RedRealmsData;
import org.concordacraft.redrealms.listener.FirstJoinPlayer;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class RedRealms extends JavaPlugin {

    private Boolean hasNBTApi = false;

    @Override
    public void onEnable() {
        RedLog.showPluginTitle();

        // Settings init
        RedRealmsSettings.initialization(this);
        RedRealmsData.initialization(this);


        // Listeners
        Bukkit.getPluginManager().registerEvents(new FirstJoinPlayer(), this);

        // Commands
        getCommand("campcreate").setExecutor(new CampCreate(this));
        getCommand("addchunk").setExecutor(new AddChunk(this));

    }

    @Override
    public void onDisable() {

    }
    public static RedRealms getPlugin() {
        return RedRealms.getPlugin(RedRealms.class);
    }
    public Boolean hasNBTApi () { return hasNBTApi(); };
}

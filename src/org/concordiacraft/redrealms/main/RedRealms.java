package org.concordiacraft.redrealms.main;

import org.concordiacraft.redrealms.commands.AddChunk;
import org.concordiacraft.redrealms.commands.CampCreate;
import org.concordiacraft.redrealms.config.RedRealmsSettings;
import org.concordiacraft.redrealms.data.RedRealmsData;
import org.concordiacraft.redrealms.listener.CustomItemShieldBreaker;
import org.concordiacraft.redrealms.listener.PlayerJoin;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class RedRealms extends JavaPlugin {

    @Override
    public void onEnable() {
        RedLog.showPluginTitle();

        // Settings init
        RedRealmsSettings.initialization(this);
        RedRealmsData.initialization(this);


        // Events & Listeners
        Bukkit.getPluginManager().registerEvents(new CustomItemShieldBreaker(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerJoin(), this);

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
}

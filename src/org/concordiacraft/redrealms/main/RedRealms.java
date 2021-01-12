package org.concordiacraft.redrealms.main;

import org.concordiacraft.redrealms.addons.AddonManager;
import org.concordiacraft.redrealms.commands.AddChunk;
import org.concordiacraft.redrealms.commands.town.TownCreate;
import org.concordiacraft.redrealms.config.RedRealmsSettings;
import org.concordiacraft.redrealms.data.RedRealmsData;
import org.concordiacraft.redrealms.listeners.PlayerJoin;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class RedRealms extends JavaPlugin {

    @Override
    public void onEnable() {
        RedLog.showPluginTitle();
        // Loading addon-manager
        AddonManager.initialization();

        // Settings init
        RedRealmsSettings.initialization(this);
        RedRealmsData.initialization(this);

        // Events & Listeners (without addons)
        Bukkit.getPluginManager().registerEvents(new PlayerJoin(), this);

        // Commands
        getCommand("campcreate").setExecutor(new TownCreate(this));
        getCommand("addchunk").setExecutor(new AddChunk(this));

    }

    @Override
    public void onDisable() {

    }
    public static RedRealms getPlugin() {
        return RedRealms.getPlugin(RedRealms.class);
    }
}

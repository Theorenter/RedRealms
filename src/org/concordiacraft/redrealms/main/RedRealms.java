package org.concordiacraft.redrealms.main;

import org.concordiacraft.redrealms.addons.AddonManager;
import org.concordiacraft.redrealms.commands.AddChunk;
import org.concordiacraft.redrealms.commands.town.RegionManagament;
import org.concordiacraft.redrealms.config.RedConfigManager;
import org.concordiacraft.redrealms.data.PromptData;
import org.concordiacraft.redrealms.data.RedRealmsData;
import org.concordiacraft.redrealms.listeners.PlayerInteractListener;
import org.concordiacraft.redrealms.listeners.PlayerJoin;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.concordiacraft.redrealms.listeners.TownListener;
import org.concordiacraft.redrealms.listeners.chunkguard.ChunkGuardListener;
import org.concordiacraft.redutils.main.RedPlugin;
import org.concordiacraft.redutils.main.utils.RedLog;

public class RedRealms extends JavaPlugin implements RedPlugin {

    private static RedLog redlog;

    @Override
    public void onEnable() {
        redlog = new RedLog(this);
        redlog.showPluginTitle();
        // Loading addon-manager
        AddonManager.initialization();

        // Settings init
        RedConfigManager.initialization(this);
        RedRealmsData.initialization(this);

        // Listeners (without addons)
        Bukkit.getPluginManager().registerEvents(new PlayerJoin(), this);
        Bukkit.getPluginManager().registerEvents(new ChunkGuardListener(), this);
        Bukkit.getPluginManager().registerEvents(new TownListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerInteractListener(), this);

        // Commands
        getCommand("addchunk").setExecutor(new AddChunk(this));
        getCommand("region").setExecutor(new RegionManagament(this));

        // Data
        PromptData.loadPromptData(this);
    }

    @Override
    public void onDisable() {
    }
    public static RedRealms getPlugin() {
        return RedRealms.getPlugin(RedRealms.class);
    }

    @Override
    public boolean isDebug() {
        return true;
    }

    @Override
    public RedLog getRedLogger() {
        return redlog;
    }
}

package org.concordiacraft.redrealms.main;

import org.concordiacraft.redrealms.addons.AddonManager;
import org.concordiacraft.redrealms.commands.gui.Menu;
import org.concordiacraft.redrealms.commands.gui.Profile;
import org.concordiacraft.redrealms.commands.gui.Realm;
import org.concordiacraft.redrealms.commands.gui.Town;
import org.concordiacraft.redrealms.config.RedConfigManager;
import org.concordiacraft.redrealms.data.*;
import org.concordiacraft.redrealms.listeners.GUIListener;
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
        DataManager.initialization(this);

        // Listeners (without addons)
        Bukkit.getPluginManager().registerEvents(new PlayerJoin(), this);
        Bukkit.getPluginManager().registerEvents(new ChunkGuardListener(), this);
        Bukkit.getPluginManager().registerEvents(new TownListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerInteractListener(), this);
        Bukkit.getPluginManager().registerEvents(new GUIListener(), this);

        // Commands
        getCommand("menu").setExecutor(new Menu());
        getCommand("profile").setExecutor(new Profile());
        getCommand("town").setExecutor(new Town());
        getCommand("realm").setExecutor(new Realm());

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

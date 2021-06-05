package org.concordiacraft.redrealms.main;

import org.concordiacraft.redrealms.addons.AddonManager;
import org.concordiacraft.redrealms.commands.gui.Menu;
import org.concordiacraft.redrealms.commands.redcommands.town.Town;
import org.concordiacraft.redrealms.config.ConfigDefault;
import org.concordiacraft.redrealms.config.ConfigLoader;
import org.concordiacraft.redrealms.config.ConfigLocalization;
import org.concordiacraft.redrealms.data.*;
import org.concordiacraft.redrealms.listeners.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.concordiacraft.redrealms.listeners.chunkguard.ChunkGuardListener;
import org.concordiacraft.redutils.main.RedPlugin;
import org.concordiacraft.redutils.utils.RedLog;

public class RedRealms extends JavaPlugin implements RedPlugin {

    private static RedLog redlog;

    private static ConfigDefault config;
    private static ConfigLocalization localization;

    @Override
    public void onEnable() {
        redlog = new RedLog(this);
        redlog.showPluginTitle();
        // Loading addon-manager
        AddonManager.init();

        // Settings init
        ConfigLoader.init(this);
        DataLoader.init(this);

        // Listeners (without addons)
        Bukkit.getPluginManager().registerEvents(new PlayerJoin(), this);
        Bukkit.getPluginManager().registerEvents(new RuleListener(), this);
        Bukkit.getPluginManager().registerEvents(new ChunkGuardListener(), this);
        Bukkit.getPluginManager().registerEvents(new TownListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerInteractListener(), this);
        Bukkit.getPluginManager().registerEvents(new GUIListener(), this);

        // Commands
        getCommand("menu").setExecutor(new Menu());
        getCommand("town").setExecutor(new Town());
        //getCommand("profile").setExecutor(new Profile());
        //getCommand("realm").setExecutor(new Realm());

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

    public void setConfig(ConfigDefault config) {
        RedRealms.config = config;
    }

    public void setLocalization(ConfigLocalization localization) {
        RedRealms.localization = localization;
    }

    public static ConfigDefault getDefaultConfig() {
        return config;
    }

    public static ConfigLocalization getLocalization() {
        return localization;
    }
}

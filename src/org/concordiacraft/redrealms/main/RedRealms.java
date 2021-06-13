package org.concordiacraft.redrealms.main;

import net.tnemc.core.TNE;
import net.tnemc.core.common.api.TNEAPI;
import org.bukkit.entity.Player;
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

import java.io.File;
import java.util.List;

public class RedRealms extends JavaPlugin implements RedPlugin {

    private static boolean debug = true;
    private static RedLog redlog;

    private static ConfigDefault config;
    private static ConfigLocalization localization;
    private static TNEAPI TNEAPI;

    @Override
    public void onEnable() {
        redlog = new RedLog(this);
        redlog.showPluginTitle();
        // Loading addon-manager
        AddonManager.init();

        // Economy
        TNEAPI = TNE.instance().api();
        // Settings init
        ConfigLoader.init(this);
        DataLoader.init(this);

        //RedAdvancementManager.init();

        // Listeners (without addons)
        Bukkit.getPluginManager().registerEvents(new PlayerJoin(), this);
        Bukkit.getPluginManager().registerEvents(new ChunkGuardListener(), this);
        Bukkit.getPluginManager().registerEvents(new TownListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerInteractListener(), this);
        Bukkit.getPluginManager().registerEvents(new GUIListener(), this);
        Bukkit.getPluginManager().registerEvents(new RuleListener(), this);
        //Bukkit.getPluginManager().registerEvents(new RecipeDiscoverListener(), this);

        // Commands
        getCommand("menu").setExecutor(new Menu());
        getCommand("town").setExecutor(new Town());
        //getCommand("profile").setExecutor(new Profile());
        //getCommand("realm").setExecutor(new Realm());

        // Data
        PromptData.loadPromptData(this);

        // Read data about RedPlayers if players stay on the server after reload
        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
            RedPlayer.loadPlayer(p);
        }

        File townFolder = new File(this.getDataFolder() + File.separator + "data" + File.separator + "towns" + File.separator);
        File[] towns = townFolder.listFiles();
        if (towns == null)
            return;
        for (File townFile : townFolder.listFiles()) {
            String tName = townFile.getName();
            int index = tName.indexOf('.');
            tName = tName.substring(0, index);
            RedTown.loadTown(tName);
            redlog.debug(tName + " was read");
        }
    }

    @Override
    public void onDisable() {
    }

    public static RedRealms getPlugin() {
        return RedRealms.getPlugin(RedRealms.class);
    }
    @Override
    public boolean isDebug() { return debug; }
    @Override
    public void setDebug(boolean debugStatus) { debug = debugStatus; }

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

    public static TNEAPI getTNEAPI() {
        return TNEAPI;
    }
}

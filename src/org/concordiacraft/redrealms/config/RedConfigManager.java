package org.concordiacraft.redrealms.config;

import org.concordiacraft.redrealms.main.RedRealms;

import java.io.File;

/**
 * @author Theorenter
 * Plugin Configuration File Loader
 */
public class RedConfigManager {


    // Config Initialization
    public static void initialization(RedRealms plugin) {

        //region Checking for data folder existence
        File redRealmsDataFolder = plugin.getDataFolder();
        if (!redRealmsDataFolder.isDirectory())
        {
            plugin.getRedLogger().warning("The folder with the plugin settings storage was not detected");
            plugin.getRedLogger().warning("Creating a new settings folder");
            redRealmsDataFolder.mkdir();
        }
        //endregion

        // Settings loading

        ConfigDefault configDefault = new ConfigDefault(plugin,"settings" + File.separator + "config.yml");
        ConfigLocalization configLocalization = new ConfigLocalization(plugin, "settings" + File.separator + "localization" + File.separator + ConfigDefault.getGlobalLocalization());

        // End message
        plugin.getRedLogger().info("The plugin configuration was loaded");
    }
}

package org.concordiacraft.redrealms.config;

import org.concordiacraft.redrealms.main.RedRealms;
import org.concordiacraft.redrealms.rules.RuleManager;
import org.concordiacraft.redrealms.utilits.BiomeManager;

import java.io.File;

/**
 * @author Theorenter
 * Plugin Configuration File Loader
 */
public final class ConfigLoader {

    // Config Initialization
    public static void init(RedRealms plugin) {

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
        plugin.setConfig(new ConfigDefault(plugin,"settings" + File.separator + "config.yml"));
        plugin.setLocalization(new ConfigLocalization(plugin, "settings" + File.separator + "localization" + File.separator + RedRealms.getDefaultConfig().getLocalizationFileName()));

        RuleManager.init(plugin);


        // End message
        plugin.getRedLogger().info("The plugin configuration was loaded");
    }
}

package org.concordiacraft.redrealms.config;

import org.concordiacraft.redrealms.main.RedRealms;

import java.io.File;

public class RedRealmsSettings {


    // Config Initialization
    public static void initialization(RedRealms plugin) {

        //region Checking for data folder existence
        File redRealmsDataFolder = plugin.getDataFolder();
        if (!redRealmsDataFolder.isDirectory())
        {
            RedRealms.redlog.warning("The folder with the plugin settings storage was not detected");
            redRealmsDataFolder.mkdir();
            RedRealms.redlog.warning("Creating a new settings folder");
        }
        //endregion

        // Settings loading

        ConfigDefault configDefault = new ConfigDefault(plugin,"settings" + File.separator + "config.yml");
        ConfigLocalization configLocalization = new ConfigLocalization(plugin, "settings" + File.separator + "localization" + File.separator + ConfigDefault.getGlobalLocalization());

        // End message
        RedRealms.redlog.info("The plugin configuration was loaded");
    }
}

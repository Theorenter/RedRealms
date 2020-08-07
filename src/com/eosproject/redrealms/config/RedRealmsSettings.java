package com.eosproject.redrealms.config;

import com.eosproject.redrealms.main.RedLog;
import com.eosproject.redrealms.main.RedRealms;

import java.io.File;

public class RedRealmsSettings {

    // Main class
    private static RedRealms plugin = RedRealms.getPlugin(RedRealms.class);

    // Config Initialization
    public static void initialization() {

        //region Checking for data folder existence
        File redRealmsDataFolder = plugin.getDataFolder();
        if (!redRealmsDataFolder.isDirectory())
        {
            RedLog.warning("The folder with the plugin settings storage was not detected.");
            redRealmsDataFolder.mkdir();
            RedLog.warning("Creating a new settings folder.");
        }
        //endregion

        // Settings loading
        ConfigDefault configDefault = new ConfigDefault("config.yml");
        ConfigMaterialManager configMaterialsManager = new ConfigMaterialManager("materials.yml");

        // Data loading


        // End message
        RedLog.info("The plugin configuration was loaded.");
    }
}

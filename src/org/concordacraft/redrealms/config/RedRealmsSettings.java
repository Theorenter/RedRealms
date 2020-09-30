package org.concordacraft.redrealms.config;

import org.concordacraft.redrealms.main.RedLog;
import org.concordacraft.redrealms.main.RedRealms;

import java.io.File;

public class RedRealmsSettings {


    // Config Initialization
    public static void initialization(RedRealms plugin) {

        //region Checking for data folder existence
        File redRealmsDataFolder = plugin.getDataFolder();
        if (!redRealmsDataFolder.isDirectory())
        {
            RedLog.warning("The folder with the plugin settings storage was not detected");
            redRealmsDataFolder.mkdir();
            RedLog.warning("Creating a new settings folder");
        }
        //endregion

        // Settings loading
        ConfigDefault configDefault = new ConfigDefault(plugin,"config.yml");
        ConfigMaterialManager configMaterialsManager = new ConfigMaterialManager(plugin, "materials.yml");


        // End message
        RedLog.info("The plugin configuration was loaded");
    }
}

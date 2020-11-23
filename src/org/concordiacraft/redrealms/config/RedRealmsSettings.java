package org.concordiacraft.redrealms.config;

import org.concordiacraft.redrealms.main.RedLog;
import org.concordiacraft.redrealms.main.RedRealms;
import org.concordiacraft.redrealms.materials.MaterialManager;

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
        MaterialManager materialReader = new MaterialManager(plugin);


        // End message
        RedLog.info("The plugin configuration was loaded");
    }
}

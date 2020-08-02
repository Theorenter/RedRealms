package com.eosproject.redrealms.file;

import com.eosproject.redrealms.RedRealms;
import com.eosproject.redrealms.file.settings.MaterialsFile;

import java.io.File;

public class LoaderFileManager {

    // Main class
    private static RedRealms plugin;

    // Constructor
    public LoaderFileManager(RedRealms plugin) {

        this.plugin = plugin;

        //region Checking for data folder existence
        File redRealmsDataFolder = plugin.getDataFolder();
        if (!redRealmsDataFolder.isDirectory())
        {
            plugin.log.info("The folder with the plugin data storage was not detected. Creating a new folder.");
            redRealmsDataFolder.mkdir();
        }
        //endregion

        // Settings loading
        MaterialsFile materialsFile = new MaterialsFile("materials.yml");

        // Data loading
    }
}

package org.concordiacraft.redrealms.config;

import org.concordiacraft.redrealms.main.RedLog;
import org.concordiacraft.redrealms.main.RedRealms;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public abstract class ConfigAbstractSetup {

    // CustomConfig fields
    protected static File customFile;
    protected static FileConfiguration customConfig;

    // Name of .yml that we will work with
    protected static String fullFileName;

    // Constructor
    ConfigAbstractSetup(RedRealms plugin, String fullFileName) {

        // File Creation
        this.fullFileName = fullFileName;

        customFile = new File(plugin.getDataFolder(),  fullFileName);
        if (!customFile.exists()) {
            RedLog.warning(fullFileName + " was not found. Create new ones.");
            customFile.getParentFile().mkdirs();
            plugin.saveResource(fullFileName, false);
        }

        // File configuration
        customConfig = YamlConfiguration.loadConfiguration(customFile);
    }

    public static void SaveCustomConfig() {
        try {
            customConfig.save(customFile);
            RedLog.info(fullFileName + " has been saved successfully!");
        } catch (IOException e) {
            RedLog.error("Could not save the " + fullFileName, e);
        }
    }

    public static void reloadCustomConfig() {
        customConfig = YamlConfiguration.loadConfiguration(customFile);
    }

    // Config Getter
    public static FileConfiguration getCustomConfig() {
        return customConfig;
    }
}

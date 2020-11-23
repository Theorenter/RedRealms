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

    // Path to settings files
    protected static String settingsPath = "settings" + File.separator;

    // Name of .yml that we will work with
    protected static String YMLFileName;

    // Constructor
    ConfigAbstractSetup(RedRealms plugin, String YMLFileName) {

        // File Creation
        this.YMLFileName = YMLFileName;

        customFile = new File(plugin.getDataFolder(), settingsPath + YMLFileName);
        if (!customFile.exists()) {
            RedLog.warning(YMLFileName + " was not found. Create new ones.");
            customFile.getParentFile().mkdirs();
            plugin.saveResource(settingsPath + YMLFileName, false);
        }

        // File configuration
        customConfig = YamlConfiguration.loadConfiguration(customFile);
    }

    public static void SaveCustomConfig() {
        try {
            customConfig.save(customFile);
            RedLog.info(YMLFileName + " has been saved successfully!");
        } catch (IOException e) {
            RedLog.error("Could not save the " + YMLFileName + " file!", e);
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

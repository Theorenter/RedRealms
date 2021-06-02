package org.concordiacraft.redrealms.config;


import org.concordiacraft.redrealms.main.RedRealms;
import org.concordiacraft.redutils.config.ExtendedRedConfig;
import org.concordiacraft.redutils.utils.RedFormatter;

import java.util.List;

/**
 * @author Theorenter
 * Global localization config for the plugin
 */
public final class ConfigLocalization extends ExtendedRedConfig {
    ConfigLocalization(RedRealms plugin, String YMLFileName) {
        super(plugin, YMLFileName);
    }

    /**
     * This method returns raw string from the localization file
     * @param path yml-path to the localization string
     * @return the raw string from the localization file
     */
    public String getRawString(String path) {
        String str = this.customConfig.getString(path);
        if (str != null) { return str; }
        else {
            RedRealms.getPlugin().getRedLogger().warning("The line under ID \"" + path + "\" was not found.");
            return "The line under ID \"" + path + "\" was not found.";
        }
    }

    /**
     * This method returns formatted string from the localization file
     * @param path yml-path to the localization string
     * @return the formatted string from the localization file
     */
    public String getString(String path) {
        return RedFormatter.format(getRawString(path));
    }
}

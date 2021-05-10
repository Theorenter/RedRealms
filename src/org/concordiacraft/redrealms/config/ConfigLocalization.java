package org.concordiacraft.redrealms.config;


import org.concordiacraft.redrealms.main.RedRealms;
import org.concordiacraft.redutils.main.config.ConfigAbstractSetup;
import org.concordiacraft.redutils.main.utils.RedFormatter;

/**
 * @author Theorenter
 * Global localization config for the plugin
 */
public class ConfigLocalization extends ConfigAbstractSetup {
    ConfigLocalization(RedRealms plugin, String YMLFileName) {
        super(plugin, YMLFileName);
    }

    /**
     * This method returns raw string from the localization file
     * @param path yml-path to the localization string
     * @return the raw string from the localization file
     */
    public static String getRawString(String path) {
        String str = (String) getCustomConfig().get(path);
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
    public static String getString(String path) {
        return RedFormatter.format(getRawString(path));
    }
    /**
     * This method returns formatted strings from the localization file
     * @param path yml-path to the localization string
     * @return the formatted strings from the localization file
     */
    public static String[] getStrings(String... path) {
        return RedFormatter.format(path);
    }
}

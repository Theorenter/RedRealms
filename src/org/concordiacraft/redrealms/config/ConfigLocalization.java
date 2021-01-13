package org.concordiacraft.redrealms.config;


import org.concordiacraft.redrealms.main.RedRealms;
import org.concordiacraft.redutils.main.utils.RedFormatter;

public class ConfigLocalization extends ConfigAbstractSetup {
    ConfigLocalization(RedRealms plugin, String YMLFileName) {
        super(plugin, YMLFileName);
    }

    public static String getString(String path) {
        String str = (String) getCustomConfig().get(path);
        if (str != null) { return str; }
        else return "The line under ID \"" + path + "\" was not found.";
    }

    public static String getFormatString(String path) {
        return RedFormatter.format(getString(path));
    }
    public static String[] getFormatString(String... path) {
        return RedFormatter.format(path);
    }
}

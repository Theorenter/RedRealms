package org.concordiacraft.redrealms.config;

import org.concordiacraft.redrealms.main.RedRealms;

public class ConfigDefault extends ConfigAbstractSetup{

    private static String resourcePackLink;
    private static Boolean debugMode;
    private static String localization;

    ConfigDefault(RedRealms plugin, String YMLFileName) {
        super(plugin, YMLFileName);
        localization = (String) customConfig.get("localization");
        resourcePackLink = (String) customConfig.get("plugin.resource-pack");
        debugMode = (Boolean) customConfig.get("plugin.debug");
    }
    public static String getResourcePackLink() { return resourcePackLink; }
    public static Boolean isDebugMode() { return debugMode; }
    public static String getGlobalLocalization() { return localization; }
}

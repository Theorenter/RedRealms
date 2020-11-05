package org.concordacraft.redrealms.config;

import org.concordacraft.redrealms.main.RedRealms;

public class ConfigDefault extends ConfigAbstractSetup{

    private static String resourcePackLink;

    ConfigDefault(RedRealms plugin, String YMLFileName) {
        super(plugin, YMLFileName);
        resourcePackLink = (String) customConfig.get("plugin.resource-pack");
    }
    public static String getResourcePackLink() { return resourcePackLink; }
}

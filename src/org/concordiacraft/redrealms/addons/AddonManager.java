package org.concordiacraft.redrealms.addons;

import org.bukkit.Bukkit;
import org.concordiacraft.redrealms.listeners.reditems.TownBannerPlaceListener;
import org.concordiacraft.redrealms.main.RedRealms;

/**
 * @author Theorenter
 * Class manager for accounting for other RedPlagins
 */
public class AddonManager {

    private static Boolean redItemsStatus = null;
    private static Boolean redUtilsStatus = null;

    public static void initialization() {
        redItemsStatus = initializePlugin("RedItems");
        redUtilsStatus = initializePlugin("RedUtils");
        RedItemsManage(redItemsStatus);
    }
    private static boolean initializePlugin(String pluginName){
        boolean pluginStatus;
        try {
            pluginStatus = RedRealms.getPlugin().getServer().getPluginManager().getPlugin(pluginName).isEnabled();
        } catch (NullPointerException e) {
            pluginStatus = false;
        }
        return pluginStatus;
    }
    public static Boolean getRedItemsStatus() {
        return redItemsStatus;
    }

    private static void RedItemsManage(Boolean hasAddon) {
        if (hasAddon) {
            Bukkit.getPluginManager().registerEvents(new TownBannerPlaceListener(), RedRealms.getPlugin());
        } else {
            RedRealms.getPlugin().getRedLogger().warning("This server does not use the RedItems addon!");
        }
    }
}

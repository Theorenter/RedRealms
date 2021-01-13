package org.concordiacraft.redrealms.addons;

import org.bukkit.Bukkit;
import org.concordiacraft.redrealms.listeners.CustomItemRiderModifier;
import org.concordiacraft.redrealms.listeners.CustomItemShieldBreaker;
import org.concordiacraft.redrealms.listeners.TownNewListener;
import org.concordiacraft.redrealms.main.RedRealms;
import org.concordiacraft.redutils.main.utils.RedLog;

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
            Bukkit.getPluginManager().registerEvents(new CustomItemShieldBreaker(RedRealms.getPlugin()), RedRealms.getPlugin());
            Bukkit.getPluginManager().registerEvents(new CustomItemRiderModifier(RedRealms.getPlugin()), RedRealms.getPlugin());
            Bukkit.getPluginManager().registerEvents(new TownNewListener(RedRealms.getPlugin()), RedRealms.getPlugin());
        } else {
            RedRealms.redlog.warning("This server does not use the RedItems addon! ");
        }
    }
}

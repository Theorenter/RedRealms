package org.concordiacraft.redrealms.addons;

import org.bukkit.Bukkit;
import org.concordiacraft.redrealms.listeners.CustomItemRiderModifier;
import org.concordiacraft.redrealms.listeners.CustomItemShieldBreaker;
import org.concordiacraft.redrealms.listeners.TownNewListener;
import org.concordiacraft.redrealms.main.RedLog;
import org.concordiacraft.redrealms.main.RedRealms;

public class AddonManager {
    private static Boolean redItemsStatus = null;

    public static void initialization() {
        try {
            redItemsStatus = RedRealms.getPlugin().getServer().getPluginManager().getPlugin("RedItems").isEnabled();
        } catch (NullPointerException e) {
            redItemsStatus = false;
        }

        RedItemsManage(redItemsStatus);
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
            RedLog.warning("This server does not use the RedItems addon! ");
        }
    }
}

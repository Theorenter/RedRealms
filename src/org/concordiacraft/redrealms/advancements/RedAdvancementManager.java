package org.concordiacraft.redrealms.advancements;

import eu.endercentral.crazy_advancements.manager.AdvancementManager;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.concordiacraft.redrealms.main.RedRealms;

/**
 * @author Theorenter
 * Advancements Manager
 */
public final class RedAdvancementManager {

    private static AdvancementManager advancementManager;

    public static void init() {
        if (!RedRealms.getDefaultConfig().hasCustomAdvancements()) {
            for (World w : Bukkit.getServer().getWorlds()) {
                w.setGameRule(GameRule.DO_LIMITED_CRAFTING, false);
            }
            return;
        }
        advancementManager = new AdvancementManager();

        for (World w : Bukkit.getServer().getWorlds()) {
            w.setGameRule(GameRule.DO_LIMITED_CRAFTING, true);
        }


    }

    public static AdvancementManager getAdvancementManager() {
        return advancementManager;
    }
}

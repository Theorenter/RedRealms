package org.concordiacraft.redrealms.main;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.concordiacraft.redrealms.addons.AddonManager;
import org.concordiacraft.redrealms.commands.AddChunk;
import org.concordiacraft.redrealms.commands.town.RegionManagament;
import org.concordiacraft.redrealms.commands.town.TownCreate;
import org.concordiacraft.redrealms.config.RedRealmsSettings;
import org.concordiacraft.redrealms.data.RedRealmsData;
import org.concordiacraft.redrealms.listeners.PlayerJoin;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.concordiacraft.redrealms.listeners.chunkguard.ChunkGuardListener;
import org.concordiacraft.redutils.main.utils.RedLog;

public class RedRealms extends JavaPlugin {
    public static RedLog redlog;
    @Override
    public void onEnable() {
        redlog = new RedLog(this);
        redlog.showPluginTitle();
        // Loading addon-manager
        AddonManager.initialization();

        // Settings init
        RedRealmsSettings.initialization(this);
        RedRealmsData.initialization(this);

        // Events & Listeners (without addons)
        Bukkit.getPluginManager().registerEvents(new PlayerJoin(), this);
        Bukkit.getPluginManager().registerEvents(new ChunkGuardListener(), this);
        // Commands
        getCommand("towncreate").setExecutor(new TownCreate(this));
        getCommand("addchunk").setExecutor(new AddChunk(this));
        getCommand("region").setExecutor(new RegionManagament(this));

    }

    @Override
    public void onDisable() {

    }
    public static RedRealms getPlugin() {
        return RedRealms.getPlugin(RedRealms.class);
    }
}

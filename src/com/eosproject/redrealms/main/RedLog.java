package com.eosproject.redrealms.main;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public class RedLog {

    private static JavaPlugin plugin = RedRealms.getPlugin(RedRealms.class);

    public static void heading(String title) { plugin.getLogger().log(Level.INFO, "=================[ " + title + " ]================="); }
    public static void info(String message) { plugin.getLogger().log(Level.INFO, message); }
    public static void debug(String message) { plugin.getLogger().log(Level.INFO,"[DEBUG] " + message); }
    public static void warning(String message) { plugin.getLogger().log(Level.WARNING, message); }
    public static void error(String message) { plugin.getLogger().log(Level.SEVERE, message); }

    public static void info(String message, Exception e) { plugin.getLogger().log(Level.INFO, message, e); }
    public static void debug(String message, Exception e) { plugin.getLogger().log(Level.INFO,"[DEBUG] " + message, e); }
    public static void warning(String message, Exception e) { plugin.getLogger().log(Level.WARNING, message, e); }
    public static void error(String message, Exception e) { plugin.getLogger().log(Level.SEVERE, message, e); }
}

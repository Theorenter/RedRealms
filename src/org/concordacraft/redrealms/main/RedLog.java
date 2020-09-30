package org.concordacraft.redrealms.main;

import org.concordacraft.redrealms.config.ConfigDefault;
import org.concordacraft.redrealms.config.RedRealmsSettings;

import java.util.logging.Level;

public class RedLog {

    public static void heading(String title) { RedRealms.getPlugin().getLogger().log(Level.INFO, "=================[ " + title + " ]================="); }
    public static void info(String message) { RedRealms.getPlugin().getLogger().log(Level.INFO, message); }
    public static void debug(String message) { RedRealms.getPlugin().getLogger().log(Level.INFO,"[DEBUG] " + message); }
    public static void warning(String message) { RedRealms.getPlugin().getLogger().log(Level.WARNING, message); }
    public static void error(String message) { RedRealms.getPlugin().getLogger().log(Level.SEVERE, message); }

    public static void info(String message, Exception e) { RedRealms.getPlugin().getLogger().log(Level.INFO, message, e); }
    public static void debug(String message, Exception e) { RedRealms.getPlugin().getLogger().log(Level.INFO,"[DEBUG] " + message, e); }
    public static void warning(String message, Exception e) { RedRealms.getPlugin().getLogger().log(Level.WARNING, message, e); }
    public static void error(String message, Exception e) { RedRealms.getPlugin().getLogger().log(Level.SEVERE, message, e); }
}

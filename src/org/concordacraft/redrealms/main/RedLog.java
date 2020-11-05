package org.concordacraft.redrealms.main;

import jdk.internal.org.objectweb.asm.tree.FieldInsnNode;
import org.bukkit.ChatColor;
import org.fusesource.jansi.Ansi;

import java.util.logging.Level;

public class RedLog {

    public static void showPluginTitle() {
        RedRealms.getPlugin().getLogger().log(Level.INFO, "");
        RedRealms.getPlugin().getLogger().log(Level.INFO, ASCIIRed +" _____          _ " + ASCIIWhite + "_____            _               ");
        RedRealms.getPlugin().getLogger().log(Level.INFO, ASCIIRed +"|  __ \\        | |" + ASCIIWhite + "  __ \\          | |              ");
        RedRealms.getPlugin().getLogger().log(Level.INFO, ASCIIRed +"| |__) |___  __| |" + ASCIIWhite + " |__) |___  __ _| |_ __ ___  ___ ");
        RedRealms.getPlugin().getLogger().log(Level.INFO, ASCIIRed +"|  _  // _ \\/ _` |" + ASCIIWhite + "  _  // _ \\/ _` | | '_ ` _ \\/ __|");
        RedRealms.getPlugin().getLogger().log(Level.INFO, ASCIIRed +"| | \\ \\  __/ (_| |" + ASCIIWhite + " | \\ \\  __/ (_| | | | | | | \\__ \\");
        RedRealms.getPlugin().getLogger().log(Level.INFO, ASCIIRed +"|_|  \\_\\___|\\__,_|" + ASCIIWhite + "_|  \\_\\___|\\__,_|_|_| |_| |_|___/");
        RedRealms.getPlugin().getLogger().log(Level.INFO, "");
    }
    public static void info(String message) { RedRealms.getPlugin().getLogger().log(Level.INFO, message); }
    public static void debug(String message) { RedRealms.getPlugin().getLogger().log(Level.INFO,"[DEBUG] " + message); }
    public static void warning(String message) { RedRealms.getPlugin().getLogger().log(Level.WARNING, ASCIIYellow + message + ASCIIReset); }
    public static void error(String message) { RedRealms.getPlugin().getLogger().log(Level.SEVERE, ASCIIRed + message); }

    public static void info(String message, Exception e) { RedRealms.getPlugin().getLogger().log(Level.INFO, message, e); }
    public static void debug(String message, Exception e) { RedRealms.getPlugin().getLogger().log(Level.INFO,"[DEBUG] " + message, e); }
    public static void warning(String message, Exception e) { RedRealms.getPlugin().getLogger().log(Level.WARNING, ASCIIYellow + message, e); }
    public static void error(String message, Exception e) { RedRealms.getPlugin().getLogger().log(Level.SEVERE, ASCIIRed + message, e); }

    // ASCII colors
    private final static String ASCIIBlack = Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.BLACK).bold().toString();
    private final static String ASCIIBlue = Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.BLUE).boldOff().toString();
    private final static String ASCIICyan = Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.CYAN).boldOff().toString();
    private final static String ASCIIGreen = Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.GREEN).boldOff().toString();
    private final static String ASCIIMagenta = Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.MAGENTA).boldOff().toString();
    private final static String ASCIIRed = Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.RED).boldOff().toString();
    private final static String ASCIIWhite = Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.WHITE).boldOff().toString();
    private final static String ASCIIYellow = Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.YELLOW).boldOff().toString();
    private final static String ASCIIReset = Ansi.ansi().a(Ansi.Attribute.RESET).toString();


    public static String getASCIIBlack() { return ASCIIBlack; }
    public static String getASCIIBlue() { return ASCIIBlue; }
    public static String getASCIICyan() { return ASCIICyan; }
    public static String getASCIIGreen() { return ASCIIGreen; }
    public static String getASCIIMagenta() { return ASCIIMagenta; }
    public static String getASCIIRed() { return ASCIIRed; }
    public static String getASCIIWhite() { return ASCIIWhite; }
    public static String getASCIIYellow() { return ASCIIYellow; }
    public static String getASCIIReset() { return ASCIIReset; }
}

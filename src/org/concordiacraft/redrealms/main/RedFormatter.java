package org.concordiacraft.redrealms.main;

import net.md_5.bungee.api.ChatColor;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class RedFormatter {
    private static final Pattern hexColor = Pattern.compile("<#[a-fA-F0-9]{6}>");
    private static final Map<String, String> formatMap = new HashMap<String, String>() {{
        put("<reset>", "§r");
        put("<bold>", "§l");
        put("<italic>", "§o");
        put("<underline>", "§n");
        put("<strike>", "§m");
        put("<magic>", "§k");
    }};

    public static String format(String str) {
        Matcher matcher = hexColor.matcher(str);
        while (matcher.find()) {
            String fullColor = str.substring(matcher.start(), matcher.end());
            String color = str.substring(matcher.start() + 1, matcher.end() - 1);
            str = str.replace(fullColor, ChatColor.of(color).toString());
            matcher = hexColor.matcher(str);
        }
        for(Map.Entry<String, String> entry : formatMap.entrySet()) {
            String k = entry.getKey();
            String v = entry.getValue();
            while (str.contains(k)) {
                str = str.replace(k, v);
            }
        } return str;
    }
    public static String[] format(String... strs) {
        for (String str : strs) {
            Matcher matcher = hexColor.matcher(str);
            while (matcher.find()) {
                String fullColor = str.substring(matcher.start(), matcher.end());
                String color = str.substring(matcher.start() + 1, matcher.end() - 1);
                str = str.replace(fullColor, ChatColor.of(color).toString());
                matcher = hexColor.matcher(str);
            }
            for (Map.Entry<String, String> entry : formatMap.entrySet()) {
                String k = entry.getKey();
                String v = entry.getValue();
                while (str.contains(k)) { str = str.replace(k, v); }
            }
        }
        return strs;
    }
}
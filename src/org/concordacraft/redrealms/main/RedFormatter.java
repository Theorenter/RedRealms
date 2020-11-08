package org.concordacraft.redrealms.main;

import net.md_5.bungee.api.ChatColor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class RedFormatter {
    private static final Pattern hexColor = Pattern.compile("<#[a-fA-F0-9]{6}>");
    private static final String reset = "<reset>";
    private static final String bold = "<bold>";
    private static final String italic = "<italic>";
    private static final String underline = "<underline>";
    private static final String strikethrough = "<strikethrough>";
    private static final String magic = "<obfuscated>";

    public static final String format(String str) {
        str = setInternalReset(str);
        str = setInternalBold(str);
        str = setInternalItalic(str);
        str = setInternalUnderline(str);
        str = setInternalStrikethrough(str);
        str = setInternalMagic(str);
        str = setInternalColor(str);
        return str;
    }
    private static String setInternalColor(String str) {
        Matcher matcher = hexColor.matcher(str);
        while (matcher.find()) {
            String fullColor = str.substring(matcher.start(), matcher.end());
            String color = str.substring(matcher.start() + 1, matcher.end() - 1);
            str = str.replace(fullColor, ChatColor.of(color).toString());
            matcher = hexColor.matcher(str);
        } return str;
    }
    private static String setInternalReset(String str) {
        while (str.contains(reset)) {
            str = str.replace(reset, "§R");
            RedLog.debug(str);
        } return str;
    }
    private static String setInternalBold(String str) {
        while (str.contains(bold)) {
            str = str.replace(bold, "§l");
            RedLog.debug(str);
        } return str;
    }
    private static String setInternalItalic(String str) {
        while (str.contains(italic)) {
            str = str.replace(italic, "§o");
            RedLog.debug(str);
        } return str;
    }
    private static String setInternalUnderline(String str) {
        while (str.contains(underline)) {
            str = str.replace(underline, "§n");
            RedLog.debug(str);
        } return str;
    }
    private static String setInternalStrikethrough(String str) {
        while (str.contains(strikethrough)) {
            str = str.replace(strikethrough, "§m");
            RedLog.debug(str);
        } return str;
    }
    private static String setInternalMagic(String str) {
        while (str.contains(magic)) {
            str = str.replace(magic, "§k");
            RedLog.debug(str);
        } return str;
    }
}
        /*if (str.contains("<clr#")) {
            StringBuffer stringBuffer = new StringBuffer(str);
            while (str.contains("<clr#")) {
                String color;
                int startPos, endPos;

                startPos = stringBuffer.indexOf("<clr#");
                endPos = stringBuffer.indexOf(">");
                color = stringBuffer.substring(startPos + 4, endPos);

                stringBuffer.delete(startPos, endPos + 1);

                str = stringBuffer.toString();
                RedLog.info(str);
                break;
            }
        }*/
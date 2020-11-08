package org.concordacraft.redrealms.utilits;

import net.md_5.bungee.api.ChatColor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class RedColoring {
    private static final Pattern hexColor = Pattern.compile("<#[a-fA-F0-9]{6}>");
    /*("<normal>");
    ("<bold>");
    ("<italic>");
    ("<underline>");
    ("<strikethrough>");
    ("<obfuscated>");*/

    public static String setInternalColor(String str) {
        Matcher matcher = hexColor.matcher(str);
        while (matcher.find()) {
            String fullColor = str.substring(matcher.start(), matcher.end());
            String color = str.substring(matcher.start() + 1, matcher.end() - 1);
            str = str.replace(fullColor, ChatColor.of(color).toString());
            matcher = hexColor.matcher(str);
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
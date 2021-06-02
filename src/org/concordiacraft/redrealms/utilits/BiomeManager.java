package org.concordiacraft.redrealms.utilits;

import org.concordiacraft.redrealms.main.RedRealms;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class BiomeManager {

    public static String getBiomeType(String biomeName) {
        String biomeType;

        for (Map.Entry<String, List<String>> entry : RedRealms.getDefaultConfig().getBiomeTypes().entrySet()) {
            List<String> biomeList = entry.getValue();
            for (String b : biomeList) {
                if (b.equalsIgnoreCase(biomeName)) {
                    biomeType = entry.getKey();
                    return biomeType;
                }
            }
        }
        return null;
    }
}

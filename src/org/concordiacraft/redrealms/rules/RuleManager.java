package org.concordiacraft.redrealms.rules;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.concordiacraft.redrealms.main.RedRealms;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Theorenter
 * The class responsible for loading all extended rules and storing them
 */
public final class RuleManager {

    private static RedRealms plugin;

    private static ArrayList<RuleReform> allReforms = new ArrayList<>();
    private static ArrayList<RuleSoc> allSocs = new ArrayList<>();
    private static ArrayList<RuleTech> allTechs = new ArrayList<>();


    private static File reformsFile;
    private static File socsFile;
    private static File techsFile;

    public static void init(RedRealms plugin) {

        RuleManager.plugin = plugin;

        String inDataFolderPath = plugin.getDataFolder().getPath() + File.separator + "settings" + File.separator + "content" + File.separator + "rules";
        String inPluginPath = "settings" + File.separator + "content" + File.separator + "rules" + File.separator;

        // If the "rules" folder is missing, it is automatically created with all the contents
        File extendedRuleFiles = new File(inDataFolderPath);

        if (!(extendedRuleFiles.isDirectory())) {
            plugin.saveResource(inPluginPath + "reforms.yml", false);
            plugin.saveResource(inPluginPath + "socs.yml", false);
            plugin.saveResource(inPluginPath + "techs.yml", false);
            plugin.getRedLogger().warning("reforms.yml, socs.yml and techs.yml was created in " + inDataFolderPath + "\\");
        }

        reformsFile = new File(inDataFolderPath, "reforms.yml");
        socsFile = new File(inDataFolderPath, "socs.yml");
        techsFile = new File(inDataFolderPath, "techs.yml");

        FileConfiguration reformsConfig = YamlConfiguration.loadConfiguration(reformsFile);
        FileConfiguration socsConfig = YamlConfiguration.loadConfiguration(socsFile);
        FileConfiguration techsConfig = YamlConfiguration.loadConfiguration(techsFile);

        // Mark: although the initSocs() and initTechs()
        // methods are similar and may have
        // been the same method with different
        // parameter values, they are not the same
        // method because they may be revised later.

        plugin.getRedLogger().info("Loading Reforms...");
        initReforms(reformsConfig);

        plugin.getRedLogger().info("Loading Social research...");
        initSocs(socsConfig);

        plugin.getRedLogger().info("Loading Techs...");
        initTechs(techsConfig);
    }

    private static void initReforms(FileConfiguration reformsConfig) {
        List<Map<?, ?>> objMap = reformsConfig.getMapList("reforms");
        for (Map<?, ?> o : objMap) {
            String ID = (String) o.get("ID");
            HashMap<String, Boolean> rules = (HashMap<String, Boolean>) o.get("rules");
            String displayName = (String) o.get("displayName");
            ArrayList<String> description = (ArrayList<String>) o.get("description");
            Material material = Material.getMaterial((String) o.get("material"));
            int customModelData = (Integer) o.get("custom-model-data");
            HashMap<String, Boolean> requiredRules = null;

            boolean hasRequiredRules = false;

            if (o.containsKey("requirements")) {
                Map<String, ?> req = (Map<String, ?>) o.get("requirements");
                if (req.containsKey("required-rules")) {
                    hasRequiredRules = true;
                    requiredRules = new HashMap<>((HashMap<String, Boolean>) req.get("required-rules"));
                }
            }

            RuleReform ruleReform = new RuleReform(ID, rules, displayName, description, requiredRules, material, customModelData);
            allReforms.add(ruleReform);

            //ruleLoadingDebugLog(ID, rules, displayName, description, requiredRules, material, customModelData, hasRequiredRules);
        }
    }
    private static void initSocs(FileConfiguration socsConfig) {
        List<Map<?, ?>> objMap = socsConfig.getMapList("socs");
        for (Map<?, ?> o : objMap) {
            String ID = (String) o.get("ID");
            HashMap<String, Boolean> rules = (HashMap<String, Boolean>) o.get("rules");
            String displayName = (String) o.get("displayName");
            ArrayList<String> description = (ArrayList<String>) o.get("description");
            Material material = Material.getMaterial((String) o.get("material"));
            int customModelData = (Integer) o.get("custom-model-data");

            Map<String, Boolean> requiredRules = null;
            Map<String, Double> priceModifiers = null;

            Map<String, ?> req = (Map<String, ?>) o.get("requirements");
            double price = (Double) req.get("price");
            double exp = (Double) req.get("exp");

            boolean hasRequiredRules = req.containsKey("required-rules");
            boolean hasPriceModifiers = req.containsKey("price-modifiers");

            if (hasRequiredRules) {
                requiredRules = new HashMap((HashMap<String, Boolean>) req.get("required-rules"));
            }
            if (hasPriceModifiers) {
                priceModifiers = new HashMap((HashMap<String, Double>) req.get("price-modifiers"));
            }

            RuleSoc ruleSoc = new RuleSoc(ID, rules, displayName, description, requiredRules, material, customModelData, price, exp, priceModifiers);
            allSocs.add(ruleSoc);

            //ruleLoadingDebugLog(ID, rules, displayName, description, requiredRules, material, customModelData, price, exp, priceModifiers, hasRequiredRules, hasPriceModifiers);
        }
    }
    private static void initTechs(FileConfiguration techsConfig) {
        List<Map<?, ?>> objMap = techsConfig.getMapList("techs");
        for (Map<?, ?> o : objMap) {
            String ID = (String) o.get("ID");
            HashMap<String, Boolean> rules = (HashMap<String, Boolean>) o.get("rules");
            String displayName = (String) o.get("displayName");
            ArrayList<String> description = (ArrayList<String>) o.get("description");
            Material material = Material.getMaterial((String) o.get("material"));
            int customModelData = (Integer) o.get("custom-model-data");

            HashMap<String, Boolean> requiredRules = null;
            HashMap<String, Double> priceModifiers = null;

            Map<String, ?> req = (Map<String, ?>) o.get("requirements");
            double price = (Double) req.get("price");
            double exp = (Double) req.get("exp");

            boolean hasRequiredRules = req.containsKey("required-rules");
            boolean hasPriceModifiers = req.containsKey("price-modifiers");

            if (hasRequiredRules) {
                requiredRules = new HashMap((HashMap<String, Boolean>) req.get("required-rules"));
            }
            if (hasPriceModifiers) {
                priceModifiers = new HashMap((HashMap<String, Double>) req.get("price-modifiers"));
            }

            RuleTech ruleTech = new RuleTech(ID, rules, displayName, description, requiredRules, material, customModelData, price, exp, priceModifiers);
            allTechs.add(ruleTech);

            //ruleLoadingDebugLog(ID, rules, displayName, description, requiredRules, material, customModelData, price, exp, priceModifiers, hasRequiredRules, hasPriceModifiers);
        }
    }

    private static void ruleLoadingDebugLog(
            final String ID,
            final Map<String, Boolean> rules,
            final String displayName,
            final ArrayList<String> description,
            final Map<String, Boolean> requiredRules,
            final Material material,
            final int customModelData,
            final double price,
            final double exp,
            final Map<String, Double> priceModifiers,
            final boolean hasRequiredRules,
            final boolean hasPriceModifiers) {

        // Debug log
        plugin.getRedLogger().debugStyled("");
        plugin.getRedLogger().debugStyled("[");
        plugin.getRedLogger().debugStyled("    ID: " + ID);
        plugin.getRedLogger().debugStyled("    rules: ");
        for (Map.Entry<String, Boolean> entry : rules.entrySet()) {
            RedRealms.getPlugin().getRedLogger().debugStyled("        " + entry.toString());
        }
        plugin.getRedLogger().debugStyled("    displayName: " + displayName);
        plugin.getRedLogger().debugStyled("    description: [");
        for (String s : description) {
            RedRealms.getPlugin().getRedLogger().debugStyled("        " + s);
        }
        plugin.getRedLogger().debugStyled("    ]");
        plugin.getRedLogger().debugStyled("    material: " + material.getKey().getKey());
        plugin.getRedLogger().debugStyled("    customModelData: " + customModelData);
        if (hasRequiredRules) {
            plugin.getRedLogger().debugStyled("    requiredRules: [");
            for (Map.Entry<String, Boolean> entry : requiredRules.entrySet()) {
                plugin.getRedLogger().debugStyled("        " + entry.getKey() + ": " + entry.getValue());
            }
            plugin.getRedLogger().debugStyled("    ]");
        } else {
            plugin.getRedLogger().debugStyled("    requiredRules: null");
        }
        plugin.getRedLogger().debugStyled("    price: " + price);
        plugin.getRedLogger().debugStyled("    exp: " + exp);
        if (hasPriceModifiers) {
            plugin.getRedLogger().debugStyled("    priceModifiers: [");
            for (Map.Entry<String, Double> entry : priceModifiers.entrySet()) {
                plugin.getRedLogger().debugStyled("        " + entry.getKey() + ": " + entry.getValue());
            }
            plugin.getRedLogger().debugStyled("    ]");
        } else {
            plugin.getRedLogger().debugStyled("    priceModifiers: null");
        }
        plugin.getRedLogger().debugStyled("];");
    }

    private static void ruleLoadingDebugLog(
            final String ID,
            final Map<String, Boolean> rules,
            final String displayName,
            final ArrayList<String> description,
            final Map<String, Boolean> requiredRules,
            final Material material,
            final int customModelData,
            final boolean hasRequiredRules) {
        if (!plugin.isDebug()) { return; }

        // Debug log
        plugin.getRedLogger().debugStyled("");
        plugin.getRedLogger().debugStyled("[");
        plugin.getRedLogger().debugStyled("    ID: " + ID);
        plugin.getRedLogger().debugStyled("    rules: ");
        for (Map.Entry<String, Boolean> entry : rules.entrySet()) {
            RedRealms.getPlugin().getRedLogger().debugStyled("        " + entry.toString());
        }
        plugin.getRedLogger().debugStyled("    displayName: " + displayName);
        plugin.getRedLogger().debugStyled("    description: [");
        for (String s : description) {
            RedRealms.getPlugin().getRedLogger().debugStyled("        " + s);
        }
        plugin.getRedLogger().debugStyled("    ]");
        plugin.getRedLogger().debugStyled("    material: " + material.getKey().getKey());
        plugin.getRedLogger().debugStyled("    customModelData: " + customModelData);
        if (hasRequiredRules) {
            plugin.getRedLogger().debugStyled("    requiredRules: [");
            for (Map.Entry<String, Boolean> entry : requiredRules.entrySet()) {
                plugin.getRedLogger().debugStyled("        " + entry.getKey() + ": " + entry.getValue());
            }
            plugin.getRedLogger().debugStyled("    ]");
        } else {
            plugin.getRedLogger().debugStyled("    requiredRules: null");
        }
        plugin.getRedLogger().debugStyled("];");
    }

    /*public static ExtendedRule getExtendedRuleByRule(String rule) {
        ExtendedRule extendedRule;
        for (RuleReform extRule : allReforms) {
            if (extRule.rules.containsKey(rule)) {
                extendedRule = extRule;
                return extendedRule;
            }
        }
        for (RuleSoc extRule : allSocs) {
            if (extRule.rules.containsKey(rule)) {
                extendedRule = extRule;
                return extendedRule;
            }
        }
        for (RuleTech extRule : allTechs) {
            if (extRule.rules.containsKey(rule)) {
                extendedRule = extRule;
                return extendedRule;
            }
        }
        return null;
    }*/

    /*public static void changeRule(RedTown town, String ruleID, boolean value) {
        if (!ruleID.contains("."))
            // Изменение значения правила у города
            plugin.getRedLogger().debug("-");
        else {
            // Get rule group ID
            int indexOfDot = ruleID.indexOf('.');
            String ruleGroup = ruleID.substring(0, indexOfDot);

        }
    }

    public static void hasRule(String rule, RedTown town) {
    }*/

    public static ArrayList<RuleReform> getAllReforms() { return allReforms; }
    public static ArrayList<RuleSoc> getAllSocialResearches() { return allSocs; }
    public static ArrayList<RuleTech> getAllTechs() { return allTechs; }
}

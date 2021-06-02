package org.concordiacraft.redrealms.rules;

import org.bukkit.Material;

import java.util.ArrayList;
import java.util.Map;

/**
 * @author Theorenter
 * Reform extended by ExtendedRule abstract class
 */
public class RuleReform extends ExtendedRule {

    protected RuleReform(
            String ID,
            Map<String, Boolean> rules,
            String displayName,
            ArrayList<String> description,
            Map<String, Boolean> requiredRules,
            Material material,
            int customModelData) {
        super(ID, rules, displayName, description, requiredRules, material, customModelData);
    }
}

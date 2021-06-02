package org.concordiacraft.redrealms.rules;

import org.bukkit.Material;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Theorenter
 * Tech extended by ExtendedRule abstract class
 */

public class RuleTech extends ExtendedRule {

    private final double cost;
    private final double exp;
    private Map<String, Double> costModifiers;

    protected RuleTech(
            String ID,
            Map<String, Boolean> rules,
            String displayName,
            ArrayList<String> description,
            Map<String, Boolean> requiredRules,
            Material material,
            int customModelData,
            double cost,
            double exp,
            Map<String, Double> costModifiers) {
        super(ID, rules, displayName, description, requiredRules, material, customModelData);
        this.cost = cost;
        this.exp = exp;
        this.costModifiers = new HashMap<>(); this.costModifiers = costModifiers;
    }
}

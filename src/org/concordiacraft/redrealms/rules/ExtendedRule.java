package org.concordiacraft.redrealms.rules;

import org.bukkit.Material;
import org.concordiacraft.redutils.main.RedUtils;
import org.concordiacraft.redutils.utils.RedFormatter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Theorenter
 * Rule (or flag) - a control element for towns and realms.
 * The rule itself is a text key and a Boolean value.
 * Extended Rule-An abstract class that is implemented in
 * more advanced controls, such as reform,
 * social research, and technology
 */
public abstract class ExtendedRule {
    private final String ID;
    private final String displayName;
    private Map<String, Boolean> rules;
    private List<String> description;
    private Map<String, Boolean> requiredRules;
    private final Material material;
    private final int customModelData;

    protected ExtendedRule(
            String ID,
            Map<String, Boolean> rules,
            String displayName,
            List<String> description,
            Map<String, Boolean> requiredRules,
            Material material,
            int customModelData) {

        this.ID = ID;
        this.rules = new HashMap<>(); this.rules = rules;
        this.displayName = RedFormatter.format(displayName);
        this.description = new ArrayList<>(); this.description = RedFormatter.format(description);
        this.requiredRules = new HashMap<>(); this.requiredRules = requiredRules;
        this.material = material;
        this.customModelData = customModelData;
    }

    public String getID() { return ID; }

    public String getDisplayName() { return displayName; }

    public Map<String, Boolean> getRules() { return rules; }

    public List<String> getDescription() { return description; }

    public Map<String, Boolean> getRequiredRules() { return requiredRules; }

    public Material getMaterial() { return material; }

    public int getCustomModelData() { return customModelData; }
}


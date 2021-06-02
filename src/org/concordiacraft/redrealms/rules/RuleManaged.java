package org.concordiacraft.redrealms.rules;

/**
 * @author Theornetenr
 * An interface that is implemented
 * in classes that are managed by key-value rule sets
 */

public interface RuleManaged {
    void changeRule(String ruleID, boolean value);
}

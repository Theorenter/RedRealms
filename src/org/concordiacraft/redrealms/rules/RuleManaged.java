package org.concordiacraft.redrealms.rules;

/**
 * @author Theornetenr
 * An interface that is implemented
 * in classes that are managed by key-value rule sets
 */

public interface RuleManaged {
    boolean getRuleValue(String ruleID);
    void changeRule(String ruleID, boolean value);
}

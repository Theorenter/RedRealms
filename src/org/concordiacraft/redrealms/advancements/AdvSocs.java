package org.concordiacraft.redrealms.advancements;

import eu.endercentral.crazy_advancements.Advancement;
import eu.endercentral.crazy_advancements.AdvancementDisplay;
import eu.endercentral.crazy_advancements.AdvancementVisibility;
import eu.endercentral.crazy_advancements.NameKey;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * @author Theorenter
 * the Advancement system that displays social research.
 */
public final class AdvSocs {

    private static AdvancementDisplay rootDisplay;

    public void init() {
        ItemStack icon = new ItemStack(Material.FLINT_AND_STEEL);
        ItemMeta im = icon.getItemMeta();
        im.setCustomModelData(102);
        icon.setItemMeta(im);

        rootDisplay = new AdvancementDisplay(icon, "Социальные исследования", "TODO", AdvancementDisplay.AdvancementFrame.TASK, false, false, AdvancementVisibility.ALWAYS);
        Advancement root = new Advancement(null, new NameKey("custom", "root"), rootDisplay);

    }
}

package org.concordiacraft.redrealms.gui;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * @author Theorenter
 * The town menu
 */
public class GUITown extends RedGUI {

    public GUITown() {
        super(null, 45);

        initializeItems();
    }

    @Override
    void initializeItems() {
        //ItemStack i = createFunctionalGUIItemStack(Material.FLINT_AND_STEEL, 100, "Покинуть город", "Тестовое описание");
        //inv.setItem(44, it);
    }
}

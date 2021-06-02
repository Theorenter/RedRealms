package org.concordiacraft.redrealms.gui;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * @author Theorenter
 * The profile menu
 */
public class GUIProfile extends RedGUI {

    public GUIProfile() {
        super(null, 45);

        initializeItems();
    }

    @Override
    void initializeItems() {
        ItemStack it = createGUIItemStack(Material.FLINT_AND_STEEL, 100, "Тестовый предмет", "Тестовое описание");
        inv.setItem(1, it);
    }
}

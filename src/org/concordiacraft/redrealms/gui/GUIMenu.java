package org.concordiacraft.redrealms.gui;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.concordiacraft.redrealms.main.RedRealms;

/**
 * @author Theorenter
 * The player menu, which contains all the
 * necessary information about the game on the server
 */
public class GUIMenu extends RedGUI {

    public GUIMenu() {
        super(null, 45, "§f\uF801\u038D");

        initializeItems();
    }

    @Override
    protected void initializeItems() {

        int nullItemTextureNum = 101;
        Material mat = Material.FLINT_AND_STEEL;

        ItemStack profileItem = createFunctionalGUIItemStack(mat, nullItemTextureNum,
                RedRealms.getLocalization().getString("gui.menu.profile.name"),
                "UNQ-GUI-COMMAND", "profile",
                RedRealms.getLocalization().getString("gui.menu.profile.lore-1"),
                RedRealms.getLocalization().getString("gui.menu.profile.lore-2"),
                RedRealms.getLocalization().getString("gui.menu.profile.lore-3"));
        inv.setItem(0, profileItem);
        inv.setItem(1, profileItem);
        inv.setItem(9, profileItem);
        inv.setItem(10, profileItem);

        ItemStack townItem = createFunctionalGUIItemStack(mat, nullItemTextureNum,
                RedRealms.getLocalization().getString("gui.menu.town.name"),
                "UNQ-GUI-COMMAND", "town menu",
                RedRealms.getLocalization().getString("gui.menu.town.lore-1"),
                RedRealms.getLocalization().getString("gui.menu.town.lore-2"),
                RedRealms.getLocalization().getString("gui.menu.town.lore-3"));
        inv.setItem(3, townItem);
        inv.setItem(4, townItem);
        inv.setItem(5, townItem);
        inv.setItem(12, townItem);
        inv.setItem(13, townItem);
        inv.setItem(14, townItem);

        ItemStack realmItem = createFunctionalGUIItemStack(mat, nullItemTextureNum,
                RedRealms.getLocalization().getString("gui.menu.realm.name"),
                "UNQ-GUI-COMMAND", "realm",
                RedRealms.getLocalization().getString("gui.menu.realm.lore-1"),
                RedRealms.getLocalization().getString("gui.menu.realm.lore-2"),
                RedRealms.getLocalization().getString("gui.menu.realm.lore-3"));
        inv.setItem(7, realmItem);
        inv.setItem(8, realmItem);
        inv.setItem(16, realmItem);
        inv.setItem(17, realmItem);

        ItemStack fractionItem = createGUIItemStack(mat, nullItemTextureNum,
                RedRealms.getLocalization().getString("gui.menu.fraction.name"),
                RedRealms.getLocalization().getString("gui.menu.locked-lore-1"),
                RedRealms.getLocalization().getString("gui.menu.locked-lore-2"),
                RedRealms.getLocalization().getString("gui.menu.locked-lore-3"));
        inv.setItem(27, fractionItem);
        inv.setItem(28, fractionItem);
        inv.setItem(36, fractionItem);
        inv.setItem(37, fractionItem);

        ItemStack marketItem = createGUIItemStack(mat, nullItemTextureNum,
                RedRealms.getLocalization().getString("gui.menu.market.name"),
                RedRealms.getLocalization().getString("gui.menu.locked-lore-1"),
                RedRealms.getLocalization().getString("gui.menu.locked-lore-2"),
                RedRealms.getLocalization().getString("gui.menu.locked-lore-3"));
        inv.setItem(30, marketItem);
        inv.setItem(31, marketItem);
        inv.setItem(32, marketItem);
        inv.setItem(39, marketItem);
        inv.setItem(40, marketItem);
        inv.setItem(41, marketItem);

        ItemStack encyclopediaItem = createGUIItemStack(mat, nullItemTextureNum,
                RedRealms.getLocalization().getString("gui.menu.encyclopedia.name"),
                RedRealms.getLocalization().getString("gui.menu.locked-lore-1"),
                RedRealms.getLocalization().getString("gui.menu.locked-lore-2"),
                RedRealms.getLocalization().getString("gui.menu.locked-lore-3"));
        inv.setItem(34, encyclopediaItem);
        inv.setItem(35, encyclopediaItem);
        inv.setItem(43, encyclopediaItem);
        inv.setItem(44, encyclopediaItem);
    }
}
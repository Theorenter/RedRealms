package org.concordiacraft.redrealms.data;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.concordiacraft.reditems.items.ItemManager;
import org.concordiacraft.reditems.main.RedItems;
import org.concordiacraft.redrealms.main.RedRealms;
import org.bukkit.entity.Player;

import java.io.File;

public class RedPlayer extends RedData {

    // Fields
    private final String id;
    private String name;
    private String title;
    private String realmName;
    private String townName;

    // Getters and Setters
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public void setRealmName(String realmName) {
        this.realmName = realmName;
    }

    public String getId() {
        return id;
    }

    public String getTownName() { return townName; }

    public String getRealmName() { return realmName; }

    public boolean hasTown() { return townName != null; }

    public boolean hasRuleToCraft(ItemStack recipeResult) {
        String act = "canCraft";
        // Determine the ruleKey based on whether the player creates a custom or default minecraft itemStack
        String ruleKey;
        if (ItemManager.isCustomItem(recipeResult)) {
            NamespacedKey nsk = new NamespacedKey(RedItems.getPlugin(), "reditems-id");
            ruleKey = act + recipeResult.getItemMeta().getPersistentDataContainer().get(nsk, PersistentDataType.STRING);
        } else { ruleKey = act + recipeResult.getType().getKey().getKey(); }

        // Determine whether the player is in the town and whether he has the corresponding rule
        if (this.hasTown()) {
            RedTown rt = RedData.loadTown(this.realmName);
            return rt.getRuleValue(ruleKey);
        } else {
            // ЧЁ ДЕЛАТЬ ЕСЛИ ЭТА СВОЛОТА
            return true;
            // НЕ СОСТОИТ В ГОРОДЕ И ЧЁ-ТО КРАФТИТ
        }
    }

    public void setTownName(String townName) { this.townName = townName; }

    public static String getDataPath() {
        return (RedRealms.getPlugin().getDataFolder() + File.separator + "data" + File.separator + "players");
    }

    public File getFile() {
        return new File(RedRealms.getPlugin().getDataFolder() + File.separator + "data" + File.separator +
                "players" + File.separator + id + ".yml");
    }

    // Constructor
    protected RedPlayer(Player p) {
        this.id = p.getUniqueId().toString();

        if (!readFile()) {
            this.name = p.getName();
            updateFile();
        }
    }
}

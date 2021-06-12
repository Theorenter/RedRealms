package org.concordiacraft.redrealms.data;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;
import org.concordiacraft.reditems.items.ItemManager;
import org.concordiacraft.reditems.main.RedItems;
import org.concordiacraft.redrealms.main.RedRealms;
import org.bukkit.entity.Player;
import org.concordiacraft.redrealms.rules.RuleManager;
import org.concordiacraft.redutils.requests.RequestManager;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class RedPlayer extends RedData {

    // Fields
    private final String id;
    private String name;
    private String title;
    private String realmName;
    private String townName;

    // Getters and Setters

    /**
     * @return UUID of the player.
     */
    public String getId() { return id; }

    /**
     * @return name of the player.
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of the player in his RedPlayer data.
     * @param name name of the player.
     */
    public void setName(String name) {
        this.name = name;
    }

    /*public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }*/

    /**
     * Sets the realm in which the player is a member.
     * WARNING: If a player moves from one town to another,
     * they must also be removed from the list of town and realm!
     * @param realmName name of the realm.
     */
    public void setRealmName(String realmName) { this.realmName = realmName; }

    /**
     * @return the name of the town in which the player is a member.
     */
    public String getTownName() { return townName; }

    /**
     * @return the name of the realm in which the player is a member.
     */
    public String getRealmName() { return realmName; }

    /**
     * @return does the player belong to any town.
     */
    public boolean hasTown() { return townName != null; }

    /**
     * @param recipeResult the item that should be placed on the workbench/in the stove, etc.
     * @return whether the player has the ability to create an item.
     */
    public boolean hasRuleToCraft(ItemStack recipeResult) {
        // Determine the ruleKey based on whether the player creates a custom or default minecraft itemStack
        String ruleKey;
        if (ItemManager.isCustomItem(recipeResult)) {
            NamespacedKey nsk = new NamespacedKey(RedItems.getPlugin(), "reditems-id");
            ruleKey = recipeResult.getItemMeta().getPersistentDataContainer().get(nsk, PersistentDataType.STRING).toUpperCase();
        } else { ruleKey = recipeResult.getType().getKey().getKey().toUpperCase(); }

        // Determine whether the player is in the town and whether he has the corresponding rule
        if (this.hasTown()) {
            RedTown rt = RedData.loadTown(this.realmName);
            if (!rt.getCraftRules().containsKey(ruleKey)) { return true; }
            return rt.getCraftRules().get(ruleKey);
        } else {
            if (!RuleManager.getCraftPattern().containsKey(ruleKey)) { return true; }
            return RuleManager.getCraftPattern().get(ruleKey);
        }
    }

    public boolean hasRuleToUse(ItemStack item) {
        // Determine the ruleKey based on whether the player creates a custom or default minecraft itemStack
        String ruleKey;
        if (ItemManager.isCustomItem(item)) {
            NamespacedKey nsk = new NamespacedKey(RedItems.getPlugin(), "reditems-id");
            ruleKey = item.getItemMeta().getPersistentDataContainer().get(nsk, PersistentDataType.STRING);
        } else { ruleKey = item.getType().getKey().getKey(); }

        // Determine whether the player is in the town and whether he has the corresponding rule
        if (this.hasTown()) {
            RedTown rt = RedData.loadTown(this.realmName);
            if (!rt.getUseRules().containsKey(ruleKey)) { return true; }
            return rt.getUseRules().get(ruleKey);
        } else {
            if (!RuleManager.getUsePattern().containsKey(ruleKey)) { return true; }
            return RuleManager.getUsePattern().get(ruleKey);
        }
    }
    public void updatePlayer(boolean isUpdate){
        String jsonInputString = "{\"UUID\": \""+this.id+"\"," +
                " \"Nickname\": \""+this.name+"\"";
                //" \"Cash\": \""+this.townName+"\""; //TODO after economy integration
        if (this.townName!=null) jsonInputString+=" \"Town\": \""+this.townName+"\"";
        jsonInputString+="}";
        String finalJsonInputString = jsonInputString;
        new BukkitRunnable() {
            @Override
            public void run() {
                try{

                    URL url = new URL(RedRealms.getDefaultConfig().getHostname()+ "/api/player");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setDoOutput(true);
                    connection.setInstanceFollowRedirects(false);
                    if(isUpdate){
                        connection.setRequestMethod("PUT");
                    } else connection.setRequestMethod("POST");
                    connection.setRequestProperty("apiKey",RedRealms.getDefaultConfig().getApiKey());
                    connection.setRequestProperty("Content-Type", "application/json; utf-8");
                    connection.setRequestProperty("charset", "utf-8");
                    connection.setRequestProperty("Accept", "application/json");
                    connection.connect();


                    try(OutputStream os = connection.getOutputStream()) {
                        byte[] input = finalJsonInputString.getBytes(StandardCharsets.UTF_8);
                        os.write(input, 0, input.length);
                    }
                    try(BufferedReader br = new BufferedReader(
                            new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                        StringBuilder response = new StringBuilder();
                        String responseLine = null;
                        while ((responseLine = br.readLine()) != null) {
                            response.append(responseLine.trim());
                        }
                        RedRealms.getPlugin().getRedLogger().debug(response.toString());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.runTaskAsynchronously(RedRealms.getPlugin());


    }
    /**
     * Sets the town in which the player is a member.
     * WARNING: If a player moves from one town to another,
     * they must also be removed from the list of town and realm!
     * @param townName name of the town.
     */
    public void setTownName(String townName) {
        this.townName = townName;
    }

    /**
     * @return file of the player.
     */
    public File getFile() {
        return new File(RedRealms.getPlugin().getDataFolder() + File.separator + "data" + File.separator +
                "players" + File.separator + id + ".yml");
    }

    /**
     * @return true if the player is the mayor and false if not.
     */
    public boolean isMayor() {
        if (!this.hasTown()) return false;
        RedTown rt = RedData.loadTown(this.getTownName());
        return rt.getMayorID().equals(this.getId());
    }

    // Constructor
    protected RedPlayer(Player p) {
        this.id = p.getUniqueId().toString();

        if (!readFile()) {
            this.name = p.getName();

            updateFile();
        }
    }
    protected RedPlayer(String playerID) {
        this.id = playerID;
        if (!readFile()) {
            updateFile();
        }
    }

    public static String getDataPath() {
        return (RedRealms.getPlugin().getDataFolder() + File.separator + "data" + File.separator + "players");
    }
}
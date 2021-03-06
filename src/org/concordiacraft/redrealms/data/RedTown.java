package org.concordiacraft.redrealms.data;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.block.banner.Pattern;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.concordiacraft.redrealms.events.TownCreationConversationEvent;
import org.concordiacraft.redrealms.main.RedRealms;
import org.concordiacraft.redrealms.rules.RuleManager;
import org.concordiacraft.redrealms.utilits.ChunkWork;

import java.io.*;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class RedTown extends RedData {
    private String name;
    private String mayorID;

    private String homeBiomeType;
    private String title;
    private String worldName;

    private List<String> citizensIDs = new ArrayList<>();
    private List<Integer> capitalChunk = new ArrayList<>();

    private HashMap<String, List<Pattern>> townBanner;
    private List<List<Integer>> chunks = new ArrayList<>();

    // Rules
    private Map<String, Boolean> craftRules;
    private Map<String, Boolean> useRules;

    // Extended rules
    private List<String> researchedTechsID = new ArrayList<>();

    // Economy
    private BigDecimal balance = new BigDecimal(0);

    /**
     * WARNING: THIS CONSTRUCTOR IS NOT USED TO CREATE TOWNS.
     * you should use RedData.CreateTown() or
     * another constructors
     *
     * @param townName
     */
    protected RedTown(String townName) {
        this.name = townName;
        readFile();
    }

    /**
     * Valid town constructor.
     *
     * @param townName     name of the town
     * @param townFounder  founder of the town.
     * @param townBanner   town flag.
     * @param capitalChunk the chunk that the town will be based on.
     */
    public RedTown(String townName, Player townFounder, ItemStack townBanner, Chunk capitalChunk, String homeBiomeType) {
        this.name = townName;
        this.mayorID = townFounder.getUniqueId().toString();
        this.townBanner = new HashMap<>();
        this.townBanner.put(townBanner.getType().getKey().getKey(), ((BannerMeta) townBanner.getItemMeta()).getPatterns());
        this.citizensIDs = new ArrayList<>();
        this.citizensIDs.add(townFounder.getUniqueId().toString());
        this.homeBiomeType = homeBiomeType;
        this.worldName = capitalChunk.getWorld().getName();

        RedPlayer redPlayer = RedData.loadPlayer(townFounder);
        redPlayer.setTownName(townName);
        redPlayer.updateFile();

        // rules
        this.craftRules = new HashMap<>(RuleManager.getCraftPattern());
        this.useRules = new HashMap<>(RuleManager.getUsePattern());

        setCapitalChunk(capitalChunk);
        addChunk(capitalChunk);

        Bukkit.getServer().getPluginManager().callEvent(new TownCreationConversationEvent(townName, townFounder, townBanner, capitalChunk));
        RedChunk rc = RedData.loadChunk(capitalChunk);
        rc.setTownOwner(name);
        rc.setMunicipality(true);
        rc.updateFile();
        updateFile();

        RedRealms.getPlugin().getRedLogger().info("Player " + mayorID + " created a new town - " + "\"" + name + "\"");
        // create economy account
        this.balance = new BigDecimal(0);
    }

    public void updateTown(boolean isUpdate) {
        if (!RedRealms.getDefaultConfig().isNetworkEnabled()) return;
        RedPlayer mayor = RedData.loadPlayer(this.mayorID);
        String jsonInputString = "{\"Name\": \"" + this.name + "\"," +
                " \"Owner\": \"" + mayor.getName() + "\"" +
                " \"Cash\": \"" + RedRealms.getTNEAPI().getAccount(this.getName()).getHoldings() + "\"" +
                "}";

        new BukkitRunnable() {
            @Override
            public void run() {
                try {

                    URL url = new URL(RedRealms.getDefaultConfig().getHostname() + "/api/town");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setDoOutput(true);
                    connection.setInstanceFollowRedirects(false);
                    if (isUpdate) {
                        connection.setRequestMethod("PUT");
                    } else connection.setRequestMethod("POST");
                    connection.setRequestProperty("apiKey", RedRealms.getDefaultConfig().getApiKey());
                    connection.setRequestProperty("Content-Type", "application/json; utf-8");
                    connection.setRequestProperty("charset", "utf-8");
                    connection.setRequestProperty("Accept", "application/json");
                    connection.connect();


                    try (OutputStream os = connection.getOutputStream()) {
                        byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                        os.write(input, 0, input.length);
                    }
                    try (BufferedReader br = new BufferedReader(
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
     * @return file of the town.
     */
    public File getFile() {
        return new File(RedRealms.getPlugin().getDataFolder() + File.separator + "data" + File.separator +
                "towns" + File.separator + name + ".yml");
    }

    /**
     * @return capital chunk.
     */
    public List<Integer> getCapitalChunk() {
        return capitalChunk;
    }

    /**
     * @param capitalChunk main town Chunk.
     */
    public void setCapitalChunk(List<Integer> capitalChunk) {
        this.capitalChunk = capitalChunk;

    }
    /**
     * @param capitalChunk main town Chunk.
     */
    public void setCapitalChunk(Chunk capitalChunk) {
        this.setCapitalChunk(ChunkWork.chunkCreate(capitalChunk));
    }

    /**
     * @return town name;
     */
    public String getName() {
        return name;
    }

    /**
     * @param name name of the town.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Add a chunk to the town.
     *
     * @param chunk chunk to add to the town.
     */
    public void addChunk(Chunk chunk) {
        ArrayList<Integer> ChunkCoords = ChunkWork.chunkCreate(chunk);
        chunks.add(ChunkCoords);
        updateFile();
    }

    /**
     * Remove a chunk from the town.
     *
     * @param chunk chunk to remove from the town.
     */
    public void removeChunk(Chunk chunk) {
        ArrayList<Integer> ChunkCoords = ChunkWork.chunkCreate(chunk);
        chunks.remove(ChunkCoords);
        updateFile();
    }

    /**
     * @return all chunks within the town.
     */
    public List<List<Integer>> getChunks() {
        return chunks;
    }

    /**
     * @param playerID UUID of the player to be appointed as the head of the town.
     */
    public void setMayorID(String playerID) {
        this.mayorID = playerID;
    }

    /**
     * @return mayor's UUID in the String format.
     */
    public String getMayorID() {
        return this.mayorID;
    }

    /**
     * @return UUID of all citizens of the town.
     */
    public List<String> getCitizenIDs() {
        return citizensIDs;
    }


    /**
     * Add a player to the town.
     * @param playerID UUID of the new citizen.
     */
    public void addCitizen(String playerID) { this.citizensIDs.add(playerID); }

    /**
     * Add a player to the town.
     * @param redPlayer new citizen.
     */
    public void addCitizen(RedPlayer redPlayer) {
        this.citizensIDs.add(redPlayer.getId());
        redPlayer.setTownName(this.getName());
        redPlayer.updateFile();
        updateFile();
    }

    public boolean hasChunk(Chunk chunk) {
        List<Integer> loc = new ArrayList<>();
        loc.add(chunk.getX()); loc.add(chunk.getZ());
        for (List<Integer> list : chunks) {
            if ((list.get(0) == loc.get(0)) && (list.get(1) == loc.get(1))) return true;
        }
        return false;
    }

    public BigDecimal getBalance() {
        return this.balance;
    }

    public void addBalance(BigDecimal s) {
        balance = balance.add(s);
        updateFile();
    }

    public void decBalance(BigDecimal s) {
        balance = balance.add(s.negate());
        updateFile();
    }

    /**
     * Sends a message to all citizens of the city who are online.
     * WARNING: The method is executed asynchronously!
     * @param message message.
     */
    public void sendMessageToAllCitizens(String message) {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (!citizensIDs.contains(p.getUniqueId().toString()))
                        continue;
                    p.sendRawMessage(message);
                }
            }
        }.runTaskAsynchronously(RedRealms.getPlugin());

    }

    /**
     * @return researched town's techs.
     */
    public List<String> getResearchedTechsID() {
        return researchedTechsID;
    }

    /**
     * @param id identifier of the tech.
     * @return true if the tech is researched and false if not.
     */
    public boolean hasResearchedTech(String id) {
        return researchedTechsID.contains(id);
    }

    /**
     * @return craft rules.
     */
    public Map<String, Boolean> getCraftRules() {
        return this.craftRules;
    }

    /**
     * @return use rules.
     */
    public Map<String, Boolean> getUseRules() {
        return useRules;
    }

    /**
     * @param player the player being kicked out.
     */
    public void kickPlayer(Player player) {

        RedPlayer rp = RedData.loadPlayer(player);
        citizensIDs.remove(player.getUniqueId().toString());
        rp.setTownName(null);
        rp.updateFile();

        // If the player was the last citizen.
        if (citizensIDs.isEmpty()) {
            delete();
            for (Player p : Bukkit.getServer().getOnlinePlayers())
                p.sendRawMessage(String.format(RedRealms.getLocalization().getString("messages.notifications.town-was-deserted"), name));
            for (List<Integer> chunk : chunks) {
                RedChunk newChunk = RedData.loadChunk(this.worldName, (ArrayList<Integer>) chunk);
                newChunk.setTownOwner(null);
                if ((newChunk.getPrivateOwnerUUID() != null) && (newChunk.getPrivateOwnerUUID().equals(player.getUniqueId().toString())))
                    newChunk.setPrivateOwnerUUID(null);
                newChunk.updateFile();
            }
            RedRealms.getPlugin().getRedLogger().info("Town \"" + this.name + "\" was deleted because there were no citizens left in it" );
            return;
        }

        // If the player was the mayor.
        if (this.mayorID.equals(rp.getId())) {
            List<String> civicList;
            civicList = citizensIDs;
            civicList.remove(mayorID);
            RedPlayer redP;
            for (String citizenID : citizensIDs) {
                this.setMayorID(citizenID);
                redP = RedData.loadPlayer(citizenID);
                sendMessageToAllCitizens(String.format(RedRealms.getLocalization().getString(("messages.notifications.force-mayor-change")), redP.getName(), this.name));
                updateFile();
                return;
            }
        }

        updateFile();
    }

    /**
     * Deleting the town.
     */
    public void delete() {
        getAllTowns().remove(this.name);
        for (List<Integer> chunk : chunks) {
            RedChunk newChunk = RedData.loadChunk(this.worldName, (ArrayList<Integer>) chunk);
            newChunk.setTownOwner(null);
            newChunk.setMunicipality(null);
            newChunk.setPrivateOwnerUUID(null);
            newChunk.updateFile();
        }
        deleteFile();
    }
}


    /*public void setResidentID(ArrayList<String> residentNames) {
        this.residentsIDList = residentNames;
    }*/
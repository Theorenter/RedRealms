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
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class RedTown extends RedData {
    private String name;
    private String mayorID;

    private String homeBiomeType;
    private String title;
    private double budget;

    private List<String> citizensIDs = new ArrayList<>();
    private List<Integer> capitalChunk = new ArrayList<>();

    private HashMap<String, List<Pattern>> townBanner;
    private List<List<Integer>> chunks = new ArrayList<>();

    // Rules
    private Map<String, Boolean> craftRules = new HashMap<>();
    private Map<String, Boolean> useRules = new HashMap<>();

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

        RedPlayer redPlayer = RedData.loadPlayer(townFounder);
        redPlayer.setTownName(townName);
        redPlayer.updateFile();

        // rules
        this.craftRules = RuleManager.getCraftPattern();
        this.useRules = RuleManager.getUsePattern();

        setCapitalChunk(capitalChunk);
        addChunk(capitalChunk);

        Bukkit.getServer().getPluginManager().callEvent(new TownCreationConversationEvent(townName, townFounder, townBanner, capitalChunk));
        RedChunk rc = RedData.loadChunk(capitalChunk);
        rc.setTownOwner(name);
        rc.updateFile();
        updateFile();

        RedRealms.getPlugin().getRedLogger().info("Player " + mayorID + " created a new town - " + "\"" + name + "\"");

    }

    public void updateTown(boolean isUpdate) {
        if (!RedRealms.getDefaultConfig().isNetworkEnabled()) return;
        RedPlayer mayor = RedData.loadPlayer(this.mayorID);
        String jsonInputString = "{\"Name\": \"" + this.name + "\"," +
                " \"Owner\": \"" + mayor.getName() + "\"" +
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
     * @param chunk
     */
    public void addChunk(List<Integer> chunk) {
        chunks.add(chunk);
    }

    /**
     * Add a chunk to the town.
     *
     * @param chunk chunk to add to the town.
     */
    public void addChunk(Chunk chunk) {
        ArrayList<Integer> ChunkCoords = ChunkWork.chunkCreate(chunk);
        chunks.add(ChunkCoords);
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
     *
     * @param playerID UUID of the citizen.
     */
    public void addCitizen(String playerID) {
        this.citizensIDs.add(playerID);
    }

    /**
     * @return craft rules.
     */
    public Map<String, Boolean> getCraftRules() {
        return craftRules;
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
                RedChunk newChunk = RedData.loadChunk((ArrayList<Integer>) chunk);
                newChunk.setTownOwner(null);
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
            for (String citizenID : citizensIDs) {
                this.setMayorID(citizenID);
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
            RedChunk newChunk = RedData.loadChunk((ArrayList<Integer>) chunk);
            newChunk.setTownOwner(null);
            newChunk.updateFile();
        }
        deleteFile();
    }
}


    /*public void setResidentID(ArrayList<String> residentNames) {
        this.residentsIDList = residentNames;
    }*/
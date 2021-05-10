package org.concordiacraft.redrealms.utilits;


import org.bukkit.Chunk;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;
import org.concordiacraft.redrealms.data.RedChunk;
import org.concordiacraft.redrealms.data.RedPlayer;
import org.concordiacraft.redrealms.data.RedTown;

import java.util.ArrayList;
import java.util.HashMap;

// This class allows easy conversion between Bukkit class Chunk and our chunks
public final class ChunkWork {

    //@param X chunk X
    //@param Z chunk Z
    //@return arraylist for other methods
    public static ArrayList<Integer> chunkCreate (int X, int Z){
        ArrayList<Integer> chunkCoords = new ArrayList<>();
        chunkCoords.add(X);
        chunkCoords.add(Z);
        return chunkCoords;
    }

    public static boolean canInteract(Chunk chunk, Player player){
        RedPlayer redPlayer = new RedPlayer(player);
        RedChunk redChunk = new RedChunk(chunk);
        if (redChunk.getOwner()==null) return true;

        RedTown town = new RedTown(redChunk.getOwner());

        return town.getResidentNames().contains(redPlayer.getId());
    }
    /*
    @param chunk Chunk we want to convert
    @return array list that represents coordinates of converted chunk
     */
    public static ArrayList<Integer> chunkCreate (Chunk chunk){
        ArrayList<Integer> chunkCoords = new ArrayList<>();
        chunkCoords.add(chunk.getX());
        chunkCoords.add(chunk.getZ());
        return chunkCoords;
    }
    public static boolean chunksContains(ArrayList<ArrayList<Integer>> chunks, ArrayList<Integer> chunk) {
        return chunks.contains(chunk);
    }
    public static boolean chunksContains(ArrayList<ArrayList<Integer>> chunks, Chunk chunk) {
        ArrayList<Integer> chunkCoords = new ArrayList<>();
        chunkCoords.add(chunk.getX());
        chunkCoords.add(chunk.getZ());
        return chunks.contains(chunkCoords);
    }
    public static Biome getBiome(Chunk chunk) {
        int maxCount = 0;
        Biome maxBiome = null;
        HashMap<Biome, Integer> biomeCount = new HashMap<>();
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                Biome biome = chunk.getBlock(x, 64, z).getBiome();
                if (biomeCount.containsKey(biome)) {
                    int biomeC = biomeCount.get(biome) + 1;
                    if (biomeC > maxCount) maxBiome = biome;
                    biomeCount.put(biome, biomeC);
                } else biomeCount.put(biome, 1);
            }
        }
        return maxBiome;
    }
}
package org.concordiacraft.redrealms.utilits;


import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import org.concordiacraft.redrealms.data.DataChunk;
import org.concordiacraft.redrealms.data.DataPlayer;
import org.concordiacraft.redrealms.data.DataTown;

import java.util.ArrayList;
import java.util.HashMap;

// This class allows easy conversion between Bukkit class Chunk and our chunks
public abstract class ChunkWork {


    public static ArrayList<Integer> chunkCreate (int X, int Z){
        ArrayList<Integer> chunkCoords = new ArrayList<>();
        chunkCoords.add(X);
        chunkCoords.add(Z);
        return chunkCoords;
    }
    public static boolean canInteract(Chunk chunk, Player player){
        DataPlayer dataPlayer = new DataPlayer(player);
        DataChunk dataChunk = new DataChunk(chunk);
        if (dataChunk.getOwner()==null) return true;

        DataTown town = new DataTown(dataChunk.getOwner());

        return town.getResidentNames().contains(dataPlayer.getID());
    }
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
    public static String getBiome(Chunk chunk){
        int maxCount = 0;
        String maxBiome = "";
        HashMap<String,Integer> biomeCount = new HashMap<>(); 
        for (int x = 0;x<16;x++) {
            for (int z = 0;z<16;z++) {
                String biome = chunk.getBlock(x, 64, z).getBiome().getKey().toString();
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
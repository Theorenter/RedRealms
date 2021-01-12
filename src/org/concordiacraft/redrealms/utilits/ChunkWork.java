package org.concordiacraft.redrealms.utilits;


import org.bukkit.Chunk;

import java.util.ArrayList;

// This class allows easy conversion between Bukkit class Chunk and our chunks
public abstract class ChunkWork {
    public static ArrayList<Integer> chunkCreate (int X, int Z){
        ArrayList<Integer> chunkCoords = new ArrayList<>();
        chunkCoords.add(X);
        chunkCoords.add(Z);
        return chunkCoords;
    }
    public static ArrayList<Integer> chunkCreate (Chunk chunk){
        ArrayList<Integer> chunkCoords = new ArrayList<>();
        chunkCoords.add(chunk.getX());
        chunkCoords.add(chunk.getZ());
        return chunkCoords;
    }
    public static boolean chunksContains(ArrayList<ArrayList<Integer>> chunks, ArrayList<Integer> chunk) {
        if (chunks.contains(chunk)) return true;
        else return false;
    }
    public static boolean chunksContains(ArrayList<ArrayList<Integer>> chunks, Chunk chunk) {
        ArrayList<Integer> chunkCoords = new ArrayList<>();
        chunkCoords.add(chunk.getX());
        chunkCoords.add(chunk.getZ());
        if (chunks.contains(chunkCoords)) return true;
        else return false;
    }
}
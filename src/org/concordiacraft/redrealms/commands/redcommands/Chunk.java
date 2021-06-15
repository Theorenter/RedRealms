package org.concordiacraft.redrealms.commands.redcommands;

import org.bukkit.entity.Player;
import org.concordiacraft.redrealms.config.ConfigDefault;
import org.concordiacraft.redrealms.data.RedChunk;
import org.concordiacraft.redrealms.data.RedData;
import org.concordiacraft.redrealms.data.RedPlayer;
import org.concordiacraft.redrealms.main.RedRealms;
import org.concordiacraft.redutils.commands.RedCommand;

import java.util.ArrayList;

public class Chunk extends RedCommand {
    @Override
    public void init() {
        command = "/chunk";
        commands.put("map", "Передать чанк игроку");
        commands.put("capture", "Приобрести чанк");
        commands.put("drop", "Покинуть чанк");
        commands.put("give", "Передать чанк игроку");
    }

    @Override
    public void showHelp() {
        sender.sendMessage(RedRealms.getLocalization().getString("messages.help.chunk.header"));
        sender.sendMessage(RedRealms.getLocalization().getString("messages.help.chunk.map"));
        sender.sendMessage(RedRealms.getLocalization().getString("messages.help.chunk.capture"));
        sender.sendMessage(RedRealms.getLocalization().getString("messages.help.chunk.drop"));
        sender.sendMessage(RedRealms.getLocalization().getString("messages.help.chunk.give"));
    }

    // Capture chunk by the player
    public void mapCMD() {
        if (!(sender instanceof Player)) {
            RedRealms.getPlugin().getRedLogger().info(RedRealms.getLocalization().getRawString("messages.errors.only-for-players"));
            return;
        }

        Player p = (Player) sender;

        if (!sender.hasPermission("redrealms.chunk.map")) {
            ((Player) sender).sendRawMessage(RedRealms.getLocalization().getString("messages.errors.don't-have-permissions-command"));
            p.playSound(p.getLocation(), RedRealms.getDefaultConfig().getErrorSoundName(),
                    RedRealms.getDefaultConfig().getErrorSoundVolume(), RedRealms.getDefaultConfig().getErrorSoundPitch());
            return;
        }

        RedPlayer rp = RedData.loadPlayer(p);


        // Find player's chunk
        org.bukkit.Chunk curChunk = p.getLocation().getChunk();
        int stX = curChunk.getX() - 2;
        int stZ = curChunk.getZ() - 13;


        p.sendRawMessage(String.format(RedRealms.getLocalization().getString("map.vignette"), curChunk.getX(), curChunk.getZ()));
        p.sendRawMessage("");
        for (int x = stX; x < stX + 7; x++) {
            String mapLine = "";
            for (int z = stZ; z < stZ + 27; z++) {
                ArrayList<Integer> loc = new ArrayList<>();
                loc.add(x); loc.add(z);

                RedChunk c = RedData.loadChunk(p.getWorld().getName(), loc);

                String mapEl = RedRealms.getLocalization().getRawString("map.default-chunk");

                // Check if it's territory when player stay
                if ((x == stX + 4) && (z == stZ + 14))
                    mapEl = RedRealms.getLocalization().getRawString("map.standing-chunk");

                if (!c.hasTownOwner()) {
                    mapEl = RedRealms.getLocalization().getString("map.colors.wilderness") + mapEl;
                } else {
                    if (c.getTownOwner().equals(rp.getTownName()))
                        mapEl = RedRealms.getLocalization().getString("map.colors.your-town") + mapEl;
                    else
                        mapEl = RedRealms.getLocalization().getString("map.colors.another-town") + mapEl;
                }
                mapLine += mapEl;
            }
            p.sendRawMessage(mapLine);
        }



    }
    public void captureCMD() {

    }
}
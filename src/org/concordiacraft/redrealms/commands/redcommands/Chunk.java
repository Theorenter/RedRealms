package org.concordiacraft.redrealms.commands.redcommands;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.concordiacraft.redrealms.config.ConfigDefault;
import org.concordiacraft.redrealms.data.RedChunk;
import org.concordiacraft.redrealms.data.RedData;
import org.concordiacraft.redrealms.data.RedPlayer;
import org.concordiacraft.redrealms.data.RedTown;
import org.concordiacraft.redrealms.main.RedRealms;
import org.concordiacraft.redrealms.utilits.ChunkWork;
import org.concordiacraft.redutils.commands.RedCommand;

import java.math.BigDecimal;
import java.util.ArrayList;

public class Chunk extends RedCommand {
    @Override
    public void init() {
        command = "/chunk";
        //commands.put("map", "Передать чанк игроку");
        commands.put("capture", "Приобрести чанк");
        commands.put("drop", "Покинуть чанк");
        commands.put("give", "Передать чанк игроку");
    }

    @Override
    public void showHelp() {
        sender.sendMessage(RedRealms.getLocalization().getString("messages.help.chunk.header"));
        //sender.sendMessage(RedRealms.getLocalization().getString("messages.help.chunk.map"));
        sender.sendMessage(RedRealms.getLocalization().getString("messages.help.chunk.capture"));
        sender.sendMessage(RedRealms.getLocalization().getString("messages.help.chunk.drop"));
        sender.sendMessage(RedRealms.getLocalization().getString("messages.help.chunk.give"));
    }

    // Capture chunk by the player
    /*public void mapCMD() {
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



    }*/
    public void captureCMD() {
        if (!(sender instanceof Player)) {
            RedRealms.getPlugin().getRedLogger().info(RedRealms.getLocalization().getRawString("messages.errors.only-for-players"));
            return;
        }

        Player p = (Player) sender;

        if (!sender.hasPermission("redrealms.chunk.capture")) {
            sender.sendMessage(RedRealms.getLocalization().getString("messages.errors.don't-have-permissions-command"));
            p.playSound(p.getLocation(), RedRealms.getDefaultConfig().getErrorSoundName(),
                    RedRealms.getDefaultConfig().getErrorSoundVolume(), RedRealms.getDefaultConfig().getErrorSoundPitch());
            return;
        }

        RedPlayer rp = RedData.loadPlayer(p);

        if (!rp.isMayor()) {
            p.sendRawMessage(RedRealms.getLocalization().getString("messages.errors.player-is-not-mayor"));
            p.playSound(p.getLocation(), RedRealms.getDefaultConfig().getErrorSoundName(),
                    RedRealms.getDefaultConfig().getErrorSoundVolume(), RedRealms.getDefaultConfig().getErrorSoundPitch());
            return;
        }


        RedTown rt = RedData.loadTown(rp.getTownName());
        RedChunk rc = RedData.loadChunk(p.getLocation().getChunk());

        if (rc.hasTownOwner()) {
            p.sendRawMessage(RedRealms.getLocalization().getString("messages.errors.not-your-town-chunk"));
            p.playSound(p.getLocation(), RedRealms.getDefaultConfig().getErrorSoundName(),
                    RedRealms.getDefaultConfig().getErrorSoundVolume(), RedRealms.getDefaultConfig().getErrorSoundPitch());
            return;
        }

        rc.setMunicipality(true);
        double price = RedRealms.getDefaultConfig().getPriceBiomeTypes().get(rc.getBiomeType());

        BigDecimal s = new BigDecimal(price);

        if (rt.getBalance().compareTo(s) < 0) {
            p.sendRawMessage(String.format(RedRealms.getLocalization().getString(("messages.errors.not-enough-money-town")), rp.getTownName(), rt.getBalance()));
            p.playSound(p.getLocation(), RedRealms.getDefaultConfig().getErrorSoundName(),
                    RedRealms.getDefaultConfig().getErrorSoundVolume(), RedRealms.getDefaultConfig().getErrorSoundPitch());
            return;
        }

        rt.decBalance(s);
        rt.addChunk(p.getLocation().getChunk());
        p.sendRawMessage(String.format(RedRealms.getLocalization().getString(("messages.errors.successful-capture")), rc.getX() + ", " + rc.getZ(), s.toString()));
    }

    public void dropCMD() {
        if (!(sender instanceof Player)) {
            RedRealms.getPlugin().getRedLogger().info(RedRealms.getLocalization().getRawString("messages.errors.only-for-players"));
            return;
        }

        Player p = (Player) sender;

        if (!sender.hasPermission("redrealms.chunk.drop")) {
            sender.sendMessage(RedRealms.getLocalization().getString("messages.errors.don't-have-permissions-command"));
            p.playSound(p.getLocation(), RedRealms.getDefaultConfig().getErrorSoundName(),
                    RedRealms.getDefaultConfig().getErrorSoundVolume(), RedRealms.getDefaultConfig().getErrorSoundPitch());
            return;
        }

        RedPlayer rp = RedData.loadPlayer(p);

        if (!rp.isMayor()) {
            p.sendRawMessage(RedRealms.getLocalization().getString("messages.errors.player-is-not-mayor"));
            p.playSound(p.getLocation(), RedRealms.getDefaultConfig().getErrorSoundName(),
                    RedRealms.getDefaultConfig().getErrorSoundVolume(), RedRealms.getDefaultConfig().getErrorSoundPitch());
            return;

        }

        RedChunk rc = RedData.loadChunk(p.getLocation().getChunk());

        if (!rp.getTownName().equals(rc.getTownOwner())) {
            p.sendRawMessage(RedRealms.getLocalization().getString("messages.errors.not-your-town-chunk"));
            p.playSound(p.getLocation(), RedRealms.getDefaultConfig().getErrorSoundName(),
                    RedRealms.getDefaultConfig().getErrorSoundVolume(), RedRealms.getDefaultConfig().getErrorSoundPitch());
            return;
        }

        if (!rc.isMunicipality()) {
            p.sendRawMessage(RedRealms.getLocalization().getString("messages.errors.can-not-forcibly-remove-chunk"));
            p.playSound(p.getLocation(), RedRealms.getDefaultConfig().getErrorSoundName(),
                    RedRealms.getDefaultConfig().getErrorSoundVolume(), RedRealms.getDefaultConfig().getErrorSoundPitch());
            return;
        }

        RedTown rt = RedData.loadTown(rp.getTownName());

        rt.removeChunk(p.getLocation().getChunk());
        p.sendRawMessage(String.format(RedRealms.getLocalization().getString(("messages.errors.successful-drop-chunk")), p.getLocation().getChunk().getX() + ", " + p.getLocation().getChunk().getZ()));
    }

    public void giveCMD() {
        if (!(sender instanceof Player)) {
            RedRealms.getPlugin().getRedLogger().info(RedRealms.getLocalization().getRawString("messages.errors.only-for-players"));
            return;
        }

        Player p = (Player) sender;

        if (!sender.hasPermission("redrealms.chunk.give")) {
            sender.sendMessage(RedRealms.getLocalization().getString("messages.errors.don't-have-permissions-command"));
            p.playSound(p.getLocation(), RedRealms.getDefaultConfig().getErrorSoundName(),
                    RedRealms.getDefaultConfig().getErrorSoundVolume(), RedRealms.getDefaultConfig().getErrorSoundPitch());
            return;
        }

        RedPlayer rp = RedData.loadPlayer(p);
        RedChunk rc = RedData.loadChunk(p.getLocation().getChunk());

        if (!rc.hasTownOwner()) {
            sender.sendMessage(RedRealms.getLocalization().getString("messages.errors.this-chunk-is-wilderness"));
            p.playSound(p.getLocation(), RedRealms.getDefaultConfig().getErrorSoundName(),
                    RedRealms.getDefaultConfig().getErrorSoundVolume(), RedRealms.getDefaultConfig().getErrorSoundPitch());
            return;
        }

        if (!rp.isMayor() || !rc.getPrivateOwnerUUID().equals(p.getUniqueId().toString())) {
            sender.sendMessage(RedRealms.getLocalization().getString("messages.errors.you-can-not-give-another-chunks"));
            p.playSound(p.getLocation(), RedRealms.getDefaultConfig().getErrorSoundName(),
                    RedRealms.getDefaultConfig().getErrorSoundVolume(), RedRealms.getDefaultConfig().getErrorSoundPitch());
            return;
        }

        if (args.length != 2) {
            p.sendRawMessage(RedRealms.getLocalization().getString("messages.errors.invalid-length-arguments"));
            p.playSound(p.getLocation(), RedRealms.getDefaultConfig().getErrorSoundName(),
                    RedRealms.getDefaultConfig().getErrorSoundVolume(), RedRealms.getDefaultConfig().getErrorSoundPitch());
            return;
        }

        if (Bukkit.getPlayer(args[1]) == null) {
            p.sendRawMessage(String.format(RedRealms.getLocalization().getString("messages.errors.player-not-on-the-server"), args[1]));
            p.playSound(p.getLocation(), RedRealms.getDefaultConfig().getErrorSoundName(),
                    RedRealms.getDefaultConfig().getErrorSoundVolume(), RedRealms.getDefaultConfig().getErrorSoundPitch());
            return;
        }

        Player pReceiver = Bukkit.getPlayer(args[1]);

        RedTown rt = RedData.loadTown(rp.getTownName());

    }
}
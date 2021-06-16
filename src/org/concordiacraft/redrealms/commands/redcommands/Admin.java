package org.concordiacraft.redrealms.commands.redcommands;

import org.bukkit.entity.Player;
import org.concordiacraft.redrealms.data.RedData;
import org.concordiacraft.redrealms.data.RedPlayer;
import org.concordiacraft.redrealms.data.RedTown;
import org.concordiacraft.redrealms.main.RedRealms;
import org.concordiacraft.redutils.commands.RedCommand;

public class Admin extends RedCommand {
    @Override
    public void init() {
        command = "/admin";
        //commands.put("map", "Передать чанк игроку");
        commands.put("forcejoin", "присоедениться к городу");
    }

    @Override
    public void showHelp() {
        sender.sendMessage(RedRealms.getLocalization().getString("messages.help.admin.header"));
        sender.sendMessage(RedRealms.getLocalization().getString("messages.help.admin.forcejoin"));
    }

    public void forcejoinCMD() {
        if (!(sender instanceof Player)) {
            RedRealms.getPlugin().getRedLogger().info(RedRealms.getLocalization().getRawString("messages.errors.only-for-players"));
            return;
        }

        Player p = (Player) sender;

        if (!sender.hasPermission("redrealms.admin.forcejoin")) {
            p.sendRawMessage(RedRealms.getLocalization().getString("messages.errors.don't-have-permissions-command"));
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

        if (!RedData.getAllTowns().containsKey(args[1])) {
            p.sendRawMessage(RedRealms.getLocalization().getString("messages.errors.town-does-not-exist"));
            p.playSound(p.getLocation(), RedRealms.getDefaultConfig().getErrorSoundName(),
                    RedRealms.getDefaultConfig().getErrorSoundVolume(), RedRealms.getDefaultConfig().getErrorSoundPitch());
            return;
        }
        RedPlayer rp = RedData.loadPlayer(p);
        RedTown rt = RedData.loadTown(args[1]);

        if (rp.hasTown()) {
            RedData oldTown = RedData.loadTown(rp.getTownName());
        }

        rt.addCitizen(rp);
        p.sendRawMessage(String.format(RedRealms.getLocalization().getString(("messages.notifications.forcejoin-town")), rp.getTownName()));
    }
}

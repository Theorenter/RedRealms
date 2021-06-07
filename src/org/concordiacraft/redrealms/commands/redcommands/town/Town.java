package org.concordiacraft.redrealms.commands.redcommands.town;

import org.bukkit.entity.Player;
import org.concordiacraft.redrealms.data.RedData;
import org.concordiacraft.redrealms.data.RedPlayer;
import org.concordiacraft.redrealms.data.RedTown;
import org.concordiacraft.redrealms.gui.GUITown;
import org.concordiacraft.redrealms.main.RedRealms;
import org.concordiacraft.redutils.commands.RedCommand;

public class Town extends RedCommand {
    @Override
    public void init() {
        command = "/town";
        commands.put("invite", "Пригласить игрока в город");
        commands.put("leave", "Покинуть город");
        commands.put("menu", "Открыть меню города");
    }

    @Override
    public void showHelp() {
        sender.sendMessage(RedRealms.getLocalization().getString("messages.help.town.1"));
        sender.sendMessage(RedRealms.getLocalization().getString("messages.help.town.2"));
        sender.sendMessage(RedRealms.getLocalization().getString("messages.help.town.3"));
        sender.sendMessage(RedRealms.getLocalization().getString("messages.help.town.4"));
    }

    public void leaveCMD() {
        if (!(sender instanceof Player)) {
            RedRealms.getPlugin().getRedLogger().info(RedRealms.getLocalization().getRawString("messages.errors.only-for-players"));
            return;
        }
        Player p = (Player) sender;

        if (!sender.hasPermission("redrealms.town.leave")) {
            ((Player) sender).sendRawMessage(RedRealms.getLocalization().getString("messages.errors.don't-have-permissions-command"));
            p.playSound(p.getLocation(), RedRealms.getDefaultConfig().getErrorSoundName(),
                    RedRealms.getDefaultConfig().getErrorSoundVolume(), RedRealms.getDefaultConfig().getErrorSoundPitch());
            return;
        }

        RedPlayer rp = RedData.loadPlayer(p);

        if (!rp.hasTown()) {
            p.sendRawMessage(RedRealms.getLocalization().getString("messages.errors.player-not-in-town"));
            p.playSound(p.getLocation(), RedRealms.getDefaultConfig().getErrorSoundName(),
                    RedRealms.getDefaultConfig().getErrorSoundVolume(), RedRealms.getDefaultConfig().getErrorSoundPitch());
            return;
        }

        RedTown rt = RedData.loadTown(rp.getTownName());
        rt.kickPlayer(p);
    }

    public void menuCMD() {
        if (!(sender instanceof Player)) {
            RedRealms.getPlugin().getRedLogger().info(RedRealms.getLocalization().getRawString("messages.errors.only-for-players"));
            return;
        }

        Player p = (Player) sender;

        if (!sender.hasPermission("redrealms.town.menu.open")) {
            sender.sendMessage(RedRealms.getLocalization().getString("messages.errors.don't-have-permissions-command"));
            p.playSound(p.getLocation(), RedRealms.getDefaultConfig().getErrorSoundName(),
                    RedRealms.getDefaultConfig().getErrorSoundVolume(), RedRealms.getDefaultConfig().getErrorSoundPitch());
            return;
        }

        RedPlayer rp = RedData.loadPlayer(p);

        if (!rp.hasTown()) {
            p.sendRawMessage(RedRealms.getLocalization().getString("messages.errors.player-not-in-town"));
            p.playSound(p.getLocation(), RedRealms.getDefaultConfig().getErrorSoundName(),
                    RedRealms.getDefaultConfig().getErrorSoundVolume(), RedRealms.getDefaultConfig().getErrorSoundPitch());
            return;
        }


        GUITown gui = new GUITown();
        gui.openInventory((Player) sender);
    }

    public void inviteCMD() {
        if (!(sender instanceof Player)) {
            RedRealms.getPlugin().getRedLogger().info(RedRealms.getLocalization().getRawString("messages.errors.only-for-players"));
            return;
        }

        Player p = (Player) sender;

        if (!sender.hasPermission("redrealms.town.mayor.invite")) {
            sender.sendMessage(RedRealms.getLocalization().getString("messages.errors.don't-have-permissions-command"));
            p.playSound(p.getLocation(), RedRealms.getDefaultConfig().getErrorSoundName(),
                    RedRealms.getDefaultConfig().getErrorSoundVolume(), RedRealms.getDefaultConfig().getErrorSoundPitch());
            return;
        }

        RedPlayer rp = RedData.loadPlayer(p);

        if (!rp.hasTown()) {
            p.sendRawMessage(RedRealms.getLocalization().getString("messages.errors.player-not-in-town"));
            p.playSound(p.getLocation(), RedRealms.getDefaultConfig().getErrorSoundName(),
                    RedRealms.getDefaultConfig().getErrorSoundVolume(), RedRealms.getDefaultConfig().getErrorSoundPitch());
            return;
        }

        if (!rp.isMayor()) {
            p.sendRawMessage(RedRealms.getLocalization().getString("messages.errors.player-is-not-mayor"));
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
        p.sendRawMessage(args[1]);
    }
}

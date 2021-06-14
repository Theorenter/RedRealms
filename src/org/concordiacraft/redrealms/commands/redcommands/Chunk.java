package org.concordiacraft.redrealms.commands.redcommands;

import org.concordiacraft.redrealms.main.RedRealms;
import org.concordiacraft.redutils.commands.RedCommand;

public class Chunk extends RedCommand {
    @Override
    public void init() {
        command = "/chunk";
        commands.put("capture", "Приобрести чанк");
        commands.put("drop", "Покинуть чанк");
        commands.put("give", "Передать чанк игроку");
        commands.put("nationalize", "Передать в состав города");
    }

    @Override
    public void showHelp() {
        sender.sendMessage(RedRealms.getLocalization().getString("messages.help.chunk.header"));
        sender.sendMessage(RedRealms.getLocalization().getString("messages.help.chunk.buy"));
        sender.sendMessage(RedRealms.getLocalization().getString("messages.help.chunk.drop"));
        sender.sendMessage(RedRealms.getLocalization().getString("messages.help.chunk.give"));
        sender.sendMessage(RedRealms.getLocalization().getString("messages.help.chunk.nationalize"));
    }
}
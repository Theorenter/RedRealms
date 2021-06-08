package org.concordiacraft.redrealms.prompts.town;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.ValidatingPrompt;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.concordiacraft.redrealms.data.*;
import org.concordiacraft.redrealms.main.RedRealms;
import org.concordiacraft.redutils.utils.RedFormatter;

import java.util.Objects;
import java.util.regex.Pattern;

public class TownCreate extends ValidatingPrompt {

    private final String biomeLocale;
    private final String biomeType;
    private final String hoverText;
    private final Chunk newTownChunk;
    private final ItemStack townBanner;
    private boolean isError = false;

    public TownCreate(String biomeType, Chunk newTownChunk, ItemStack townBanner) {

        this.biomeType = biomeType;
        this.newTownChunk = newTownChunk;
        this.townBanner = townBanner;

        biomeLocale = RedRealms.getLocalization().getRawString("biomes." + biomeType + "-biome");
        hoverText = RedRealms.getLocalization().getString("conversations.prompts.town-create-start.hover-" + biomeType);

    }
/*    @Override
    public String getPromptText(ConversationContext context) {

        String stringSpace = RedRealms.getLocalization().getRawString("conversations.space");

        String charPortrait = RedRealms.getLocalization().getString("conversations.characters.consul.portrait");
        String charName = stringSpace + RedRealms.getLocalization().getString("conversations.name-preformat") +
                RedRealms.getLocalization().getString("conversations.characters.consul.name");
        String s1 = stringSpace;
        String s2 = stringSpace;
        String s3 = stringSpace;

        Player p = (Player) context.getForWhom();

        if (biomeLocale == null) {

            s1 += RedRealms.getLocalization().getString("conversations.prompts.consul.cannot-create-town-there.1");
            s2 += RedRealms.getLocalization().getString("conversations.prompts.consul.cannot-create-town-there.2");
            s3 += RedRealms.getLocalization().getString("conversations.prompts.consul.cannot-create-town-there.3");

            p.sendRawMessage(charPortrait);
            p.sendRawMessage(RedFormatter.format(charName));

            p.sendRawMessage(RedFormatter.format(s1));
            p.sendRawMessage(RedFormatter.format(s2));
            p.sendRawMessage(RedFormatter.format(s3));

            return "";
        }

        if (!hasInvalidInput) {
            String helpColor = RedRealms.getLocalization().getRawString("components-color.help");
            String convTextColor = RedRealms.getLocalization().getRawString("components-color.conversation-text");

            p.sendRawMessage(charPortrait);
            p.sendRawMessage(RedFormatter.format(charName));

            s1 = RedRealms.getLocalization().getString("conversations.prompts.consul.town-create-start.1");
            s2 += RedRealms.getLocalization().getString("conversations.prompts.consul.town-create-start.2");
            s3 += RedRealms.getLocalization().getString("conversations.prompts.consul.town-create-start.3");

            TextComponent tc = new TextComponent(stringSpace);

            TextComponent hoverComponent = new TextComponent(biomeLocale);
            hoverComponent.setColor(ChatColor.of(helpColor));
            hoverComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(hoverText)));
            hoverComponent.setBold(true);

            TextComponent tcText = new TextComponent(String.format(s1, ""));
            tcText.setColor(ChatColor.of(convTextColor));

            tc.addExtra(hoverComponent);
            tc.addExtra(tcText);

            p.spigot().sendMessage(tc);
            p.sendRawMessage(RedFormatter.format(s2));
            p.sendRawMessage(RedFormatter.format(s3));

            return "";
        }
        return RedRealms.getLocalization().getString("messages.notifications.town-creation-after-invalid");
    }

    @Override
    protected Prompt acceptValidatedInput(ConversationContext context, String s) {
        Player mayor = (Player) context.getForWhom();
        PromptData.removeFromPromptMap(((Player) context.getForWhom()).getUniqueId());

        RedTown newTown = new RedTown(s, (Player) context.getForWhom(), townBanner, newTownChunk);
        Bukkit.getServer().broadcastMessage(String.format(RedRealms.getLocalization().getString("messages.notifications.new-town-was-created"), mayor.getName(), s));
        return null;
    }*/

    @Override
    public String getPromptText(ConversationContext context) {

        Player p = (Player) context.getForWhom();

        if (biomeLocale == null) {
            return RedFormatter.format("messages.errors.cannot-create-town-here");
        }

        String helpColor = RedRealms.getLocalization().getRawString("components-color.help");
        String convTextColor = RedRealms.getLocalization().getRawString("components-color.conversation-text");

        TextComponent tc = new TextComponent();

        TextComponent hoverComponent = new TextComponent(biomeLocale);
        hoverComponent.setColor(ChatColor.of(helpColor));
        hoverComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(hoverText)));
        hoverComponent.setBold(true);

        TextComponent tcText = new TextComponent(RedRealms.getLocalization().getString("conversations.prompts.town-create-start." + biomeType));
        tcText.setColor(ChatColor.of(convTextColor));

        tc.addExtra(hoverComponent);
        tc.addExtra(tcText);

        p.spigot().sendMessage(tc);
        return RedRealms.getLocalization().getString("conversations.prompts.town-create-start.prompt");
    }

    @Override
    protected Prompt acceptValidatedInput(ConversationContext context, String s) {

        Player p = (Player) context.getForWhom();

        if (isError) {
            ItemStack i = (ItemStack) PromptData.getPromptMap().get(((Player) context.getForWhom()).getUniqueId());
            Inventory pi = p.getInventory();

            if (pi.firstEmpty() == -1) {
                p.getWorld().dropItem(p.getLocation(), i);
               RedRealms.getPlugin().getRedLogger().info("Игроку " + p.getName() + " [" + p.getUniqueId().toString() + "]" + " был возврашён следующий предмет: " + i.getItemMeta().getDisplayName() + " (Заспаунился рядом с игроком)");
            } else {
                pi.addItem(i);
                RedRealms.getPlugin().getRedLogger().info("Игроку " + p.getName() + " [" + p.getUniqueId().toString() + "]" + " был возврашён следующий предмет: " + i.getItemMeta().getDisplayName() + "");
            }
            PromptData.removeFromPromptMap(p.getUniqueId());
            return END_OF_CONVERSATION;
        }

        new RedTown(s, p, townBanner, newTownChunk, biomeType);
        PromptData.removeFromPromptMap(p.getUniqueId());
        for (Player pl : Bukkit.getServer().getOnlinePlayers()) {
            if (!pl.equals(p))
                pl.sendRawMessage(String.format(RedRealms.getLocalization().getString("messages.notifications.new-town-was-created"), p.getName(), s));
        }

        p.sendRawMessage(String.format(RedRealms.getLocalization().getString("messages.notifications.new-town-was-created-by-you"), s));
        return END_OF_CONVERSATION;
    }

    @Override
    protected boolean isInputValid(ConversationContext context, String input) {

        RedPlayer rp = RedData.loadPlayer((Player) context.getForWhom());

        // max-min length check
        if (input.length() < RedRealms.getDefaultConfig().getNameMinLength()) {
            context.getForWhom().sendRawMessage(String.format(RedRealms.getLocalization().getString("messages.errors.this-name-too-short"), RedRealms.getDefaultConfig().getNameMinLength()));
            isError = true;
            return true;
        }
        if (input.length() > RedRealms.getDefaultConfig().getNameMaxLength()) {
            context.getForWhom().sendRawMessage(String.format(RedRealms.getLocalization().getString("messages.errors.this-name-too-long"), RedRealms.getDefaultConfig().getNameMaxLength()));
            isError = true;
            return true;
        }

        // regex check
        Pattern pattern = Pattern.compile(Objects.requireNonNull(RedRealms.getDefaultConfig().getNameRegex()));
        if (!pattern.matcher(input).matches()) {
            context.getForWhom().sendRawMessage(RedRealms.getLocalization().getString("messages.errors.this-name-out-of-regex"));
            isError = true;
            return true;
        }

        // name check
        RedTown town = RedData.loadTown(input);
        if (town.readFile()) {
            context.getForWhom().sendRawMessage(RedRealms.getLocalization().getString("messages.errors.this-town-name-already-taken"));
            isError = true;
            return true;
        }

        // check if player already in town
        if (rp.hasTown()) {
            context.getForWhom().sendRawMessage(String.format(RedRealms.getLocalization().getString("messages.errors.player-already-in-town"), rp.getTownName()));
            ((Player) context.getForWhom()).playSound(((Player) context.getForWhom()).getLocation(),
                    RedRealms.getDefaultConfig().getErrorSoundName(), RedRealms.getDefaultConfig().getErrorSoundVolume(), RedRealms.getDefaultConfig().getErrorSoundPitch());
            isError = true;
            return true;
        }

        // check if this chunk already owned
        RedChunk rc = RedData.loadChunk(newTownChunk);
        if (rc.hasTownOwner()) {
            context.getForWhom().sendRawMessage(String.format(RedRealms.getLocalization().getString("messages.errors.this-territory-already-taken"), rc.getTownOwner()));
            ((Player) context.getForWhom()).playSound(((Player) context.getForWhom()).getLocation(),
                    RedRealms.getDefaultConfig().getErrorSoundName(), RedRealms.getDefaultConfig().getErrorSoundVolume(), RedRealms.getDefaultConfig().getErrorSoundPitch());
            isError = true;
            return true;
        }

        return true;
    }

}

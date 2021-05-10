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
import org.bukkit.inventory.ItemStack;
import org.concordiacraft.redrealms.config.ConfigDefault;
import org.concordiacraft.redrealms.config.ConfigLocalization;
import org.concordiacraft.redrealms.data.RedTown;
import org.concordiacraft.redrealms.data.PromptData;
import org.concordiacraft.redutils.main.utils.RedFormatter;

import java.util.regex.Pattern;

public class TownCreatePrompt extends ValidatingPrompt {

    private String biomeLocale = null;
    private String hoverText = null;
    private boolean hasInvalidInput = false;
    private Chunk newTownChunk;
    private ItemStack townBanner;

    public TownCreatePrompt(String biomeType, Chunk newTownChunk, ItemStack townBanner) {

        this.newTownChunk = newTownChunk;
        this.townBanner = townBanner;

        switch (biomeType) {
            case "snowy": {
                biomeLocale = ConfigLocalization.getRawString("biomes.snowy-biome");
                hoverText = ConfigLocalization.getString("conversations.prompts.consul.town-create-start.hover-snowy");
                break;
            }
            case "cold": {
                biomeLocale = ConfigLocalization.getRawString("biomes.cold-biome");
                hoverText = ConfigLocalization.getString("conversations.prompts.consul.town-create-start.hover-cold");
                break;
            }
            case "temperate": {
                biomeLocale = ConfigLocalization.getRawString("biomes.temperate-biome");
                hoverText = ConfigLocalization.getString("conversations.prompts.consul.town-create-start.hover-temperate");
                break;
            }
            case "warm": {
                biomeLocale = ConfigLocalization.getRawString("biomes.warm-biome");
                hoverText = ConfigLocalization.getString("conversations.prompts.consul.town-create-start.hover-warm");
                break;
            }
        }
    }
    @Override
    public String getPromptText(ConversationContext context) {

        String stringSpace = ConfigLocalization.getRawString("conversations.space");

        String charPortrait = ConfigLocalization.getString("conversations.characters.consul.portrait");
        String charName = stringSpace + ConfigLocalization.getString("conversations.name-preformat") +
                ConfigLocalization.getString("conversations.characters.consul.name");
        String s1 = stringSpace;
        String s2 = stringSpace;
        String s3 = stringSpace;

        Player p = (Player) context.getForWhom();

        if (biomeLocale == null) {

            s1 += ConfigLocalization.getString("conversations.prompts.consul.cannot-create-town-there.1");
            s2 += ConfigLocalization.getString("conversations.prompts.consul.cannot-create-town-there.2");
            s3 += ConfigLocalization.getString("conversations.prompts.consul.cannot-create-town-there.3");

            p.sendRawMessage(charPortrait);
            p.sendRawMessage(RedFormatter.format(charName));

            p.sendRawMessage(RedFormatter.format(s1));
            p.sendRawMessage(RedFormatter.format(s2));
            p.sendRawMessage(RedFormatter.format(s3));

            return "";
        }

        if (!hasInvalidInput) {
            String helpColor = ConfigLocalization.getRawString("components-color.help");
            String convTextColor = ConfigLocalization.getRawString("components-color.conversation-text");

            p.sendRawMessage(charPortrait);
            p.sendRawMessage(RedFormatter.format(charName));

            s1 = ConfigLocalization.getString("conversations.prompts.consul.town-create-start.1");
            s2 += ConfigLocalization.getString("conversations.prompts.consul.town-create-start.2");
            s3 += ConfigLocalization.getString("conversations.prompts.consul.town-create-start.3");

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
        return ConfigLocalization.getString("messages.notifications.town-creation-after-invalid");
    }

    @Override
    protected Prompt acceptValidatedInput(ConversationContext context, String s) {
        Player mayor = (Player) context.getForWhom();
        PromptData.removeFromPromptMap(((Player) context.getForWhom()).getUniqueId());
        RedTown newTown = new RedTown(s, (Player) context.getForWhom(), townBanner, newTownChunk);

        Bukkit.getServer().broadcastMessage(String.format(ConfigLocalization.getString("messages.notifications.new-town-was-created"), mayor.getName(), s));
        return null;
    }

    @Override
    protected boolean isInputValid(ConversationContext context, String input) {
        // Max-min length check
        if (input.length() < ConfigDefault.getNameMinLength()) {
            context.getForWhom().sendRawMessage(String.format(ConfigLocalization.getString("messages.errors.this-name-too-short"), ConfigDefault.getNameMinLength()));
            hasInvalidInput = true;
            return false;
        }
        if (input.length() > ConfigDefault.getNameMaxLength()) {
            context.getForWhom().sendRawMessage(String.format(ConfigLocalization.getString("messages.errors.this-name-too-long"), ConfigDefault.getNameMaxLength()));
            hasInvalidInput = true;
            return false;
        }

        // Regex check
        Pattern pattern = Pattern.compile(ConfigDefault.getNameRegex());
        if (!pattern.matcher(input).matches()) {
            context.getForWhom().sendRawMessage(String.format(ConfigLocalization.getString("messages.errors.this-name-out-of-regex"), ConfigDefault.getNameMinLength()));
            hasInvalidInput = true;
            return false;
        }

        // Name check
        RedTown town = new RedTown(input);
        if (town.readFile()) {
            context.getForWhom().sendRawMessage(String.format(ConfigLocalization.getString("messages.errors.this-town-name-already-taken"), ConfigDefault.getNameMinLength()));
            hasInvalidInput = true;
            return false;
        }
        return true;
    }
}

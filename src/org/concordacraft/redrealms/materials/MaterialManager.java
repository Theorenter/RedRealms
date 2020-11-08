package org.concordacraft.redrealms.materials;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;
import org.concordacraft.redrealms.main.RedLog;
import org.concordacraft.redrealms.main.RedRealms;
import org.concordacraft.redrealms.main.RedFormatter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.URL;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class MaterialManager {

    private List<ItemStack> customItems = new ArrayList<>();
    private final String customItemsPath = ("settings" + File.separator + "materials" + File.separator + "items");
    private File customItemsFile;

    public MaterialManager(RedRealms plugin) {


        itemsFileLoader(plugin);
        initializeCustomItems(plugin);
    }

    private void itemsFileLoader(RedRealms plugin) {
        File itemsData = new File(plugin.getDataFolder() + File.separator + "settings" + File.separator + "materials" + File.separator + "items");
        if (!itemsData.isDirectory()) {
            CodeSource src = plugin.getClass().getProtectionDomain().getCodeSource();
            URL jar = src.getLocation();
            try {
                ZipInputStream zip = new ZipInputStream(jar.openStream());
                while (true) {
                    ZipEntry e = zip.getNextEntry();
                    if (e == null)
                        break;
                    String fileFullName = e.getName();
                    if ((fileFullName.startsWith("settings/materials/items/")) && fileFullName.endsWith(".json")) {
                        plugin.saveResource(fileFullName, false);
                    }
                }
            } catch (IOException e) {
                RedLog.error("An error occurred when creating custom items!", e);
            }
        }
    }

    private void initializeCustomItems(RedRealms plugin) {
        customItemsFile = new File(plugin.getDataFolder() + File.separator + customItemsPath);
        if (customItemsFile.listFiles().length > 0) {
            for (File customItem : customItemsFile.listFiles()) {
                try {
                    // Custom item fields
                    String itemID;
                    ItemStack itemStack;
                    Material minecraftMaterial;

                    ItemMeta itemMeta = null;
                    Long damage;
                    Boolean unbreakable;
                    String displayName;
                    List<String> lore;
                    Long customModelData;

                    // Main part
                    JSONParser jsonParser = new JSONParser();
                    Object parsed = jsonParser.parse(new BufferedReader(new InputStreamReader(new FileInputStream(customItem.getPath()), "UTF-8")));
                    JSONObject jsonObject = (JSONObject) parsed;

                    itemID = (String) jsonObject.get("redrealms-id");
                    JSONObject itemStackObject = (JSONObject) jsonObject.get("item-stack");
                    {
                        minecraftMaterial = Material.getMaterial((String) itemStackObject.get("minecraft-material"));
                        if (itemStackObject.containsKey("damage")) {
                            damage = (Long) itemStackObject.get("damage");
                        }
                    }
                    itemStack = new ItemStack(minecraftMaterial);

                    // Meta
                    JSONObject metaObject = (JSONObject) jsonObject.get("meta");
                    if (metaObject != null) {
                        itemMeta = itemStack.getItemMeta();

                        if (metaObject.containsKey("display-name")) {
                            String formatDisplayName;
                            displayName = (String) metaObject.get("display-name");
                            formatDisplayName = RedFormatter.format(displayName);
                            itemMeta.setDisplayName(formatDisplayName);
                        }
                        if (metaObject.containsKey("lore")) {
                            lore = (List<String>) metaObject.get("lore");
                            List<String> formatLore = new ArrayList<>();
                            for (String s : lore) {
                                formatLore.add(RedFormatter.format(s));
                            }
                            itemMeta.setLore(formatLore);
                        }
                        if (metaObject.containsKey("unbreakable")) {
                            unbreakable = (Boolean) itemStackObject.get("unbreakable");
                            itemMeta.setUnbreakable(unbreakable);
                        }
                        if (metaObject.containsKey("custom-model-data")) {
                            customModelData = (Long) metaObject.get("custom-model-data");
                            itemMeta.setCustomModelData(customModelData.intValue());
                        }
                    }
                    // Attributes
                    if (jsonObject.containsKey("attributes")) {
                        JSONArray attributesObject = (JSONArray) jsonObject.get("attributes");
                        for (Object objAttribute : attributesObject) {
                            JSONObject attribute = (JSONObject) objAttribute;
                            itemMeta = addAttribute(attribute, itemMeta);
                        }
                    }

                    itemStack.setItemMeta(itemMeta);
                    customItems.add(itemStack);
                    JSONArray recipesObject = (JSONArray) jsonObject.get("recipes");
                    for (Object objRecipe: recipesObject){
                        JSONObject recipe = (JSONObject) objRecipe;
                        if (recipe.containsKey("shaped-recipe")) {
                            newShapedRecipe(recipe, itemStack); break; }
                        /*if (recipe.containsKey("shapeless-recipe")) {
                            Bukkit.addRecipe(newShapelessRecipe()); }
                        if (recipe.containsKey("furnace-recipe")) {
                            Bukkit.addRecipe(newFurnaceRecipe()); }
                        if (recipe.containsKey("blasting-recipe")) {
                            Bukkit.addRecipe(newBlastingRecipe()); }
                        if (recipe.containsKey("smoking-recipe")) {
                            Bukkit.addRecipe(newSmokingRecipe()); }
                        if (recipe.containsKey("campfire-recipe")) {
                            Bukkit.addRecipe(newCampfireRecipe()); }
                        if (recipe.containsKey("smithing-recipe")) {
                            Bukkit.addRecipe(newSmithingRecipe()); }
                        if (recipe.containsKey("stonecutting-recipe")) {
                            Bukkit.addRecipe(newStonecuttingRecipe()); }*/
                    }

                } catch (IOException | ParseException e) {
                    RedLog.error("Cannot create a custom item from the " + customItem + " file", e);
                }
            }
        }
    }
    private void newShapedRecipe(JSONObject recipe, ItemStack itemStack) {
        JSONObject shapedObject = (JSONObject) recipe.get("shaped-recipe");
        String keyString = (String) shapedObject.get("recipe-key");
        Map<String, String> ingredients = (Map<String, String>) shapedObject.get("ingredients");

        if (shapedObject.containsKey("amount")) {
            Long amount = (Long) shapedObject.get("amount");
            if (amount > 0 && amount <= itemStack.getMaxStackSize()) {
                itemStack.setAmount(amount.intValue());
            } else {
                RedLog.warning("The amount of executed items for the recipe " +
                        keyString + " is specified incorrectly! Max stack size of this itemStack: " +
                        itemStack.getMaxStackSize() + " | Specified number: " + amount);
            }
        }

        JSONArray jsonShape = (JSONArray) shapedObject.get("shape");
        List<String> shape = new ArrayList<>();
        for (Object shapeRow: jsonShape) { shape.add(shapeRow.toString()); }

        NamespacedKey namespacedKey = new NamespacedKey(RedRealms.getPlugin(), keyString);
        ShapedRecipe shapedRecipe = new ShapedRecipe(namespacedKey, itemStack);
        shapedRecipe.shape(shape.get(0), shape.get(1), shape.get(2));
        ingredients.forEach((k, v) -> shapedRecipe.setIngredient(k.charAt(0), Material.getMaterial(v)));

        Bukkit.addRecipe(shapedRecipe);
    }
    /*private ShapelessRecipe newShapelessRecipe() { ShapelessRecipe shapelessRecipe; return shapelessRecipe; }
    private FurnaceRecipe newFurnaceRecipe() { FurnaceRecipe furnaceRecipe; return furnaceRecipe; }
    private BlastingRecipe newBlastingRecipe () { BlastingRecipe blastingRecipe; return blastingRecipe; }
    private SmokingRecipe newSmokingRecipe () { SmokingRecipe smokingRecipe; return smokingRecipe; }
    private CampfireRecipe newCampfireRecipe () { CampfireRecipe campfireRecipe; return campfireRecipe; }
    private SmithingRecipe newSmithingRecipe () { SmithingRecipe smithingRecipe; return smithingRecipe; }
    private StonecuttingRecipe newStonecuttingRecipe () { StonecuttingRecipe stonecuttingRecipe; return stonecuttingRecipe; }*/

    private ItemMeta addAttribute(JSONObject attribute, ItemMeta itemMeta) {
        String attributeMod = (String) attribute.get("attribute-modifier");
        Long amount = (Long) attribute.get("attribute-amount");
        String operationSt = (String) attribute.get("attribute-operation");
        String eqSlot = (String) attribute.get("equipment-slot");

        AttributeModifier modifier;

        AttributeModifier.Operation operation = null;
        if (operationSt != null) {

            switch (operationSt) {
                case "ADD-NUMBER": {
                    operation = AttributeModifier.Operation.ADD_NUMBER;
                    break;
                }
                case "ADD-SCALAR": {
                    operation = AttributeModifier.Operation.ADD_SCALAR;
                    break;
                }
                case "MULTIPLY-SCALAR": {
                    operation = AttributeModifier.Operation.MULTIPLY_SCALAR_1;
                }
                default: {
                    RedLog.warning("Field \"attribute-operation\" for the item \"" + itemMeta.getDisplayName() + "\" is set incorrectly!");
                    RedLog.warning("Available values for this field: \"ADD-NUMBER\", \"ADD-SCALAR\", \"MULTIPLY-SCALAR\"");
                }
            }
        }

        EquipmentSlot equipmentSlot = null;
        if (eqSlot != null) {
            switch (eqSlot) {
                case "HAND": { equipmentSlot = EquipmentSlot.HAND; break; }
                case "OFF-HAND": { equipmentSlot = EquipmentSlot.OFF_HAND; break; }
                case "HEAD": { equipmentSlot = EquipmentSlot.HEAD; break; }
                case "CHEST": { equipmentSlot = EquipmentSlot.CHEST; break; }
                case "LEGS": { equipmentSlot = EquipmentSlot.LEGS; break; }
                case "FEET": { equipmentSlot = EquipmentSlot.FEET; break; }
                default : {
                    RedLog.warning("Field \"equipment-slot\" for the item \"" + itemMeta.getDisplayName() + "\" is set incorrectly!");
                    RedLog.warning("Available values for this field: \"HAND\", \"OFF-HAND\", \"HEAD\", \"CHEST\", \"LEGS\", \"FEET\" (or you can simply delete this field so that the modifier applies to everything at once)");
                }
            }
            modifier = new AttributeModifier(UUID.randomUUID(), attributeMod, amount, operation, equipmentSlot);
        } else {
            modifier = new AttributeModifier(UUID.randomUUID(), attributeMod, amount, operation);
        }

        Attribute atr = null;
        switch (attributeMod) {
            case "GENERIC-ARMOR": { atr = Attribute.GENERIC_ARMOR; break; }
            case "GENERIC-ARMOR-TOUGHNESS": { atr = Attribute.GENERIC_ARMOR_TOUGHNESS; break; }
            case "GENERIC-ATTACK-DAMAGE": { atr = Attribute.GENERIC_ATTACK_DAMAGE; break; }
            case "GENERIC-ATTACK-KNOCKBACK": { atr = Attribute.GENERIC_ATTACK_KNOCKBACK; break; }
            case "GENERIC-ATTACK-SPEED": { atr = Attribute.GENERIC_ATTACK_SPEED; break; }
            case "GENERIC-FLYING-SPEED": { atr = Attribute.GENERIC_FLYING_SPEED; break; }
            case "GENERIC-FOLLOW-RANGE": { atr = Attribute.GENERIC_FOLLOW_RANGE; break; }
            case "GENERIC-KNOCKBACK-RESISTANCE": { atr = Attribute.GENERIC_KNOCKBACK_RESISTANCE; break; }
            case "GENERIC-LUCK": { atr = Attribute.GENERIC_LUCK; break; }
            case "GENERIC-MAX-HEALTH": { atr = Attribute.GENERIC_MAX_HEALTH; break; }
            case "GENERIC-MOVEMENT-SPEED": { atr = Attribute.GENERIC_MOVEMENT_SPEED; break; }
            default : {
                RedLog.warning("Field \"attribute-modifier\" for the item \"" + itemMeta.getDisplayName() + "\" is set incorrectly!");
                RedLog.warning("Available values for this field: \"GENERIC-ARMOR\", \"GENERIC-ARMOR-TOUGHNESS\", \"GENERIC-ATTACK-DAMAGE\", \"GENERIC-ATTACK-KNOCKBACK\", \"GENERIC-ATTACK-SPEED\", \"GENERIC-FOLLOW-RANGE\", \"GENERIC-KNOCKBACK-RESISTANCE\", \"GENERIC-LUCK\", \"GENERIC-MAX-HEALTH\", \"GENERIC-MOVEMENT-SPEED\"");
            }
        }
        itemMeta.addAttributeModifier(atr, modifier);
        return itemMeta;
    }
}
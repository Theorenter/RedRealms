package org.concordiacraft.redrealms.materials;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.concordiacraft.redrealms.main.RedLog;
import org.concordiacraft.redrealms.main.RedRealms;
import org.concordiacraft.redrealms.main.RedFormatter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.URL;
import java.security.CodeSource;
import java.util.*;
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
                        // Enchantments
                        if (metaObject.containsKey("enchantments")) {
                            JSONArray enchantmentsObject = (JSONArray) metaObject.get("enchantments");
                            for (Object objAEnchant : enchantmentsObject) {
                                JSONObject enchant = (JSONObject) objAEnchant;
                                itemMeta = addEnchantment(enchant, itemMeta, itemStack);
                            }
                        }
                        // Attributes
                        if (metaObject.containsKey("attributes")) {
                            JSONArray attributesObject = (JSONArray) metaObject.get("attributes");
                            for (Object objAttribute : attributesObject) {
                                JSONObject attribute = (JSONObject) objAttribute;
                                itemMeta = addAttribute(attribute, itemMeta);
                            }
                        }
                        // Persistent data
                        if (metaObject.containsKey("persistent-data")) {
                            JSONArray dataObject = (JSONArray) metaObject.get("persistent-data");
                            for (Object objData : dataObject) {
                                JSONObject data = (JSONObject) objData;
                                itemMeta = addPersistentData(data, itemMeta);
                            }
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

    private ItemMeta addEnchantment(JSONObject enchantment, ItemMeta itemMeta, ItemStack itemStack) {
        String enchantmentString = (String) enchantment.get("enchantment");
        Long level = (Long) enchantment.get("level");
        Boolean ignoreLevelRestriction = (Boolean) enchantment.get("ignore-level-restriction");
        Enchantment enchant = null;
        switch (enchantmentString) {
            case "ARROW-DAMAGE": { enchant = Enchantment.ARROW_DAMAGE; break; }
            case "ARROW-FIRE": { enchant = Enchantment.ARROW_FIRE; break; }
            case "ARROW-INFINITE": { enchant = Enchantment.ARROW_INFINITE; break; }
            case "ARROW-KNOCKBACK": { enchant = Enchantment.ARROW_KNOCKBACK; break; }
            case "BINDING-CURSE": { enchant = Enchantment.BINDING_CURSE; break; }
            case "CHANNELING": { enchant = Enchantment.CHANNELING; break; }
            case "DAMAGE-ALL": { enchant = Enchantment.DAMAGE_ALL; break; }
            case "DAMAGE-ARTHROPODS": { enchant = Enchantment.DAMAGE_ARTHROPODS; break; }
            case "DAMAGE-UNDEAD": { enchant = Enchantment.DAMAGE_UNDEAD; break; }
            case "DEPTH-STRIDER": { enchant = Enchantment.DEPTH_STRIDER; break; }
            case "DIG-SPEED": { enchant = Enchantment.DIG_SPEED; break; }
            case "DURABILITY": { enchant = Enchantment.DURABILITY; break; }
            case "FIRE-ASPECT": { enchant = Enchantment.FIRE_ASPECT; break; }
            case "FROST-WALKER": { enchant = Enchantment.FROST_WALKER; break; }
            case "IMPALING": { enchant = Enchantment.IMPALING; break; }
            case "KNOCKBACK": { enchant = Enchantment.KNOCKBACK; break; }
            case "LOOT-BONUS-BLOCKS": { enchant = Enchantment.LOOT_BONUS_BLOCKS; break; }
            case "LOOT-BONUS-MOBS": { enchant = Enchantment.LOOT_BONUS_MOBS; break; }
            case "LOYALTY": { enchant = Enchantment.LOYALTY; break; }
            case "LUCK": { enchant = Enchantment.LUCK; break; }
            case "LURE": { enchant = Enchantment.LURE; break; }
            case "MENDING": { enchant = Enchantment.MENDING; break; }
            case "MULTISHOT": { enchant = Enchantment.MULTISHOT; break; }
            case "OXYGEN": { enchant = Enchantment.OXYGEN; break; }
            case "PIERCING": { enchant = Enchantment.PIERCING; break; }
            case "PROTECTION-ENVIRONMENTAL": { enchant = Enchantment.PROTECTION_ENVIRONMENTAL; break; }
            case "PROTECTION-EXPLOSIONS": { enchant = Enchantment.PROTECTION_EXPLOSIONS; break; }
            case "PROTECTION-FALL": { enchant = Enchantment.PROTECTION_FALL; break; }
            case "PROTECTION-FIRE": { enchant = Enchantment.PROTECTION_FIRE; break; }
            case "PROTECTION-PROJECTILE": { enchant = Enchantment.PROTECTION_PROJECTILE; break; }
            case "QUICK-CHARGE": { enchant = Enchantment.QUICK_CHARGE; break; }
            case "RIPTIDE": { enchant = Enchantment.RIPTIDE; break; }
            case "SILK-TOUCH": { enchant = Enchantment.SILK_TOUCH; break; }
            case "SOUL-SPEED": { enchant = Enchantment.SOUL_SPEED; break; }
            case "SWEEPING-EDGE": { enchant = Enchantment.SWEEPING_EDGE; break; }
            case "THORNS": { enchant = Enchantment.THORNS; break; }
            case "VANISHING-CURSE": { enchant = Enchantment.VANISHING_CURSE; break; }
            case "WATER-WORKER": { enchant = Enchantment.WATER_WORKER; break; }
            default: {
                RedLog.warning("Field \"enchantment\" for the item \"" + itemMeta.getDisplayName() + "\" is set incorrectly!");
                RedLog.warning("Here is a list of acceptable values for this field (last values):");
                for (Object element : Arrays.stream(Enchantment.values()).toArray()) { RedLog.warning(element.toString().replace('_', '-')); }
                return itemMeta;
            }
        }
        if (!enchant.canEnchantItem(itemStack)) {
            RedLog.warning("Enchantment \"" + enchantmentString + "\" cannot be applied to an item named \""+ itemMeta.getDisplayName() + "\"");
        }
        if ((level > enchant.getMaxLevel()) && (ignoreLevelRestriction == false)) {
            RedLog.warning("The maximum enchantment \"" + enchantmentString + "\" of an item named \""+ itemMeta.getDisplayName() +"\" is: " + enchant.getMaxLevel());
        }
        itemMeta.addEnchant(enchant, level.intValue(), ignoreLevelRestriction);
        return itemMeta;
    }

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

    private ItemMeta addPersistentData(JSONObject persistentData, ItemMeta itemMeta) {
        String dataKey = (String) persistentData.get("data-key");
        String dataTypeStr = (String) persistentData.get("data-type");
        PersistentDataContainer dataContainer = itemMeta.getPersistentDataContainer();
        PersistentDataType persistentDataType;

        switch (dataTypeStr) {
            case "BYTE" : {
                persistentDataType = PersistentDataType.BYTE;
                Long dataValue = (Long) persistentData.get("data-value");
                dataContainer.set(new NamespacedKey(RedRealms.getPlugin(), dataKey), persistentDataType, dataValue.byteValue());
                break;
            }
            case "BYTE_ARRAY" : {
                persistentDataType = PersistentDataType.BYTE_ARRAY;
                Byte[] dataValue = (Byte[]) persistentData.get("data-value");
                dataContainer.set(new NamespacedKey(RedRealms.getPlugin(), dataKey), persistentDataType, dataValue);
                break;
            }
            case "DOUBLE" : {
                persistentDataType = PersistentDataType.DOUBLE;
                Double dataValue = (Double) persistentData.get("data-value");
                dataContainer.set(new NamespacedKey(RedRealms.getPlugin(), dataKey), persistentDataType, dataValue);
                break;
            }
            case "FLOAT" : {
                persistentDataType = PersistentDataType.FLOAT;
                Float dataValue = (Float) persistentData.get("data-value");
                dataContainer.set(new NamespacedKey(RedRealms.getPlugin(), dataKey), persistentDataType, dataValue);
                break;
            }
            case "INTEGER" : {
                persistentDataType = PersistentDataType.INTEGER;
                Long dataValue = (Long) persistentData.get("data-value");
                dataContainer.set(new NamespacedKey(RedRealms.getPlugin(), dataKey), persistentDataType, dataValue.intValue());
                break;
            }
            case "INTEGER_ARRAY" : {
                persistentDataType = PersistentDataType.INTEGER_ARRAY;
                JSONArray valuesArray = (JSONArray) persistentData.get("data-value");
                int[] dataValue = new int[valuesArray.size()];
                int i = 0;
                for (Object el : valuesArray) {
                    dataValue[i] = ((Long) el).intValue();
                    i++;
                }
                dataContainer.set(new NamespacedKey(RedRealms.getPlugin(), dataKey), persistentDataType, dataValue);
                break;
            }
            case "LONG" : {
                persistentDataType = PersistentDataType.LONG;
                Long dataValue = (Long) persistentData.get("data-value");
                dataContainer.set(new NamespacedKey(RedRealms.getPlugin(), dataKey), persistentDataType, dataValue);
                break;
            }
            case "LONG_ARRAY" : {
                persistentDataType = PersistentDataType.LONG_ARRAY;
                Long[] dataValue = (Long[]) persistentData.get("data-value");
                dataContainer.set(new NamespacedKey(RedRealms.getPlugin(), dataKey), persistentDataType, dataValue);
                break;
            }
            case "SHORT" : {
                persistentDataType = PersistentDataType.SHORT;
                Short dataValue = (Short) persistentData.get("data-value");
                dataContainer.set(new NamespacedKey(RedRealms.getPlugin(), dataKey), persistentDataType, dataValue);
                break;
            }
            case "STRING" : {
                persistentDataType = PersistentDataType.STRING;
                String dataValue = (String) persistentData.get("data-value");
                dataContainer.set(new NamespacedKey(RedRealms.getPlugin(), dataKey), persistentDataType, dataValue);
                break;
            }
            default : {
                RedLog.warning("Field \"data-type\" for the item \"" + itemMeta.getDisplayName() + "\" is set incorrectly!");
                RedLog.warning("Here is a list of acceptable values for this field:");
                RedLog.warning("BYTE, BYTE_ARRAY, DOUBLE, FLOAT, INTEGER, INTEGER_ARRAY, SHORT, STRING");
                return itemMeta; }
        }
        return itemMeta;
    }
}
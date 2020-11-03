package org.concordacraft.redrealms.materials;
import org.apache.logging.log4j.core.pattern.AbstractStyleNameConverter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;
import org.concordacraft.redrealms.main.RedLog;
import org.concordacraft.redrealms.main.RedRealms;
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
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class MaterialManager {

    private List<ItemStack> customItems = new ArrayList<>();
    private final String customItemsPath = ("settings" + File.separator + "materials" + File.separator + "items");
    private File customItemsFile;

    // Custom item fields
    private String itemID;
    private ItemStack itemStack;
    private Material minecraftMaterial;

    private ItemMeta itemMeta;
    private Short damage;
    private Boolean unbreakable;
    private String displayName;
    private List<String> lore;

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
                    JSONParser jsonParser = new JSONParser();
                    Object parsed = jsonParser.parse(new BufferedReader(new InputStreamReader(new FileInputStream(customItem.getPath()), "UTF-8")));
                    JSONObject jsonObject = (JSONObject) parsed;

                    itemID = (String) jsonObject.get("redrealms-id");

                    JSONObject itemStackObject = (JSONObject) jsonObject.get("item-stack");
                    {
                        minecraftMaterial = Material.getMaterial((String) itemStackObject.get("minecraft-material"));
                        Long damageLong = (Long) itemStackObject.get("damage"); damage = damageLong.shortValue(); damageLong = null;
                    }
                    itemStack = new ItemStack(minecraftMaterial);

                    
                    JSONObject metaObject = (JSONObject) jsonObject.get("meta");
                    if (metaObject != null) {
                        itemMeta = itemStack.getItemMeta();

                        displayName = (String) metaObject.get("display-name");
                        lore = (List<String>) metaObject.get("lore");
                        unbreakable = (Boolean) itemStackObject.get("unbreakable");

                        itemMeta.setDisplayName(displayName);
                        itemMeta.setLore(lore);
                        itemMeta.setUnbreakable(unbreakable);

                        itemStack.setItemMeta(itemMeta);
                    }
                    customItems.add(itemStack);

                    JSONArray recipesObject = (JSONArray) jsonObject.get("recipes");

                    for (Object ObjRecipe: recipesObject){
                        JSONObject recipe = (JSONObject) ObjRecipe;
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
            if (amount > 1 && amount < itemStack.getMaxStackSize()) {
                itemStack.setAmount(amount.intValue());
            } else {
                RedLog.warning("The amount of executed items for the recipe " +
                        keyString + " is specified incorrectly! Max stack size of this itemStack: " +
                        itemStack.getMaxStackSize() + "nSpecified number: " + amount);
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
}
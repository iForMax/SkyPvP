package me.firas.skypvp.enchant;

import com.avaje.ebeaninternal.server.persist.BindValues;
import me.firas.skypvp.util.ItemBuilder;
import me.firas.skypvp.util.TextHelper;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;

import javax.lang.model.type.IntersectionType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

public class Menu {

    public Menu(Player player, ItemStack itemStack) {
        if (!itemStack.getType().equals(Material.ENCHANTED_BOOK)) {
            return;
        }
        Inventory inventory;
        String NoItemsToEnchant = "There is no Items to enchant";
        String equipmentArmor = TextHelper.text("&5&oEquipped");
        EnchantmentStorageMeta meta = (EnchantmentStorageMeta) itemStack.getItemMeta();
        if (meta.getStoredEnchants().toString().contains(Enchantment.DAMAGE_ALL.toString())) {
            String levels = null;
            int level = 0;
            if (meta.getStoredEnchants().containsValue(1)) {
                levels = "I";
                level=1;
            }
            if (meta.getStoredEnchants().containsValue(2)) {
                levels = "II";
                level=2;

            }
            if (meta.getStoredEnchants().containsValue(3)) {
                levels = "III";
                level=3;

            }
            if (meta.getStoredEnchants().containsValue(4)) {
                levels = "IV";
                level=4;

            }
            if (meta.getStoredEnchants().containsValue(5)) {
                levels = "Max";
                level=5;

            }
            int xd = -1;
            String name = "Sharpness "+levels+"";
            ArrayList<ItemStack> itemStackArrayList = new ArrayList<>();
            for (ItemStack item : player.getInventory().getContents()){
                if (item == null){
                    continue;
                }
                String itemed = WordUtils.capitalizeFully(item.getType().name().replace("_", " "));
                if (itemed.contains("Sword") || itemed.contains("Axe")){
                    itemStackArrayList.add(item);
                    xd++;
                }
            }
            if (itemStackArrayList.isEmpty()){
                inventory = Bukkit.createInventory(null,0,NoItemsToEnchant);
                player.openInventory(inventory);
                return;
            }
            if (xd <= 8){
                inventory = Bukkit.createInventory(null,9,name);
            } else if (xd <= 17){
                inventory = Bukkit.createInventory(null,18,name);
            } else if (xd <= 26){
                inventory = Bukkit.createInventory(null,27,name);
            }else{
                inventory = Bukkit.createInventory(null,36,name);
            }
            for (ItemStack itemStack1 : itemStackArrayList){
                inventory.addItem(itemStack1);
            }
            player.openInventory(inventory);
            return;

        }
        if (meta.getStoredEnchants().toString().contains(Enchantment.ARROW_DAMAGE.toString())) {
            String levels = null;
            int level = 0;
            if (meta.getStoredEnchants().containsValue(1)) {
                levels = "I";
                level=1;
            }
            if (meta.getStoredEnchants().containsValue(2)) {
                levels = "II";
                level=2;

            }
            if (meta.getStoredEnchants().containsValue(3)) {
                levels = "III";
                level=3;

            }
            if (meta.getStoredEnchants().containsValue(4)) {
                levels = "IV";
                level=4;

            }
            if (meta.getStoredEnchants().containsValue(5)) {
                levels = "Max";
                level=5;

            }
            int xd = -1;
            String name = "Power "+levels+"";
            ArrayList<ItemStack> itemStackArrayList = new ArrayList<>();
            for (ItemStack item : player.getInventory().getContents()){
                if (item == null){
                    continue;
                }
                String itemed = WordUtils.capitalizeFully(item.getType().name().replace("_", " "));
                if (itemed.contains("Bow")){
                    itemStackArrayList.add(item);
                    xd++;
                }
            }
            if (itemStackArrayList.isEmpty()){
                inventory = Bukkit.createInventory(null,0,NoItemsToEnchant);
                player.openInventory(inventory);
                return;
            }
            if (xd <= 8){
                inventory = Bukkit.createInventory(null,9,name);
            } else if (xd <= 17){
                inventory = Bukkit.createInventory(null,18,name);
            } else if (xd <= 26){
                inventory = Bukkit.createInventory(null,27,name);
            }else{
                inventory = Bukkit.createInventory(null,36,name);
            }
            for (ItemStack itemStack1 : itemStackArrayList){
                inventory.addItem(itemStack1);
            }
            player.openInventory(inventory);
            return;

        }
        if (meta.getStoredEnchants().toString().contains(Enchantment.ARROW_KNOCKBACK.toString())) {
            String levels = null;
            int level = 0;
            if (meta.getStoredEnchants().containsValue(1)) {
                levels = "I";
                level=1;
            }
            if (meta.getStoredEnchants().containsValue(2)) {
                levels = "II";
                level = 2;
            }
            int xd = -1;
            String name = "Punch "+levels+"";
            ArrayList<ItemStack> itemStackArrayList = new ArrayList<>();
            for (ItemStack item : player.getInventory().getContents()){
                if (item == null){
                    continue;
                }
                String itemed = WordUtils.capitalizeFully(item.getType().name().replace("_", " "));
                if (itemed.contains("Bow")){
                    itemStackArrayList.add(item);
                    xd++;
                }
            }
            if (itemStackArrayList.isEmpty()){
                inventory = Bukkit.createInventory(null,0,NoItemsToEnchant);
                player.openInventory(inventory);
                return;
            }
            if (xd <= 8){
                inventory = Bukkit.createInventory(null,9,name);
            } else if (xd <= 17){
                inventory = Bukkit.createInventory(null,18,name);
            } else if (xd <= 26){
                inventory = Bukkit.createInventory(null,27,name);
            }else{
                inventory = Bukkit.createInventory(null,36,name);
            }
            for (ItemStack itemStack1 : itemStackArrayList){
                inventory.addItem(itemStack1);
            }
            player.openInventory(inventory);
            return;
        }

        if (meta.getStoredEnchants().toString().contains(Enchantment.FIRE_ASPECT.toString())) {
            String levels = null;
            int level = 0;
            if (meta.getStoredEnchants().containsValue(1)) {
                levels = "I";
                level=1;
            }
            if (meta.getStoredEnchants().containsValue(2)) {
                levels = "II";
                level=2;

            }
            if (meta.getStoredEnchants().containsValue(3)) {
                levels = "III";
                level=3;

            }
            if (meta.getStoredEnchants().containsValue(4)) {
                levels = "IV";
                level=4;

            }
            if (meta.getStoredEnchants().containsValue(5)) {
                levels = "Max";
                level=5;

            }
            int xd = -1;
            String name = "Fire Aspect "+levels+"";
            ArrayList<ItemStack> itemStackArrayList = new ArrayList<>();
            for (ItemStack item : player.getInventory().getContents()){
                if (item == null){
                    continue;
                }
                String itemed = WordUtils.capitalizeFully(item.getType().name().replace("_", " "));
                if (itemed.contains("Sword")){
                    itemStackArrayList.add(item);
                    xd++;
                }
            }
            if (itemStackArrayList.isEmpty()){
                inventory = Bukkit.createInventory(null,0,NoItemsToEnchant);
                player.openInventory(inventory);
                return;
            }
            if (xd <= 8){
                inventory = Bukkit.createInventory(null,9,name);
            } else if (xd <= 17){
                inventory = Bukkit.createInventory(null,18,name);
            } else if (xd <= 26){
                inventory = Bukkit.createInventory(null,27,name);
            }else{
                inventory = Bukkit.createInventory(null,36,name);
            }
            for (ItemStack itemStack1 : itemStackArrayList){
                inventory.addItem(itemStack1);
            }
            player.openInventory(inventory);
            return;
        }
        if (meta.getStoredEnchants().toString().contains(Enchantment.KNOCKBACK.toString())) {
            String levels = null;
            int level = 0;
            if (meta.getStoredEnchants().containsValue(1)) {
                levels = "I";
                level=1;
            }
            if (meta.getStoredEnchants().containsValue(2)) {
                levels = "II";
                level=2;

            }
            int xd = -1;
            String name = "Knockback "+levels+"";
            ArrayList<ItemStack> itemStackArrayList = new ArrayList<>();
            for (ItemStack item : player.getInventory().getContents()){
                if (item == null){
                    continue;
                }
                String itemed = WordUtils.capitalizeFully(item.getType().name().replace("_", " "));
                if (itemed.contains("Sword") || itemed.contains("Axe")){
                    itemStackArrayList.add(item);
                    xd++;
                }
            }
            if (itemStackArrayList.isEmpty()){
                inventory = Bukkit.createInventory(null,0,NoItemsToEnchant);
                player.openInventory(inventory);
                return;
            }
            if (xd <= 8){
                inventory = Bukkit.createInventory(null,9,name);
            } else if (xd <= 17){
                inventory = Bukkit.createInventory(null,18,name);
            } else if (xd <= 26){
                inventory = Bukkit.createInventory(null,27,name);
            }else{
                inventory = Bukkit.createInventory(null,36,name);
            }
            for (ItemStack itemStack1 : itemStackArrayList){
                inventory.addItem(itemStack1);
            }
            player.openInventory(inventory);
            return;
        }
        if (meta.getStoredEnchants().toString().contains(Enchantment.ARROW_FIRE.toString())) {
            String levels = null;
            int level = 0;
            if (meta.getStoredEnchants().containsValue(1)) {
                levels = "I";
                level=1;
            }
            if (meta.getStoredEnchants().containsValue(2)) {
                levels = "II";
                level=2;

            }
            int xd = -1;
            String name = "Flame "+levels+"";
            ArrayList<ItemStack> itemStackArrayList = new ArrayList<>();
            for (ItemStack item : player.getInventory().getContents()){
                if (item == null){
                    continue;
                }
                String itemed = WordUtils.capitalizeFully(item.getType().name().replace("_", " "));
                if (itemed.contains("Bow")){
                    if (item.getEnchantments().containsKey(Enchantment.ARROW_FIRE)){
                        if (item.getEnchantments().containsValue(level)){
                            continue;
                        }
                    }
                    itemStackArrayList.add(item);
                    xd++;
                }
            }
            if (itemStackArrayList.isEmpty()){
                inventory = Bukkit.createInventory(null,0,NoItemsToEnchant);
                player.openInventory(inventory);
                return;
            }
            if (xd <= 8){
                inventory = Bukkit.createInventory(null,9,name);
            } else if (xd <= 17){
                inventory = Bukkit.createInventory(null,18,name);
            } else if (xd <= 26){
                inventory = Bukkit.createInventory(null,27,name);
            }else{
                inventory = Bukkit.createInventory(null,36,name);
            }
            for (ItemStack itemStack1 : itemStackArrayList){
                inventory.addItem(itemStack1);
            }
            player.openInventory(inventory);
            return;
        }
        if (meta.getStoredEnchants().toString().contains(Enchantment.ARROW_INFINITE.toString())) {
            String levels = null;
            int level = 0;
            if (meta.getStoredEnchants().containsValue(1)) {
                levels = "I";
                level=1;
            }
            int xd = -1;
            String name = "Infinity "+levels+"";
            ArrayList<ItemStack> itemStackArrayList = new ArrayList<>();
            for (ItemStack item : player.getInventory().getContents()){
                if (item == null){
                    continue;
                }
                String itemed = WordUtils.capitalizeFully(item.getType().name().replace("_", " "));
                if (itemed.contains("Bow")){
                    itemStackArrayList.add(item);
                    xd++;
                }
            }
            if (itemStackArrayList.isEmpty()){
                inventory = Bukkit.createInventory(null,0,NoItemsToEnchant);
                player.openInventory(inventory);
                return;
            }
            if (xd <= 8){
                inventory = Bukkit.createInventory(null,9,name);
            } else if (xd <= 17){
                inventory = Bukkit.createInventory(null,18,name);
            } else if (xd <= 26){
                inventory = Bukkit.createInventory(null,27,name);
            }else{
                inventory = Bukkit.createInventory(null,36,name);
            }
            for (ItemStack itemStack1 : itemStackArrayList){
                inventory.addItem(itemStack1);
            }
            player.openInventory(inventory);
            return;
        }
        if (meta.getStoredEnchants().toString().contains(Enchantment.PROTECTION_ENVIRONMENTAL.toString())) {
            String levels = null;
            int level = 0;
            if (meta.getStoredEnchants().containsValue(1)) {
                levels = "I";
                level=1;
            }
            if (meta.getStoredEnchants().containsValue(2)) {
                levels = "II";
                level=2;

            }
            if (meta.getStoredEnchants().containsValue(3)) {
                levels = "III";
                level=3;

            }
            if (meta.getStoredEnchants().containsValue(4)) {
                levels = "IV";
                level=4;

            }
            if (meta.getStoredEnchants().containsValue(5)) {
                levels = "Max";
                level=5;

            }
            int xd = -1;
            String name = "Protection "+levels+"";
            ArrayList<ItemStack> itemStackArrayList = new ArrayList<>();
            for (ItemStack item : player.getInventory().getArmorContents()) {
                if (item == null){
                    continue;
                }
                String itemed = WordUtils.capitalizeFully(item.getType().name().replace("_", " "));
                if (itemed.contains("Helmet") || itemed.contains("Leggings") || itemed.contains("Chestplate") || itemed.contains("Boots")){
                    ItemStack itemx = new ItemStack(item);
                    ArrayList lore = new ArrayList();
                    lore.add(TextHelper.text(equipmentArmor));
                    ItemMeta meta1 = itemx.getItemMeta();
                    meta1.setLore(lore);

                    itemx.setItemMeta(meta1);
                    itemStackArrayList.add(itemx);

                    xd++;
                }
            }
            for (ItemStack item : player.getInventory().getContents()){
                if (item == null){
                    continue;
                }
                String itemed = WordUtils.capitalizeFully(item.getType().name().replace("_", " "));
                if (itemed.contains("Helmet") || itemed.contains("Leggings") || itemed.contains("Chestplate") || itemed.contains("Boots")){
                    itemStackArrayList.add(item);
                    xd++;
                }
            }
                if (itemStackArrayList.isEmpty()){
                    inventory = Bukkit.createInventory(null,0,NoItemsToEnchant);
                    player.openInventory(inventory);
                    return;
            }
            if (xd <= 8){
                inventory = Bukkit.createInventory(null,9,name);
            } else if (xd <= 17){
                inventory = Bukkit.createInventory(null,18,name);
            } else if (xd <= 26){
                inventory = Bukkit.createInventory(null,27,name);
            }else if (xd <= 35){
                inventory = Bukkit.createInventory(null,36,name);
            } else {
                inventory = Bukkit.createInventory(null,45,name);

            }
            for (ItemStack itemStack1 : itemStackArrayList){
                inventory.addItem(itemStack1);
            }
            player.openInventory(inventory);
            return;
        }
        if (meta.getStoredEnchants().toString().contains(Enchantment.PROTECTION_FIRE.toString())) {
            String levels = null;
            int level = 0;
            if (meta.getStoredEnchants().containsValue(1)) {
                levels = "I";
                level=1;
            }
            if (meta.getStoredEnchants().containsValue(2)) {
                levels = "II";
                level=2;

            }
            if (meta.getStoredEnchants().containsValue(3)) {
                levels = "III";
                level=3;

            }
            if (meta.getStoredEnchants().containsValue(4)) {
                levels = "IV";
                level=4;

            }
            if (meta.getStoredEnchants().containsValue(5)) {
                levels = "Max";
                level=5;

            }
            int xd = -1;
            String name = "Fire Protection "+levels+"";
            ArrayList<ItemStack> itemStackArrayList = new ArrayList<>();
            for (ItemStack item : player.getInventory().getArmorContents()) {
                if (item == null){
                    continue;
                }
                String itemed = WordUtils.capitalizeFully(item.getType().name().replace("_", " "));
                if (itemed.contains("Helmet") || itemed.contains("Leggings") || itemed.contains("Chestplate") || itemed.contains("Boots")){
                    ItemStack itemx = new ItemStack(item);
                    ArrayList lore = new ArrayList();
                    lore.add(TextHelper.text(equipmentArmor));
                    ItemMeta meta1 = itemx.getItemMeta();
                    meta1.setLore(lore);
                    itemx.setItemMeta(meta1);
                    itemStackArrayList.add(itemx);
                    xd++;
                }
            }
            for (ItemStack item : player.getInventory().getContents()){
                if (item == null){
                    continue;
                }
                String itemed = WordUtils.capitalizeFully(item.getType().name().replace("_", " "));
                if (itemed.contains("Helmet") || itemed.contains("Leggings") || itemed.contains("Chestplate") || itemed.contains("Boots")){
                    itemStackArrayList.add(item);
                    xd++;
                }
            }
            if (itemStackArrayList.isEmpty()){
                inventory = Bukkit.createInventory(null,0,NoItemsToEnchant);
                player.openInventory(inventory);
                return;
            }
            if (xd <= 8){
                inventory = Bukkit.createInventory(null,9,name);
            } else if (xd <= 17){
                inventory = Bukkit.createInventory(null,18,name);
            } else if (xd <= 26){
                inventory = Bukkit.createInventory(null,27,name);
            }else if (xd <= 35){
                inventory = Bukkit.createInventory(null,36,name);
            } else {
                inventory = Bukkit.createInventory(null,45,name);

            }
            for (ItemStack itemStack1 : itemStackArrayList){
                inventory.addItem(itemStack1);
            }
            player.openInventory(inventory);
            return;

        }
        if (meta.getStoredEnchants().toString().contains(Enchantment.DURABILITY.toString())) {
            String levels = null;
            int level = 0;
            if (meta.getStoredEnchants().containsValue(1)) {
                levels = "I";
                level=1;
            }
            if (meta.getStoredEnchants().containsValue(2)) {
                levels = "II";
                level=2;

            }
            if (meta.getStoredEnchants().containsValue(3)) {
                levels = "III";
                level=3;

            }
            if (meta.getStoredEnchants().containsValue(4)) {
                levels = "IV";
                level=4;

            }
            if (meta.getStoredEnchants().containsValue(5)) {
                levels = "V";
                level=5;

            }
            int xd = -1;
            String name = "Unbreaking "+levels+"";
            ArrayList<ItemStack> itemStackArrayList = new ArrayList<>();
            for (ItemStack item : player.getInventory().getArmorContents()) {
                if (item == null){
                    continue;
                }
                String itemed = WordUtils.capitalizeFully(item.getType().name().replace("_", " "));
                if (itemed.contains("Helmet") || itemed.contains("Leggings") || itemed.contains("Chestplate") || itemed.contains("Boots")){
                    ItemStack itemx = new ItemStack(item);
                    ArrayList lore = new ArrayList();
                    lore.add(TextHelper.text(equipmentArmor));
                    ItemMeta meta1 = itemx.getItemMeta();
                    meta1.setLore(lore);

                    itemx.setItemMeta(meta1);
                    itemStackArrayList.add(itemx);
                    xd++;

                }
            }
            for (ItemStack item : player.getInventory().getContents()){
                if (item == null){
                    continue;
                }
                String itemed = WordUtils.capitalizeFully(item.getType().name().replace("_", " "));
                if (itemed.contains("Helmet") || itemed.contains("Leggings") || itemed.contains("Chestplate") || itemed.contains("Boots") || itemed.contains("Sword") || itemed.contains("Bow") || itemed.contains("Rod") || itemed.contains("Axe")){
                    itemStackArrayList.add(item);
                    xd++;
                }
            }
            if (itemStackArrayList.isEmpty()){
                inventory = Bukkit.createInventory(null,0,NoItemsToEnchant);
                player.openInventory(inventory);
                return;
            }
            if (xd <= 8){
                inventory = Bukkit.createInventory(null,9,name);
            } else if (xd <= 17){
                inventory = Bukkit.createInventory(null,18,name);
            } else if (xd <= 26){
                inventory = Bukkit.createInventory(null,27,name);
            }else if (xd <= 35){
                inventory = Bukkit.createInventory(null,36,name);
            } else {
                inventory = Bukkit.createInventory(null,45,name);

            }
            for (ItemStack itemStack1 : itemStackArrayList){
                inventory.addItem(itemStack1);
            }
            player.openInventory(inventory);
            return;

        }
    }
}

package me.firas.skypvp.enchant;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;

public class ClickEnchant implements Listener {
    private boolean isNamed(Inventory inventory, String contains) {
    return inventory.getTitle().contains(contains);
    }

    @EventHandler
    public void ClickEnchanters(InventoryClickEvent e){
        Player player = (Player) e.getWhoClicked();
        Inventory inventory = e.getInventory();
        ItemStack itemStack = e.getCurrentItem();
        if (isNamed(inventory,"Sharpness")) {
            e.setCancelled(true);
            if (e.getCurrentItem() == null|| e.getCurrentItem().getType().equals(Material.AIR)) return;
            if (e.getRawSlot() >= 0 && e.getRawSlot() < inventory.getSize()) {
                for (ItemStack item : player.getInventory().getContents()){
                    if (item == null){
                        continue;
                    }
                    if (item.equals(itemStack)){
                        int level = 0;
                        if (e.getInventory().getTitle().contains("I")) level=1;
                        if (e.getInventory().getTitle().contains("II")) level=2;
                        if (e.getInventory().getTitle().contains("III")) level=3;
                        if (e.getInventory().getTitle().contains("IV")) level=4;
                        if (e.getInventory().getTitle().contains("Max")) level=5;
                        item.addEnchantment(Enchantment.DAMAGE_ALL,level);
                        player.closeInventory();
                        for (ItemStack itemd : player.getInventory().getContents()){
                            if (itemd == null || !(itemd.getType().equals(Material.ENCHANTED_BOOK))){
                                continue;
                            }
                            EnchantmentStorageMeta meta = (EnchantmentStorageMeta) itemd.getItemMeta();
                            if (meta.getStoredEnchants().toString().contains(Enchantment.DAMAGE_ALL.toString())) {
                                if (meta.getStoredEnchants().containsValue(level)){
                                    player.getInventory().removeItem(new ItemStack(itemd));
                                    return;
                                }
                            }
                        }
                        return;
                    }
                }
            }
        }
        if (isNamed(inventory,"Fire Aspect")) {
            e.setCancelled(true);
            if (e.getCurrentItem() == null|| e.getCurrentItem().getType().equals(Material.AIR)) return;
            if (e.getRawSlot() >= 0 && e.getRawSlot() < inventory.getSize()) {
                for (ItemStack item : player.getInventory().getContents()){
                    if (item == null){
                        continue;
                    }
                    if (item.equals(itemStack)){
                        int level = 0;
                        if (e.getInventory().getTitle().contains("I")) level=1;
                        if (e.getInventory().getTitle().contains("II")) level=2;
                        item.addEnchantment(Enchantment.FIRE_ASPECT,level);
                        player.closeInventory();
                        for (ItemStack itemd : player.getInventory().getContents()){
                            if (itemd == null || !(itemd.getType().equals(Material.ENCHANTED_BOOK))){
                                continue;
                            }
                            EnchantmentStorageMeta meta = (EnchantmentStorageMeta) itemd.getItemMeta();
                            if (meta.getStoredEnchants().toString().contains(Enchantment.FIRE_ASPECT.toString())) {
                                if (meta.getStoredEnchants().containsValue(level)){
                                    player.getInventory().removeItem(new ItemStack(itemd));
                                    return;
                                }
                            }
                        }
                        return;
                    }
                }
            }
        }
        if (isNamed(inventory,"Knockback")) {
            e.setCancelled(true);
            if (e.getCurrentItem() == null|| e.getCurrentItem().getType().equals(Material.AIR)) return;
            if (e.getRawSlot() >= 0 && e.getRawSlot() < inventory.getSize()) {
                for (ItemStack item : player.getInventory().getContents()){
                    if (item == null){
                        continue;
                    }
                    if (item.equals(itemStack)){
                        int level = 0;
                        if (e.getInventory().getTitle().contains("I")) level=1;
                        if (e.getInventory().getTitle().contains("II")) level=2;
                        item.addEnchantment(Enchantment.KNOCKBACK,level);
                        player.closeInventory();
                        for (ItemStack itemd : player.getInventory().getContents()){
                            if (itemd == null || !(itemd.getType().equals(Material.ENCHANTED_BOOK))){
                                continue;
                            }
                            EnchantmentStorageMeta meta = (EnchantmentStorageMeta) itemd.getItemMeta();
                            if (meta.getStoredEnchants().toString().contains(Enchantment.KNOCKBACK.toString())) {
                                if (meta.getStoredEnchants().containsValue(level)){
                                    player.getInventory().removeItem(new ItemStack(itemd));
                                    return;
                                }
                            }
                        }
                        return;
                    }
                }
            }
        }
        if (isNamed(inventory,"Power")) {
            e.setCancelled(true);
            if (e.getCurrentItem() == null|| e.getCurrentItem().getType().equals(Material.AIR)) return;

            if (e.getRawSlot() >= 0 && e.getRawSlot() < inventory.getSize()) {
                for (ItemStack item : player.getInventory().getContents()){
                    if (item == null){
                        continue;
                    }
                    if (item.equals(itemStack)){
                        int level = 0;
                        if (e.getInventory().getTitle().contains("I")) level=1;
                        if (e.getInventory().getTitle().contains("II")) level=2;
                        if (e.getInventory().getTitle().contains("III")) level=3;
                        if (e.getInventory().getTitle().contains("Max")) level=5;
                        if (e.getInventory().getTitle().contains("IV")) level=4;
                        item.addEnchantment(Enchantment.ARROW_DAMAGE,level);
                        player.closeInventory();
                        for (ItemStack itemd : player.getInventory().getContents()){
                            if (itemd == null || !(itemd.getType().equals(Material.ENCHANTED_BOOK))){
                                continue;
                            }
                            EnchantmentStorageMeta meta = (EnchantmentStorageMeta) itemd.getItemMeta();
                            if (meta.getStoredEnchants().toString().contains(Enchantment.ARROW_DAMAGE.toString())) {
                                if (meta.getStoredEnchants().containsValue(level)){
                                    player.getInventory().removeItem(new ItemStack(itemd));
                                    return;
                                }
                            }
                        }
                        return;
                    }
                }
            }
        }
        if (isNamed(inventory,"Infinity")) {
            e.setCancelled(true);
            if (e.getCurrentItem() == null|| e.getCurrentItem().getType().equals(Material.AIR)) return;

            if (e.getRawSlot() >= 0 && e.getRawSlot() < inventory.getSize()) {
                for (ItemStack item : player.getInventory().getContents()){
                    if (item == null){
                        continue;
                    }
                    if (item.equals(itemStack)){
                        int level = 0;
                        if (e.getInventory().getTitle().contains("I")) level=1;
                        item.addEnchantment(Enchantment.ARROW_INFINITE,level);
                        player.closeInventory();
                        for (ItemStack itemd : player.getInventory().getContents()){
                            if (itemd == null || !(itemd.getType().equals(Material.ENCHANTED_BOOK))){
                                continue;
                            }
                            EnchantmentStorageMeta meta = (EnchantmentStorageMeta) itemd.getItemMeta();
                            if (meta.getStoredEnchants().toString().contains(Enchantment.ARROW_INFINITE.toString())) {
                                if (meta.getStoredEnchants().containsValue(level)){
                                    player.getInventory().removeItem(new ItemStack(itemd));
                                    return;
                                }
                            }
                        }
                        return;
                    }
                }
            }
        }
        if (isNamed(inventory,"Punch")) {
            e.setCancelled(true);
            if (e.getCurrentItem() == null|| e.getCurrentItem().getType().equals(Material.AIR)) return;

            if (e.getRawSlot() >= 0 && e.getRawSlot() < inventory.getSize()) {
                for (ItemStack item : player.getInventory().getContents()){
                    if (item == null){
                        continue;
                    }
                    if (item.equals(itemStack)){
                        int level = 0;
                        if (e.getInventory().getTitle().contains("I")) level=1;
                        if (e.getInventory().getTitle().contains("II")) level=2;
                        item.addEnchantment(Enchantment.ARROW_KNOCKBACK,level);
                        player.closeInventory();
                        for (ItemStack itemd : player.getInventory().getContents()){
                            if (itemd == null || !(itemd.getType().equals(Material.ENCHANTED_BOOK))){
                                continue;
                            }
                            EnchantmentStorageMeta meta = (EnchantmentStorageMeta) itemd.getItemMeta();
                            if (meta.getStoredEnchants().toString().contains(Enchantment.ARROW_KNOCKBACK.toString())) {
                                if (meta.getStoredEnchants().containsValue(level)){
                                    player.getInventory().removeItem(new ItemStack(itemd));
                                    return;
                                }
                            }
                        }
                        return;
                    }
                }
            }
        }
        if (isNamed(inventory,"Flame")) {
            e.setCancelled(true);
            if (e.getCurrentItem() == null|| e.getCurrentItem().getType().equals(Material.AIR)) return;

            if (e.getRawSlot() >= 0 && e.getRawSlot() < inventory.getSize()) {
                for (ItemStack item : player.getInventory().getContents()){
                    if (item == null){
                        continue;
                    }
                    if (item.equals(itemStack)){
                        int level = 0;
                        if (e.getInventory().getTitle().contains("I")) level=1;
                        item.addEnchantment(Enchantment.ARROW_FIRE,level);
                        player.closeInventory();
                        for (ItemStack itemd : player.getInventory().getContents()){
                            if (itemd == null || !(itemd.getType().equals(Material.ENCHANTED_BOOK))){
                                continue;
                            }
                            EnchantmentStorageMeta meta = (EnchantmentStorageMeta) itemd.getItemMeta();
                            if (meta.getStoredEnchants().toString().contains(Enchantment.ARROW_FIRE.toString())) {
                                if (meta.getStoredEnchants().containsValue(level)){
                                    player.getInventory().removeItem(new ItemStack(itemd));
                                    return;
                                }
                            }
                        }
                        return;
                    }
                }
            }
        }
        if (isNamed(inventory,"Fire Protection")) {
            e.setCancelled(true);
            if (e.getCurrentItem() == null|| e.getCurrentItem().getType().equals(Material.AIR)) return;

            if (e.getRawSlot() >= 0 && e.getRawSlot() < inventory.getSize()) {
                for (ItemStack item : player.getInventory().getArmorContents()) {
                    if (item == null) {
                        continue;
                    }
                    ItemStack itemStack1 = new ItemStack(itemStack);
                    ItemMeta itemMeta = itemStack1.getItemMeta();
                    itemMeta.setLore(null);
                    itemStack1.setItemMeta(itemMeta);
                    itemStack = itemStack1;
                    if (item.equals(itemStack)){
                        int level = 0;
                        if (e.getInventory().getTitle().contains("I")) level=1;
                        if (e.getInventory().getTitle().contains("II")) level=2;
                        if (e.getInventory().getTitle().contains("III")) level=3;
                        if (e.getInventory().getTitle().contains("IV")) level=4;
                        item.addEnchantment(Enchantment.PROTECTION_FIRE,level);
                        player.closeInventory();
                        for (ItemStack itemd : player.getInventory().getContents()){
                            if (itemd == null || !(itemd.getType().equals(Material.ENCHANTED_BOOK))){
                                continue;
                            }
                            EnchantmentStorageMeta meta = (EnchantmentStorageMeta) itemd.getItemMeta();
                            if (meta.getStoredEnchants().toString().contains(Enchantment.PROTECTION_FIRE.toString())) {
                                if (meta.getStoredEnchants().containsValue(level)){
                                    player.getInventory().removeItem(new ItemStack(itemd));
                                    return;
                                }
                            }
                        }
                        return;
                    }
                }
                for (ItemStack item : player.getInventory().getContents()){
                    if (item == null){
                        continue;
                    }
                    if (item.equals(itemStack)){
                        int level = 0;
                        if (e.getInventory().getTitle().contains("I")) level=1;
                        if (e.getInventory().getTitle().contains("II")) level=2;
                        if (e.getInventory().getTitle().contains("III")) level=3;
                        if (e.getInventory().getTitle().contains("IV")) level=4;
                        item.addEnchantment(Enchantment.PROTECTION_FIRE,level);
                        player.closeInventory();
                        for (ItemStack itemd : player.getInventory().getContents()){
                            if (itemd == null || !(itemd.getType().equals(Material.ENCHANTED_BOOK))){
                                continue;
                            }
                            EnchantmentStorageMeta meta = (EnchantmentStorageMeta) itemd.getItemMeta();
                            if (meta.getStoredEnchants().toString().contains(Enchantment.PROTECTION_FIRE.toString())) {
                                if (meta.getStoredEnchants().containsValue(level)){
                                    player.getInventory().removeItem(new ItemStack(itemd));
                                    return;
                                }
                            }
                        }
                        return;
                    }
                }
            }
            return;
        }
        if (isNamed(inventory,"Protection")) {
            e.setCancelled(true);
            if (e.getCurrentItem() == null|| e.getCurrentItem().getType().equals(Material.AIR)) return;

            if (e.getRawSlot() >= 0 && e.getRawSlot() < inventory.getSize()) {
                for (ItemStack item : player.getInventory().getArmorContents()) {
                    if (item == null) {
                        continue;
                    }
                    ItemStack itemStack1 = new ItemStack(itemStack);
                    ItemMeta itemMeta = itemStack1.getItemMeta();
                    itemMeta.setLore(null);
                    itemStack1.setItemMeta(itemMeta);
                    itemStack = itemStack1;
                    if (item.equals(itemStack)){
                        int level = 0;
                        if (e.getInventory().getTitle().contains("I")) level=1;
                        if (e.getInventory().getTitle().contains("II")) level=2;
                        if (e.getInventory().getTitle().contains("III")) level=3;
                        if (e.getInventory().getTitle().contains("Max")) level=5;
                        if (e.getInventory().getTitle().contains("IV")) level=4;
                        item.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL,level);
                        player.closeInventory();
                        for (ItemStack itemd : player.getInventory().getContents()){
                            if (itemd == null || !(itemd.getType().equals(Material.ENCHANTED_BOOK))){
                                continue;
                            }
                            EnchantmentStorageMeta meta = (EnchantmentStorageMeta) itemd.getItemMeta();
                            if (meta.getStoredEnchants().toString().contains(Enchantment.PROTECTION_ENVIRONMENTAL.toString())) {
                                if (meta.getStoredEnchants().containsValue(level)){
                                    player.getInventory().removeItem(new ItemStack(itemd));
                                    return;
                                }
                            }
                        }
                        return;
                    }
                }
                for (ItemStack item : player.getInventory().getContents()){
                    if (item == null){
                        continue;
                    }
                    if (item.equals(itemStack)){
                        int level = 0;
                        if (e.getInventory().getTitle().contains("I")) level=1;
                        if (e.getInventory().getTitle().contains("II")) level=2;
                        if (e.getInventory().getTitle().contains("III")) level=3;
                        if (e.getInventory().getTitle().contains("Max")) level=5;
                        if (e.getInventory().getTitle().contains("IV")) level=4;
                        item.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL,level);
                        player.closeInventory();
                        for (ItemStack itemd : player.getInventory().getContents()){
                            if (itemd == null || !(itemd.getType().equals(Material.ENCHANTED_BOOK))){
                                continue;
                            }
                            EnchantmentStorageMeta meta = (EnchantmentStorageMeta) itemd.getItemMeta();
                            if (meta.getStoredEnchants().toString().contains(Enchantment.PROTECTION_ENVIRONMENTAL.toString())) {
                                if (meta.getStoredEnchants().containsValue(level)){
                                    player.getInventory().removeItem(new ItemStack(itemd));
                                    return;
                                }
                            }
                        }
                        return;
                    }
                }
            }
        }
        if (isNamed(inventory,"Unbreaking")) {
            e.setCancelled(true);
            if (e.getCurrentItem() == null|| e.getCurrentItem().getType().equals(Material.AIR)) return;

            if (e.getRawSlot() >= 0 && e.getRawSlot() < inventory.getSize()) {
                for (ItemStack item : player.getInventory().getArmorContents()) {
                    if (item == null) {
                        continue;
                    }
                    ItemStack itemStack1 = new ItemStack(itemStack);
                    ItemMeta itemMeta = itemStack1.getItemMeta();
                    itemMeta.setLore(null);
                    itemStack1.setItemMeta(itemMeta);
                    itemStack = itemStack1;
                    if (item.equals(itemStack)){
                        int level = 0;
                        if (e.getInventory().getTitle().contains("I")) level=1;
                        if (e.getInventory().getTitle().contains("II")) level=2;
                        if (e.getInventory().getTitle().contains("III")) level=3;
                        item.addEnchantment(Enchantment.DURABILITY,level);
                        player.closeInventory();
                        for (ItemStack itemd : player.getInventory().getContents()){
                            if (itemd == null || !(itemd.getType().equals(Material.ENCHANTED_BOOK))){
                                continue;
                            }
                            EnchantmentStorageMeta meta = (EnchantmentStorageMeta) itemd.getItemMeta();
                            if (meta.getStoredEnchants().toString().contains(Enchantment.DURABILITY.toString())) {
                                if (meta.getStoredEnchants().containsValue(level)){
                                    player.getInventory().removeItem(new ItemStack(itemd));
                                    return;
                                }
                            }
                        }
                        return;
                    }
                }
                for (ItemStack item : player.getInventory().getContents()){
                    if (item == null){
                        continue;
                    }
                    if (item.equals(itemStack)){
                        int level = 0;
                        if (e.getInventory().getTitle().contains("I")) level=1;
                        if (e.getInventory().getTitle().contains("II")) level=2;
                        if (e.getInventory().getTitle().contains("III")) level=3;
                        item.addEnchantment(Enchantment.DURABILITY,level);
                        player.closeInventory();
                        for (ItemStack itemd : player.getInventory().getContents()){
                            if (itemd == null || !(itemd.getType().equals(Material.ENCHANTED_BOOK))){
                                continue;
                            }
                            EnchantmentStorageMeta meta = (EnchantmentStorageMeta) itemd.getItemMeta();
                            if (meta.getStoredEnchants().toString().contains(Enchantment.DURABILITY.toString())) {
                                if (meta.getStoredEnchants().containsValue(level)){
                                    player.getInventory().removeItem(new ItemStack(itemd));
                                    return;
                                }
                            }
                        }
                        return;
                    }
                }
            }
        }
    }
}

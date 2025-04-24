package me.firas.skypvp.generators;

import me.firas.skypvp.Main;
import me.firas.skypvp.util.ItemBuilder;
import me.firas.skypvp.util.TextHelper;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class GeneratorMenu {
    Inventory inventory = Bukkit.createInventory(null,54,"Generators");
    public GeneratorMenu(Player player) {
        ArrayList<ItemStack> Items = new ArrayList<>();
        for (int i = 1; i <= Main.getInstance().getGeneratorsHandler().getValue("Size of Iron"); i++) {
            Location location = Main.getInstance().getGeneratorsHandler().getLocation("iron-"+i);
            if (location == null) continue;
            Items.add(new ItemBuilder(Material.IRON_INGOT,1,0).setDisplayname("&7Iron Generator &8(&e"+i+"&8)").setLore("", TextHelper.text("&8▪ &a&lLeft-Click &7Teleport"),TextHelper.text("&8▪ &6&lRight-Click &7Remove")).build());
        }
        for (int i = 1; i <= Main.getInstance().getGeneratorsHandler().getValue("Size of Gold"); i++) {
            Location location = Main.getInstance().getGeneratorsHandler().getLocation("gold-"+i);
            if (location == null) continue;
            Items.add(new ItemBuilder(Material.GOLD_INGOT,1,0).setDisplayname("&6Gold Generator &8(&e"+i+"&8)").setLore("", TextHelper.text("&8▪ &a&lLeft-Click &7Teleport"),TextHelper.text("&8▪ &6&lRight-Click &7Remove")).build());
        }
        for (int i = 1; i <= Main.getInstance().getGeneratorsHandler().getValue("Size of Emerald"); i++) {
            Location location = Main.getInstance().getGeneratorsHandler().getLocation("emerald-"+i);
            if (location == null) continue;
            Items.add(new ItemBuilder(Material.EMERALD,1,0).setDisplayname("&aEmerald Generator &8(&e"+i+"&8)").setLore("", TextHelper.text("&8▪ &a&lLeft-Click &7Teleport"),TextHelper.text("&8▪ &6&lRight-Click &7Remove")).build());
        }
        for (int i = 1; i <= 3; i++){
            String type;
            if (i == 1){
                type = "pyramid";
            } else if (i == 2){
                type = "aliens";
            } else {
                type = "arena";
            }
            Location location = Main.getInstance().getGeneratorsHandler().getLocation("koth-"+type);
            if (location == null) continue;
            Items.add(new ItemBuilder(Material.WOOL,1,2).setDisplayname("&dKoTH "+type+"").setLore("", TextHelper.text("&8▪ &a&lLeft-Click &7Teleport"),TextHelper.text("&8▪ &6&lRight-Click &7Remove")).build());
        }
        for (int i = 1; i <= Main.getInstance().getGeneratorsHandler().getValue("Size of EnderChest"); i++) {
            Location location = Main.getInstance().getGeneratorsHandler().getLocation("enderchest-"+i);
            if (location == null) continue;
            Items.add(new ItemBuilder(Material.ENDER_CHEST,1,2).setDisplayname("&5Ender Chest &8(&e"+i+"&8)").setLore("", TextHelper.text("&8▪ &a&lLeft-Click &7Teleport"),TextHelper.text("&8▪ &6&lRight-Click &7Remove")).build());
        }
        for (int i = 1; i <= Main.getInstance().getGeneratorsHandler().getValue("Size of Menu"); i++) {
            Location location = Main.getInstance().getGeneratorsHandler().getLocation("menu-"+i);
            if (location == null) continue;
            Items.add(new ItemBuilder(Material.CHEST,1,0).setDisplayname("&dMenu &8(&e"+i+"&8)").setLore("", TextHelper.text("&8▪ &a&lLeft-Click &7Teleport"),TextHelper.text("&8▪ &6&lRight-Click &7Remove")).build());
        }
        for (int i = 1; i <= Main.getInstance().getGeneratorsHandler().getValue("Size of XP"); i++) {
            Location location = Main.getInstance().getGeneratorsHandler().getLocation("xp-"+i);
            if (location == null) continue;
            Items.add(new ItemBuilder(Material.EXP_BOTTLE,1,0).setDisplayname("&eXP &8(&e"+i+"&8)").setLore("", TextHelper.text("&8▪ &a&lLeft-Click &7Teleport"),TextHelper.text("&8▪ &6&lRight-Click &7Remove")).build());
        }
        for (ItemStack itemStack : Items){
            inventory.addItem(itemStack);
        }
        player.openInventory(inventory);

    }
}

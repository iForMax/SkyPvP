package me.firas.skypvp.settings;

import me.firas.skypvp.Main;
import me.firas.skypvp.util.TextHelper;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class Settings {
    public Settings(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 36, "Settings");
        inventory.setItem(10, time(player));
        inventory.setItem(14, Scramble(player));
        inventory.setItem(12, Chat(player));
        inventory.setItem(16,SafeDropKit(player));
        inventory.setItem(31, Main.getInstance().getItemStackAPI().BackToMenu());
        player.openInventory(inventory);
    }

    public ItemStack time(Player player) {
        ItemStack itemStack = new ItemStack(Material.WATCH);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(TextHelper.text("&eClock &a&l" + Main.getInstance().get(player).getTime()));
        ArrayList<String> lore = new ArrayList<>();
        lore.add(TextHelper.text("&7Change your clock"));
        lore.add("");
        for (String time : (Main.getInstance()).times) {
            if (Main.getInstance().get(player).getTime().contains(time)) {
                lore.add(TextHelper.text(" &a◆ " + time));
                continue;
            }
            lore.add(TextHelper.text(" &8◆ &7" + time));
        }
        lore.add("");
        lore.add(TextHelper.text("&8▪ &a&lLeft-Click &7Next &a⇣"));
        lore.add(TextHelper.text("&8▪ &6&lRight-Click &7Previous &a⇡"));
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public ItemStack Chat(Player player) {
        ItemStack itemStack = new ItemStack(Material.PAPER);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(TextHelper.text("&eChat"));
        ArrayList<String> lore = new ArrayList<>();
        boolean is = Boolean.parseBoolean(Main.getInstance().get(player).getChat());
        if (is) {
            lore.add(TextHelper.text("&7You will &asee&7 any message"));
            lore.add(TextHelper.text("&7from the public chat"));
            lore.add("");
            lore.add(TextHelper.text(" &8◆ &7OFF"));
            lore.add(TextHelper.text(" &a◆ ON"));
            lore.add("");
            lore.add(TextHelper.text("&8▪ &a&lLeft-Click &cDeactivate"));
        } else {
            lore.add(TextHelper.text("&7You will &cnot see &7any message"));
            lore.add(TextHelper.text("&7from the public chat"));
            lore.add("");
            lore.add(TextHelper.text(" &a◆ OFF"));
            lore.add(TextHelper.text(" &8◆ &7ON"));
            lore.add("");
            lore.add(TextHelper.text("&8▪ &a&lLeft-Click &aActivate"));
        }
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public ItemStack SafeDropKit(Player player) {
        ItemStack itemStack = new ItemStack(Material.BOW);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(TextHelper.text("&eSafe Drop Kit"));
        ArrayList<String> lore = new ArrayList<>();
        if (Main.getInstance().get(player).getSettings().contains("SafeDropKit")) {
            lore.add(TextHelper.text("&7You &ccan &7drop your"));
            lore.add(TextHelper.text("&7kit"));
            lore.add("");
            lore.add(TextHelper.text(" &a◆ OFF"));
            lore.add(TextHelper.text(" &8◆ &7ON"));
            lore.add("");
            lore.add(TextHelper.text("&8▪ &a&lLeft-Click &aActivate"));

        } else {
            lore.add(TextHelper.text("&7You &acan not &7drop your"));
            lore.add(TextHelper.text("&7kit"));
            lore.add("");
            lore.add(TextHelper.text(" &8◆ &7OFF"));
            lore.add(TextHelper.text(" &a◆ ON"));
            lore.add("");
            lore.add(TextHelper.text("&8▪ &a&lLeft-Click &cDeactivate"));



        }
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
    public ItemStack Scramble(Player player) {
        ItemStack itemStack = new ItemStack(Material.REDSTONE_TORCH_ON);
        ItemMeta itemMeta = itemStack.getItemMeta();
        boolean scramble = Boolean.parseBoolean(Main.getInstance().get(player).getScramble());
        itemMeta.setDisplayName(TextHelper.text("&eScramble"));
        ArrayList<String> lore = new ArrayList<>();
        if (player.hasPermission("skypvp.scramble")) {
            if (scramble) {
                lore.add(TextHelper.text("&7You are currently &chide"));
                lore.add(TextHelper.text("&7your rank"));
                lore.add(TextHelper.text(""));

                lore.add(TextHelper.text(" &8◆ &7OFF"));
                lore.add(TextHelper.text(" &a◆ ON"));
                lore.add("");
                lore.add(TextHelper.text("&8▪ &a&lLeft-Click &cDeactivate"));
            } else {
                lore.add(TextHelper.text("&7You are currently &ashown"));
                lore.add(TextHelper.text("&7your rank"));
                lore.add(TextHelper.text(""));
                lore.add(TextHelper.text(" &a◆ OFF"));
                lore.add(TextHelper.text(" &8◆ &7ON"));
                lore.add("");
                lore.add(TextHelper.text("&8▪ &a&lLeft-Click &aActivate"));
            }
        } else {
            lore.add(TextHelper.text("&7You are currently &ashown"));
            lore.add(TextHelper.text("&7your rank"));
            lore.add(TextHelper.text(" &a◆ OFF"));
            lore.add(TextHelper.text(" &8◆ &7ON"));
            lore.add("");
            lore.add(TextHelper.text("&8▪ &c&lPREMIUM FEATURE"));
        }
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}

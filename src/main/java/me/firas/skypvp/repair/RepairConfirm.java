package me.firas.skypvp.repair;

import me.firas.skypvp.Main;
import me.firas.skypvp.util.ItemBuilder;
import me.firas.skypvp.util.TextHelper;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class RepairConfirm {
    Inventory inventory = Bukkit.createInventory(null,27,"Are you sure? (Fix all)");

    public RepairConfirm(Player player, int price){
        inventory.setItem(11,new ItemBuilder(Material.WOOL,1,5).setDisplayname("&aYes").setLore("", TextHelper.text(TextHelper.text("&6Cost&8: &a"+price+" &5XP")),"",TextHelper.text("&8▪ &a&lLeft-Click &7Confirm")).build());
        inventory.setItem(15,new ItemBuilder(Material.WOOL,1,14).setDisplayname("&cNO").setLore("",TextHelper.text("&7Click here to cancel repair process"),TextHelper.text("&7and keep your coins in your wallet"),"",TextHelper.text("&8▪ &a&lLeft-Click &7Close")).build());
        player.openInventory(inventory);
    }
}

package me.firas.skypvp.menu.menus;
import me.firas.skypvp.Main;
import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
public class Enchantments {
    Inventory inventory = Bukkit.createInventory(null, 54, "Shop: Enchantment");

    public Enchantments(Player player) {

        this.inventory.setItem(0, Main.getInstance().getItemStackAPI().Enchantment(Enchantment.DAMAGE_ALL, 1, 10, "Iron"));
        this.inventory.setItem(1, Main.getInstance().getItemStackAPI().Enchantment(Enchantment.DAMAGE_ALL, 2, 15, "Iron"));
        this.inventory.setItem(2, Main.getInstance().getItemStackAPI().Enchantment(Enchantment.DAMAGE_ALL, 3, 15, "Gold"));
        this.inventory.setItem(3, Main.getInstance().getItemStackAPI().Enchantment(Enchantment.DAMAGE_ALL, 4, 10, "Emerald"));
        this.inventory.setItem(9, Main.getInstance().getItemStackAPI().EnchantmentCant(Enchantment.PROTECTION_ENVIRONMENTAL,1));
        this.inventory.setItem(10, Main.getInstance().getItemStackAPI().Enchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2, 15, "Iron"));
        this.inventory.setItem(11, Main.getInstance().getItemStackAPI().Enchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3, 15, "Gold"));
        this.inventory.setItem(12, Main.getInstance().getItemStackAPI().Enchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4, 8, "Emerald"));
        this.inventory.setItem(18, Main.getInstance().getItemStackAPI().Enchantment(Enchantment.ARROW_DAMAGE, 1, 10, "Iron"));
        this.inventory.setItem(19, Main.getInstance().getItemStackAPI().Enchantment(Enchantment.ARROW_DAMAGE, 2, 15, "Iron"));
        this.inventory.setItem(20, Main.getInstance().getItemStackAPI().Enchantment(Enchantment.ARROW_DAMAGE, 3, 15, "Gold"));
        this.inventory.setItem(21, Main.getInstance().getItemStackAPI().Enchantment(Enchantment.ARROW_DAMAGE, 4, 10, "Emerald"));
        this.inventory.setItem(27, Main.getInstance().getItemStackAPI().EnchantmentCant(Enchantment.PROTECTION_FIRE, 1));
        this.inventory.setItem(28, Main.getInstance().getItemStackAPI().Enchantment(Enchantment.PROTECTION_FIRE, 2, 15, "Iron"));
        this.inventory.setItem(29, Main.getInstance().getItemStackAPI().Enchantment(Enchantment.PROTECTION_FIRE, 3, 15, "Gold"));
        this.inventory.setItem(30, Main.getInstance().getItemStackAPI().Enchantment(Enchantment.PROTECTION_FIRE, 4, 10, "Emerald"));
        this.inventory.setItem(36, Main.getInstance().getItemStackAPI().Enchantment(Enchantment.DURABILITY, 1, 20, "Iron"));
        this.inventory.setItem(37, Main.getInstance().getItemStackAPI().Enchantment(Enchantment.DURABILITY, 2, 20, "Gold"));
        this.inventory.setItem(38, Main.getInstance().getItemStackAPI().Enchantment(Enchantment.DURABILITY, 3, 5, "Emerald"));
        this.inventory.setItem(53, Main.getInstance().getItemStackAPI().Enchantment(Enchantment.ARROW_INFINITE, 1, 10, "Emerald"));
        this.inventory.setItem(44, Main.getInstance().getItemStackAPI().Enchantment(Enchantment.ARROW_FIRE, 1, 10, "Emerald"));
        this.inventory.setItem(45, Main.getInstance().getItemStackAPI().Enchantment(Enchantment.KNOCKBACK, 1, 15, "Gold"));
        this.inventory.setItem(46, Main.getInstance().getItemStackAPI().Enchantment(Enchantment.KNOCKBACK, 2, 10, "Emerald"));
        this.inventory.setItem(26, Main.getInstance().getItemStackAPI().Enchantment(Enchantment.ARROW_KNOCKBACK, 1, 32, "Gold"));
        this.inventory.setItem(25, Main.getInstance().getItemStackAPI().Enchantment(Enchantment.ARROW_KNOCKBACK, 2, 15, "Emerald"));
        this.inventory.setItem(8, Main.getInstance().getItemStackAPI().Enchantment(Enchantment.FIRE_ASPECT, 1,32 , "Gold"));
        this.inventory.setItem(7, Main.getInstance().getItemStackAPI().Enchantment(Enchantment.FIRE_ASPECT, 2, 15, "Emerald"));




        this.inventory.setItem(49, Main.getInstance().getItemStackAPI().BackToMenu());
        player.openInventory(this.inventory);
    }
}



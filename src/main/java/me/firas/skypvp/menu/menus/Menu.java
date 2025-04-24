package me.firas.skypvp.menu.menus;

import me.firas.skypvp.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.io.IOException;

public class Menu {
    Inventory inventory = Bukkit.createInventory(null, 45, "Menu");


    public Menu(Player player) throws IOException {
        try {
            this.inventory.setItem(11, Main.getInstance().getItemStackAPI().KitsMenu(player));
            this.inventory.setItem(13, Main.getInstance().getItemStackAPI().KitViewer(player));
            this.inventory.setItem(15, Main.getInstance().getItemStackAPI().TrailsMenu(player));
            this.inventory.setItem(28, Main.getInstance().getItemStackAPI().PackagesShop());
            this.inventory.setItem(30, Main.getInstance().getItemStackAPI().EnderChestMenu(player));
            this.inventory.setItem(32, Main.getInstance().getItemStackAPI().SettingsMenu());
            this.inventory.setItem(34, Main.getInstance().getItemStackAPI().EnchantmentMenu());
            player.openInventory(this.inventory);
        } catch (Throwable $ex) {
            throw $ex;
        }
    }
}

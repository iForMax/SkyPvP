package me.firas.skypvp.quests;

import me.firas.skypvp.quests.menus.Kills;
import me.firas.skypvp.quests.menus.MainMenu;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryClickEvent implements Listener {

    @EventHandler
    public void AchievementClickEvent(org.bukkit.event.inventory.InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        if (player.getOpenInventory().getTopInventory().getTitle().contains("Achievement: Kills")) {
            e.setCancelled(true);
            if (e.getRawSlot() == 22){
                new MainMenu(player);
                return;
            }
        }
        if (player.getOpenInventory().getTopInventory().getTitle().equalsIgnoreCase("Achievements")) {
            if (e.getRawSlot() == 10){
                new Kills(player);
                return;
            }
        }
    }
}

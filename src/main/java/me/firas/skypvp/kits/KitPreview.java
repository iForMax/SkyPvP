package me.firas.skypvp.kits;

import me.firas.skypvp.Main;
import me.firas.skypvp.enums.KitType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class KitPreview {
    Inventory inventory = Bukkit.createInventory(null,45,"Preview Kit");

    public KitPreview(Player player, KitType kitType){
        int xd = 19;
        for (ItemStack item : kitType.getArmour()){
            inventory.setItem(xd,item);
            xd++;
        }
        for (ItemStack item : kitType.getTools()){
            inventory.setItem(xd,item);
            xd++;
        }
        inventory.setItem(40, Main.getInstance().getItemStackAPI().BackToMenu());
        player.openInventory(inventory);
    }
}

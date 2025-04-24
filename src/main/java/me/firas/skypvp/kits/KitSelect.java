package me.firas.skypvp.kits;

import com.avaje.ebeaninternal.server.persist.BindValues;
import me.firas.skypvp.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;


public class KitSelect {
    Inventory inventory = Bukkit.createInventory(null,36,"Kits");

    public KitSelect(Player player){
        inventory.setItem(11, Main.getInstance().getKit().KitSelected(player,null,ChatColor.BLUE+"Member",null, Material.CHAINMAIL_CHESTPLATE, null));
        inventory.setItem(12, Main.getInstance().getKit().KitSelected(player,"skypvp.kit.iron", ChatColor.GRAY+ "Iron",2500, Material.IRON_CHESTPLATE,null));
        inventory.setItem(13, Main.getInstance().getKit().KitSelected(player,"skypvp.kit.diamond",ChatColor.AQUA+"Diamond",null, Material.DIAMOND_CHESTPLATE,null));
        inventory.setItem(14, Main.getInstance().getKit().KitSelected(player,"skypvp.kit.mars",ChatColor.GREEN+"Mars",null, Material.LEATHER_CHESTPLATE,Color.GREEN));
        inventory.setItem(15, Main.getInstance().getKit().KitSelected(player,"skypvp.kit.super",ChatColor.DARK_PURPLE+"Super",null, Material.LEATHER_CHESTPLATE,Color.PURPLE));
        inventory.setItem(31,Main.getInstance().getItemStackAPI().BackToMenu());
        player.openInventory(inventory);
    }
}

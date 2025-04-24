package me.firas.skypvp.commands;

import me.firas.skypvp.util.ItemBuilder;
import me.firas.skypvp.util.TextHelper;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class Trash implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player player = (Player) sender;
        if (player.getOpenInventory().getTitle().contains("Trash")){
            return true;
        }
        Inventory inventory = Bukkit.createInventory(null,45,"Trash");
        int sized = 36;
        while (sized < 45){
            inventory.setItem(sized,new ItemBuilder(Material.STAINED_GLASS_PANE,1,15).setDisplayname("&r").build());
            sized++;
        }
        inventory.setItem(36,new ItemBuilder(Material.CHEST,1,0).setDisplayname("&a&lReturn back").setLore("",TextHelper.text("&7Put all items from the trash"), ChatColor.GRAY+"to your inventory","",TextHelper.text("&8▪ &a&lLeft-Click &7Back")).build());
        inventory.setItem(39,new ItemBuilder(Material.BUCKET,1,0).setDisplayname("&a&lPut Your inventory").setLore("",TextHelper.text("&7Put all your inventory"), ChatColor.GRAY+"to the trash","",TextHelper.text("&8▪ &a&lLeft-Click &7Put")).build());
        inventory.setItem(41,new ItemBuilder(Material.ARROW,1,0).setDisplayname("&a&lPut selected items").setLore("",TextHelper.text("&7Put all items you"), ChatColor.GRAY+"selected in your cursor","",TextHelper.text("&8▪ &a&lLeft-Click &7Put")).build());
        inventory.setItem(40,new ItemBuilder(Material.BARRIER,1,0).setDisplayname("&c&lClose").setLore("",ChatColor.GRAY+"All items you put in the trash",ChatColor.GRAY+"it will permanently removed","",TextHelper.text("&8▪ &a&lLeft-Click &7Close")).build());
        player.openInventory(inventory);
        return false;
    }
}

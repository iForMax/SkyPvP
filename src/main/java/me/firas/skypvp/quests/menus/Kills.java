package me.firas.skypvp.quests.menus;

import me.firas.skypvp.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class Kills {
    Inventory inventory = Bukkit.createInventory(null,27,"Achievement: Kills");

    public Kills(Player player){
        inventory.addItem(Main.getInstance().getQuest().AddAchievementItem(player,0,10, Material.STAINED_CLAY,"Kills","I","&cNothing ):"));
        inventory.addItem(Main.getInstance().getQuest().AddAchievementItem(player,10,50, Material.STAINED_CLAY,"Kills","II","&520 XP"));
        inventory.addItem(Main.getInstance().getQuest().AddAchievementItem(player,50,100, Material.STAINED_CLAY,"Kills","III","&e10 &7Iron ingot","&5200 XP"));
        inventory.addItem(Main.getInstance().getQuest().AddAchievementItem(player,100,500, Material.STAINED_CLAY,"Kills","IV","&e10 &7Iron ingot","&5200 XP"));
        inventory.addItem(Main.getInstance().getQuest().AddAchievementItem(player,500,1000, Material.STAINED_CLAY,"Kills","V","&e10 &7Iron ingot","&5200 XP"));
        inventory.addItem(Main.getInstance().getQuest().AddAchievementItem(player,1000,2000, Material.STAINED_CLAY,"Kills","VI","&e10 &7Iron ingot","&5200 XP"));
        inventory.addItem(Main.getInstance().getQuest().AddAchievementItem(player,2000,3000, Material.STAINED_CLAY,"Kills","VII","&e10 &7Iron ingot","&5200 XP"));
        inventory.addItem(Main.getInstance().getQuest().AddAchievementItem(player,3000,4000, Material.STAINED_CLAY,"Kills","IX","&e10 &7Iron ingot","&5200 XP"));
        inventory.addItem(Main.getInstance().getQuest().AddAchievementItem(player,4000,10000, Material.STAINED_CLAY,"Kills","X","&e10 &7Iron ingot","&5200 XP"));
        inventory.addItem(new ItemStack(Material.AIR));
        inventory.setItem(22,Main.getInstance().getQuest().returnPage());
        player.openInventory(inventory);
    }
}

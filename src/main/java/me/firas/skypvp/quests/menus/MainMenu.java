package me.firas.skypvp.quests.menus;

import me.firas.skypvp.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class MainMenu {
    Inventory inventory = Bukkit.createInventory(null,45,"Achievements");

    public MainMenu(Player player){
        inventory.setItem(10, Main.getInstance().getQuest().AddAchievementFullItem(player,10000,"Kills"));
        inventory.setItem(13, Main.getInstance().getQuest().AddAchievementFullItem(player,10000,"Time"));
        inventory.setItem(16, Main.getInstance().getQuest().AddAchievementFullItem(player,10000,"Pickup"));
        inventory.setItem(29, Main.getInstance().getQuest().AddAchievementFullItem(player,10000,"Death"));
        inventory.setItem(33, Main.getInstance().getQuest().AddAchievementFullItem(player,10000,"Bow"));


        player.openInventory(inventory);
    }
}

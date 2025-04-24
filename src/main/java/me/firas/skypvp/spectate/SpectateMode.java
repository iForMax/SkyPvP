package me.firas.skypvp.spectate;

import dev.mqzen.boards.BoardManager;


import me.firas.skypvp.Main;
import me.firas.skypvp.scoreboard.Board;
import me.firas.skypvp.util.ItemBuilder;
import me.firas.skypvp.util.TextHelper;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class SpectateMode {
    public static HashMap<Player, ItemStack[]> InventoryOfPlayer = new HashMap<>();
    public static HashMap<Player, ItemStack[]> ArmorOfPlayer = new HashMap<>();
    public static ItemStack ps(Player player){
        ItemStack itemStack = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        ItemMeta itemMeta = itemStack.getItemMeta();
        ArrayList<String> lores = new ArrayList<>();
        if (player.getName().equals("Webmasters")){
            lores.add(TextHelper.text("&7Hello There! &c❤"));
        }
        lores.add(TextHelper.text(""));
        lores.add(TextHelper.text("&b▶ Click to teleport"));
        itemMeta.setDisplayName(TextHelper.text("&9"+player.getDisplayName()));
        itemMeta.setLore(lores);
        itemStack.setItemMeta(itemMeta);
        SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();
        skullMeta.setOwner(player.getName());
        itemStack.setItemMeta(skullMeta);
        return itemStack;
    }



    public static void JoinSpectate(Player player){
        if (player.getGameMode().equals(GameMode.SPECTATOR)){
            return;
        }
        if (!player.isOnGround()){
            player.sendMessage(TextHelper.format("&cYou need to be on the ground to enter spectate mode"));
            return;
        }
        player.closeInventory();
        InventoryOfPlayer.put(player,player.getInventory().getContents());
        ArmorOfPlayer.put(player,player.getInventory().getArmorContents());
        player.getInventory().setArmorContents(null);
        player.getInventory().clear();
        player.getInventory().addItem(new ItemBuilder(Material.COMPASS,1,0).setDisplayname("&a&lTracker").build());
        player.getInventory().setItem(8,new ItemBuilder(Material.SLIME_BALL,1,0).setDisplayname("&c&lLeave").build());
        Main.getInstance().spectate.add(player);
        BoardManager.getInstance().removeBoard(player);
        BoardManager.getInstance().setupNewBoard(player, new me.firas.skypvp.scoreboard.Spectate());
        player.sendMessage(TextHelper.format("&aYou are now a spectator"));
        player.sendMessage(TextHelper.format("&7Use &a/tpa [username] &7to teleport"));
        player.setGameMode(GameMode.ADVENTURE);
        player.setAllowFlight(true);
        player.spigot().setCollidesWithEntities(false);
        for (Player players : Bukkit.getOnlinePlayers()){
            players.hidePlayer(player);
        }

    }
    public static void LeaveSpectate(Player player){
        player.closeInventory();
        player.getInventory().clear();
        player.getInventory().setContents(InventoryOfPlayer.get(player));
        player.getInventory().setArmorContents(ArmorOfPlayer.get(player));
        ArmorOfPlayer.remove(player);
        InventoryOfPlayer.remove(player);
        Main.getInstance().spectate.remove(player);
        BoardManager.getInstance().removeBoard(player);
        BoardManager.getInstance().setupNewBoard(player, new Board());
        player.sendMessage(TextHelper.format("&cYou are no longer spectator"));
        Location mapLocation = Main.getInstance().getLocationHandler().getLocation("spawn");
        player.teleport(mapLocation);
        player.setGameMode(GameMode.SURVIVAL);
        player.spigot().setCollidesWithEntities(true);
        player.setAllowFlight(false);
        player.setFlying(false);
        player.updateInventory();
        for (Player players : Bukkit.getOnlinePlayers()){
            players.showPlayer(player);
        }
    }
}

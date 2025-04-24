package me.firas.skypvp.generators;

import me.firas.skypvp.Main;
import me.firas.skypvp.util.TextHelper;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class Pickup implements Listener {
    @EventHandler
    public void PickupCoins(PlayerPickupItemEvent event){
        if (event.getItem() == null) return;
        if (event.getItem().getItemStack() == null) return;
        if (event.getItem().getItemStack().getType() == null) return;
        if (event.getItem().getItemStack().getItemMeta() == null) return;
        if (event.getItem().getItemStack().getItemMeta().getDisplayName() == null) return;
        Player player = event.getPlayer();
        if (event.getItem().getItemStack().getItemMeta().getDisplayName().contains("Coins")){
            event.getItem().remove();
            event.setCancelled(true);
            player.playSound(player.getLocation(), Sound.NOTE_PLING,1,2);
            player.sendMessage(TextHelper.format("&fYou got &a"+event.getItem().getItemStack().getItemMeta().getDisplayName()));
            Main.getInstance().get(player).addStats("coins",Integer.valueOf(ChatColor.stripColor(event.getItem().getItemStack().getItemMeta().getDisplayName())));
        }
    }
}

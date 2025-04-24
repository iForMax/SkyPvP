package me.firas.skypvp.regions.listener;

import me.firas.skypvp.Main;
import me.firas.skypvp.util.TextHelper;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Wand implements Listener {
    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (event.getItem() == null || event.getItem().getType().equals(Material.AIR)) return;
        if (!event.getItem().getType().equals(Material.STICK)) return;
        if (!event.getItem().getItemMeta().getDisplayName().contains(TextHelper.format("&eWand"))) return;
        Player player = event.getPlayer();
        event.setCancelled(true);
        Pattern pattern = Pattern.compile("\\[(.*?)\\]");
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) { //POS 1
            Matcher matcher = pattern.matcher(event.getItem().getItemMeta().getDisplayName());
            if (!matcher.find()) return;
            String name = matcher.group(1);
            Main.getInstance().getRegions().setPos1(player, name);
            player.sendMessage(TextHelper.format("&7Pos-1 set for the region&c " + name));
        }
        if (event.getAction() == Action.LEFT_CLICK_BLOCK) { //POS 2
            Matcher matcher = pattern.matcher(event.getItem().getItemMeta().getDisplayName());
            if (!matcher.find()) return;
            String name = matcher.group(1);
            Main.getInstance().getRegions().setPos2(player, name);
            player.sendMessage(TextHelper.format("&7Pos-2 set for the region&c " + name));
            {
            }
        }

    }
}

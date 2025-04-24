package me.firas.skypvp.trails;

import me.firas.skypvp.Main;
import me.firas.skypvp.menu.menus.Menu;
import me.firas.skypvp.util.TextHelper;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.io.IOException;

public class InventoryClick implements Listener {
    private boolean isNamed(Inventory inventory, String contains) {
        return inventory.getTitle().equals(contains);
    }

    @EventHandler
    public void inveo(InventoryClickEvent e) throws IOException {
        Inventory inventory = e.getInventory();
        Player player = (Player) e.getWhoClicked();
        if (isNamed(inventory,"Trails")){
            e.setCancelled(true);
            if (e.getRawSlot() == 31){
                if (e.getClick().isRightClick()) {
                    player.closeInventory();
                    return;
                }
                new Menu(player);
                return;

            }
            if (e.getCurrentItem() != null){
                if (e.getCurrentItem().getItemMeta() == null) return;
                for (String lore : e.getCurrentItem().getItemMeta().getLore()){
                    String lord = ChatColor.stripColor(lore);
                    if (lord.contains("Cost")){
                        String Finals = ChatColor.stripColor(lore).replaceAll("[a-zA-Z[_][:][ ][✪]]", "");
                        int coins = Integer.parseInt(Finals);
                        if (Main.getInstance().get(player).getCoins() < coins){
                            player.closeInventory();
                            player.sendMessage(TextHelper.format("&7You need &8(&f"+(coins-Main.getInstance().get(player).getCoins())+"&8) &7more &eCoins"+" &7to purchase"));
                            player.playSound(player.getLocation(), Sound.VILLAGER_NO,1,1);
                            return;
                        }
                        String name = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).toLowerCase();
                        Main.getInstance().get(player).addSetting(name);
                        Main.getInstance().get(player).addStats("coins",-coins);
                        Main.getInstance().get(player).setTrail(name);
                        new Trails(player);
                        player.playSound(player.getLocation(),Sound.LEVEL_UP,1,2);
                        break;
                    } else if (lord.contains("Select")){
                        String name = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).toLowerCase();
                        Main.getInstance().get(player).setTrail(name);
                        new Trails(player);
                        player.playSound(player.getLocation(),Sound.NOTE_PLING,1,2);
                        break;
                    } else if (lord.contains("Remove")){
                        Main.getInstance().get(player).setTrail("None");
                        new Trails(player);
                        player.playSound(player.getLocation(),Sound.NOTE_PLING,1,1);
                    }
                }
            }
        }
    }
}

package me.firas.skypvp.repair;

import me.firas.skypvp.Main;
import me.firas.skypvp.util.TextHelper;
import net.md_5.bungee.api.chat.*;
import org.apache.commons.lang.WordUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class AnvilListener implements org.bukkit.event.Listener {

    @EventHandler
    public void RightClickRepairAnvil(PlayerInteractEvent event) {
        if (event.isCancelled()) return;
        Action action = event.getAction();
        Player player = event.getPlayer();
        Block block = event.getClickedBlock();
        if (action.equals(Action.RIGHT_CLICK_BLOCK)) {
            if (block.getType().equals(Material.ANVIL)) {
                event.setCancelled(true);
                new RepairMenu(player);
                return;
            }
        }
    }

    private boolean isNamed(Inventory inventory, String contains) {
        return inventory.getTitle().equals(contains);
    }




    @EventHandler
    public void ClickEventRepairMenu(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        Inventory inventory = e.getInventory();
        ItemStack item = e.getCurrentItem();
        if (isNamed(inventory,"Are you sure? (Fix all)")){
            e.setCancelled(true);
            if (e.getRawSlot() == 15){
                new RepairMenu(player);
                return;
            }
            if (e.getRawSlot() == 11) {
                int price = 0;
                for (String lore : item.getItemMeta().getLore()) {
                    if (lore.contains("Cost")) {
                        String Finals = ChatColor.stripColor(lore).replaceAll("[a-zA-Z[_][:][ ][✪]]", "");
                        try {
                            price = Integer.parseInt(Finals);

                        } catch (NumberFormatException es) {
                            player.sendMessage(TextHelper.format("Error!"));
                            player.closeInventory();
                            return;
                        }
                        if (player.getLevel() < price) {
                            player.closeInventory();
                            player.sendMessage(TextHelper.format("&7You need &8(&f"+(price- player.getLevel())+"&8) &7more &5XP"+" &7to repair"));
                            player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1, 1);
                            return;
                        }
                    }
                }
                ArrayList<ItemStack> itemStacks = new ArrayList<>();
                for (ItemStack itemStack : player.getInventory().getArmorContents()){
                    if (itemStack == null)
                        continue;
                    if (itemStack.getType().equals(Material.AIR))
                        continue;
                    if (itemStack.getDurability() != 0){
                        itemStack.setDurability((short) 0);
                        itemStacks.add(itemStack);

                    }
                }

                for (ItemStack itemStack : player.getInventory()){
                    if (itemStack == null)
                        continue;
                    if (itemStack.getType().equals(Material.AIR))
                        continue;
                    String itemed = WordUtils.capitalizeFully(itemStack.getType().name().replace("_", " "));
                    if (!(itemed.contains("Sword")|| itemed.contains("Axe")||itemed.contains("Helmet")||itemed.contains("Chestplate")||itemed.contains("Leggings")||itemed.contains("Boots")||itemed.contains("Bow")||itemed.contains("Rod"))){
                        continue;
                    }
                    if (itemStack.getDurability() != 0){
                        itemStack.setDurability((short) 0);
                        itemStacks.add(itemStack);
                    }
                }
                player.closeInventory();
                String chat = TextHelper.format("&aSuccessfully fix all items (&c&l-"+price+"&a)");
                player.sendMessage(chat);
                player.playSound(player.getLocation(),Sound.ANVIL_USE,1,1);
                player.setLevel(player.getLevel()-price);
                return;
            }
        }
        if (isNamed(inventory, "Repair Menu")) {
            e.setCancelled(true);
            if (e.getRawSlot() >= 0 && e.getRawSlot() < inventory.getSize()) {
                if (e.getCurrentItem() == null || e.getCurrentItem().getType().equals(Material.AIR)) return;
                if (e.getCurrentItem().getType().equals(Material.STAINED_GLASS_PANE)) return;
                if (e.getCurrentItem().getType().equals(Material.ANVIL)) {
                    int price = 0;
                    for (String lore : item.getItemMeta().getLore()) {
                        if (lore.contains("Cost")) {
                            String Finals = ChatColor.stripColor(lore).replaceAll("[a-zA-Z[_][:][ ][✪]]", "");
                            try {
                                price = Integer.parseInt(Finals);

                            } catch (NumberFormatException es) {
                                player.sendMessage(TextHelper.format("Error!"));
                                player.closeInventory();
                                return;
                            }
                            if (player.getLevel() < price) {
                                player.closeInventory();
                                player.sendMessage(TextHelper.format("&7You need &8(&f"+(price-player.getLevel())+"&8) &7more &5XP"+" &7to repair"));
                                player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1, 1);
                                return;
                            }
                        }
                    }
                    new RepairConfirm(player,price);
                    return;
                }
                int price;
                for (String lore : item.getItemMeta().getLore()) {
                    if (lore.contains("Cost")) {
                        String Finals = ChatColor.stripColor(lore).replaceAll("[a-zA-Z[_][:][ ][✪]]", "");
                        try {
                            price = Integer.parseInt(Finals);

                        } catch (NumberFormatException es) {
                            player.sendMessage(TextHelper.format("Error!"));
                            player.closeInventory();
                            return;
                        }
                        if (item.getDurability() != 0) {
                            if (player.getLevel() < price) {
                                player.closeInventory();
                                player.sendMessage(TextHelper.format("&7You need &8(&f" + (price - player.getLevel()) + "&8) &7more &5XP" + " &7to repair"));
                                player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1, 1);
                                return;
                            }
                            ItemStack itemStack = new ItemStack(item);
                            ItemMeta meta = itemStack.getItemMeta();
                            meta.setLore(null);
                            itemStack.setItemMeta(meta);
                            for (ItemStack itemStacks : player.getInventory().getArmorContents()) {
                                if (itemStacks == null) continue;
                                if (itemStacks.getType() == Material.AIR) continue;

                                if (itemStacks.equals(itemStack)) {
                                    itemStacks.setDurability((short) 0);
                                    break;
                                }
                            }
                            for (ItemStack itemStacks : player.getInventory()) {
                                if (itemStacks == null) continue;
                                if (itemStacks.getType() == Material.AIR) continue;

                                if (itemStacks.equals(itemStack)) {
                                    itemStacks.setDurability((short) 0);
                                    break;
                                }
                            }
                            player.setLevel(player.getLevel() - price);
                            new RepairMenu(player);
                            player.playSound(player.getLocation(), Sound.ANVIL_LAND, 0.5F, 2);
                            return;
                        }
                    }
                }
            }
        }
    }
    @EventHandler
    public void RepairPickup(PlayerPickupItemEvent e){
        if (e.getItem() == null) return;
        if (e.getItem().getItemStack() == null) return;
        if (e.getItem().getItemStack().getDurability() != 0){
            if (e.getPlayer().getOpenInventory().getTitle().contains("Are you sure? (Fix all)")) {
                e.getPlayer().closeInventory();
                return;
            }
            if (e.getPlayer().getOpenInventory().getTitle().contains("Repair Menu") || e.getPlayer().getOpenInventory().getTitle().contains("repair")){
                new BukkitRunnable() {
                    @Override
                    public void run () {
                        if (e.getPlayer().getOpenInventory().getTitle().contains("Repair Menu") || e.getPlayer().getOpenInventory().getTitle().contains("repair")) {
                            new RepairMenu(e.getPlayer());
                        }
                    }
                }.runTaskLater(Main.getInstance(), 1);
                new RepairMenu(e.getPlayer());
            }
        }
    }
}

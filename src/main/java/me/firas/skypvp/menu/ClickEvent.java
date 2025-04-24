package me.firas.skypvp.menu;

import me.firas.skypvp.Main;
import me.firas.skypvp.combat.CombatLogger;
import me.firas.skypvp.combat.CombatPlayer;
import me.firas.skypvp.combat.events.PlayerEnterCombatEvent;
import me.firas.skypvp.combat.events.PlayerLeaveCombatEvent;
import me.firas.skypvp.enums.KitType;
import me.firas.skypvp.events.EventType;
import me.firas.skypvp.kits.KitPreview;
import me.firas.skypvp.kits.KitSelect;
import me.firas.skypvp.kits.KitView;
import me.firas.skypvp.menu.menus.*;
import me.firas.skypvp.settings.Settings;
import me.firas.skypvp.trails.Trails;
import me.firas.skypvp.util.ActionBar;
import me.firas.skypvp.util.TextHelper;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.IOException;

public class ClickEvent implements Listener {
    private boolean isNamed(Inventory inventory, String contains) {
        return inventory.getTitle().equals(contains);
    }

    public void BuyItems(Player player,ItemStack item,ItemStack itemStacks) {
        if (player.getInventory().firstEmpty() == -1) {
            player.closeInventory();
            player.sendMessage(TextHelper.format("&cYou're inventory is full"));
            player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1, 1);
            return;
        }
        for (String lore : item.getItemMeta().getLore()) {
            if (lore.contains("Cost")) {
                Material material;
                String type;
                if (lore.contains("Iron")) {
                    material = Material.IRON_INGOT;
                    type = "&7Iron";
                } else if (lore.contains("Gold")) {
                    material = Material.GOLD_INGOT;
                    type = "&6Gold";
                } else if (lore.contains("Emerald")) {
                    material = Material.EMERALD;
                    type = "&aEmerald";
                } else {
                    material = null;
                    type = "&eCoins";
                }
                String Finals = ChatColor.stripColor(lore).replaceAll("[a-zA-Z[_][:][ ][✪]]", "");
                int price = 0;
                try {
                    price = Integer.parseInt(Finals);

                } catch (NumberFormatException e) {
                    player.sendMessage(TextHelper.format("Error!"));
                    player.closeInventory();
                    return;
                }
                if (material != null) {
                    if (!player.getInventory().contains(material, price)) {
                        int number = 0;
                        for (ItemStack itemStack : player.getInventory()) {
                            if (itemStack == null) continue;
                            if (itemStack.getType().equals(material)) {
                                number = number + itemStack.getAmount();
                            }
                        }
                        player.sendMessage(TextHelper.format("&7You need &8(&f" + (price - number) + "&8) &7more " + type + " &7to purchase"));
                        player.closeInventory();
                        player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1, 1);
                        return;
                    }
                } else {
                    if (Main.getInstance().get(player).getCoins() < price) {
                        player.sendMessage(TextHelper.format("&7You need &8(&f" + (price - Main.getInstance().get(player).getCoins()) + "&8) &7more " + type + " &7to purchase"));
                        player.closeInventory();
                        player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1, 1);
                        return;
                    }
                }
                if (material != null) {
                    removeItems(player.getInventory(), material, price);
                } else {
                    Main.getInstance().get(player).addStats("coins", -price);
                }
                ItemStack itemStack = new ItemStack(itemStacks);
                ItemMeta meta = itemStack.getItemMeta();
                meta.setLore(null);
                itemStack.setItemMeta(meta);
                String name = itemStack.getItemMeta().getDisplayName();
                if (name != null) {
                    if (name.contains("Common Package")) {
                        player.getInventory().addItem(Main.getInstance().getAPackage().ItemStackPackage(1));
                    } else if (name.contains("Rare Package")) {
                        player.getInventory().addItem(Main.getInstance().getAPackage().ItemStackPackage(2));
                    } else if (name.contains("Epic Package")) {
                        player.getInventory().addItem(Main.getInstance().getAPackage().ItemStackPackage(3));
                    } else if (name.contains("Legendary Package")) {
                        player.getInventory().addItem(Main.getInstance().getAPackage().ItemStackPackage(4));
                    } else if (name.contains("Mythical Package")) {
                        player.getInventory().addItem(Main.getInstance().getAPackage().ItemStackPackage(5));
                    } else {
                        player.getInventory().addItem(itemStack);
                    }
                } else {
                    player.getInventory().addItem(itemStack);
                }
                player.playSound(player.getLocation(), Sound.SUCCESSFUL_HIT, 1, 1);
                return;
            }
        }
    }
    public static void removeItems(Inventory inventory, Material type, int amount) {
        if (amount <= 0) return;
        int size = inventory.getSize();
        for (int slot = 0; slot < size; slot++) {
            ItemStack is = inventory.getItem(slot);
            if (is == null) continue;
            if (type == is.getType()) {
                int newAmount = is.getAmount() - amount;
                if (newAmount > 0) {
                    is.setAmount(newAmount);
                    break;
                } else {
                    inventory.clear(slot);
                    amount = -newAmount;
                    if (amount == 0) break;
                }
            }
        }
    }
    @EventHandler
    public void PlayerCombatJoinEvent(PlayerEnterCombatEvent enterCombatEvent) {
        final Player player = enterCombatEvent.getPlayer();
        (new BukkitRunnable() {
            public void run() {
                if (Main.getInstance().getCombatPlayer().isInCombat(player)) {
                    ActionBar.sendAction(player, TextHelper.text("&aStatus&8: &cInfight &8(&7" + CombatLogger.TimeRemaining(player) + "&8)"));
                    return;
                }
                cancel();
            }
        }).runTaskTimer(Main.getInstance(), 0L, 20L);
    }

    @EventHandler
    public void PlayerLeaveCombatEvent(PlayerLeaveCombatEvent e) {
        Player player = e.getPlayer();
        ActionBar.sendAction(player, TextHelper.text("&aStatus&8: &aSafe"));
        Main.getInstance().getCombatPlayer().removePlayer(player);
    }

    @EventHandler
    public void ClickEventInventory(InventoryClickEvent e) throws IOException {
        Player player = (Player) e.getWhoClicked();
        Inventory inventory = e.getInventory();
        if (isNamed(inventory,"Are you sure? (Claim Package)")){
            e.setCancelled(true);
            if (e.getRawSlot() == 11){
                Main.getInstance().getAPackage().ClaimPackage(player);
                return;
            } else if (e.getRawSlot() == 15){
                player.closeInventory();
                return;
            }
        }
        if (isNamed(inventory,"Events")){
            e.setCancelled(true);
            player.closeInventory();
            if (e.getRawSlot() == 13) {
                if (e.getCurrentItem().getType().equals(Material.BARRIER)) {
                    Main.getInstance().getEvents().setTimeEvent(0,1);
                    return;
                }
                if (e.getCurrentItem().getType().equals(Material.CHEST)) {
                    Main.getInstance().getEvents().setEvent(EventType.KoTH);
                    return;
                }
            } else if (e.getRawSlot() == 12){
                if (e.getCurrentItem().getType().equals(Material.EMERALD_BLOCK)) {
                    Main.getInstance().getEvents().setEvent(EventType.Emerald);
                    return;
                }
            } else if (e.getRawSlot() == 14){
                if (e.getCurrentItem().getType().equals(Material.DRAGON_EGG)) {
                    Main.getInstance().getEvents().setEvent(EventType.Boss);
                    return;
                }
            }
        }
        if (isNamed(inventory, "Menu")){
            e.setCancelled(true);
            switch (e.getRawSlot()){
                case 11:
                    new KitSelect(player);
                    break;
                case 13:
                    new KitView(player);
                    break;
                case 15:
                    if (e.getClick().isRightClick()){
                        if (!Main.getInstance().get(player).getTrail().contains("None")){
                            Main.getInstance().get(player).setTrail("None");
                            player.playSound(player.getLocation(),Sound.NOTE_PLING,1,1);
                            new Menu(player);
                            break;
                        }
                    }
                    new Trails(player);
                    break;
                case 28:
                    Main.getInstance().getAPackage().shopPackage(player);
                    break;
                case 30:
                    Main.getInstance().getEnderchests().openEnderChest(player,1);
                    break;
                case 32:
                    new Settings(player);
                    break;
                case 34:
                    new Enchantments(player);
                    break;
            }
            return;
        }
        if (isNamed(inventory, "Preview Kit")) {
            e.setCancelled(true);
            if (e.getRawSlot() == 40){
                if (e.getClick().isRightClick()) {
                    player.closeInventory();
                    return;
                }
                new KitSelect(player);
                return;
            }
        }
        if (isNamed(inventory, "Kits")) {
            e.setCancelled(true);
            if (e.getRawSlot() >= 0 && e.getRawSlot() <= inventory.getSize()) {
                if (e.getCurrentItem() == null || e.getCurrentItem().getType() == null || e.getCurrentItem().getType() == Material.AIR) {
                    return;
                }
                if ((e.getCurrentItem().getType() == Material.SKULL_ITEM)){
                    if (e.getClick().isRightClick()) {
                        player.closeInventory();
                        return;
                    }
                    new Menu(player);
                    return;
                }
                if (e.getClick() == ClickType.RIGHT){
                    switch (e.getRawSlot()){
                        case 11:
                            new KitPreview(player, KitType.MEMBER);
                            break;
                        case 12:
                            new KitPreview(player, KitType.Iron);
                            break;
                        case 13:
                            new KitPreview(player, KitType.Diamond);
                            break;
                        case 14:
                            new KitPreview(player, KitType.Mars);
                            break;
                        case 15:
                            new KitPreview(player, KitType.Super);
                            break;
                    }
                    return;
                }

                for (String lores : e.getCurrentItem().getItemMeta().getLore()){
                    String lore = ChatColor.stripColor(lores);

                    String name = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName());

                    if (lore.contains("Left-Click Select")){
                        if (name.contains("Member")){
                            Main.getInstance().get(player).setKit(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName().toUpperCase()));
                        } else {
                            Main.getInstance().get(player).setKit(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()));
                        }
                        player.playSound(player.getLocation(),Sound.NOTE_PLING,1,2);
                        new KitSelect(player);
                        break;
                    } else if (lore.contains("PREMIUM FEATURE")){
                        player.closeInventory();
                        player.playSound(player.getLocation(),Sound.VILLAGER_NO,1,1);
                        player.sendMessage(TextHelper.format("&cYou need &f"+e.getCurrentItem().getItemMeta().getDisplayName()+" &crank to use this kit"));
                        player.sendMessage(TextHelper.format("&cPurchase ranks at /store"));
                        break;
                    } else if (lore.contains("Left-Click Purchase")){
                        player.closeInventory();
                        if (Main.getInstance().get(player).getCoins() < 2500){
                            player.sendMessage(TextHelper.format("&7You need &8(&f"+(2500-Main.getInstance().get(player).getCoins())+"&8) &7more &eCoins"+" &7to purchase"));
                            player.playSound(player.getLocation(),Sound.VILLAGER_NO,1,1);
                            break;
                        }
                        player.sendMessage(TextHelper.format("&aSuccessfully purchase "+e.getCurrentItem().getItemMeta().getDisplayName()+" &akit."));
                        player.playSound(player.getLocation(),Sound.LEVEL_UP,1,2);
                        Main.getInstance().get(player).addSetting("Iron");
                        Main.getInstance().get(player).setKit("Iron");
                        Main.getInstance().get(player).addStats("coins",-2500);
                        new KitSelect(player);
                        break;
                    }
                }
                return;
            }
            return;
        }
        if (isNamed(inventory,"Shop: Enchantment")){
            e.setCancelled(true);
            if (e.getRawSlot() >= 0 && e.getRawSlot() < player.getOpenInventory().getTopInventory().getSize()){
                if (e.getRawSlot() == 49){

                    new Menu(player);
                    return;

                } else {
                    if (e.getCurrentItem() == null) return;
                    if (e.getCurrentItem().getItemMeta() == null) return;
                    Main.getInstance().getItemStackAPI().confirmPurchase(e.getCurrentItem(),player,false);
                    //BuyItems(player,e.getCurrentItem());
                }
                return;
            }
        }
        if (isNamed(inventory,"Confirm Purchase")){
            e.setCancelled(true);
            if (e.getRawSlot() == 40){
                new Enchantments(player);
                return;
            } else if (e.getRawSlot() == 20){
                BuyItems(player,e.getCurrentItem(),inventory.getItem(4));
            } else if (e.getRawSlot() == 24){
                BuyItems(player,e.getCurrentItem(),inventory.getItem(4));
            }
            return;
        }
        if (isNamed(inventory,"Confirm Purchase Package")){
            e.setCancelled(true);
            if (e.getRawSlot() == 40){
                Main.getInstance().getAPackage().shopPackage(player);
                return;
            } else if (e.getRawSlot() == 20){
                BuyItems(player,e.getCurrentItem(),inventory.getItem(4));
            } else if (e.getRawSlot() == 24){
                BuyItems(player,e.getCurrentItem(),inventory.getItem(4));
            }
            return;
        }
        if (isNamed(inventory,"Kit Viewer (Click)")) {
            e.setCancelled(true);
            if (e.getRawSlot() >= 0 && e.getRawSlot() <= inventory.getSize()) {
                if (e.getCurrentItem() == null || e.getCurrentItem().getType() == null || e.getCurrentItem().getType() == Material.AIR) {
                    return;
                }
                if ((e.getCurrentItem().getType() == Material.SKULL_ITEM)) {
                    if (e.getClick().isRightClick()) {
                        player.closeInventory();
                        return;
                    }
                    new Menu(player);
                    return;
                }
                player.getInventory().addItem(e.getCurrentItem());
            }
            return;
        }
        if (player.getOpenInventory().getTopInventory().getTitle().contains("Previewing")){
            e.setCancelled(true);
            if (e.getRawSlot() == 22){
                if (e.getClick().isRightClick()) {
                    player.closeInventory();
                    return;
                }
                Main.getInstance().getAPackage().shopPackage(player);
                return;
            }
        }
            if (isNamed(inventory,"Shop: Packages")){
                e.setCancelled(true);
                switch (e.getRawSlot()){
                    case 31:
                        if (e.getClick().isRightClick()) {
                            player.closeInventory();
                            break;
                        }
                        new Menu(player);
                        break;
                    case 11:
                        if (e.getClick().isRightClick()){
                            player.openInventory(Main.getInstance().getAPackage().previewPackage(1));
                            return;
                        }
                        Main.getInstance().getItemStackAPI().confirmPurchase(e.getCurrentItem(),player,true);
                        break;
                    case 12:
                        if (e.getClick().isRightClick()){
                            player.openInventory(Main.getInstance().getAPackage().previewPackage(2));
                            return;
                        }
                        Main.getInstance().getItemStackAPI().confirmPurchase(e.getCurrentItem(),player,true);
                        break;
                    case 13:
                        if (e.getClick().isRightClick()){
                            player.openInventory(Main.getInstance().getAPackage().previewPackage(3));
                            return;
                        }
                        Main.getInstance().getItemStackAPI().confirmPurchase(e.getCurrentItem(),player,true);
                        break;
                    case 14:
                        if (e.getClick().isRightClick()){
                            player.openInventory(Main.getInstance().getAPackage().previewPackage(4));
                            return;
                        }
                        Main.getInstance().getItemStackAPI().confirmPurchase(e.getCurrentItem(),player,true);
                        break;
                    case 15:
                        if (e.getClick().isRightClick()){
                            player.openInventory(Main.getInstance().getAPackage().previewPackage(5));
                            return;
                        }
                        Main.getInstance().getItemStackAPI().confirmPurchase(e.getCurrentItem(),player,true);
                        break;
                }
            }
            return;
    }
}

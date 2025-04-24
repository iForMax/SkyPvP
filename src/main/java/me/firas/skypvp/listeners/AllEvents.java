package me.firas.skypvp.listeners;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;

import me.firas.skypvp.Main;
import me.firas.skypvp.enchant.Menu;

import me.firas.skypvp.enums.KitType;
import me.firas.skypvp.events.EventType;
import me.firas.skypvp.regions.Region;
import me.firas.skypvp.settings.Settings;
import me.firas.skypvp.spectate.SpectateMode;
import me.firas.skypvp.util.ActionBar;
import me.firas.skypvp.util.Cooldown;
import me.firas.skypvp.util.ItemBuilder;
import me.firas.skypvp.util.TextHelper;
import me.firas.skypvp.util.Title;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.apache.commons.lang.WordUtils;
import org.bukkit.*;
import org.bukkit.block.Sign;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.*;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;


public class AllEvents implements Listener {
    @EventHandler
    public void WeatherChanges(WeatherChangeEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void FoodChanges(FoodLevelChangeEvent e) {
        e.setCancelled(true);
    }

    public void drops(ItemStack[] itemStacks, ItemStack[] its,Location location){
        HashSet<ItemStack> items = new HashSet<>();
        items.addAll(Arrays.asList(its));
        items.addAll(Arrays.asList(itemStacks));
        for (ItemStack itemStack : items){
            if (itemStack == null) continue;
            if (itemStack.getItemMeta() == null) continue;
            if (itemStack.getItemMeta().hasDisplayName() && !itemStack.getItemMeta().getDisplayName().contains("Package")) continue;
            location.getWorld().dropItem(location,itemStack);
        }

    }
    @EventHandler
    public void dropCancelifLess(PlayerDropItemEvent e){
        if (e.getPlayer().getLocation().getY() <= 60){
            e.setCancelled(true);
        }
    }
    @EventHandler
    public void dropItemGameMaster(PlayerDropItemEvent e){
        Player player = e.getPlayer();
        Material type = e.getItemDrop().getItemStack().getType();
        if (player.getGameMode().equals(GameMode.CREATIVE)){
            if (type.equals(Material.IRON_INGOT) | type.equals(Material.GOLD_INGOT)|type.equals(Material.EMERALD)|type.equals(Material.ENCHANTED_BOOK)|type.equals(Material.SKULL_ITEM)){
                if (!Main.getInstance().GameMaster.contains(player)){
                    e.setCancelled(true);
                }
            }
        }
    }



    @EventHandler
    public void QuitEvent(PlayerQuitEvent e) {
        Main.getInstance().get(e.getPlayer()).setStreak(0);
        Player player = e.getPlayer();
        e.setQuitMessage(null);

        Main.getInstance().build.remove(e.getPlayer());
        if (this.PlayersGiant.contains(e.getPlayer()))
            e.getPlayer().teleport(Main.getInstance().getLocationHandler().getLocation("spawn"));
        if ((Main.getInstance()).spectate.contains(e.getPlayer()))
            SpectateMode.LeaveSpectate(e.getPlayer());
        if (player.getGameMode().equals(GameMode.SPECTATOR)){
            player.setGameMode(GameMode.SURVIVAL);
            player.teleport(Main.getInstance().getLocationHandler().getLocation("spawn"));
            player.setHealth(20.0D);
            Main.getInstance().getKit().grantKit(player, KitType.valueOf(Main.getInstance().get(player).getKit()));
        }
        if (Main.getInstance().getCombatPlayer().isInCombat(e.getPlayer())){
            if (player.getLocation().getY() <= 60){
                Main.getInstance().getKit().spawnDrops(player.getInventory().getContents(), player.getInventory().getArmorContents());
            } else {
                drops(player.getInventory().getContents(),player.getInventory().getArmorContents(), player.getLocation());
            }
            player.getInventory().clear();
            player.setHealth(player.getMaxHealth());
            player.teleport(Main.getInstance().getLocationHandler().getLocation("spawn"));
            Main.getInstance().getKit().grantKit(player, KitType.valueOf(Main.getInstance().get(player).getKit()));
            Player enemy = (Main.getInstance().getCombatPlayer()).combatPlayer.get(player);
            DecimalFormat df = new DecimalFormat("#.0");
            String heartsLeft = df.format(enemy.getHealth()/2);
            if (enemy != null) {
                PlayerKillEvent killEvent = new PlayerKillEvent(enemy);
                Bukkit.getPluginManager().callEvent(killEvent);
                int coins;
                Random ran = new Random();
                if (Main.getInstance().get(player).getKills() >= 1000) {
                    if (Main.getInstance().get(player).kdr() <= 1.0D) {
                        coins = ran.nextInt(10) + 1;
                    } else if (Main.getInstance().get(player).kdr() <= 2.0D) {
                        coins = ran.nextInt(15) + 10;
                    } else if (Main.getInstance().get(player).kdr() <= 3.0D) {
                        coins = ran.nextInt(20) + 15;
                    } else if (Main.getInstance().get(player).kdr() <= 4.0D) {
                        coins = ran.nextInt(25) + 20;
                    } else if (Main.getInstance().get(player).kdr() <= 5.0D) {
                        coins = ran.nextInt(30) + 25;
                    } else if (Main.getInstance().get(player).kdr() <= 8.0D) {
                        coins = ran.nextInt(40) + 30;
                    } else {
                        coins = ran.nextInt(50) + 40;
                    }
                } else {
                    coins = ran.nextInt(10) + 1;
                }
                int points = Main.getInstance().get(player).getPoints()/20;
                Main.getInstance().get(enemy).addStats("coins", coins);
                Main.getInstance().get(player).addStats("deaths", 1);
                Main.getInstance().get(enemy).addStats("points",points);
                player.sendMessage(TextHelper.format("&cYour enemy &9" + enemy.getDisplayName() + " &cstill had &e" + heartsLeft + "&c❤"));
                player.sendMessage(TextHelper.format("&c&l- "+points+" Points"));
                enemy.sendMessage(TextHelper.format("&9" + player.getDisplayName() + " &chas been &c&lELIMINATED&c!"));
                enemy.sendMessage(TextHelper.format("&a&l+ "+points+" Points &6&l+ "+coins+" Coins"));
                Main.getInstance().get(player).addStats("points",-points);
                Main.getInstance().get(player).setStreak(0);
                Main.getInstance().get(enemy).addStats("kills", 1);
                if (Main.getInstance().get(player).getPoints() <= 0){
                    Main.getInstance().get(player).setPoints(0);
                }
            }
        }
        Main.getInstance().get(e.getPlayer()).save();
    }
    @EventHandler
    public void damageSpectate(EntityDamageByEntityEvent e){
        if (e.getDamager() instanceof Player){
            Player player = (Player) e.getDamager();
            if (Main.getInstance().spectate.contains(player)){
                e.setCancelled(true);
            }
        }
    }


    @EventHandler
    public void InteractPackage(PlayerInteractEvent e) {
        if (e.getItem() == null)
            return;
        if (e.getItem().getItemMeta() == null)
            return;
        if (e.getItem().getItemMeta().getDisplayName() == null)
            return;
        Player player = e.getPlayer();
        if (e.getItem().getItemMeta().getDisplayName().contains("Package")) {
            e.setCancelled(true);
            Main.getInstance().getAPackage().Confirmation(player);
            return;
        }
        if (e.getItem().getItemMeta().getDisplayName().contains("Tracker")) {
            if (Main.getInstance().spectate.contains(player)) {
                if (cooldown.isOnCooldown(player.getUniqueId())) return;
                cooldown.addCooldown(player.getUniqueId());
                e.setCancelled(true);
                return;
            }
        }
        if (e.getItem().getItemMeta().getDisplayName().contains("Leave")) {
            if (Main.getInstance().spectate.contains(player)) {
                SpectateMode.LeaveSpectate(player);
            }
        }
    }


    @EventHandler
    public void InteractChest(PlayerInteractEvent e) {
        if (e.isCancelled())
            return;
        if (e.getClickedBlock() == null)
            return;
        if (e.getClickedBlock().getType().equals(Material.ENDER_CHEST) &&
                e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            e.setCancelled(true);
            Main.getInstance().getEnderchests().openEnderChest(e.getPlayer(), 1);
            e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.CHEST_OPEN, 1.0F, 0.0F);
        }
    }


    @EventHandler
    public void addKillstreak(PlayerKillEvent e){
        Player player = e.getKiller();
        Main.getInstance().get(player).addStats("streak",1);
        int streak = Main.getInstance().get(player).getStreak();
        if (streak % 5 == 0 && streak != 0){
            Bukkit.broadcastMessage(TextHelper.format("&b&lSTREAK! &7"+player.getDisplayName()+" &7is on &b&l"+streak+" &7killstreak &e&l!"));
        }
    }


    private boolean isInventoryEmpty(Player p) {
        boolean inventoryEmpty = true;
        for (ItemStack it : p.getInventory().getContents()) {
            if (it != null)
                inventoryEmpty = false;
        }
        return inventoryEmpty;
    }

    private final Cooldown cooldown = Cooldown.builder().duration(1L).name("Trash").cooldowns(new HashMap<>()).build();


    @EventHandler
    public void rightClickSign(PlayerInteractEvent e) {
        if (e.getClickedBlock() == null)
            return;
        if (e.getClickedBlock().getType() == Material.SIGN || e.getClickedBlock().getType() == Material.SIGN_POST || e.getClickedBlock().getType() == Material.WALL_SIGN) {
            Sign sign = (Sign)e.getClickedBlock().getState();
            Player player = e.getPlayer();
            if (sign.getLine(1).contains("Heal")) {
                e.getPlayer().setHealth(e.getPlayer().getMaxHealth());
                e.getPlayer().setFireTicks(0);
                e.getPlayer().sendMessage(TextHelper.format("&a&lYou have been healed"));
                e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.SUCCESSFUL_HIT, 1.0F, 1.0F);
            } else if (sign.getLine(1).contains("Spawn")) {
                player.teleport(Main.getInstance().getLocationHandler().getLocation("spawn"));
                e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.SUCCESSFUL_HIT, 1.0F, 1.0F);
            } else if (sign.getLine(1).contains("Trash")) {
                if (player.getOpenInventory().getTitle().contains("Trash"))
                    return;
                Inventory inventory = Bukkit.createInventory(null, 45, "Trash");
                int sized = 36;
                while (sized < 45) {
                    inventory.setItem(sized, (new ItemBuilder(Material.STAINED_GLASS_PANE, 1, 15)).setDisplayname("&r").build());
                    sized++;
                }
                inventory.setItem(36, (new ItemBuilder(Material.CHEST, 1, 0)).setDisplayname("&a&lReturn back").setLore(new String[] { "", TextHelper.text("&7Put all items from the trash"), ChatColor.GRAY + "to your inventory", "", TextHelper.text("&8&a&lLeft-Click &7Back") }).build());
                inventory.setItem(39, (new ItemBuilder(Material.BUCKET, 1, 0)).setDisplayname("&a&lPut Your inventory").setLore(new String[] { "", TextHelper.text("&7Put all your inventory"), ChatColor.GRAY + "to the trash", "", TextHelper.text("&8&a&lLeft-Click &7Put") }).build());
                inventory.setItem(41, (new ItemBuilder(Material.ARROW, 1, 0)).setDisplayname("&a&lPut selected items").setLore(new String[] { "", TextHelper.text("&7Put all items you"), ChatColor.GRAY + "selected in your cursor", "", TextHelper.text("&8&a&lLeft-Click &7Put") }).build());
                inventory.setItem(40, (new ItemBuilder(Material.BARRIER, 1, 0)).setDisplayname("&c&lClose").setLore(new String[] { "", ChatColor.GRAY + "All items you put in the trash", ChatColor.GRAY + "it will permanently removed", "", TextHelper.text("&8&a&lLeft-Click &7Close") }).build());
                player.openInventory(inventory);
            }
        }
    }

    @EventHandler
    public void InventoryClicks(InventoryClickEvent e) throws IOException {
        if (!(e.getWhoClicked() instanceof Player))
            return;
        if (e.isCancelled()) return;
        Player player = (Player)e.getWhoClicked();
        if (player.getOpenInventory().getTitle().equals("EnderChest")) {
            e.setCancelled(true);
        }
            if (player.getOpenInventory().getTitle().contains("Generators")) {
            e.setCancelled(true);
            if (e.getCurrentItem() == null) return;
            if (e.getCurrentItem().getType() == null) return;
            if (e.getCurrentItem().getType().equals(Material.IRON_INGOT)){
                if (e.getCurrentItem().getItemMeta().getDisplayName() == null) return;
                String named = "iron";
                if (e.getClick().isRightClick()){
                    String name = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName());
                    String Finals = ChatColor.stripColor(name).replaceAll("[a-zA-Z[_][:][ ][✪][(][)]]","");
                    int number = Integer.parseInt(Finals);
                    Main.getInstance().getGeneratorsHandler().removeLocation(named,number);
                    player.playSound(player.getLocation(),Sound.NOTE_PLING,1,2);
                    return;
                }
                String name = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName());
                String Finals = ChatColor.stripColor(name).replaceAll("[a-zA-Z[_][:][ ][✪][(][)]]","");
                int number = Integer.parseInt(Finals);
                Location location = Main.getInstance().getGeneratorsHandler().getLocation(named+"-"+number);
                player.teleport(location);
                player.playSound(player.getLocation(),Sound.ENDERMAN_TELEPORT,1,1);
            }
            else if (e.getCurrentItem().getType().equals(Material.GOLD_INGOT)){
                if (e.getCurrentItem().getItemMeta().getDisplayName() == null) return;
                String named = "gold";
                if (e.getClick().isRightClick()){
                    String name = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName());
                    String Finals = ChatColor.stripColor(name).replaceAll("[a-zA-Z[_][:][ ][✪][(][)]]","");
                    int number = Integer.parseInt(Finals);
                    Main.getInstance().getGeneratorsHandler().removeLocation(named,number);
                    player.playSound(player.getLocation(),Sound.NOTE_PLING,1,2);
                    return;
                }
                String name = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName());
                String Finals = ChatColor.stripColor(name).replaceAll("[a-zA-Z[_][:][ ][✪][(][)]]","");
                int number = Integer.parseInt(Finals);
                Location location = Main.getInstance().getGeneratorsHandler().getLocation(named+"-"+number);
                player.teleport(location);
                player.playSound(player.getLocation(),Sound.ENDERMAN_TELEPORT,1,1);
            }
            else if (e.getCurrentItem().getType().equals(Material.EXP_BOTTLE)){
                if (e.getCurrentItem().getItemMeta().getDisplayName() == null) return;
                String named = "xp";
                if (e.getClick().isRightClick()){
                    String name = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName());
                    String Finals = ChatColor.stripColor(name).replaceAll("[a-zA-Z[_][:][ ][✪][(][)]]","");
                    int number = Integer.parseInt(Finals);
                    Main.getInstance().getGeneratorsHandler().removeLocation(named,number);
                    player.playSound(player.getLocation(),Sound.NOTE_PLING,1,2);
                    return;
                }
                String name = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName());
                String Finals = ChatColor.stripColor(name).replaceAll("[a-zA-Z[_][:][ ][✪][(][)]]","");
                int number = Integer.parseInt(Finals);
                Location location = Main.getInstance().getGeneratorsHandler().getLocation(named+"-"+number);
                player.teleport(location);
                player.playSound(player.getLocation(),Sound.ENDERMAN_TELEPORT,1,1);
            }
            else if (e.getCurrentItem().getType().equals(Material.EMERALD)){
                if (e.getCurrentItem().getItemMeta().getDisplayName() == null) return;
                String named = "emerald";
                if (e.getClick().isRightClick()){
                    String name = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName());
                    String Finals = ChatColor.stripColor(name).replaceAll("[a-zA-Z[_][:][ ][✪][(][)]]","");
                    int number = Integer.parseInt(Finals);
                    Main.getInstance().getGeneratorsHandler().removeLocation(named,number);
                    player.playSound(player.getLocation(),Sound.NOTE_PLING,1,2);
                    return;
                }
                String name = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName());
                String Finals = ChatColor.stripColor(name).replaceAll("[a-zA-Z[_][:][ ][✪][(][)]]","");
                int number = Integer.parseInt(Finals);
                Location location = Main.getInstance().getGeneratorsHandler().getLocation(named+"-"+number);
                player.teleport(location);
                player.playSound(player.getLocation(),Sound.ENDERMAN_TELEPORT,1,1);
            }
            else if (e.getCurrentItem().getType().equals(Material.WOOL)){
                if (e.getCurrentItem().getItemMeta().getDisplayName() == null) return;
                if (e.getClick().isRightClick()){
                    String name = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName().toLowerCase().replaceAll(" ","-"));
                    Main.getInstance().getGeneratorsHandler().removeLocation(name);
                    player.playSound(player.getLocation(),Sound.NOTE_PLING,1,2);
                    return;
                }
                String name = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName().toLowerCase().replaceAll(" ","-"));
                Location location = Main.getInstance().getGeneratorsHandler().getLocation(name);
                player.teleport(location);
                player.playSound(player.getLocation(),Sound.ENDERMAN_TELEPORT,1,1);
            }
            else if (e.getCurrentItem().getType().equals(Material.ENDER_CHEST)){
                if (e.getCurrentItem().getItemMeta().getDisplayName() == null) return;
                String named = "enderchest";
                if (e.getClick().isRightClick()){
                    String name = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName());
                    String Finals = ChatColor.stripColor(name).replaceAll("[a-zA-Z[_][:][ ][✪][(][)]]","");
                    int number = Integer.parseInt(Finals);
                    Main.getInstance().getGeneratorsHandler().removeLocation(named,number);
                    player.playSound(player.getLocation(),Sound.NOTE_PLING,1,2);
                    return;
                }
                String name = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName());
                String Finals = ChatColor.stripColor(name).replaceAll("[a-zA-Z[_][:][ ][✪][(][)]]","");
                int number = Integer.parseInt(Finals);
                Location location = Main.getInstance().getGeneratorsHandler().getLocation(named+"-"+number);
                player.teleport(location);
                player.playSound(player.getLocation(),Sound.ENDERMAN_TELEPORT,1,1);
            }
            else if (e.getCurrentItem().getType().equals(Material.CHEST)){
                if (e.getCurrentItem().getItemMeta().getDisplayName() == null) return;
                String named = "menu";
                if (e.getClick().isRightClick()){
                    String name = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName());
                    String Finals = ChatColor.stripColor(name).replaceAll("[a-zA-Z[_][:][ ][✪][(][)]]","");
                    int number = Integer.parseInt(Finals);
                    Main.getInstance().getGeneratorsHandler().removeLocation(named,number);
                    player.playSound(player.getLocation(),Sound.NOTE_PLING,1,2);
                    return;
                }
                String name = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName());
                String Finals = ChatColor.stripColor(name).replaceAll("[a-zA-Z[_][:][ ][✪][(][)]]","");
                int number = Integer.parseInt(Finals);
                Location location = Main.getInstance().getGeneratorsHandler().getLocation(named+"-"+number);
                player.teleport(location);
                player.playSound(player.getLocation(),Sound.ENDERMAN_TELEPORT,1,1);
            }
        }
            if (player.getOpenInventory().getTitle().contains("Settings")) {
            e.setCancelled(true);
            if (e.getRawSlot() == 31) {
                if (e.getClick().isRightClick()) {
                    player.closeInventory();
                    return;
                }
                new me.firas.skypvp.menu.menus.Menu(player);
                return;
            }
            if (e.getRawSlot() == 14) {
                if (!player.hasPermission("skypvp.scramble")) {
                    player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1.0F, 1.0F);
                    return;
                }
                boolean scramble = Boolean.parseBoolean(Main.getInstance().get(player).getScramble());
                if (scramble) {
                    Main.getInstance().get(player).setScramble("false");
                } else {
                    Main.getInstance().get(player).setScramble("true");
                }
                new Settings(player);
                player.playSound(player.getLocation(), Sound.CLICK, 1.0F, 2.0F);
                return;
            }

            if (e.getRawSlot() == 12) {
                boolean chat = Boolean.parseBoolean(Main.getInstance().get(player).getChat());
                if (chat) {
                    Main.getInstance().get(player).setChat("false");
                } else {
                    Main.getInstance().get(player).setChat("true");
                }
                new Settings(player);
                player.playSound(player.getLocation(), Sound.CLICK, 1.0F, 2.0F);
                return;
            }
            if (e.getRawSlot() == 16) {
                if (Main.getInstance().get(player).getSettings().contains("SafeDropKit")) {
                    Main.getInstance().get(player).removeSetting("SafeDropKit");
                } else {
                    Main.getInstance().get(player).addSetting("SafeDropKit");
                }
                new Settings(player);
                player.playSound(player.getLocation(), Sound.CLICK, 1.0F, 2.0F);
                return;
            }
            if (e.getRawSlot() == 10) {
                if (e.getClick().isRightClick()) {
                    if (Main.getInstance().get(player).getTime().equals("Day")) {
                        Main.getInstance().get(player).setTime("Morning");
                    } else if (Main.getInstance().get(player).getTime().equals("Sunset")) {
                        Main.getInstance().get(player).setTime("Day");
                    } else if (Main.getInstance().get(player).getTime().equals("Midnight")) {
                        Main.getInstance().get(player).setTime("Sunset");
                    } else if (Main.getInstance().get(player).getTime().equals("Dawn")) {
                        Main.getInstance().get(player).setTime("Midnight");
                    } else if (Main.getInstance().get(player).getTime().equals("Morning")) {
                        Main.getInstance().get(player).setTime("Dawn");
                    } else {
                        player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1.0F, 1.0F);
                        return;
                    }
                    new Settings(player);
                    player.playSound(player.getLocation(), Sound.CLICK, 1.0F, 2.0F);
                    Main.getInstance().updateTime(player);
                    return;
                }
                if (Main.getInstance().get(player).getTime().equals("Day")) {
                    Main.getInstance().get(player).setTime("Sunset");
                } else if (Main.getInstance().get(player).getTime().equals("Sunset")) {
                    Main.getInstance().get(player).setTime("Midnight");
                } else if (Main.getInstance().get(player).getTime().equals("Midnight")) {
                    Main.getInstance().get(player).setTime("Dawn");
                } else if (Main.getInstance().get(player).getTime().equals("Dawn")) {
                    Main.getInstance().get(player).setTime("Morning");
                } else if (Main.getInstance().get(player).getTime().equals("Morning")) {
                    Main.getInstance().get(player).setTime("Day");
                } else {
                    player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1.0F, 1.0F);
                    return;
                }
                new Settings(player);
                player.playSound(player.getLocation(), Sound.CLICK, 1.0F, 2.0F);
                Main.getInstance().updateTime(player);
                return;
            }
        }
        if (player.getOpenInventory().getTitle().contains("Trash")) {
            if (e.getRawSlot() >= 36 && e.getRawSlot() < 45)
                e.setCancelled(true);
            if (e.getRawSlot() == 36) {
                if (this.cooldown.isOnCooldown(player.getUniqueId())) {
                    player.sendMessage(TextHelper.format("&fPlease wait few seconds..."));
                    return;
                }
                if (player.getInventory().firstEmpty() == -1) {
                    player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1.0F, 1.0F);
                    return;
                }
                int sloted = -1;
                int ifslots = -1;
                for (ListIterator<ItemStack> listIterator1 = player.getOpenInventory().getTopInventory().iterator(); listIterator1.hasNext(); ) {
                    ItemStack itemStack = listIterator1.next();
                    if (sloted > 35)
                        break;
                    if (itemStack == null)
                        ifslots++;
                    sloted++;
                    if (ifslots == 35) {
                        player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1.0F, 1.0F);
                        this.cooldown.addCooldown(player.getUniqueId());
                        return;
                    }
                }
                int slots = -1;
                for (ListIterator<ItemStack> listIterator2 = player.getOpenInventory().getTopInventory().iterator(); listIterator2.hasNext(); ) {
                    ItemStack itemStack = listIterator2.next();
                    if (player.getInventory().firstEmpty() == -1 ||
                            slots >= 35)
                        break;
                    slots++;
                    if (itemStack == null ||
                            itemStack.getType().equals(Material.AIR))
                        continue;
                    player.getInventory().addItem(new ItemStack[] { itemStack });
                    player.getOpenInventory().getTopInventory().removeItem(new ItemStack[] { itemStack });
                }
                return;
            }
            if (e.getRawSlot() == 40) {
                player.closeInventory();
                return;
            }
            if (e.getRawSlot() == 41) {
                if (player.getOpenInventory().getTopInventory().firstEmpty() == -1) {
                    player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1.0F, 1.0F);
                    return;
                }
                if (player.getItemOnCursor() == null || player.getItemOnCursor().getType().equals(Material.AIR)) {
                    player.playSound(player.getLocation(), Sound.VILLAGER_YES, 1.0F, 2.0F);
                    return;
                }
                for (ListIterator<ItemStack> listIterator = player.getInventory().iterator(); listIterator.hasNext(); ) {
                    ItemStack itemStack = listIterator.next();
                    if (player.getOpenInventory().getTopInventory().firstEmpty() == -1)
                        break;
                    if (itemStack == null ||
                            itemStack.getType().equals(Material.AIR))
                        continue;
                    ItemStack itemStack1 = new ItemStack(player.getItemOnCursor());
                    if (itemStack.getType().equals(itemStack1.getType())) {
                        player.getOpenInventory().getTopInventory().addItem(new ItemStack[] { itemStack });
                        player.getInventory().removeItem(new ItemStack[] { itemStack });
                    }
                }
                player.getOpenInventory().getTopInventory().addItem(new ItemStack[] { player.getItemOnCursor() });
                player.setItemOnCursor(new ItemStack(Material.AIR));
                return;
            }
            if (e.getRawSlot() == 39) {
                if (isInventoryEmpty(player)) {
                    player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1.0F, 2.0F);
                    return;
                }
                if (player.getOpenInventory().getTopInventory().firstEmpty() == -1) {
                    player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1.0F, 1.0F);
                    return;
                }
                for (ListIterator<ItemStack> listIterator = player.getInventory().iterator(); listIterator.hasNext(); ) {
                    ItemStack itemStack = listIterator.next();
                    if (player.getOpenInventory().getTopInventory().firstEmpty() == -1)
                        break;
                    if (itemStack == null ||
                            itemStack.getType().equals(Material.AIR))
                        continue;
                    player.getOpenInventory().getTopInventory().addItem(new ItemStack[] { itemStack });
                    player.getInventory().removeItem(new ItemStack[] { itemStack });
                }
            }
        }
    }



    @EventHandler
    public void creeperExplode(EntityExplodeEvent e) {
        if (e.getEntity() instanceof Creeper)
            e.blockList().clear();
    }

    @EventHandler
    public void clearDropsFromGiantMobs(EntityDeathEvent e) {
        if (!(e.getEntity() instanceof Player))
            e.getDrops().clear();
    }

    ArrayList<Player> PlayersGiant = new ArrayList<>();

    public void startTeleports() {
        (new BukkitRunnable() {
            int seconds = 10;

            public void run() {
                if (AllEvents.this.PlayersGiant.isEmpty()) {
                    cancel();
                    return;
                }
                if (this.seconds <= 0) {
                    for (Player player : AllEvents.this.PlayersGiant) {
                        player.teleport(Main.getInstance().getLocationHandler().getLocation("spawn"));
                        player.playSound(player.getLocation(), Sound.SUCCESSFUL_HIT, 1.0F, 1.0F);
                        ActionBar.sendAction(player, TextHelper.text("&aTeleported"));
                    }
                    cancel();
                    return;
                }
                for (Player player : AllEvents.this.PlayersGiant) {
                    ActionBar.sendAction(player, TextHelper.text("&eTeleport to spawn in &a" + this.seconds + " &eseconds"));
                    player.playSound(player.getLocation(), Sound.NOTE_BASS, 1.0F, 1.0F);
                }
                this.seconds--;
            }
        }).runTaskTimer((Plugin)Main.getInstance(), 0L, 20L);
    }
    @EventHandler
    public void giantFire(EntityDamageEvent e){
        if (e.getEntity() instanceof Giant){
            if (e.getCause().equals(EntityDamageEvent.DamageCause.FIRE)){
                e.setCancelled(true);
            }
        }
    }
    @EventHandler
    public void giantKilled(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof org.bukkit.entity.Giant) {
            if (Main.getInstance().getEvents().isBossEvent()) {
                if (((Giant) e.getEntity()).getHealth() <= 12) {
                    ((Giant) e.getEntity()).setHealth(100);
                    if ((Main.getInstance().getBoss()).level == 1) {
                        Main.getInstance().getBoss().updateLevel(2);
                    } else if ((Main.getInstance().getBoss()).level == 2) {
                        Main.getInstance().getBoss().updateLevel(3);
                    } else if ((Main.getInstance().getBoss()).level == 3) {
                        Main.getInstance().getBoss().updateLevel(4);
                    } else if ((Main.getInstance().getBoss()).level == 4) {
                        Main.getInstance().getBoss().updateLevel(5);
                    } else {
                        for (Entity entity : e.getEntity().getNearbyEntities(40.0D, 15.0D, 40.0D)) {
                            if (entity instanceof Player) {
                                Player player = (Player) entity;
                                this.PlayersGiant.add(player);
                                player.playSound(player.getLocation(), Sound.ENDERDRAGON_HIT, 1.0F, 2.0F);
                                continue;
                            }
                            if (entity instanceof Creeper) {
                                Creeper mob = (Creeper) entity;
                                mob.setHealth(0.0D);
                                continue;
                            }
                            if (entity instanceof Zombie) {
                                Zombie mob = (Zombie) entity;
                                mob.setHealth(0.0D);
                                continue;
                            }
                            if (entity instanceof Skeleton) {
                                Skeleton mob = (Skeleton) entity;
                                mob.setHealth(0.0D);
                            }
                        }
                        startTeleports();
                        if (e.getDamager() instanceof Player) {
                            Bukkit.broadcastMessage(TextHelper.format("&a" + ((Player) e.getDamager()).getDisplayName() + " &chas killed the &4&lGiant&c."));
                        }
                    }
                }
            }
        }
    }
    @EventHandler
    public void cancelMovingItems(InventoryClickEvent e){
        if (e.getWhoClicked() instanceof Player){
            Player player = (Player) e.getWhoClicked();
            if (Main.getInstance().spectate.contains(player)){
                e.setCancelled(true);
            }
        }
    }

    public final Cooldown CooldownFrame = Cooldown.builder().duration(1).name("ItemFrame").cooldowns(new HashMap<>()).build();


    @EventHandler
    public void rightclickItemFrame(PlayerInteractAtEntityEvent e) {
        if (e.getRightClicked() instanceof ItemFrame) {
            ItemFrame itemFrame = (ItemFrame)e.getRightClicked();
            Player player = e.getPlayer();
            if (Main.getInstance().spectate.contains(player)){
                itemFrame.setRotation(Rotation.COUNTER_CLOCKWISE_45);
                return;
            }
            ItemStack itemStack = itemFrame.getItem();
            if (this.CooldownFrame.isOnCooldown(player.getUniqueId())) {
                itemFrame.setRotation(Rotation.COUNTER_CLOCKWISE_45);
                return;
            }

            itemFrame.setRotation(Rotation.COUNTER_CLOCKWISE_45);
            this.CooldownFrame.addCooldown(player.getUniqueId());
            player.playSound(player.getLocation(), Sound.SUCCESSFUL_HIT, 0.5F, 1.0F);
            if (itemStack.getType() == Material.EGG || itemStack.getType() == Material.SNOW_BALL || itemStack.getType() == Material.ARROW|| itemStack.getType() == Material.GOLDEN_APPLE){
                itemStack.setAmount(8);
            }

            player.getInventory().addItem(itemStack);
            return;
        }
    }


    @EventHandler
    public void itemFrameDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof ItemFrame)
            e.setCancelled(true);
    }
    @EventHandler
    public void removeItemDamage(EntityDamageByEntityEvent e){
        if (e.getEntity() instanceof ItemFrame) {
            if (e.getDamager() instanceof Player) {
                Player player = (Player) e.getDamager();
                if (Main.getInstance().build.contains(player)) {
                    player.playSound(player.getLocation(), Sound.SUCCESSFUL_HIT, 1, 1);
                    e.getEntity().remove();
                }
            }
        }
    }
    @EventHandler
    public void PlaceEvent(BlockPlaceEvent e){
        if (e.getPlayer().getGameMode().equals(GameMode.CREATIVE)){
            return;
        }
        e.setCancelled(true);
        if (e.getBlockPlaced().getType().equals(Material.SNOW_BLOCK)){
            e.getPlayer().setItemInHand(new ItemStack(Material.AIR));
        }
    }
    @EventHandler
    public void onPlayerCraft(PrepareItemCraftEvent e) {
        e.getInventory().setResult(new ItemStack(Material.AIR));
    }
    @EventHandler
    public void BreakEvent(BlockBreakEvent e){
        if (e.getPlayer().getGameMode().equals(GameMode.CREATIVE)){
            return;
        }
        e.setCancelled(true);
    }
    @EventHandler
    public void interactSpectate(PlayerInteractEvent e){
        Player player = e.getPlayer();
        if (Main.getInstance().spectate.contains(player)){
            e.setCancelled(true);
            player.updateInventory();
        }
    }
    @EventHandler
    public void interactTrapdoors(PlayerInteractEvent e){
        if (!e.getPlayer().getGameMode().equals(GameMode.CREATIVE)){
            if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
                if (e.getClickedBlock().getType().equals(Material.TRAP_DOOR) || e.getClickedBlock().getType().equals(Material.WOODEN_DOOR) || e.getClickedBlock().getType().equals(Material.DARK_OAK_DOOR) || e.getClickedBlock().getType().equals(Material.ACACIA_DOOR) || e.getClickedBlock().getType().equals(Material.BIRCH_DOOR) || e.getClickedBlock().getType().equals(Material.SPRUCE_DOOR) || e.getClickedBlock().getType().equals(Material.JUNGLE_DOOR) || e.getClickedBlock().getType().equals(Material.WOOD_DOOR) || e.getClickedBlock().getType().equals(Material.DARK_OAK_FENCE_GATE) || e.getClickedBlock().getType().equals(Material.ACACIA_FENCE_GATE) || e.getClickedBlock().getType().equals(Material.BIRCH_FENCE_GATE) || e.getClickedBlock().getType().equals(Material.SPRUCE_FENCE_GATE) || e.getClickedBlock().getType().equals(Material.JUNGLE_FENCE_GATE) || e.getClickedBlock().getType().equals(Material.HOPPER) || e.getClickedBlock().getType().equals(Material.WORKBENCH) || e.getClickedBlock().getType().equals(Material.WOOD_BUTTON) || e.getClickedBlock().getType().equals(Material.STONE_BUTTON) || e.getClickedBlock().getType().equals(Material.LEVER))
                    e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void InterACT(PlayerInteractEvent e) {
        if (e.getItem() == null)
            return;
        if (e.getItem().getType().equals(Material.ENCHANTED_BOOK)) {
            e.setCancelled(true);
            Player players = e.getPlayer();
            new Menu(players, e.getItem());
            return;
        }
    }

    @EventHandler
    public void ChatGame(AsyncPlayerChatEvent e) {
        if (e.isCancelled())
            return;
        e.setCancelled(true);
        Player player = e.getPlayer();
        String chatPlayer = player.getDisplayName();
        if (player.getName().equals("Webmasters") | player.getName().equals("IchBinKlauss") | player.getName().equals("NaifAlmalki")) {
            Bukkit.broadcastMessage(TextHelper.text("&8▏ &c❤ &8▏ &9" + chatPlayer + " &8» &f" + e.getMessage()));
            return;
        }
        boolean chats = Boolean.parseBoolean(Main.getInstance().get(player).getChat());
        if (!chats) {
            player.sendMessage(TextHelper.format("&cYou are currently disabled the chat!"));
            TextHelper.rawCMD(player,"&a&lClick here &7to activate the chat","settings chat","&aClick here to activate","&athe chat"," ","&7You can change this option","&7from the settings");
            return;
        }
        if (Main.getInstance().get(player).getScramble().equals("true")) {
            String chat;
            if (player.isOp()){
                chat = TextHelper.text("&8▏ &e# &8▏ &9" + chatPlayer + " &8» &f" + e.getMessage());
            } else {
                chat = TextHelper.text("&8▏ &e# &8▏ &9" + chatPlayer + " &8» &f")+ e.getMessage();
            }
            TextComponent msg = new TextComponent(chat);
            for (Player players : Bukkit.getOnlinePlayers()) {
                boolean chatd = Boolean.parseBoolean(Main.getInstance().get(players).getChat());
                if (!chatd)
                    continue;
                players.spigot().sendMessage((BaseComponent)msg);
            }
        } else {
            String chat;
            if (player.isOp()){
                chat = TextHelper.text("&8▏ " + Main.getInstance().get(player).getPrestige() + " &8▏ &9" + chatPlayer + " &8» &f" + e.getMessage());
            }else {
                chat = TextHelper.text("&8▏ " + Main.getInstance().get(player).getPrestige() + " &8▏ &9" + chatPlayer + " &8» &f") + e.getMessage();
            }
            TextComponent msg = new TextComponent(chat);
            msg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (new ComponentBuilder(TextHelper.text("&b┏--------------------------┓\n&b┃ &7Username &8» &f" + chatPlayer + "\n&b┃ &7Prestige &8» &f" +
                    Main.getInstance().get(player).getPrestige() + "\n&b┃ &7Points &8» &f"+
                    Main.getInstance().get(player).getPoints() + "\n&b┃ &7K/D &8» &f" +
                    Main.getInstance().get(player).kdr() + "\n&b┃ &7Coins &8» &f" +
                    Main.getInstance().get(player).getCoins() + "\n&b┃ &7Kills &8» &f" +
                    Main.getInstance().get(player).getKills() + "\n&b┃ &7Killstreak &8» &f" +
                    Main.getInstance().get(player).getStreak() + "\n&b┗--------------------------┛")))
                    .create()));
            for (Player players : Bukkit.getOnlinePlayers()) {
                boolean chatd = Boolean.parseBoolean(Main.getInstance().get(players).getChat());

                if (!chatd)
                    continue;
                players.spigot().sendMessage((BaseComponent)msg);
            }
        }
    }

    public final Cooldown Portals = Cooldown.builder().duration(1L).name("portal").cooldowns(new HashMap<>()).build();

    @EventHandler
    public void endPortalGiant(PlayerPortalEvent e) {
        Player player = e.getPlayer();
        if (e.getCause().equals(PlayerTeleportEvent.TeleportCause.END_PORTAL)) {
            e.setCancelled(true);
            if (this.Portals.isOnCooldown(player.getUniqueId()))
                return;
            if (Main.getInstance().getEvents().getCurrentEvent().equals(EventType.Boss)) {
                this.Portals.addCooldown(player.getUniqueId());
                Random random = new Random();
                int ran = random.nextInt(10) + 1;
                player.teleport(Main.getInstance().getLocationHandler().getLocation("giant-spawn-" + ran));
                player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1.0F, 1.0F);
                return;
            }
        }
    }
    @EventHandler
    public void disableDamageinSpawn(EntityDamageEvent e){
        if (e.getEntity() instanceof Player){
            Player player = (Player) e.getEntity();
            if (Main.getInstance().spectate.contains(player)){
                e.setCancelled(true);
                return;
            }
            for (Region region : (Main.getInstance()).regionMap.values()) {
                if (region.contains(player.getLocation())) {
                    e.setCancelled(true);
                    return;
                }
            }
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        if (event.isCancelled())
            return;
        if (!(event.getEntity() instanceof Player))
            return;
        Player target = (Player)event.getEntity();
        Player player = null;
        if (event.getDamager() instanceof Projectile) {
            if (((Projectile)event.getDamager()).getShooter() instanceof Player)
                player = (Player)((Projectile)event.getDamager()).getShooter();
        } else if (event.getDamager() instanceof Player) {
            player = (Player)event.getDamager();
        }
        if (player == null)
            return;
        if (player == target)
            return;
        for (Region region : (Main.getInstance()).regionMap.values()) {
            if (region.contains(player.getLocation()) || region.contains(target.getLocation())) {
                event.setCancelled(true);
                return;
            }
        }
        if (Main.getInstance().spectate.contains(player)){
            event.setCancelled(true);
            return;
        }
        Main.getInstance().getCombatPlayer().addCombat(player);
        Main.getInstance().getCombatPlayer().addCombat(target);
        (Main.getInstance().getCombatPlayer()).combatPlayer.put(player, target);
        (Main.getInstance().getCombatPlayer()).combatPlayer.put(target, player);
    }



    @EventHandler
    public void DeathEvents(PlayerDeathEvent e) {
        Player player = e.getEntity();
        Main.getInstance().get(player).setStreak(0);

        if (this.PlayersGiant.contains(player))
            this.PlayersGiant.remove(player);
        e.setDeathMessage(null);
        if (player.getGameMode().equals(GameMode.SPECTATOR)) {
            player.setHealth(20.0D);
            return;
        }
        int coinsd = Main.getInstance().get(player).getCoins();
        if (Main.getInstance().getCombatPlayer().isInCombat(player) &&
                (Main.getInstance().getCombatPlayer()).combatPlayer.containsKey(player)) {
            Player enemy = (Player)(Main.getInstance().getCombatPlayer()).combatPlayer.get(player);

            DecimalFormat df = new DecimalFormat("#.0");
            String heartsLeft = df.format(enemy.getHealth()/2);
            if (enemy != null) {
                PlayerKillEvent killEvent = new PlayerKillEvent(enemy);
                Bukkit.getPluginManager().callEvent(killEvent);
                int coins;
                Random ran = new Random();
                if (Main.getInstance().get(player).getKills() >= 1000) {
                    if (Main.getInstance().get(player).kdr() <= 1.0D) {
                        coins = ran.nextInt(10) + 1;
                    } else if (Main.getInstance().get(player).kdr() <= 2.0D) {
                        coins = ran.nextInt(15) + 10;
                    } else if (Main.getInstance().get(player).kdr() <= 3.0D) {
                        coins = ran.nextInt(20) + 15;
                    } else if (Main.getInstance().get(player).kdr() <= 4.0D) {
                        coins = ran.nextInt(25) + 20;
                    } else if (Main.getInstance().get(player).kdr() <= 5.0D) {
                        coins = ran.nextInt(30) + 25;
                    } else if (Main.getInstance().get(player).kdr() <= 8.0D) {
                        coins = ran.nextInt(40) + 30;
                    } else {
                        coins = ran.nextInt(50) + 40;
                    }
                } else {
                    coins = ran.nextInt(10) + 1;
                }
                int points = Main.getInstance().get(player).getPoints()/20;
                Main.getInstance().get(enemy).addStats("coins", coins);
                Main.getInstance().get(player).addStats("deaths", 1);
                Main.getInstance().get(enemy).addStats("points",points);
                player.sendMessage(TextHelper.format("&cYour enemy &9" + enemy.getDisplayName() + " &cstill had &e" + heartsLeft + "&c❤"));
                player.sendMessage(TextHelper.format("&c&l- "+points+" Points"));
                enemy.sendMessage(TextHelper.format("&9" + player.getDisplayName() + " &chas been &c&lELIMINATED&c!"));
                enemy.sendMessage(TextHelper.format("&a&l+ "+points+" Points &6&l+ "+coins+" Coins"));
                Main.getInstance().get(player).addStats("points",-points);
                Main.getInstance().get(player).setStreak(0);
                Main.getInstance().get(enemy).addStats("kills", 1);
                if (Main.getInstance().get(player).getPoints() <= 0){
                    Main.getInstance().get(player).setPoints(0);
                }
            }
        }
        if (Main.getInstance().getCombatPlayer().isInCombat(player))
            Main.getInstance().getCombatPlayer().removePlayer(player);
        player.setHealth(20.0D);
        Main.getInstance().Respawning(player);
        for (ItemStack itemStack : e.getDrops()) {
            if (itemStack == null || itemStack.getType().equals(Material.AIR))
                continue;
            if (itemStack.getItemMeta().hasDisplayName() && !itemStack.getItemMeta().getDisplayName().contains("Package"))
                itemStack.setType(Material.AIR);
        }
    }

    public void sendMessage(final Player p, final String s) {
        final int i = s.length();
        (new BukkitRunnable() {
            int c = 0;

            public void run() {
                if (this.c != i) {
                    (new Title("", TextHelper.text("&8" + s.substring(0, this.c + 1) + " &8"), 0, 25, 10)).send(p);
                    this.c++;
                } else {
                    (new Title("", TextHelper.text("&8" + s + " &8"), 0, 10, 10)).send(p);
                            cancel();
                }
            }
        }).runTaskTimer((Plugin)Main.getInstance(), 2L, 2L);
    }

    @EventHandler
    public void Drop(PlayerDropItemEvent e) {
        if (e.isCancelled())
            return;
        if (e.getItemDrop() == null)
            return;
        if (e.getItemDrop().getItemStack() == null)
            return;
        Player player = e.getPlayer();
        if (Main.getInstance().spectate.contains(player)){
            e.setCancelled(true);
            return;
        }
        if (e.getItemDrop().getItemStack().getItemMeta().hasDisplayName() && !e.getItemDrop().getItemStack().getItemMeta().getDisplayName().contains("Package")) {
            if (!Main.getInstance().get(player).getSettings().contains("SafeDropKit")){
                e.setCancelled(true);
                player.playSound(player.getLocation(), Sound.ITEM_BREAK, 1.0F, 2.0F);
                player.sendMessage(TextHelper.format("&7You can't drop this item!"));
                TextHelper.rawCMD(player,"&a&lClick here &7to disable this message","settings safedropkit","&aClick here to &cdeactivate","&aSafe drop kit","","&7You can change this option","&7from the settings");
            } else {
                player.playSound(player.getLocation(), Sound.ITEM_BREAK, 0.4F, 2.0F);
                e.getItemDrop().remove();
            }
        }
    }

    @EventHandler
    public void RightclickArmors(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        if (player.getAllowFlight())
            return;
        String itemed = WordUtils.capitalizeFully(player.getItemInHand().getType().name().replace("_", " "));
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR) {
            if (itemed.contains("Helmet") &&
                    player.getInventory().getHelmet() != null) {
                player.getInventory().setItemInHand(player.getInventory().getHelmet());
                player.getInventory().setHelmet(e.getItem());
                player.playSound(player.getLocation(), Sound.DIG_WOOL, 1.0F, 1.0F);
                return;
            }
            if (itemed.contains("Chestplate") &&
                    player.getInventory().getChestplate() != null) {
                player.getInventory().setItemInHand(player.getInventory().getChestplate());
                player.getInventory().setChestplate(e.getItem());
                player.playSound(player.getLocation(), Sound.DIG_WOOL, 1.0F, 1.0F);
                return;
            }
            if (itemed.contains("Leggings") &&
                    player.getInventory().getLeggings() != null) {
                player.getInventory().setItemInHand(player.getInventory().getLeggings());
                player.getInventory().setLeggings(e.getItem());
                player.playSound(player.getLocation(), Sound.DIG_WOOL, 1.0F, 1.0F);
                return;
            }
            if (itemed.contains("Boots") &&
                    player.getInventory().getBoots() != null) {
                player.getInventory().setItemInHand(player.getInventory().getBoots());
                player.getInventory().setBoots(e.getItem());
                player.playSound(player.getLocation(), Sound.DIG_WOOL, 1.0F, 1.0F);
                return;
            }
        }
    }

    @EventHandler
    public void onState(BlockFromToEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onForm(BlockFormEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onGrow(BlockGrowEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onSoil(EntityInteractEvent e){
        if (e.getBlock().getType() == Material.SOIL) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent e){
        if (e.getEntityType() == EntityType.PRIMED_TNT) {
            e.setCancelled(false);
            e.blockList().clear();
        } else if(e.getEntityType() == EntityType.MINECART_TNT) {
            e.setCancelled(true);
        }
        e.blockList().clear();
    }

    @EventHandler
    public void onExplosionPrime(ExplosionPrimeEvent e){
        if (e.getEntityType() == EntityType.PRIMED_TNT) {
            e.setCancelled(false);
        }
    }

    @EventHandler
    public void onBurn(BlockBurnEvent e){
        e.setCancelled(true);
    }

    @EventHandler
    public void onSpread(BlockSpreadEvent e){
        e.setCancelled(true);
    }

    /*@EventHandler
    public void onPhysics(BlockPhysicsEvent e) {
        if (e.getChangedType().equals(Material.REDSTONE) | e.getChangedType().equals(Material.LEVER) | e.getChangedType().equals(Material.REDSTONE_TORCH_ON) | e.getChangedType().equals(Material.DIODE_BLOCK_OFF) | e.getChangedType().equals(Material.DIODE_BLOCK_ON) | e.getChangedType().equals(Material.REDSTONE_WIRE) | e.getChangedType().equals(Material.PISTON_STICKY_BASE) | e.getChangedType().equals(Material.DIODE) | e.getChangedType().equals(Material.STONE_PLATE) |  e.getChangedType().equals(Material.GOLD_PLATE) |  e.getChangedType().equals(Material.IRON_PLATE) |  e.getChangedType().equals(Material.WOOD_PLATE) |  e.getChangedType().equals(Material.STONE_BUTTON) |  e.getChangedType().equals(Material.WOOD_BUTTON)) return;
        e.setCancelled(true);
    }

     */

    @EventHandler
    public void onBlockChangeState(EntityChangeBlockEvent e){
        e.setCancelled(true);
        if(!(e.getEntity() instanceof Player)) {
            e.getEntity().remove();
        }
    }

}

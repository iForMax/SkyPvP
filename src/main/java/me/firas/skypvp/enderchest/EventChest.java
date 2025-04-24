package me.firas.skypvp.enderchest;

import me.firas.skypvp.Main;
import me.firas.skypvp.util.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;

public class EventChest implements Listener {


    @EventHandler
    public void CloseEnderChest(InventoryCloseEvent e){
        if (!(e.getPlayer() instanceof Player)) return;
        Player player = (Player) e.getPlayer();
        if (e.getInventory().getTitle().contains("Ender Chest #1")){
            Main.getInstance().get(player).setEnderchest1(Main.getInstance().getEnderchests().toBase64(e.getInventory()));
            return;
        }
        if (e.getInventory().getTitle().contains("Ender Chest #2")){
            Main.getInstance().get(player).setEnderchest2(Main.getInstance().getEnderchests().toBase64(e.getInventory()));
            return;
        }
        if (e.getInventory().getTitle().contains("Ender Chest #3")){
            Main.getInstance().get(player).setEnderchest3(Main.getInstance().getEnderchests().toBase64(e.getInventory()));
            return;
        }
        if (e.getInventory().getTitle().contains("Ender Chest #4")){
            Main.getInstance().get(player).setEnderchest4(Main.getInstance().getEnderchests().toBase64(e.getInventory()));
            return;
        }
        if (e.getInventory().getTitle().contains("Ender Chest #5")){
            Main.getInstance().get(player).setEnderchest5(Main.getInstance().getEnderchests().toBase64(e.getInventory()));
            return;
        }
        if (e.getInventory().getTitle().contains("Ender Chest #6")){
            Main.getInstance().get(player).setEnderchest6(Main.getInstance().getEnderchests().toBase64(e.getInventory()));
            return;
        }
        if (e.getInventory().getTitle().contains("Ender Chest #7")){
            Main.getInstance().get(player).setEnderchest7(Main.getInstance().getEnderchests().toBase64(e.getInventory()));
            return;
        }
        if (e.getInventory().getTitle().contains("Ender Chest #8")){
            Main.getInstance().get(player).setEnderchest8(Main.getInstance().getEnderchests().toBase64(e.getInventory()));
            return;
        }
        if (e.getInventory().getTitle().contains("Ender Chest #9")){
            Main.getInstance().get(player).setEnderchest9(Main.getInstance().getEnderchests().toBase64(e.getInventory()));
            return;
        }
    }
    private boolean isNamed(Inventory inventory, String contains) {
        return inventory.getTitle().contains(contains);
    }

    private void addEnderChest(Player player, int enderchest,int from , int to){
        Main.getInstance().get(player).removeSetting("EnderChest"+enderchest+"="+from);
        Main.getInstance().get(player).addSetting("EnderChest"+enderchest+"="+to);
    }
    private void UnlockEnderChest(Player player, int enderchest){
        Main.getInstance().get(player).addSetting("EnderChest"+enderchest+"=1");
    }
    public void confirm(Player player,int enderChest, int level,int cost,boolean isFix){
        Inventory inventory;
        if(isFix){
            inventory = Bukkit.createInventory(null,27,TextHelper.text("Are you sure? &c&l"));

        } else {
            inventory = Bukkit.createInventory(null,27,TextHelper.text("Are you sure? &4&l"));
        }
        ItemStack itemStack = new ItemStack(Material.STAINED_GLASS_PANE,1,(short) 5);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(TextHelper.text("&a&lCONFIRM"));
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(TextHelper.text("&bEnderChest&8: &7"+enderChest));
        lore.add(TextHelper.text("&bLevel&8: &7"+level));
        lore.add("");
        lore.add(TextHelper.text("&6Cost&8: &a"+cost+" &eCoins"));
        lore.add("");
        lore.add(TextHelper.text("&8▪ &a&lLeft-Click &7Confirm"));
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        ItemStack itemStacks = new ItemStack(Material.STAINED_GLASS_PANE,1,(short) 14);
        ItemMeta itemMetas = itemStacks.getItemMeta();
        itemMetas.setDisplayName(TextHelper.text("&c&lCANCEL"));
        ArrayList<String> lores = new ArrayList<>();
        lores.add("");

        lores.add(TextHelper.text("&7This item will cancel"));
        lores.add(TextHelper.text("&7the operation automatically"));

        if (isFix){
            lores.add(TextHelper.text("&7and get you back to enderchest 1"));
        } else {
            lores.add(TextHelper.text("&7and get you back to enderchest "+enderChest));
        }
        lores.add("");
        lores.add(TextHelper.text("&8▪ &a&lLeft-Click &7Cancel"));

        itemMetas.setLore(lores);
        itemStacks.setItemMeta(itemMetas);
        inventory.setItem(11,itemStack);
        inventory.setItem(15,itemStacks);

        player.openInventory(inventory);
    }

    public static final CooldownTick UpgradeECCooldown = CooldownTick.builder().duration(15).name("ECcooldown").cooldowns(new HashMap<>()).build();


    public void NotEnough(Player player, int price){
        UpgradeECCooldown.addCooldown(player.getUniqueId());
        int more = price-Main.getInstance().get(player).getCoins();
        player.sendMessage(TextHelper.format("&7You need &8(&f"+(more)+"&8) &7more &eCoins"+" &7to upgrade"));
        player.playSound(player.getLocation(),Sound.VILLAGER_NO,1,1);
    }



    @EventHandler
    public void ClickEnderChest(InventoryClickEvent e){
        Player player = (Player) e.getWhoClicked();
        Inventory inventory = e.getInventory();
        if (isNamed(inventory,TextHelper.text("Are you sure? &4&l"))){
            e.setCancelled(true);
            if (e.getRawSlot() == 15) {
                int enderchest = 1;
                for (String lore : e.getCurrentItem().getItemMeta().getLore()) {
                    if (lore.contains("and get you back")){
                        String lored = ChatColor.stripColor(lore).replaceAll("[a-zA-Z[_][:][ ][✪][#][']]", "");
                        enderchest = Integer.parseInt(lored);
                        break;
                    }
                }
                Main.getInstance().getEnderchests().openEnderChest(player,enderchest);
                return;
            }
            if (e.getRawSlot() == 11){
                int level = 0;
                int enderchest = 0;
                int price = 0;
                for (String lore : e.getCurrentItem().getItemMeta().getLore()){
                    if (lore.contains("EnderChest")){
                        String lored = ChatColor.stripColor(lore).replaceAll("[a-zA-Z[_][:][ ][✪][#][']]", "");
                        enderchest = Integer.parseInt(lored);
                        continue;
                    }
                    if (lore.contains("Level")){
                        String lored = ChatColor.stripColor(lore).replaceAll("[a-zA-Z[_][:][ ][✪][#][']]", "");
                        level = Integer.parseInt(lored);
                        continue;
                    }
                    if (lore.contains("Cost")){
                        String lored = ChatColor.stripColor(lore).replaceAll("[a-zA-Z[_][:][ ][✪][#][']]", "");
                        price = Integer.parseInt(lored);
                    }
                }
                if (Main.getInstance().get(player).getCoins() < price){
                    NotEnough(player,price);
                    return;
                }
                Main.getInstance().get(player).addStats("coins",-price);
                addEnderChest(player,enderchest,(level)-1,level);
                Main.getInstance().getEnderchests().openEnderChest(player,enderchest);
                player.playSound(player.getLocation(),Sound.LEVEL_UP,0.5F,2);
                return;
            }
        }
        if (isNamed(inventory,TextHelper.text("Are you sure? &c&l"))){
            e.setCancelled(true);
            if (e.getRawSlot() == 15){
                int enderchest = 1;
                for (String lore : e.getCurrentItem().getItemMeta().getLore()) {
                    if (lore.contains("and get you back")){
                        String lored = ChatColor.stripColor(lore).replaceAll("[a-zA-Z[_][:][ ][✪][#][']]", "");
                        enderchest = Integer.parseInt(lored);
                        break;
                    }
                }
                Main.getInstance().getEnderchests().openEnderChest(player,enderchest);
                return;
            }
            if (e.getRawSlot() == 11){
                int level = 0;
                int enderchest = 0;
                int price = 0;
                for (String lore : e.getCurrentItem().getItemMeta().getLore()){
                    if (lore.contains("EnderChest")){
                        String lored = ChatColor.stripColor(lore).replaceAll("[a-zA-Z[_][:][ ][✪][#][']]", "");
                        enderchest = Integer.parseInt(lored);
                        continue;
                    }
                    if (lore.contains("Level")){
                        String lored = ChatColor.stripColor(lore).replaceAll("[a-zA-Z[_][:][ ][✪][#][']]", "");
                        level = Integer.parseInt(lored);
                        continue;
                    }
                    if (lore.contains("Cost")){
                        String lored = ChatColor.stripColor(lore).replaceAll("[a-zA-Z[_][:][ ][✪][#][']]", "");
                        price = Integer.parseInt(lored);
                    }
                }
                if (Main.getInstance().get(player).getCoins() < price){
                    NotEnough(player,price);
                    return;
                }
                Main.getInstance().get(player).addStats("coins",-price);
                UnlockEnderChest(player,enderchest);
                Main.getInstance().getEnderchests().openEnderChest(player,enderchest);
                player.playSound(player.getLocation(),Sound.LEVEL_UP,0.5F,2);
                return;
            }
        }
        if (isNamed(inventory,"EnderChest")){
            if (e.getRawSlot() >= 45 && e.getRawSlot() <= 53){
                e.setCancelled(true);
            }
            switch (e.getRawSlot()){
                case 49:
                    String lored = ChatColor.stripColor(inventory.getTitle()).replaceAll("[a-zA-Z[_][:][ ][✪][#][']]", "");
                    int number = Integer.parseInt(lored);
                    String name = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName());
                    Player WhoEnderChest;
                    if (Bukkit.getPlayerExact(name) == null){
                        FakePlayer fakePlayer = new FakePlayer(Bukkit.getOfflinePlayer(name));
                        WhoEnderChest = fakePlayer.getPlayer();
                        Main.getInstance().get(WhoEnderChest).load();
                        Main.getInstance().get(WhoEnderChest).setEnderChest(number,Main.getInstance().getEnderchests().toBase64(inventory));
                        Main.getInstance().get(WhoEnderChest).save();
                    } else {
                        WhoEnderChest = Bukkit.getPlayerExact(name);
                        Main.getInstance().get(WhoEnderChest).setEnderChest(number,Main.getInstance().getEnderchests().toBase64(inventory));
                        Main.getInstance().get(WhoEnderChest).save();
                    }
                    player.playSound(player.getLocation(),Sound.LEVEL_UP,0.5F,1);
                    return;
            }
        }
        if (isNamed(inventory,"Ender Chest")) {
            if (e.getCurrentItem() != null && e.getCurrentItem().equals(new ItemBuilder(Material.STAINED_GLASS_PANE,1,14).setDisplayname("&c&lLOCKED").build())){
                e.setCancelled(true);
                return;
            }
            switch (e.getRawSlot()) {
                case 45:
                    e.setCancelled(true);
                    if (!e.getCursor().getType().equals(Material.AIR)){
                        player.playSound(player.getLocation(),Sound.VILLAGER_NO,1,1);
                        break;
                    }
                    if (e.getClick().isRightClick()){
                        if (UpgradeECCooldown.isOnCooldown(player.getUniqueId())){
                            return;
                        }
                        if (Main.getInstance().get(player).getSettings().contains("EnderChest1=1")){
                            for (String lore : e.getCurrentItem().getItemMeta().getLore()){
                                if (lore.contains("Cost")){
                                    String lored = ChatColor.stripColor(lore).replaceAll("[a-zA-Z[_][:][ ][✪]]", "");
                                    int price = Integer.parseInt(lored);
                                    if (Main.getInstance().get(player).getCoins() < price) {

                                        NotEnough(player,price);
                                        return;
                                    }
                                    confirm(player,1,2,price,false);
                                    return;
                                }
                            }
                            return;
                        } else if (Main.getInstance().get(player).getSettings().contains("EnderChest1=2")){
                            for (String lore : e.getCurrentItem().getItemMeta().getLore()){
                                if (lore.contains("Cost")){
                                    String lored = ChatColor.stripColor(lore).replaceAll("[a-zA-Z[_][:][ ][✪]]", "");
                                    int price = Integer.parseInt(lored);
                                    if (Main.getInstance().get(player).getCoins() < price) {

                                        NotEnough(player,price);
                                        return;
                                    }
                                    confirm(player,1,3,price,false);

                                    return;
                                }
                            }
                            return;
                        } else if (Main.getInstance().get(player).getSettings().contains("EnderChest1=3")){
                            for (String lore : e.getCurrentItem().getItemMeta().getLore()){
                                if (lore.contains("Cost")){
                                    String lored = ChatColor.stripColor(lore).replaceAll("[a-zA-Z[_][:][ ][✪]]", "");
                                    int price = Integer.parseInt(lored);
                                    if (Main.getInstance().get(player).getCoins() < price) {

                                        NotEnough(player,price);
                                        return;
                                    }
                                    confirm(player,1,4,price,false);

                                    return;
                                }
                            }
                            return;
                        } else if (Main.getInstance().get(player).getSettings().contains("EnderChest1=4")){
                            for (String lore : e.getCurrentItem().getItemMeta().getLore()){
                                if (lore.contains("Cost")){
                                    String lored = ChatColor.stripColor(lore).replaceAll("[a-zA-Z[_][:][ ][✪]]", "");
                                    int price = Integer.parseInt(lored);
                                    if (Main.getInstance().get(player).getCoins() < price) {

                                        NotEnough(player,price);
                                        return;
                                    }
                                    confirm(player,1,5,price,false);

                                    return;
                                }
                            }
                            return;
                        }
                    }
                    if (inventory.getTitle().contains("Ender Chest #1")){
                        break;
                    }
                    Main.getInstance().getEnderchests().openEnderChest(player,1);
                    player.playSound(player.getLocation(),Sound.CLICK,1,1);
                    break;
                case 46:
                    e.setCancelled(true);
                    int enderchest = 2;
                    if (!e.getCursor().getType().equals(Material.AIR)){
                        player.playSound(player.getLocation(),Sound.VILLAGER_NO,1,1);
                        break;
                    }

                    if (e.getClick().isRightClick()){
                        if (UpgradeECCooldown.isOnCooldown(player.getUniqueId())){
                            return;
                        }
                        if (Main.getInstance().get(player).getSettings().contains("EnderChest"+enderchest+"=1")){
                            for (String lore : e.getCurrentItem().getItemMeta().getLore()){
                                if (lore.contains("Cost")){
                                    String lored = ChatColor.stripColor(lore).replaceAll("[a-zA-Z[_][:][ ][✪]]", "");
                                    int price = Integer.parseInt(lored);
                                    if (Main.getInstance().get(player).getCoins() < price) {

                                        NotEnough(player,price);
                                        return;
                                    }
                                    confirm(player,enderchest,2,price,false);

                                    return;
                                }
                            }
                            return;
                        } else if (Main.getInstance().get(player).getSettings().contains("EnderChest"+enderchest+"=2")){
                            for (String lore : e.getCurrentItem().getItemMeta().getLore()){
                                if (lore.contains("Cost")){
                                    String lored = ChatColor.stripColor(lore).replaceAll("[a-zA-Z[_][:][ ][✪]]", "");
                                    int price = Integer.parseInt(lored);
                                    if (Main.getInstance().get(player).getCoins() < price) {

                                        NotEnough(player,price);
                                        return;
                                    }
                                    confirm(player,enderchest,3,price,false);

                                    return;
                                }
                            }
                            return;
                        } else if (Main.getInstance().get(player).getSettings().contains("EnderChest"+enderchest+"=3")){
                            for (String lore : e.getCurrentItem().getItemMeta().getLore()){
                                if (lore.contains("Cost")){
                                    String lored = ChatColor.stripColor(lore).replaceAll("[a-zA-Z[_][:][ ][✪]]", "");
                                    int price = Integer.parseInt(lored);
                                    if (Main.getInstance().get(player).getCoins() < price) {

                                        NotEnough(player,price);
                                        return;
                                    }
                                    confirm(player,enderchest,4,price,false);

                                    return;
                                }
                            }
                            return;
                        } else if (Main.getInstance().get(player).getSettings().contains("EnderChest"+enderchest+"=4")){
                            for (String lore : e.getCurrentItem().getItemMeta().getLore()){
                                if (lore.contains("Cost")){
                                    String lored = ChatColor.stripColor(lore).replaceAll("[a-zA-Z[_][:][ ][✪]]", "");
                                    int price = Integer.parseInt(lored);
                                    if (Main.getInstance().get(player).getCoins() < price) {

                                        NotEnough(player,price);
                                        return;
                                    }
                                    confirm(player,enderchest,5,price,false);

                                    return;
                                }
                            }
                            return;
                        } else {
                            for (String lore : e.getCurrentItem().getItemMeta().getLore()){
                                if (lore.contains("Cost")){
                                    String lored = ChatColor.stripColor(lore).replaceAll("[a-zA-Z[_][:][ ][✪]]", "");
                                    int price = Integer.parseInt(lored);
                                    if (Main.getInstance().get(player).getCoins() < price) {

                                        NotEnough(player,price);
                                        return;
                                    }
                                    confirm(player,enderchest,1,price,true);

                                    return;
                                }
                            }
                        }
                    }
                    if (inventory.getTitle().contains("Ender Chest #"+enderchest)){
                        break;
                    }
                    for (String lores : e.getCurrentItem().getItemMeta().getLore()){
                        if (lores.contains("Broken")){
                            if (UpgradeECCooldown.isOnCooldown(player.getUniqueId())){
                                return;
                            }
                            for (String lore : e.getCurrentItem().getItemMeta().getLore()) {
                                if (lore.contains("Cost")) {
                                    String lored = ChatColor.stripColor(lore).replaceAll("[a-zA-Z[_][:][ ][✪]]", "");
                                    int price = Integer.parseInt(lored);
                                    if (Main.getInstance().get(player).getCoins() < price) {
                                        UpgradeECCooldown.addCooldown(player.getUniqueId());
                                        NotEnough(player, price);
                                        return;
                                    }
                                    confirm(player,enderchest,1,price,true);

                                    return;
                                }
                            }                        }
                    }
                    Main.getInstance().getEnderchests().openEnderChest(player,enderchest);
                    player.playSound(player.getLocation(),Sound.CLICK,1,1);
                    break;
                case 47:
                    e.setCancelled(true);
                    int enderchests = 3;
                    if (!e.getCursor().getType().equals(Material.AIR)){
                        player.playSound(player.getLocation(),Sound.VILLAGER_NO,1,1);
                        break;
                    }

                    if (e.getClick().isRightClick()){
                        if (UpgradeECCooldown.isOnCooldown(player.getUniqueId())){
                            return;
                        }
                        if (Main.getInstance().get(player).getSettings().contains("EnderChest"+enderchests+"=1")){
                            for (String lore : e.getCurrentItem().getItemMeta().getLore()){
                                if (lore.contains("Cost")){
                                    String lored = ChatColor.stripColor(lore).replaceAll("[a-zA-Z[_][:][ ][✪]]", "");
                                    int price = Integer.parseInt(lored);
                                    if (Main.getInstance().get(player).getCoins() < price) {

                                        NotEnough(player,price);
                                        return;
                                    }
                                    confirm(player,enderchests,2,price,false);

                                    return;
                                }
                            }
                            return;
                        } else if (Main.getInstance().get(player).getSettings().contains("EnderChest"+enderchests+"=2")){
                            for (String lore : e.getCurrentItem().getItemMeta().getLore()){
                                if (lore.contains("Cost")){
                                    String lored = ChatColor.stripColor(lore).replaceAll("[a-zA-Z[_][:][ ][✪]]", "");
                                    int price = Integer.parseInt(lored);
                                    if (Main.getInstance().get(player).getCoins() < price) {

                                        NotEnough(player,price);
                                        return;
                                    }
                                    confirm(player,enderchests,3,price,false);

                                    return;
                                }
                            }
                            return;
                        } else if (Main.getInstance().get(player).getSettings().contains("EnderChest"+enderchests+"=3")){
                            for (String lore : e.getCurrentItem().getItemMeta().getLore()){
                                if (lore.contains("Cost")){
                                    String lored = ChatColor.stripColor(lore).replaceAll("[a-zA-Z[_][:][ ][✪]]", "");
                                    int price = Integer.parseInt(lored);
                                    if (Main.getInstance().get(player).getCoins() < price) {

                                        NotEnough(player,price);
                                        return;
                                    }
                                    confirm(player,enderchests,4,price,false);

                                    return;
                                }
                            }
                            return;
                        } else if (Main.getInstance().get(player).getSettings().contains("EnderChest"+enderchests+"=4")){
                            for (String lore : e.getCurrentItem().getItemMeta().getLore()){
                                if (lore.contains("Cost")){
                                    String lored = ChatColor.stripColor(lore).replaceAll("[a-zA-Z[_][:][ ][✪]]", "");
                                    int price = Integer.parseInt(lored);
                                    if (Main.getInstance().get(player).getCoins() < price) {

                                        NotEnough(player,price);
                                        return;
                                    }
                                    confirm(player,enderchests,5,price,false);

                                    return;
                                }
                            }
                            return;
                        } else {
                            for (String lore : e.getCurrentItem().getItemMeta().getLore()){
                                if (lore.contains("Cost")){
                                    String lored = ChatColor.stripColor(lore).replaceAll("[a-zA-Z[_][:][ ][✪]]", "");
                                    int price = Integer.parseInt(lored);
                                    if (Main.getInstance().get(player).getCoins() < price) {

                                        NotEnough(player,price);
                                        return;
                                    }
                                    confirm(player,enderchests,1,price,true);

                                    return;
                                }
                            }
                        }
                    }
                    if (inventory.getTitle().contains("Ender Chest #"+enderchests)){
                        break;
                    }
                    for (String lores : e.getCurrentItem().getItemMeta().getLore()){
                        if (lores.contains("Broken")){
                            if (UpgradeECCooldown.isOnCooldown(player.getUniqueId())){
                                return;
                            }
                            for (String lore : e.getCurrentItem().getItemMeta().getLore()) {
                                if (lore.contains("Cost")) {
                                    String lored = ChatColor.stripColor(lore).replaceAll("[a-zA-Z[_][:][ ][✪]]", "");
                                    int price = Integer.parseInt(lored);
                                    if (Main.getInstance().get(player).getCoins() < price) {
                                        UpgradeECCooldown.addCooldown(player.getUniqueId());
                                        NotEnough(player, price);
                                        return;
                                    }
                                    confirm(player,enderchests,1,price,true);

                                    return;
                                }
                            }                        }
                    }
                    Main.getInstance().getEnderchests().openEnderChest(player,enderchests);
                    player.playSound(player.getLocation(),Sound.CLICK,1,1);
                    break;
                case 48:
                    e.setCancelled(true);
                    int enderchestd = 4;
                    if (!e.getCursor().getType().equals(Material.AIR)){
                        player.playSound(player.getLocation(),Sound.VILLAGER_NO,1,1);
                        break;
                    }

                    if (e.getClick().isRightClick()){
                        if (UpgradeECCooldown.isOnCooldown(player.getUniqueId())){
                            return;
                        }
                        if (Main.getInstance().get(player).getSettings().contains("EnderChest"+enderchestd+"=1")){
                            for (String lore : e.getCurrentItem().getItemMeta().getLore()){
                                if (lore.contains("Cost")){
                                    String lored = ChatColor.stripColor(lore).replaceAll("[a-zA-Z[_][:][ ][✪]]", "");
                                    int price = Integer.parseInt(lored);
                                    if (Main.getInstance().get(player).getCoins() < price) {

                                        NotEnough(player,price);
                                        return;
                                    }
                                    confirm(player,enderchestd,2,price,false);

                                    return;
                                }
                            }
                            return;
                        } else if (Main.getInstance().get(player).getSettings().contains("EnderChest"+enderchestd+"=2")){
                            for (String lore : e.getCurrentItem().getItemMeta().getLore()){
                                if (lore.contains("Cost")){
                                    String lored = ChatColor.stripColor(lore).replaceAll("[a-zA-Z[_][:][ ][✪]]", "");
                                    int price = Integer.parseInt(lored);
                                    if (Main.getInstance().get(player).getCoins() < price) {

                                        NotEnough(player,price);
                                        return;
                                    }
                                    confirm(player,enderchestd,3,price,false);

                                    return;
                                }
                            }
                            return;
                        } else if (Main.getInstance().get(player).getSettings().contains("EnderChest"+enderchestd+"=3")){
                            for (String lore : e.getCurrentItem().getItemMeta().getLore()){
                                if (lore.contains("Cost")){
                                    String lored = ChatColor.stripColor(lore).replaceAll("[a-zA-Z[_][:][ ][✪]]", "");
                                    int price = Integer.parseInt(lored);
                                    if (Main.getInstance().get(player).getCoins() < price) {

                                        NotEnough(player,price);
                                        return;
                                    }
                                    confirm(player,enderchestd,4,price,false);

                                    return;
                                }
                            }
                            return;
                        } else if (Main.getInstance().get(player).getSettings().contains("EnderChest"+enderchestd+"=4")){
                            for (String lore : e.getCurrentItem().getItemMeta().getLore()){
                                if (lore.contains("Cost")){
                                    String lored = ChatColor.stripColor(lore).replaceAll("[a-zA-Z[_][:][ ][✪]]", "");
                                    int price = Integer.parseInt(lored);
                                    if (Main.getInstance().get(player).getCoins() < price) {
                                        NotEnough(player,price);
                                        return;
                                    }
                                    confirm(player,enderchestd,5,price,false);

                                    return;
                                }
                            }
                            return;
                        } else {
                            for (String lore : e.getCurrentItem().getItemMeta().getLore()){
                                if (lore.contains("Cost")){
                                    String lored = ChatColor.stripColor(lore).replaceAll("[a-zA-Z[_][:][ ][✪]]", "");
                                    int price = Integer.parseInt(lored);
                                    if (Main.getInstance().get(player).getCoins() < price) {
                                        NotEnough(player,price);
                                        return;
                                    }
                                    confirm(player,enderchestd,1,price,true);

                                    return;
                                }
                            }
                        }
                    }
                    if (inventory.getTitle().contains("Ender Chest #"+enderchestd)){
                        break;
                    }
                    for (String lores : e.getCurrentItem().getItemMeta().getLore()){
                        if (lores.contains("Broken")) {
                            if (UpgradeECCooldown.isOnCooldown(player.getUniqueId())) {
                                return;
                            }
                            for (String lore : e.getCurrentItem().getItemMeta().getLore()) {
                                if (lore.contains("Cost")) {
                                    String lored = ChatColor.stripColor(lore).replaceAll("[a-zA-Z[_][:][ ][✪]]", "");
                                    int price = Integer.parseInt(lored);
                                    if (Main.getInstance().get(player).getCoins() < price) {
                                        UpgradeECCooldown.addCooldown(player.getUniqueId());
                                        NotEnough(player, price);
                                        return;
                                    }
                                    confirm(player,enderchestd,1,price,true);

                                    return;
                                }
                            }
                        }
                    }
                    Main.getInstance().getEnderchests().openEnderChest(player,enderchestd);
                    player.playSound(player.getLocation(),Sound.CLICK,1,1);
                    break;
                case 49:
                    e.setCancelled(true);
                    int enderchesta = 5;
                    if (!e.getCursor().getType().equals(Material.AIR)){
                        player.playSound(player.getLocation(),Sound.VILLAGER_NO,1,1);
                        break;
                    }

                    if (e.getClick().isRightClick()){
                        if (UpgradeECCooldown.isOnCooldown(player.getUniqueId())){
                            return;
                        }
                        if (Main.getInstance().get(player).getSettings().contains("EnderChest"+enderchesta+"=1")){
                            for (String lore : e.getCurrentItem().getItemMeta().getLore()){
                                if (lore.contains("Cost")){
                                    String lored = ChatColor.stripColor(lore).replaceAll("[a-zA-Z[_][:][ ][✪]]", "");
                                    int price = Integer.parseInt(lored);
                                    if (Main.getInstance().get(player).getCoins() < price) {
                                        NotEnough(player,price);
                                        return;
                                    }
                                    confirm(player,enderchesta,2,price,false);

                                    return;
                                }
                            }
                            return;
                        } else if (Main.getInstance().get(player).getSettings().contains("EnderChest"+enderchesta+"=2")){
                            for (String lore : e.getCurrentItem().getItemMeta().getLore()){
                                if (lore.contains("Cost")){
                                    String lored = ChatColor.stripColor(lore).replaceAll("[a-zA-Z[_][:][ ][✪]]", "");
                                    int price = Integer.parseInt(lored);
                                    if (Main.getInstance().get(player).getCoins() < price) {
                                        NotEnough(player,price);
                                        return;
                                    }
                                    confirm(player,enderchesta,3,price,false);

                                    return;
                                }
                            }
                            return;
                        } else if (Main.getInstance().get(player).getSettings().contains("EnderChest"+enderchesta+"=3")){
                            for (String lore : e.getCurrentItem().getItemMeta().getLore()){
                                if (lore.contains("Cost")){
                                    String lored = ChatColor.stripColor(lore).replaceAll("[a-zA-Z[_][:][ ][✪]]", "");
                                    int price = Integer.parseInt(lored);
                                    if (Main.getInstance().get(player).getCoins() < price) {
                                        NotEnough(player,price);
                                        return;
                                    }
                                    confirm(player,enderchesta,4,price,false);

                                    return;
                                }
                            }
                            return;
                        } else if (Main.getInstance().get(player).getSettings().contains("EnderChest"+enderchesta+"=4")){
                            for (String lore : e.getCurrentItem().getItemMeta().getLore()){
                                if (lore.contains("Cost")){
                                    String lored = ChatColor.stripColor(lore).replaceAll("[a-zA-Z[_][:][ ][✪]]", "");
                                    int price = Integer.parseInt(lored);
                                    if (Main.getInstance().get(player).getCoins() < price) {
                                        NotEnough(player,price);
                                        return;
                                    }
                                    confirm(player,enderchesta,5,price,false);

                                    return;
                                }
                            }
                            return;
                        } else {
                            for (String lore : e.getCurrentItem().getItemMeta().getLore()){
                                if (lore.contains("Cost")){
                                    String lored = ChatColor.stripColor(lore).replaceAll("[a-zA-Z[_][:][ ][✪]]", "");
                                    int price = Integer.parseInt(lored);
                                    if (Main.getInstance().get(player).getCoins() < price) {
                                        NotEnough(player,price);
                                        return;
                                    }
                                    confirm(player,enderchesta,1,price,true);

                                    return;
                                }
                            }
                        }
                    }
                    if (inventory.getTitle().contains("Ender Chest #"+enderchesta)){
                        break;
                    }
                    for (String lores : e.getCurrentItem().getItemMeta().getLore()){
                        if (lores.contains("Broken")){
                            if (UpgradeECCooldown.isOnCooldown(player.getUniqueId())){
                                return;
                            }
                            for (String lore : e.getCurrentItem().getItemMeta().getLore()) {
                                if (lore.contains("Cost")) {
                                    String lored = ChatColor.stripColor(lore).replaceAll("[a-zA-Z[_][:][ ][✪]]", "");
                                    int price = Integer.parseInt(lored);
                                    if (Main.getInstance().get(player).getCoins() < price) {
                                        UpgradeECCooldown.addCooldown(player.getUniqueId());

                                        NotEnough(player, price);
                                        return;
                                    }
                                    confirm(player,enderchesta,1,price,true);

                                    return;
                                }
                            }
                            return;
                        }
                    }
                    Main.getInstance().getEnderchests().openEnderChest(player,enderchesta);
                    player.playSound(player.getLocation(),Sound.CLICK,1,1);
                    break;
                case 50:
                    e.setCancelled(true);
                    int enderchestp = 6;
                    if (!e.getCursor().getType().equals(Material.AIR)){
                        player.playSound(player.getLocation(),Sound.VILLAGER_NO,1,1);
                        break;
                    }

                    if (e.getClick().isRightClick()){
                        if (UpgradeECCooldown.isOnCooldown(player.getUniqueId())){
                            return;
                        }
                        if (Main.getInstance().get(player).getSettings().contains("EnderChest"+enderchestp+"=1")){
                            for (String lore : e.getCurrentItem().getItemMeta().getLore()){
                                if (lore.contains("Cost")){
                                    String lored = ChatColor.stripColor(lore).replaceAll("[a-zA-Z[_][:][ ][✪]]", "");
                                    int price = Integer.parseInt(lored);
                                    if (Main.getInstance().get(player).getCoins() < price) {
                                        NotEnough(player,price);
                                        return;
                                    }
                                    confirm(player,enderchestp,2,price,false);

                                    return;
                                }
                            }
                            return;
                        } else if (Main.getInstance().get(player).getSettings().contains("EnderChest"+enderchestp+"=2")){
                            for (String lore : e.getCurrentItem().getItemMeta().getLore()){
                                if (lore.contains("Cost")){
                                    String lored = ChatColor.stripColor(lore).replaceAll("[a-zA-Z[_][:][ ][✪]]", "");
                                    int price = Integer.parseInt(lored);
                                    if (Main.getInstance().get(player).getCoins() < price) {
                                        NotEnough(player,price);
                                        return;
                                    }
                                    confirm(player,enderchestp,3,price,false);

                                    return;
                                }
                            }
                            return;
                        } else if (Main.getInstance().get(player).getSettings().contains("EnderChest"+enderchestp+"=3")){
                            for (String lore : e.getCurrentItem().getItemMeta().getLore()){
                                if (lore.contains("Cost")){
                                    String lored = ChatColor.stripColor(lore).replaceAll("[a-zA-Z[_][:][ ][✪]]", "");
                                    int price = Integer.parseInt(lored);
                                    if (Main.getInstance().get(player).getCoins() < price) {
                                        NotEnough(player,price);
                                        return;
                                    }
                                    confirm(player,enderchestp,4,price,false);

                                    return;
                                }
                            }
                            return;
                        } else if (Main.getInstance().get(player).getSettings().contains("EnderChest"+enderchestp+"=4")){
                            for (String lore : e.getCurrentItem().getItemMeta().getLore()){
                                if (lore.contains("Cost")){
                                    String lored = ChatColor.stripColor(lore).replaceAll("[a-zA-Z[_][:][ ][✪]]", "");
                                    int price = Integer.parseInt(lored);
                                    if (Main.getInstance().get(player).getCoins() < price) {
                                        NotEnough(player,price);
                                        return;
                                    }
                                    confirm(player,enderchestp,5,price,false);

                                    return;
                                }
                            }
                            return;
                        } else {
                            for (String lore : e.getCurrentItem().getItemMeta().getLore()){
                                if (lore.contains("Cost")){
                                    String lored = ChatColor.stripColor(lore).replaceAll("[a-zA-Z[_][:][ ][✪]]", "");
                                    int price = Integer.parseInt(lored);
                                    if (Main.getInstance().get(player).getCoins() < price) {
                                        NotEnough(player,price);
                                        return;
                                    }
                                    confirm(player,enderchestp,1,price,true);

                                    return;
                                }
                            }
                        }
                    }
                    if (inventory.getTitle().contains("Ender Chest #"+enderchestp)){
                        break;
                    }
                    for (String lores : e.getCurrentItem().getItemMeta().getLore()) {
                        if (lores.contains("Broken")) {
                            if (UpgradeECCooldown.isOnCooldown(player.getUniqueId())){
                                return;
                            }
                            for (String lore : e.getCurrentItem().getItemMeta().getLore()) {
                                if (lore.contains("Cost")) {
                                    String lored = ChatColor.stripColor(lore).replaceAll("[a-zA-Z[_][:][ ][✪]]", "");
                                    int price = Integer.parseInt(lored);
                                    if (Main.getInstance().get(player).getCoins() < price) {
                                        UpgradeECCooldown.addCooldown(player.getUniqueId());

                                        NotEnough(player, price);
                                        return;
                                    }
                                    confirm(player,enderchestp,1,price,true);

                                    return;
                                }
                            }
                        }
                    }
                    Main.getInstance().getEnderchests().openEnderChest(player,enderchestp);
                    player.playSound(player.getLocation(),Sound.CLICK,1,1);
                    break;
                case 51:
                    e.setCancelled(true);
                    if (!e.getCursor().getType().equals(Material.AIR)){
                        player.playSound(player.getLocation(),Sound.VILLAGER_NO,1,1);
                        break;
                    }
                    if (inventory.getTitle().contains("Ender Chest #7")){
                        break;
                    }
                    if (!player.hasPermission("skypvp.enderchest.7")){
                        player.playSound(player.getLocation(),Sound.VILLAGER_NO,1,2);
                        return;
                    }
                    Main.getInstance().getEnderchests().openEnderChest(player,7);
                    player.playSound(player.getLocation(),Sound.CLICK,1,1);
                    break;
                case 52:
                    e.setCancelled(true);
                    if (!e.getCursor().getType().equals(Material.AIR)){
                        player.playSound(player.getLocation(),Sound.VILLAGER_NO,1,1);
                        break;
                    }
                    if (inventory.getTitle().contains("Ender Chest #8")){
                        break;
                    }
                    if (!player.hasPermission("skypvp.enderchest.8")){
                        player.playSound(player.getLocation(),Sound.VILLAGER_NO,1,2);
                        return;
                    }
                    Main.getInstance().getEnderchests().openEnderChest(player,8);
                    player.playSound(player.getLocation(),Sound.CLICK,1,1);
                    break;
                case 53:
                    e.setCancelled(true);
                    if (!e.getCursor().getType().equals(Material.AIR)){
                        player.playSound(player.getLocation(),Sound.VILLAGER_NO,1,1);
                        break;
                    }
                    if (inventory.getTitle().contains("Ender Chest #9")){
                        break;
                    }
                    if (!player.hasPermission("skypvp.enderchest.9")){
                        player.playSound(player.getLocation(),Sound.VILLAGER_NO,1,2);
                        return;
                    }
                    Main.getInstance().getEnderchests().openEnderChest(player,9);
                    player.playSound(player.getLocation(),Sound.CLICK,1,1);
                    break;


            }

        }
    }
}

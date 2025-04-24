package me.firas.skypvp.packages;

import com.sun.xml.internal.bind.v2.runtime.reflect.Lister;
import me.firas.skypvp.Main;
import me.firas.skypvp.util.ItemBuilder;
import me.firas.skypvp.util.TextHelper;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;

import static me.firas.skypvp.generators.help.getHead;

public class Package {
    private final ArrayList<ItemStack> Package1 = new ArrayList<>();
    private final ArrayList<ItemStack> Package2 = new ArrayList<>();
    private final ArrayList<ItemStack> Package3 = new ArrayList<>();
    private final ArrayList<ItemStack> Package4 = new ArrayList<>();
    private final ArrayList<ItemStack> Package5 = new ArrayList<>();

    public void setPackages(){
        setPackage1();
        setPackage2();
        setPackage3();
        setPackage4();
        setPackage5();

    }

    public void shopPackage(Player player){
        Inventory inventory = Bukkit.createInventory(null,36,"Shop: Packages");
        inventory.setItem(11,ItemPackage(1,"&7Iron",96,250));
        inventory.setItem(12,ItemPackage(2,"&6Gold",96,300));
        inventory.setItem(13,ItemPackage(3,"&6Gold",192,400));
        inventory.setItem(14,ItemPackage(4,"&6Gold",256,750));
        inventory.setItem(15,ItemPackage(5,"&aEmerald",128,1500));
        inventory.setItem(31,Main.getInstance().getItemStackAPI().BackToMenu());
        player.openInventory(inventory);
    }

    public String package1 = ChatColor.translateAlternateColorCodes('&', "&7Common Package");
    public String package2 = ChatColor.translateAlternateColorCodes('&', "&bRare Package");
    public String package3 = ChatColor.translateAlternateColorCodes('&', "&5Epic Package");
    public String package4 = ChatColor.translateAlternateColorCodes('&', "&cLegendary Package");
    public String package5 = ChatColor.translateAlternateColorCodes('&', "&4&lMythical Package");

    private ItemStack ItemPackage(int number,String type,int price,int coins){
        ItemStack itemStack;
        if (number == 1){
            itemStack = getHead("package1");
        } else if (number == 2){
            itemStack = getHead("package2");

        } else if (number == 3){
            itemStack = getHead("package3");
        } else if (number == 4){
            itemStack = getHead("package4");
        } else {
            itemStack = getHead("package5");
        }
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (number == 1){
            itemMeta.setDisplayName(package1);
        } else if (number==2){
            itemMeta.setDisplayName(package2);

        } else if (number==3){
            itemMeta.setDisplayName(package3);

        } else if (number==4){
            itemMeta.setDisplayName(package4);

        } else if (number==5){
            itemMeta.setDisplayName(package5);

        }
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(TextHelper.text("&6Cost&8: &a"+price+" "+type));
        lore.add(TextHelper.text("&7or"));
        lore.add(TextHelper.text("&6Cost&8: &a"+coins+" &eCoins"));
        lore.add("");
        lore.add(TextHelper.text("&8▪ &a&lLeft-Click &7Choose"));
        lore.add(TextHelper.text("&8▪ &6&lRight-Click &7Preview"));
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
    public void NoItem(Player player){
        player.sendMessage(TextHelper.format("&7Please hold your package to claim!"));
        player.playSound(player.getLocation(), Sound.VILLAGER_NO,1,1);
        player.closeInventory();
    }
    public void ClaimPackage(Player player){
        if (player.getItemInHand() == null){
            NoItem(player);
            return;
        }
        if (player.getItemInHand().getItemMeta() == null){
            NoItem(player);
            return;
        }
        if (player.getItemInHand().getItemMeta().getDisplayName() == null){
            NoItem(player);
            return;
        }
        if (!player.getItemInHand().getItemMeta().getDisplayName().contains("Package")){
            NoItem(player);
            return;
        }
        if (player.getItemInHand().getItemMeta().getDisplayName().contains(package1)){
            if (player.getItemInHand().getAmount() > 1){
                player.getItemInHand().setAmount(player.getItemInHand().getAmount()-1);
            }else {
                player.setItemInHand(null);
            }
            for (ItemStack itemStack : Package1){
                player.getInventory().addItem(itemStack);
            }
            player.playSound(player.getLocation(),Sound.BAT_TAKEOFF,1,2);
        } else if (player.getItemInHand().getItemMeta().getDisplayName().contains(package2)){
            if (player.getItemInHand().getAmount() > 1){
                player.getItemInHand().setAmount(player.getItemInHand().getAmount()-1);
            }else {
                player.setItemInHand(null);
            }
            for (ItemStack itemStack : Package2){
                player.getInventory().addItem(itemStack);
            }
            player.playSound(player.getLocation(),Sound.BAT_TAKEOFF,1,2);
        } else if (player.getItemInHand().getItemMeta().getDisplayName().contains(package3)){
            if (player.getItemInHand().getAmount() > 1){
                player.getItemInHand().setAmount(player.getItemInHand().getAmount()-1);
            }else {
                player.setItemInHand(null);
            }
            for (ItemStack itemStack : Package3){
                player.getInventory().addItem(itemStack);
            }
            player.playSound(player.getLocation(),Sound.BAT_TAKEOFF,1,2);
        } else if (player.getItemInHand().getItemMeta().getDisplayName().contains(package4)){
            if (player.getItemInHand().getAmount() > 1){
                player.getItemInHand().setAmount(player.getItemInHand().getAmount()-1);
            }else {
                player.setItemInHand(null);
            }
            for (ItemStack itemStack : Package4){
                player.getInventory().addItem(itemStack);
            }
            player.playSound(player.getLocation(),Sound.BAT_TAKEOFF,1,2);
        } else if (player.getItemInHand().getItemMeta().getDisplayName().contains(package5)){
            if (player.getItemInHand().getAmount() > 1){
                player.getItemInHand().setAmount(player.getItemInHand().getAmount()-1);
            }else {
                player.setItemInHand(null);
            }
            for (ItemStack itemStack : Package5){
                player.getInventory().addItem(itemStack);
            }
            player.playSound(player.getLocation(),Sound.BAT_TAKEOFF,1,2);
        } else {
            player.sendMessage(TextHelper.format("&c&lError! &ccontact &4Administrator &cto fix this error"));
        }
        player.closeInventory();

    }
    public ItemStack ItemStackPackage(int number){
        ItemStack itemStack;
        if (number == 1){
            itemStack = getHead("package1");
        } else if (number == 2){
            itemStack = getHead("package2");

        } else if (number == 3){
            itemStack = getHead("package3");
        } else if (number == 4){
            itemStack = getHead("package4");
        } else {
            itemStack = getHead("package5");
        }
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (number == 1){
            itemMeta.setDisplayName(package1);
        } else if (number==2){
            itemMeta.setDisplayName(package2);

        } else if (number==3){
            itemMeta.setDisplayName(package3);

        } else if (number==4){
            itemMeta.setDisplayName(package4);

        } else if (number==5){
            itemMeta.setDisplayName(package5);

        }        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(TextHelper.text("&8▪ &a&lLeft-Click &7Claim"));
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public void Confirmation(Player player){
        Inventory inventory = Bukkit.createInventory(null,27,"Are you sure? (Claim Package)");
        inventory.setItem(11,new ItemBuilder(Material.WOOL,1,5).setDisplayname("&aYes").setLore("",TextHelper.text("&7Note:"),TextHelper.text("&7Please hold your package"),"",TextHelper.text("&c&lWarning:"),TextHelper.text("&cMake sure you have space"),TextHelper.text("&cin your inventory"),"",TextHelper.text("&8▪ &a&lLeft-Click &7Confirm")).build());
        inventory.setItem(15,new ItemBuilder(Material.WOOL,1,14).setDisplayname("&cNo").setLore("",TextHelper.text("&7Closes this menu"),"",TextHelper.text("&8▪ &a&lLeft-Click &7Close")).build());
        player.openInventory(inventory);
    }


    public Inventory previewPackage(int number){
        Inventory inventory;
        if (number == 1) {
            inventory = Bukkit.createInventory(null, 27, "Previewing "+package1);
            for (ItemStack itemStack : Package1) {
                inventory.addItem(itemStack);
            }
            inventory.setItem(22, Main.getInstance().getItemStackAPI().BackToMenu());
            return inventory;
        }
        else if (number == 2) {
            inventory = Bukkit.createInventory(null, 27, "Previewing "+package2);
            for (ItemStack itemStack : Package2) {
                inventory.addItem(itemStack);
            }
            inventory.setItem(22, Main.getInstance().getItemStackAPI().BackToMenu());
            return inventory;
        }
        else if (number == 3) {
            inventory = Bukkit.createInventory(null, 27, "Previewing "+package3);
            for (ItemStack itemStack : Package3) {
                inventory.addItem(itemStack);
            }
            inventory.setItem(22, Main.getInstance().getItemStackAPI().BackToMenu());
            return inventory;
        }
        else if (number == 4) {
            inventory = Bukkit.createInventory(null, 27, "Previewing "+package4);
            for (ItemStack itemStack : Package4) {
                inventory.addItem(itemStack);
            }
            inventory.setItem(22,Main.getInstance().getItemStackAPI().BackToMenu());
            return inventory;
        }
        else {
            inventory = Bukkit.createInventory(null, 27, "Previewing "+package5);
            for (ItemStack itemStack : Package5) {
                inventory.addItem(itemStack);
            }
            inventory.setItem(22, Main.getInstance().getItemStackAPI().BackToMenu());
            return inventory;
        }
    }

    private void setPackage1() {
        this.Package1.clear();
        this.Package1.add((new ItemBuilder(Material.DIAMOND_HELMET, 1, 0)).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).build());
        this.Package1.add((new ItemBuilder(Material.DIAMOND_CHESTPLATE, 1, 0)).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).build());
        this.Package1.add((new ItemBuilder(Material.DIAMOND_LEGGINGS, 1, 0)).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).build());
        this.Package1.add((new ItemBuilder(Material.DIAMOND_BOOTS, 1, 0)).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).build());
        this.Package1.add((new ItemBuilder(Material.DIAMOND_SWORD, 1, 0)).addEnchant(Enchantment.DAMAGE_ALL, 1).build());
        this.Package1.add((new ItemBuilder(Material.BOW, 1, 0)).addEnchant(Enchantment.ARROW_DAMAGE, 1).build());
        this.Package1.add((new ItemBuilder(Material.GOLDEN_APPLE, 4, 0)).build());
        this.Package1.add((new ItemBuilder(Material.POTION, 1, 16386)).build());
    }

    private void setPackage2() {
        this.Package2.clear();
        this.Package2.add((new ItemBuilder(Material.DIAMOND_HELMET, 1, 0)).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).build());
        this.Package2.add((new ItemBuilder(Material.DIAMOND_CHESTPLATE, 1, 0)).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).build());
        this.Package2.add((new ItemBuilder(Material.DIAMOND_LEGGINGS, 1, 0)).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).build());
        this.Package2.add((new ItemBuilder(Material.DIAMOND_BOOTS, 1, 0)).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).build());
        this.Package2.add((new ItemBuilder(Material.DIAMOND_SWORD, 1, 0)).addEnchant(Enchantment.DAMAGE_ALL, 2).build());
        this.Package2.add((new ItemBuilder(Material.BOW, 1, 0)).addEnchant(Enchantment.ARROW_DAMAGE, 1).addEnchant(Enchantment.ARROW_FIRE, 1).build());
        this.Package2.add((new ItemBuilder(Material.GOLDEN_APPLE, 12, 0)).build());
        this.Package2.add((new ItemBuilder(Material.POTION, 1, 16418)).build());
    }

    private void setPackage3() {
        this.Package3.clear();
        this.Package3.add((new ItemBuilder(Material.DIAMOND_HELMET, 1, 0)).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3).build());
        this.Package3.add((new ItemBuilder(Material.DIAMOND_CHESTPLATE, 1, 0)).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3).build());
        this.Package3.add((new ItemBuilder(Material.DIAMOND_LEGGINGS, 1, 0)).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3).build());
        this.Package3.add((new ItemBuilder(Material.DIAMOND_BOOTS, 1, 0)).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3).build());
        this.Package3.add((new ItemBuilder(Material.DIAMOND_SWORD, 1, 0)).addEnchant(Enchantment.DAMAGE_ALL, 3).addEnchant(Enchantment.FIRE_ASPECT, 1).addEnchant(Enchantment.DURABILITY, 1).build());
        this.Package3.add((new ItemBuilder(Material.BOW, 1, 0)).addEnchant(Enchantment.ARROW_DAMAGE, 3).addEnchant(Enchantment.ARROW_FIRE, 1).build());
        this.Package3.add((new ItemBuilder(Material.GOLDEN_APPLE, 24, 0)).build());
        this.Package3.add((new ItemBuilder(Material.POTION, 1, 16418)).build());
    }

    private void setPackage4() {
        this.Package4.clear();
        this.Package4.add((new ItemBuilder(Material.DIAMOND_HELMET, 1, 0)).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4).build());
        this.Package4.add((new ItemBuilder(Material.DIAMOND_CHESTPLATE, 1, 0)).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3).build());
        this.Package4.add((new ItemBuilder(Material.DIAMOND_LEGGINGS, 1, 0)).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3).build());
        this.Package4.add((new ItemBuilder(Material.DIAMOND_BOOTS, 1, 0)).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4).build());
        this.Package4.add((new ItemBuilder(Material.DIAMOND_SWORD, 1, 0)).addEnchant(Enchantment.DAMAGE_ALL, 4).addEnchant(Enchantment.FIRE_ASPECT, 1).addEnchant(Enchantment.DURABILITY, 1).build());
        this.Package4.add((new ItemBuilder(Material.BOW, 1, 0)).addEnchant(Enchantment.ARROW_DAMAGE, 4).addEnchant(Enchantment.ARROW_FIRE, 1).addEnchant(Enchantment.ARROW_KNOCKBACK, 1).build());
        this.Package4.add((new ItemBuilder(Material.GOLDEN_APPLE, 48, 0)).build());
        this.Package4.add((new ItemBuilder(Material.POTION, 1, 16393)).build());
    }

    private void setPackage5() {
        this.Package5.clear();
        this.Package5.add((new ItemBuilder(Material.DIAMOND_HELMET, 1, 0)).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4).build());
        this.Package5.add((new ItemBuilder(Material.DIAMOND_CHESTPLATE, 1, 0)).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4).build());
        this.Package5.add((new ItemBuilder(Material.DIAMOND_LEGGINGS, 1, 0)).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4).build());
        this.Package5.add((new ItemBuilder(Material.DIAMOND_BOOTS, 1, 0)).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4).build());
        this.Package5.add((new ItemBuilder(Material.DIAMOND_SWORD, 1, 0)).addEnchant(Enchantment.DAMAGE_ALL, 4).addEnchant(Enchantment.FIRE_ASPECT, 2).addEnchant(Enchantment.KNOCKBACK, 1).addEnchant(Enchantment.DURABILITY, 3).build());
        this.Package5.add((new ItemBuilder(Material.BOW, 1, 0)).addEnchant(Enchantment.ARROW_DAMAGE, 4).addEnchant(Enchantment.ARROW_FIRE, 1).addEnchant(Enchantment.ARROW_KNOCKBACK, 2).addEnchant(Enchantment.ARROW_INFINITE, 1).build());
        this.Package5.add((new ItemBuilder(Material.GOLDEN_APPLE, 64, 0)).build());

        this.Package5.add((new ItemBuilder(Material.POTION, 1, 16393)).build());
        this.Package5.add((new ItemBuilder(Material.POTION, 1, 16418)).build());
        this.Package5.add((new ItemBuilder(Material.POTION, 1, 16451)).build());
    }
}

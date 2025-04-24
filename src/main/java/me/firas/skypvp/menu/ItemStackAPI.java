package me.firas.skypvp.menu;

import com.google.common.base.Strings;
import me.firas.skypvp.Main;
import me.firas.skypvp.generators.Heads;
import me.firas.skypvp.generators.help;
import me.firas.skypvp.util.ItemBuilder;
import me.firas.skypvp.util.TextHelper;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.IOException;
import java.util.ArrayList;


public class ItemStackAPI {
    public String getProgressBar(int current, int max, int totalBars, char symbol, ChatColor completedColor,
                                 ChatColor notCompletedColor) {
        float percent = (float) current / max;
        int progressBars = (int) (totalBars * percent);

        return Strings.repeat("" + completedColor + symbol, progressBars)
                + Strings.repeat("" + notCompletedColor + symbol, totalBars - progressBars);
    }


    public ItemStack EnchantmentMenu() {
        return (new ItemBuilder(Material.BOOK, 1, 0)).setDisplayname("&e&lEnchantment Books").setLore(new String[]{"", TextHelper.text("&8▪ &a&lLeft-Click &7Open")}).build();
    }

    public ItemStack SettingsMenu() {
        return (new ItemBuilder(Material.REDSTONE_COMPARATOR, 1, 0)).setDisplayname("&c&lSettings").setLore(new String[]{"", TextHelper.text("&8▪ &a&lLeft-Click &7Open")}).build();
    }

    public void confirmPurchase(ItemStack itemStack, Player player,boolean isPackage){
        Inventory inventory;
        if (isPackage){
            inventory = Bukkit.createInventory(null,45,"Confirm Purchase Package");
        } else {
            inventory = Bukkit.createInventory(null,45,"Confirm Purchase");
        }
        for (String lore : itemStack.getItemMeta().getLore()){
            if (lore.contains("Iron")){
                inventory.setItem(20,new ItemBuilder(Material.IRON_INGOT,1,0).setDisplayname("&7Pay with Iron").setLore("",TextHelper.text(lore),"",TextHelper.text("&8▪ &a&lLeft-Click &7Purchase")).build());
            } else if (lore.contains("Gold")){
                inventory.setItem(20,new ItemBuilder(Material.GOLD_INGOT,1,0).setDisplayname("&7Pay with &6Gold").setLore("",TextHelper.text(lore),"",TextHelper.text("&8▪ &a&lLeft-Click &7Purchase")).build());

            } else if (lore.contains("Emerald")){
                inventory.setItem(20,new ItemBuilder(Material.EMERALD,1,0).setDisplayname("&7Pay with &aEmerald").setLore("",TextHelper.text(lore),"",TextHelper.text("&8▪ &a&lLeft-Click &7Purchase")).build());

            } else if (lore.contains("Coins")){
                inventory.setItem(24,new ItemBuilder(Material.DOUBLE_PLANT,1,0).setDisplayname("&7Pay with &eCoins").setLore("",TextHelper.text(lore),"",TextHelper.text("&8▪ &a&lLeft-Click &7Purchase")).build());

            } else if (lore.contains("This book can be found")) return;;
        }
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setLore(null);
        itemStack.setItemMeta(itemMeta);
        inventory.setItem(4,itemStack);
        inventory.setItem(40,BackToMenu());
        player.openInventory(inventory);
    }

    public ItemStack TrailsMenu(Player player) {
        int all = 0;
        if (Main.getInstance().get(player).getSettings().contains("rainbow"))
            all++;
        if (Main.getInstance().get(player).getSettings().contains("magic"))
            all++;
        if (Main.getInstance().get(player).getSettings().contains("heart"))
            all++;
        if (Main.getInstance().get(player).getSettings().contains("flame"))
            all++;
        int percent = all * 100 / 4;
        double b = Math.round(percent * 10.0D) / 10.0D;
        String trail = Main.getInstance().get(player).getTrail();
        String trial = trail.substring(0, 1).toUpperCase() + trail.substring(1);
        ItemStack itemStack = new ItemStack(Material.ARROW);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(TextHelper.text("&e&lTrails"));
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(TextHelper.text("&f&lSelected"));
        lore.add(TextHelper.text("&e" + trial));
        lore.add("");
        lore.add(TextHelper.text("&3&lUnlocked"));
        lore.add(TextHelper.text(TextHelper.text("&8[" + getProgressBar(all, 4, 5, '■', ChatColor.GREEN, ChatColor.GRAY) + "&8] &7" + (int) b) + "%"));
        lore.add("");
        lore.add(TextHelper.text("&8▪ &a&lLeft-Click &7Open"));
        if (!trail.contains("None"))
            lore.add(TextHelper.text("&8▪ &6&lRight-Click &7Clear"));
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public ItemStack PackagesShop() {
        ItemStack itemStack = help.getHead("package5");
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(TextHelper.text("&e&lPackages"));
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(TextHelper.text("&8▪ &a&lLeft-Click &7Open"));
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public ItemStack Enchantment(Enchantment enchantment, int level, int price, String type) {
        ItemStack itemStack = new ItemStack(Material.ENCHANTED_BOOK);
        EnchantmentStorageMeta enchantmentStorageMeta = (EnchantmentStorageMeta) itemStack.getItemMeta();
        enchantmentStorageMeta.addStoredEnchant(enchantment, level, true);
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(LoreShops(price, type));
        lore.add(TextHelper.text("&7or"));
        if (type.equalsIgnoreCase("iron")){
            lore.add(LoreShops(price*2, "Coins"));
        } else if (type.equalsIgnoreCase("gold")){
            lore.add(LoreShops(price*3, "Coins"));
        } else if (type.equalsIgnoreCase("emerald")){
            lore.add(LoreShops(price*20, "Coins"));
        }
        lore.add("");
        lore.add(TextHelper.text("&8▪ &a&lLeft-Click &7Choose"));
        itemStack.setItemMeta((ItemMeta) enchantmentStorageMeta);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
    public ItemStack EnchantmentCant(Enchantment enchantment, int level) {
        ItemStack itemStack = new ItemStack(Material.ENCHANTED_BOOK);
        EnchantmentStorageMeta enchantmentStorageMeta = (EnchantmentStorageMeta) itemStack.getItemMeta();
        enchantmentStorageMeta.addStoredEnchant(enchantment, level, true);
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(TextHelper.text("&7▶ This book can be found"));
        lore.add(TextHelper.text("&7in &nislands&7 for free!"));
        itemStack.setItemMeta((ItemMeta) enchantmentStorageMeta);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public ItemStack KitsMenu(Player player) {
        String kit, kits = Main.getInstance().get(player).getKit();
        if (kits.contains("Super")) {
            kit = "&5Super";
        } else if (kits.contains("Mars")) {
            kit = "&aMars";
        } else if (kits.contains("Diamond")) {
            kit = "&bDiamond";
        } else if (kits.contains("Iron")) {
            kit = "&7Iron";
        } else {
            kit = "&9Member";
        }
        return (new ItemBuilder(Material.ITEM_FRAME, 1, 0)).setDisplayname("&a&lKits").setLore(new String[]{"", TextHelper.text("&f&lSelected"), TextHelper.text(kit), "", TextHelper.text("&8▪ &a&lLeft-Click &7Open")}).build();
    }

    public ItemStack EnderChestMenu(Player player) throws IOException {
        ItemStack itemStack = new ItemStack(Material.ENDER_CHEST);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(TextHelper.text("&5&lEnder Chests"));
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        int iron = 0;
        int gold = 0;
        int emerald = 0;
        if (!Main.getInstance().get(player).getEnderchest1().contains("None"))
            for (ItemStack itemStacks : Main.getInstance().getEnderchests().allItems(Main.getInstance().getEnderchests().fromBase64s(Main.getInstance().get(player).getEnderchest1()))) {
                if (itemStacks == null || itemStacks.getType().equals(Material.AIR))
                    continue;
                if (itemStacks.getType().equals(Material.IRON_INGOT)) {
                    if (itemStacks.getAmount() > 1) {
                        iron += itemStacks.getAmount();
                        continue;
                    }
                    iron++;
                    continue;
                }
                if (itemStacks.getType().equals(Material.GOLD_INGOT)) {
                    if (itemStacks.getAmount() > 1) {
                        gold += itemStacks.getAmount();
                        continue;
                    }
                    gold++;
                    continue;
                }
                if (itemStacks.getType().equals(Material.EMERALD)) {
                    if (itemStacks.getAmount() > 1) {
                        emerald += itemStacks.getAmount();
                        continue;
                    }
                    emerald++;
                }
            }
        if (!Main.getInstance().get(player).getEnderchest2().contains("None"))
            for (ItemStack itemStacks : Main.getInstance().getEnderchests().allItems(Main.getInstance().getEnderchests().fromBase64s(Main.getInstance().get(player).getEnderchest2()))) {
                if (itemStacks == null || itemStacks.getType().equals(Material.AIR))
                    continue;
                if (itemStacks.getType().equals(Material.IRON_INGOT)) {
                    if (itemStacks.getAmount() > 1) {
                        iron += itemStacks.getAmount();
                        continue;
                    }
                    iron++;
                    continue;
                }
                if (itemStacks.getType().equals(Material.GOLD_INGOT)) {
                    if (itemStacks.getAmount() > 1) {
                        gold += itemStacks.getAmount();
                        continue;
                    }
                    gold++;
                    continue;
                }
                if (itemStacks.getType().equals(Material.EMERALD)) {
                    if (itemStacks.getAmount() > 1) {
                        emerald += itemStacks.getAmount();
                        continue;
                    }
                    emerald++;
                }
            }
        if (!Main.getInstance().get(player).getEnderchest3().contains("None"))
            for (ItemStack itemStacks : Main.getInstance().getEnderchests().allItems(Main.getInstance().getEnderchests().fromBase64s(Main.getInstance().get(player).getEnderchest3()))) {
                if (itemStacks == null || itemStacks.getType().equals(Material.AIR))
                    continue;
                if (itemStacks.getType().equals(Material.IRON_INGOT)) {
                    if (itemStacks.getAmount() > 1) {
                        iron += itemStacks.getAmount();
                        continue;
                    }
                    iron++;
                    continue;
                }
                if (itemStacks.getType().equals(Material.GOLD_INGOT)) {
                    if (itemStacks.getAmount() > 1) {
                        gold += itemStacks.getAmount();
                        continue;
                    }
                    gold++;
                    continue;
                }
                if (itemStacks.getType().equals(Material.EMERALD)) {
                    if (itemStacks.getAmount() > 1) {
                        emerald += itemStacks.getAmount();
                        continue;
                    }
                    emerald++;
                }
            }
        if (!Main.getInstance().get(player).getEnderchest4().contains("None"))
            for (ItemStack itemStacks : Main.getInstance().getEnderchests().allItems(Main.getInstance().getEnderchests().fromBase64s(Main.getInstance().get(player).getEnderchest4()))) {
                if (itemStacks == null || itemStacks.getType().equals(Material.AIR))
                    continue;
                if (itemStacks.getType().equals(Material.IRON_INGOT)) {
                    if (itemStacks.getAmount() > 1) {
                        iron += itemStacks.getAmount();
                        continue;
                    }
                    iron++;
                    continue;
                }
                if (itemStacks.getType().equals(Material.GOLD_INGOT)) {
                    if (itemStacks.getAmount() > 1) {
                        gold += itemStacks.getAmount();
                        continue;
                    }
                    gold++;
                    continue;
                }
                if (itemStacks.getType().equals(Material.EMERALD)) {
                    if (itemStacks.getAmount() > 1) {
                        emerald += itemStacks.getAmount();
                        continue;
                    }
                    emerald++;
                }
            }
        if (!Main.getInstance().get(player).getEnderchest5().contains("None"))
            for (ItemStack itemStacks : Main.getInstance().getEnderchests().allItems(Main.getInstance().getEnderchests().fromBase64s(Main.getInstance().get(player).getEnderchest5()))) {
                if (itemStacks == null || itemStacks.getType().equals(Material.AIR))
                    continue;
                if (itemStacks.getType().equals(Material.IRON_INGOT)) {
                    if (itemStacks.getAmount() > 1) {
                        iron += itemStacks.getAmount();
                        continue;
                    }
                    iron++;
                    continue;
                }
                if (itemStacks.getType().equals(Material.GOLD_INGOT)) {
                    if (itemStacks.getAmount() > 1) {
                        gold += itemStacks.getAmount();
                        continue;
                    }
                    gold++;
                    continue;
                }
                if (itemStacks.getType().equals(Material.EMERALD)) {
                    if (itemStacks.getAmount() > 1) {
                        emerald += itemStacks.getAmount();
                        continue;
                    }
                    emerald++;
                }
            }
        if (!Main.getInstance().get(player).getEnderchest6().contains("None"))
            for (ItemStack itemStacks : Main.getInstance().getEnderchests().allItems(Main.getInstance().getEnderchests().fromBase64s(Main.getInstance().get(player).getEnderchest6()))) {
                if (itemStacks == null || itemStacks.getType().equals(Material.AIR))
                    continue;
                if (itemStacks.getType().equals(Material.IRON_INGOT)) {
                    if (itemStacks.getAmount() > 1) {
                        iron += itemStacks.getAmount();
                        continue;
                    }
                    iron++;
                    continue;
                }
                if (itemStacks.getType().equals(Material.GOLD_INGOT)) {
                    if (itemStacks.getAmount() > 1) {
                        gold += itemStacks.getAmount();
                        continue;
                    }
                    gold++;
                    continue;
                }
                if (itemStacks.getType().equals(Material.EMERALD)) {
                    if (itemStacks.getAmount() > 1) {
                        emerald += itemStacks.getAmount();
                        continue;
                    }
                    emerald++;
                }
            }
        if (!Main.getInstance().get(player).getEnderchest7().contains("None"))
            for (ItemStack itemStacks : Main.getInstance().getEnderchests().allItems(Main.getInstance().getEnderchests().fromBase64s(Main.getInstance().get(player).getEnderchest7()))) {
                if (itemStacks == null || itemStacks.getType().equals(Material.AIR))
                    continue;
                if (itemStacks.getType().equals(Material.IRON_INGOT)) {
                    if (itemStacks.getAmount() > 1) {
                        iron += itemStacks.getAmount();
                        continue;
                    }
                    iron++;
                    continue;
                }
                if (itemStacks.getType().equals(Material.GOLD_INGOT)) {
                    if (itemStacks.getAmount() > 1) {
                        gold += itemStacks.getAmount();
                        continue;
                    }
                    gold++;
                    continue;
                }
                if (itemStacks.getType().equals(Material.EMERALD)) {
                    if (itemStacks.getAmount() > 1) {
                        emerald += itemStacks.getAmount();
                        continue;
                    }
                    emerald++;
                }
            }
        if (!Main.getInstance().get(player).getEnderchest8().contains("None"))
            for (ItemStack itemStacks : Main.getInstance().getEnderchests().allItems(Main.getInstance().getEnderchests().fromBase64s(Main.getInstance().get(player).getEnderchest8()))) {
                if (itemStacks == null || itemStacks.getType().equals(Material.AIR))
                    continue;
                if (itemStacks.getType().equals(Material.IRON_INGOT)) {
                    if (itemStacks.getAmount() > 1) {
                        iron += itemStacks.getAmount();
                        continue;
                    }
                    iron++;
                    continue;
                }
                if (itemStacks.getType().equals(Material.GOLD_INGOT)) {
                    if (itemStacks.getAmount() > 1) {
                        gold += itemStacks.getAmount();
                        continue;
                    }
                    gold++;
                    continue;
                }
                if (itemStacks.getType().equals(Material.EMERALD)) {
                    if (itemStacks.getAmount() > 1) {
                        emerald += itemStacks.getAmount();
                        continue;
                    }
                    emerald++;
                }
            }
        if (!Main.getInstance().get(player).getEnderchest9().contains("None"))
            for (ItemStack itemStacks : Main.getInstance().getEnderchests().allItems(Main.getInstance().getEnderchests().fromBase64s(Main.getInstance().get(player).getEnderchest9()))) {
                if (itemStacks == null || itemStacks.getType().equals(Material.AIR))
                    continue;
                if (itemStacks.getType().equals(Material.IRON_INGOT)) {
                    if (itemStacks.getAmount() > 1) {
                        iron += itemStacks.getAmount();
                        continue;
                    }
                    iron++;
                    continue;
                }
                if (itemStacks.getType().equals(Material.GOLD_INGOT)) {
                    if (itemStacks.getAmount() > 1) {
                        gold += itemStacks.getAmount();
                        continue;
                    }
                    gold++;
                    continue;
                }
                if (itemStacks.getType().equals(Material.EMERALD)) {
                    if (itemStacks.getAmount() > 1) {
                        emerald += itemStacks.getAmount();
                        continue;
                    }
                    emerald++;
                }
            }
        lore.add(TextHelper.text("&5♦ &7Iron &f" + iron));
        lore.add(TextHelper.text("&5♦ &6Gold &f" + gold));
        lore.add(TextHelper.text("&5♦ &aEmerald &f" + emerald));
        lore.add("");
        lore.add(TextHelper.text("&8▪ &a&lLeft-Click &7Open"));
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public ItemStack KitViewer(Player player) {
        String kit, kits = Main.getInstance().get(player).getKit();
        if (kits.contains("Super")) {
            kit = "&5Super";
        } else if (kits.contains("Mars")) {
            kit = "&aMars";
        } else if (kits.contains("Diamond")) {
            kit = "&bDiamond";
        } else if (kits.contains("Iron")) {
            kit = "&7Iron";
        } else {
            kit = "&9Member";
        }
        return (new ItemBuilder(Material.IRON_BARDING, 1, 0)).setDisplayname("&7&lKit Viewer").setLore(new String[]{"", TextHelper.text("&f&lSelected"), TextHelper.text(kit), "", TextHelper.text("&8▪ &a&lLeft-Click &7Open")}).build();
    }

    public ItemStack BackToMenu() {
        ItemStack itemStack = help.getHead("back");
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(TextHelper.text("&c&lBack"));
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(TextHelper.text("&7Click here to go back to the"));
        lore.add(TextHelper.text("&7previous page"));
        lore.add("");
        lore.add(TextHelper.text("&8▪ &a&lLeft-Click &7Back"));
        lore.add(TextHelper.text("&8▪ &6&lRight-Click &7Close"));
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public String ClickTo() {
        return ChatColor.translateAlternateColorCodes('&', "&8▪ &a&lLeft-Click &7Purchase");
    }

    public String LoreShop(int price) {
        return ChatColor.translateAlternateColorCodes('&', "&6Cost&8: &a" + price + " &eCoins");
    }

    public String LoreShops(int price, String type) {
        if (type.equalsIgnoreCase("iron"))
            return ChatColor.translateAlternateColorCodes('&', "&6Cost&8: &a" + price + " &7Iron");
        if (type.equalsIgnoreCase("gold"))
            return ChatColor.translateAlternateColorCodes('&', "&6Cost&8: &a" + price + " &6Gold");
        if (type.equalsIgnoreCase("emerald"))
            return ChatColor.translateAlternateColorCodes('&', "&6Cost&8: &a" + price + " &aEmerald");
        if (type.equalsIgnoreCase("coins"))
            return ChatColor.translateAlternateColorCodes('&', "&6Cost&8: &a" + price + " &eCoins");

        return null;
    }
}

package me.firas.skypvp.enderchest;

import me.firas.skypvp.Main;
import me.firas.skypvp.util.ItemBuilder;
import me.firas.skypvp.util.TextHelper;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.*;
import java.util.ArrayList;

import static me.firas.skypvp.generators.help.getHead;

public class EnderChests {


    public String toBase64(Inventory inventory) throws IllegalStateException {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream bukkitObjectOutputStream = new BukkitObjectOutputStream(byteArrayOutputStream);
            bukkitObjectOutputStream.writeInt(inventory.getSize());
            for (int i = 0; i < 45; i++) {
                if (inventory.getItem(i) != null && inventory.getItem(i).equals(new ItemBuilder(Material.STAINED_GLASS_PANE, 1, 14).setDisplayname("&c&lLOCKED").build())) {
                    bukkitObjectOutputStream.writeObject(new ItemStack(Material.AIR));
                    continue;
                }
                bukkitObjectOutputStream.writeObject(inventory.getItem(i));
            }
            bukkitObjectOutputStream.close();
            return Base64Coder.encodeLines(byteArrayOutputStream.toByteArray());
        } catch (Exception exception) {
            throw new IllegalStateException("Unable to save item stacks.", exception);
        }
    }

    public void CheckSettings(Player player, Inventory inventory) {
        Integer numbers = Integer.parseInt(inventory.getTitle().replaceAll("Ender Chest #", ""));
        if (Main.getInstance().get(player).getSettings().contains("EnderChest" + numbers + "=0")) {
            int number = 0;
            while (number <= 44) {
                inventory.setItem(number, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, 14).setDisplayname("&c&lLOCKED").build());
                number++;
            }
        }else if (Main.getInstance().get(player).getSettings().contains("EnderChest" + numbers + "=1")) {
            int number = 9;
            while (number <= 44) {
                inventory.setItem(number, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, 14).setDisplayname("&c&lLOCKED").build());
                number++;
            }
        } else if (Main.getInstance().get(player).getSettings().contains("EnderChest" + numbers + "=2")) {
            int number = 18;
            while (number <= 44) {
                inventory.setItem(number, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, 14).setDisplayname("&c&lLOCKED").build());
                number++;
            }
        } else if (Main.getInstance().get(player).getSettings().contains("EnderChest" + numbers + "=3")) {
            int number = 27;
            while (number <= 44) {
                inventory.setItem(number, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, 14).setDisplayname("&c&lLOCKED").build());
                number++;
            }
        } else if (Main.getInstance().get(player).getSettings().contains("EnderChest" + numbers + "=4")) {
            int number = 36;
            while (number <= 44) {
                inventory.setItem(number, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, 14).setDisplayname("&c&lLOCKED").build());
                number++;
            }
        }

    }
    public ArrayList<ItemStack> allItems(Inventory inventory){
        ArrayList<ItemStack> itemStacks = new ArrayList<>();
        for (ItemStack itemStack : inventory.getContents()){
            itemStacks.add(itemStack);
        }
        return itemStacks;

    }

    public Inventory fromBase64s(String data) throws IOException {
        try {
            BukkitObjectInputStream bukkitObjectInputStream = new BukkitObjectInputStream(new ByteArrayInputStream(Base64Coder.decodeLines(data)));
            Inventory inventory = Bukkit.createInventory(null, bukkitObjectInputStream.readInt(), "EnderChest");
            for (int i = 0; i < 45; i++) {
                inventory.setItem(i, (ItemStack) bukkitObjectInputStream.readObject());
            }
            bukkitObjectInputStream.close();

            return inventory;
        } catch (ClassNotFoundException exception) {
            throw new IOException("Unable to decode class type.", exception);
        }
    }
    public Inventory fromBase64s(String data,String name,int number) throws IOException {
        try {
            BukkitObjectInputStream bukkitObjectInputStream = new BukkitObjectInputStream(new ByteArrayInputStream(Base64Coder.decodeLines(data)));
            Inventory inventory = Bukkit.createInventory(null, bukkitObjectInputStream.readInt(), name+"'s EnderChest #"+number);
            for (int i = 0; i < 45; i++) {
                inventory.setItem(i, (ItemStack) bukkitObjectInputStream.readObject());
            }
            for (int i = 45; i < 54; i++){
                inventory.setItem(i,new ItemBuilder(Material.STAINED_GLASS_PANE,1,15).setDisplayname("&r").build());
            }
            bukkitObjectInputStream.close();
            inventory.setItem(49,new ItemBuilder(Material.STAINED_CLAY,1,5).setDisplayname("&a"+name).setLore(TextHelper.text("&7Save EnderChest")).build());

            return inventory;
        } catch (ClassNotFoundException exception) {
            throw new IOException("Unable to decode class type.", exception);
        }
    }


    public Inventory fromBase64(String inventoryName, String data, Player player) throws IOException {
        try {
            BukkitObjectInputStream bukkitObjectInputStream = new BukkitObjectInputStream(new ByteArrayInputStream(Base64Coder.decodeLines(data)));
            Inventory inventory = Bukkit.createInventory(null, bukkitObjectInputStream.readInt(), inventoryName);

            for (int i = 0; i < 45; i++) {
                inventory.setItem(i, (ItemStack) bukkitObjectInputStream.readObject());
            }
            bukkitObjectInputStream.close();
            inventory.setItem(45, getEnderChest(player,1));
            inventory.setItem(46, getEnderChest(player,2));
            inventory.setItem(47, getEnderChest(player,3));
            inventory.setItem(48, getEnderChest(player,4));
            inventory.setItem(49, getEnderChest(player,5));
            inventory.setItem(50, getEnderChest(player,6));
            inventory.setItem(51,getPremiumEC(player,7));
            inventory.setItem(52,getPremiumEC(player,8));
            inventory.setItem(53,getPremiumEC(player,9));

            if (inventoryName.contains("7") || inventoryName.contains("8") || inventoryName.contains("9")){
                return inventory;
            }
            CheckSettings(player, inventory);
            return inventory;
        } catch (ClassNotFoundException exception) {
            throw new IOException("Unable to decode class type.", exception);
        }
    }

    private ItemStack getPremiumEC(Player player, int enderchest){
        ItemStack itemStack;
        if (enderchest == 7){
            itemStack = getHead("iron");
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName(TextHelper.text("&5Ender Chest #7"));
            ArrayList<String> lore = new ArrayList<>();
            lore.add("");
            if (!player.hasPermission("skypvp.enderchest.7")){
                lore.add(TextHelper.text("&8➥ &cYou need &7Iron"));
            } else {
                lore.add(TextHelper.text("&8▪ &a&lLeft-Click &7Open"));

            }
            itemMeta.setLore(lore);
            itemStack.setItemMeta(itemMeta);
        } else if (enderchest == 8){
            itemStack = getHead("diamond");
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName(TextHelper.text("&5Ender Chest #8"));
            ArrayList<String> lore = new ArrayList<>();
            lore.add("");
            if (!player.hasPermission("skypvp.enderchest.8")){
                lore.add(TextHelper.text("&8➥ &cYou need &bDiamond"));
            } else {
                lore.add(TextHelper.text("&8▪ &a&lLeft-Click &7Open"));

            }
            itemMeta.setLore(lore);
            itemStack.setItemMeta(itemMeta);
        } else if (enderchest == 9) {
            itemStack = getHead("mars");
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName(TextHelper.text("&5Ender Chest #9"));
            ArrayList<String> lore = new ArrayList<>();
            lore.add("");
            if (!player.hasPermission("skypvp.enderchest.9")){
                lore.add(TextHelper.text("&8➥ &cYou need &aMars"));
            } else {
                lore.add(TextHelper.text("&8▪ &a&lLeft-Click &7Open"));

            }
            itemMeta.setLore(lore);
            itemStack.setItemMeta(itemMeta);
        } else {
            itemStack = getHead("super");
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName(TextHelper.text("&5Ender Chest #10"));
            ArrayList<String> lore = new ArrayList<>();
            lore.add("");
            if (!player.hasPermission("skypvp.enderchest.10")){
                lore.add(TextHelper.text("&8➥ &cYou need &5Super"));
            } else {
                lore.add(TextHelper.text("&8▪ &a&lLeft-Click &7Open"));

            }
            itemMeta.setLore(lore);
            itemStack.setItemMeta(itemMeta);
        }
        return itemStack;
    }
    private ItemStack getEnderChest(Player player, int enderchest) {
        ItemStack skull;
        Integer price;
        boolean broken;
        boolean canupgrade = false;
        String levels = null;
        if (Main.getInstance().get(player).getSettings().contains("EnderChest" + enderchest + "=1")) {
            skull = getHead("level1");
            price=100;
            broken=false;
            canupgrade=true;
            levels="✮✩✩✩✩";
        } else if (Main.getInstance().get(player).getSettings().contains("EnderChest" + enderchest + "=2")){
            skull = getHead("level2");
            price=200;
            broken=false;
            canupgrade=true;
            levels="✮✮✩✩✩";

        } else if (Main.getInstance().get(player).getSettings().contains("EnderChest" + enderchest + "=3")){
            skull = getHead("level3");
            price=300;
            broken=false;
            canupgrade=true;
            levels="✮✮✮✩✩";

        } else if (Main.getInstance().get(player).getSettings().contains("EnderChest" + enderchest + "=4")){
            skull = getHead("level4");
            price=450;
            broken=false;
            canupgrade=true;
            levels="✮✮✮✮✩";

        } else if (Main.getInstance().get(player).getSettings().contains("EnderChest" + enderchest + "=5")) {
            skull = getHead("level5");
            price = null;
            broken=false;
            canupgrade=false;
            levels="✮✮✮✮✮";

        }
        else{
            price=50;
            if (enderchest == 1){
                price=0;
            }
            broken=true;
            skull = getHead("level0");

        }
        if (canupgrade || broken) {
            if (enderchest == 2) {
                price *= 2;
            } else if (enderchest == 3) {
                price *= 3;
            } else if (enderchest == 4) {
                price *= 4;
            } else if (enderchest == 5) {
                price *= 5;
            } else if (enderchest == 6) {
                price *= 6;
            } else if (enderchest == 7) {
                price *= 7;
            }
        }
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");

        if (broken){
            lore.add(TextHelper.text("&8&oBroken"));
            lore.add("");

        } else {
            lore.add(TextHelper.text("&b&lLevel"));
            lore.add(TextHelper.text("&e" + levels));
            lore.add("");
        }
        if (price != null) {
            lore.add(TextHelper.text(Main.getInstance().getItemStackAPI().LoreShop(price)));
            lore.add("");
        }
        if (broken){
            lore.add(TextHelper.text("&8▪ &a&lLeft-Click &7Fix"));
        } else {
            lore.add(TextHelper.text("&8▪ &a&lLeft-Click &7Open"));
            if (canupgrade) lore.add(TextHelper.text("&8▪ &6&lRight-Click &7Upgrade"));

        }
        ItemMeta itemMeta = skull.getItemMeta();
        itemMeta.setDisplayName(TextHelper.text("&5Ender Chest #"+enderchest));
        itemMeta.setLore(lore);
        skull.setItemMeta(itemMeta);
        return skull;
    }


    public void openEnderChest(Player player,int number){
        if (number == 1) {
            if (Main.getInstance().get(player).getEnderchest1().equals("None")) {
                Main.getInstance().get(player).addSetting("EnderChest1=1");
                try {
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    BukkitObjectOutputStream bukkitObjectOutputStream = new BukkitObjectOutputStream(byteArrayOutputStream);
                    bukkitObjectOutputStream.writeInt(54);
                    for (int i = 0; i < 45; i++) {
                        bukkitObjectOutputStream.writeObject(new ItemStack(Material.AIR));
                    }
                    bukkitObjectOutputStream.close();

                    Main.getInstance().get(player).setEnderchest1(Base64Coder.encodeLines(byteArrayOutputStream.toByteArray()));
                } catch (Exception exception) {
                    throw new IllegalStateException("Unable to save item stacks.", exception);
                }
            }
            try {
                player.openInventory(fromBase64("Ender Chest #1", Main.getInstance().get(player).getEnderchest1(), player));
            } catch (IOException e) {

                throw new RuntimeException(e);
            }
        }
        if (number == 2) {
            if (Main.getInstance().get(player).getEnderchest2().equals("None")) {
                try {
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    BukkitObjectOutputStream bukkitObjectOutputStream = new BukkitObjectOutputStream(byteArrayOutputStream);
                    bukkitObjectOutputStream.writeInt(54);
                    for (int i = 0; i < 45; i++) {
                        bukkitObjectOutputStream.writeObject(new ItemStack(Material.AIR));
                    }
                    bukkitObjectOutputStream.close();

                    Main.getInstance().get(player).setEnderchest2(Base64Coder.encodeLines(byteArrayOutputStream.toByteArray()));
                } catch (Exception exception) {

                    throw new IllegalStateException("Unable to save item stacks.", exception);
                }
            }
            try {

                player.openInventory(fromBase64("Ender Chest #2", Main.getInstance().get(player).getEnderchest2(), player));

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if (number == 3) {
            if (Main.getInstance().get(player).getEnderchest3().equals("None")) {
                try {
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    BukkitObjectOutputStream bukkitObjectOutputStream = new BukkitObjectOutputStream(byteArrayOutputStream);
                    bukkitObjectOutputStream.writeInt(54);
                    for (int i = 0; i < 45; i++) {
                        bukkitObjectOutputStream.writeObject(new ItemStack(Material.AIR));
                    }
                    bukkitObjectOutputStream.close();

                    Main.getInstance().get(player).setEnderchest3(Base64Coder.encodeLines(byteArrayOutputStream.toByteArray()));
                } catch (Exception exception) {
                    throw new IllegalStateException("Unable to save item stacks.", exception);
                }
            }
            try {
                player.openInventory(fromBase64("Ender Chest #3", Main.getInstance().get(player).getEnderchest3(), player));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if (number == 4) {
            if (Main.getInstance().get(player).getEnderchest4().equals("None")) {
                try {
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    BukkitObjectOutputStream bukkitObjectOutputStream = new BukkitObjectOutputStream(byteArrayOutputStream);
                    bukkitObjectOutputStream.writeInt(54);
                    for (int i = 0; i < 45; i++) {
                        bukkitObjectOutputStream.writeObject(new ItemStack(Material.AIR));
                    }
                    bukkitObjectOutputStream.close();

                    Main.getInstance().get(player).setEnderchest4(Base64Coder.encodeLines(byteArrayOutputStream.toByteArray()));
                } catch (Exception exception) {
                    throw new IllegalStateException("Unable to save item stacks.", exception);
                }
            }
            try {
                player.openInventory(fromBase64("Ender Chest #4", Main.getInstance().get(player).getEnderchest4(), player));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if (number == 5) {
            if (Main.getInstance().get(player).getEnderchest5().equals("None")) {
                try {
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    BukkitObjectOutputStream bukkitObjectOutputStream = new BukkitObjectOutputStream(byteArrayOutputStream);
                    bukkitObjectOutputStream.writeInt(54);
                    for (int i = 0; i < 45; i++) {
                        bukkitObjectOutputStream.writeObject(new ItemStack(Material.AIR));
                    }
                    bukkitObjectOutputStream.close();

                    Main.getInstance().get(player).setEnderchest5(Base64Coder.encodeLines(byteArrayOutputStream.toByteArray()));
                } catch (Exception exception) {
                    throw new IllegalStateException("Unable to save item stacks.", exception);
                }
            }
            try {
                player.openInventory(fromBase64("Ender Chest #5", Main.getInstance().get(player).getEnderchest5(), player));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if (number == 6) {
            if (Main.getInstance().get(player).getEnderchest6().equals("None")) {
                try {
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    BukkitObjectOutputStream bukkitObjectOutputStream = new BukkitObjectOutputStream(byteArrayOutputStream);
                    bukkitObjectOutputStream.writeInt(54);
                    for (int i = 0; i < 45; i++) {
                        bukkitObjectOutputStream.writeObject(new ItemStack(Material.AIR));
                    }
                    bukkitObjectOutputStream.close();

                    Main.getInstance().get(player).setEnderchest6(Base64Coder.encodeLines(byteArrayOutputStream.toByteArray()));
                } catch (Exception exception) {
                    throw new IllegalStateException("Unable to save item stacks.", exception);
                }
            }
            try {
                player.openInventory(fromBase64("Ender Chest #6", Main.getInstance().get(player).getEnderchest6(), player));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if (number == 7){
            if (Main.getInstance().get(player).getEnderchest7().equals("None")) {
                Inventory inventory = Bukkit.createInventory(null, 54, "Ender Chest #7");
                inventory.setItem(45, getEnderChest(player,1));
                inventory.setItem(46, getEnderChest(player,2));
                inventory.setItem(47, getEnderChest(player,3));
                inventory.setItem(48, getEnderChest(player,4));
                inventory.setItem(49, getEnderChest(player,5));
                inventory.setItem(50, getEnderChest(player,6));
                inventory.setItem(51,getPremiumEC(player,7));
                inventory.setItem(52,getPremiumEC(player,8));
                inventory.setItem(53,getPremiumEC(player,9));
                player.openInventory(inventory);
                return;
            }
            try {
                player.openInventory(fromBase64("Ender Chest #7",Main.getInstance().get(player).getEnderchest7(),player));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if (number == 8){
            if (Main.getInstance().get(player).getEnderchest8().equals("None")) {
                Inventory inventory = Bukkit.createInventory(null, 54, "Ender Chest #8");
                inventory.setItem(45, getEnderChest(player,1));
                inventory.setItem(46, getEnderChest(player,2));
                inventory.setItem(47, getEnderChest(player,3));
                inventory.setItem(48, getEnderChest(player,4));
                inventory.setItem(49, getEnderChest(player,5));
                inventory.setItem(50, getEnderChest(player,6));
                inventory.setItem(51,getPremiumEC(player,7));
                inventory.setItem(52,getPremiumEC(player,8));
                inventory.setItem(53,getPremiumEC(player,9));
                player.openInventory(inventory);
                return;
            }
            try {
                player.openInventory(fromBase64("Ender Chest #8",Main.getInstance().get(player).getEnderchest8(),player));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if (number == 9){
            if (Main.getInstance().get(player).getEnderchest9().equals("None")) {
                Inventory inventory = Bukkit.createInventory(null, 54, "Ender Chest #9");
                inventory.setItem(45, getEnderChest(player,1));
                inventory.setItem(46, getEnderChest(player,2));
                inventory.setItem(47, getEnderChest(player,3));
                inventory.setItem(48, getEnderChest(player,4));
                inventory.setItem(49, getEnderChest(player,5));
                inventory.setItem(50, getEnderChest(player,6));
                inventory.setItem(51,getPremiumEC(player,7));
                inventory.setItem(52,getPremiumEC(player,8));
                inventory.setItem(53,getPremiumEC(player,9));

                player.openInventory(inventory);
                return;
            }
            try {
                player.openInventory(fromBase64("Ender Chest #9",Main.getInstance().get(player).getEnderchest9(),player));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

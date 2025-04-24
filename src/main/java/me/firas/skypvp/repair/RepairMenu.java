package me.firas.skypvp.repair;

import com.avaje.ebeaninternal.server.persist.BindValues;
import me.firas.skypvp.Main;
import me.firas.skypvp.util.ItemBuilder;
import me.firas.skypvp.util.TextHelper;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.xml.soap.Text;
import java.util.ArrayList;
import java.util.Arrays;

public class RepairMenu {
    public RepairMenu(Player player){
        int size = 0;
        int armorsize = 0;
        String equipmentArmor = TextHelper.text("&5&oEquipped");
        ArrayList<ItemStack> itemStackArrayList = new ArrayList<>();
        for (ItemStack itemStack : player.getInventory().getArmorContents()){
            if (itemStack == null || itemStack.getType().equals(Material.AIR)){
                continue;
            }
            String itemed = WordUtils.capitalizeFully(itemStack.getType().name().replace("_", " "));
            if (!(itemed.contains("Sword")|| itemed.contains("Axe")||itemed.contains("Helmet")||itemed.contains("Chestplate")||itemed.contains("Leggings")||itemed.contains("Boots")||itemed.contains("Bow")||itemed.contains("Rod"))){
                continue;
            }

            if (itemStack.getDurability() != 0){
                ItemStack itemx = new ItemStack(itemStack);
                int finaldura = itemx.getType().getMaxDurability()-itemx.getDurability();
                int price = itemx.getDurability()/30;
                if (price == 0){
                    price = 1;
                }
                ArrayList<String> lore = new ArrayList<>();
                lore.add(TextHelper.text(equipmentArmor));
                lore.add("");
                lore.add(TextHelper.text("&7(&n"+finaldura+"&7)"));
                lore.add("");
                lore.add(TextHelper.text("&6Cost&8: &a"+price+" &5XP"));
                lore.add("");
                lore.add(TextHelper.text("&8▪ &a&lLeft-Click &7Fix"));
                ItemMeta meta1 = itemx.getItemMeta();
                meta1.setLore(lore);
                itemx.setItemMeta(meta1);
                itemStackArrayList.add(itemx);
                armorsize++;
            }
        }
        for (ItemStack itemStack : player.getInventory()){
            if (itemStack == null || itemStack.getType().equals(Material.AIR)){
                continue;
            }
            String itemed = WordUtils.capitalizeFully(itemStack.getType().name().replace("_", " "));
            if (!(itemed.contains("Sword")|| itemed.contains("Axe")||itemed.contains("Helmet")||itemed.contains("Chestplate")||itemed.contains("Leggings")||itemed.contains("Boots")||itemed.contains("Bow")||itemed.contains("Rod"))){
                continue;
            }
            if (itemStack.getDurability() != 0){
                ItemStack itemx = new ItemStack(itemStack);
                int finaldura = itemx.getType().getMaxDurability()-itemx.getDurability();
                int price = itemx.getDurability()/30;
                if (price == 0){
                    price = 1;
                }
                ArrayList<String> lore = new ArrayList<>();
                lore.add("");
                lore.add(TextHelper.text("&7(&n"+finaldura+"&7)"));
                lore.add("");
                lore.add(TextHelper.text("&6Cost&8: &a"+price+" &5XP"));
                lore.add("");
                lore.add(TextHelper.text("&8▪ &a&lLeft-Click &7Fix"));
                ItemMeta meta1 = itemx.getItemMeta();
                meta1.setLore(lore);
                itemx.setItemMeta(meta1);
                itemStackArrayList.add(itemx);
                size++;
            }
        }
        if (itemStackArrayList.isEmpty()){
            Inventory inventory = Bukkit.createInventory(null,0,"There is no Items to repair");
            player.openInventory(inventory);
            return;
        }
        int finalprice = 0;
        for (ItemStack itemStack : itemStackArrayList) {
            for (String lore : itemStack.getItemMeta().getLore()) {
                if (lore.contains("Cost")) {
                    int price;
                    String Finals = ChatColor.stripColor(lore).replaceAll("[a-zA-Z[_][:][ ][✪]]", "");
                    try {
                        price = Integer.parseInt(Finals);

                    } catch (NumberFormatException es) {
                        player.sendMessage(TextHelper.format("Error!"));
                        player.closeInventory();
                        return;
                    }
                    finalprice += price;
                }
            }
        }
        Inventory inventory;
        if (size == 0){
            inventory = Bukkit.createInventory(null,9,"Repair Menu");
        }
        else if(size <= 9 && size > 0){
            inventory = Bukkit.createInventory(null,18,"Repair Menu");
        } else if (size <= 18){
            inventory = Bukkit.createInventory(null,27,"Repair Menu");
        } else if (size <= 27){
            inventory = Bukkit.createInventory(null,36,"Repair Menu");
        } else if (size <= 36){
            inventory = Bukkit.createInventory(null,45,"Repair Menu");
        } else {
            inventory = Bukkit.createInventory(null,54,"Repair Menu");
        }

        if (inventory.getSize() == 9){
            int x = 0;
            while (x < 9){
                if (inventory.getItem(x) != (null) && !inventory.getItem(x).getType().equals(Material.AIR))
                {
                    x++;
                    continue;
                }
                inventory.setItem(x,new ItemBuilder(Material.STAINED_GLASS_PANE,1,15).setDisplayname("&r").build());
                x++;
            }

            inventory.setItem(8,new ItemBuilder(Material.ANVIL,1,0).setDisplayname("&7Fix all").setLore("",TextHelper.text("&6Cost&8: &a"+finalprice+" &5XP"),"",TextHelper.text("&8▪ &a&lLeft-Click &7Fix all")).build());

        }
        else if (inventory.getSize() == 18){
            int x = 9;
            while (x < 18){
                if (inventory.getItem(x) != (null) && !inventory.getItem(x).getType().equals(Material.AIR))
                {
                    x++;
                    continue;
                }
                inventory.setItem(x,new ItemBuilder(Material.STAINED_GLASS_PANE,1,15).setDisplayname("&r").build());
                x++;
            }
            inventory.setItem(17,new ItemBuilder(Material.ANVIL,1,0).setDisplayname("&7Fix all").setLore("",TextHelper.text("&6Cost&8: &a"+finalprice+" &5XP"),"",TextHelper.text("&8▪ &a&lLeft-Click &7Fix all")).build());

        }
        else if (inventory.getSize() == 27){
            int x = 18;
            while (x < 27){
                if (inventory.getItem(x) != (null) && !inventory.getItem(x).getType().equals(Material.AIR))
                {
                    x++;
                    continue;
                }
                inventory.setItem(x,new ItemBuilder(Material.STAINED_GLASS_PANE,1,15).setDisplayname("&r").build());
                x++;
            }

            inventory.setItem(26,new ItemBuilder(Material.ANVIL,1,0).setDisplayname("&7Fix all").setLore("",TextHelper.text("&6Cost&8: &a"+finalprice+" &5XP"),"",TextHelper.text("&8▪ &a&lLeft-Click &7Fix all")).build());
        } else if (inventory.getSize() == 36){
            int x =27;
            while (x < 36){
                if (inventory.getItem(x) != (null) && !inventory.getItem(x).getType().equals(Material.AIR))
                    {
                        x++;
                        continue;
                    }
                inventory.setItem(x,new ItemBuilder(Material.STAINED_GLASS_PANE,1,15).setDisplayname("&r").build());
                x++;

            }

            inventory.setItem(35,new ItemBuilder(Material.ANVIL,1,0).setDisplayname("&7Fix all").setLore("",TextHelper.text("&6Cost&8: &a"+finalprice+" &5XP"),"",TextHelper.text("&8▪ &a&lLeft-Click &7Fix all")).build());

        } else if (inventory.getSize() == 45){
            int x = 36;
            while (x < 45){
                if (inventory.getItem(x) != (null) && !inventory.getItem(x).getType().equals(Material.AIR))
                {
                    x++;
                    continue;
                }
                inventory.setItem(x,new ItemBuilder(Material.STAINED_GLASS_PANE,1,15).setDisplayname("&r").build());
                x++;
            }

            inventory.setItem(44,new ItemBuilder(Material.ANVIL,1,0).setDisplayname("&7Fix all").setLore("",TextHelper.text("&6Cost&8: &a"+finalprice+" &5XP"),"",TextHelper.text("&8▪ &a&lLeft-Click &7Fix all")).build());

        } else if (inventory.getSize() == 54){
            int x = 45;
            while (x < 54){
                if (inventory.getItem(x) != (null) && !inventory.getItem(x).getType().equals(Material.AIR))
                {
                    x++;
                    continue;
                }
                inventory.setItem(x,new ItemBuilder(Material.STAINED_GLASS_PANE,1,15).setDisplayname("&r").build());
                x++;
            }

            inventory.setItem(53,new ItemBuilder(Material.ANVIL,1,0).setDisplayname("&7Fix all").setLore("",TextHelper.text("&6Cost&8: &a"+finalprice+" &5XP"),"",TextHelper.text("&8▪ &a&lLeft-Click &7Fix all")).build());
        }
        for (ItemStack itemStack : itemStackArrayList){
            if (itemStack.getItemMeta().getLore().contains(equipmentArmor)) {
                String itemed = WordUtils.capitalizeFully(itemStack.getType().name().replace("_", " "));

                if (itemed.contains("Helmet")) {
                    if (inventory.getSize() == 9) {
                        inventory.setItem(0,itemStack);
                    }else if (inventory.getSize() == 18){
                        inventory.setItem(9,itemStack);
                    } else if (inventory.getSize() == 27){
                        inventory.setItem(18,itemStack);
                    } else if (inventory.getSize() == 36){
                        inventory.setItem(27,itemStack);
                    } else if (inventory.getSize() == 45) {
                        inventory.setItem(36, itemStack);
                    }
                    else{
                        inventory.setItem(45, itemStack);
                    }
                    size--;
                    continue;
                }
                if (itemed.contains("Chestplate")) {
                    if (inventory.getSize() == 9) {
                        inventory.setItem(1,itemStack);
                    }else if (inventory.getSize() == 18) {
                        inventory.setItem(10, itemStack);
                    } else if (inventory.getSize() == 27) {
                        inventory.setItem(19, itemStack);
                    } else if (inventory.getSize() == 36) {
                        inventory.setItem(28, itemStack);
                    } else if (inventory.getSize() == 45) {
                        inventory.setItem(37, itemStack);
                    } else {
                        inventory.setItem(46, itemStack);
                    }
                    size--;

                    continue;
                }
                if (itemed.contains("Leggings")) {
                    if (inventory.getSize() == 9) {
                        inventory.setItem(2,itemStack);
                    }else if (inventory.getSize() == 18) {
                        inventory.setItem(11, itemStack);
                    } else if (inventory.getSize() == 27) {
                        inventory.setItem(20, itemStack);
                    } else if (inventory.getSize() == 36) {
                        inventory.setItem(29, itemStack);
                    } else if (inventory.getSize() == 45) {
                        inventory.setItem(38, itemStack);
                    } else {
                        inventory.setItem(47, itemStack);
                    }
                    size--;

                    continue;
                }
                if (itemed.contains("Boots")){

                    if (inventory.getSize() == 9) {
                        inventory.setItem(3,itemStack);
                    }else if (inventory.getSize() == 18) {
                        inventory.setItem(12, itemStack);
                    } else if (inventory.getSize() == 27) {
                        inventory.setItem(21, itemStack);
                    } else if (inventory.getSize() == 36) {
                        inventory.setItem(30, itemStack);
                    } else if (inventory.getSize() == 45) {
                        inventory.setItem(39, itemStack);
                    } else {
                        inventory.setItem(48, itemStack);
                    }
                    size--;
                    continue;
                }
            }

            inventory.addItem(itemStack);
        }
        player.openInventory(inventory);


    }
}

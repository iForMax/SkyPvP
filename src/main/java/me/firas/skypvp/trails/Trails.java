package me.firas.skypvp.trails;

import me.firas.skypvp.Main;
import me.firas.skypvp.util.TextHelper;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class Trails {
    public Trails(Player player){
        Inventory inventory = Bukkit.createInventory(null,36,"Trails");
        inventory.setItem(31,Main.getInstance().getItemStackAPI().BackToMenu());
        inventory.setItem(10,trials(player,Material.RED_ROSE,1500,"heart","Heart","&7Shows some hearts","&7on shoot."));
        inventory.setItem(12,trials(player,Material.BLAZE_POWDER,1000,"flame","Flame","&7Shows some flame","&7on shoot."));
        inventory.setItem(14,trials(player,Material.MAGMA_CREAM,1000,"magic","Magic","&7Shows some magic","&7particle on shoot."));

        inventory.setItem(16,trials(player,Material.REDSTONE,2000,"rainbow","Rainbow","&7Shows some rainbow","&7on shoot."));
        player.openInventory(inventory);

    }

    public ItemStack trials(Player player, Material material, int price, String name, String itemStackName, String... lore){
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(TextHelper.text("&c&l"+itemStackName));
        ArrayList<String> lores = new ArrayList<>();
        for (String lored : lore){
            lores.add(TextHelper.text(lored));
        }
        lores.add(TextHelper.text(""));
        if (Main.getInstance().get(player).getSettings().contains(name)){
            itemMeta.setDisplayName(TextHelper.text("&a&l"+itemStackName));

            if (Main.getInstance().get(player).getTrail().equals(name)){
                itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                itemMeta.addEnchant(Enchantment.DURABILITY,1,true);
                lores.add(TextHelper.text("&8▪ &c&lSELECTED"));
                lores.add(TextHelper.text("&8▪ &a&lLeft-Click &7Remove"));
            } else {
                lores.add(TextHelper.text("&8▪ &a&lLeft-Click &7Select"));
            }
        } else {
            lores.add(TextHelper.text(Main.getInstance().getItemStackAPI().LoreShop(price)));
            lores.add("");
            lores.add(TextHelper.text("&8▪ &a&lLeft-Click &7Purchase"));
        }
        itemMeta.setLore(lores);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}

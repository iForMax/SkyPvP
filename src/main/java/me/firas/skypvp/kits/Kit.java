package me.firas.skypvp.kits;

import me.firas.skypvp.Main;
import me.firas.skypvp.enums.KitType;
import me.firas.skypvp.util.ItemBuilder;
import me.firas.skypvp.util.ParticleEffect;
import me.firas.skypvp.util.TextHelper;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class Kit {

    public void spawnDrops(ItemStack[] itemStacks,ItemStack[] itemStackss){
        Random random = new Random();
        int ran = random.nextInt(4)+1;
        Location location = Main.getInstance().getLocationHandler().getLocation("drop-"+ran);
        int number = 0;
        for (ItemStack itemStack : itemStacks){
            if (itemStack == null) continue;
            if (itemStack.getItemMeta() == null) continue;
            if (itemStack.getItemMeta().hasDisplayName() && !itemStack.getItemMeta().getDisplayName().contains("Package")) continue;
            Bukkit.getWorld(Main.getInstance().World).dropItem(location, itemStack);
            number++;
        }
        for (ItemStack itemStack : itemStackss){
            if (itemStack == null) continue;
            if (itemStack.getItemMeta() == null) continue;
            if (itemStack.getItemMeta().hasDisplayName() && !itemStack.getItemMeta().getDisplayName().contains("Package")) continue;
            Bukkit.getWorld(Main.getInstance().World).dropItem(location, itemStack);
            number++;
        }
        if (number==0){
            return;
        }
        ParticleEffect.CLOUD.display(0,0,0,0.4F,20,location,25);
        ParticleEffect.SMOKE_LARGE.display(0,0,0,0.4F,20,location,25);
        Bukkit.getWorld(Main.getInstance().World).playSound(location,Sound.FIREWORK_BLAST,1,2);
    }

    public void grantKit(Player player, KitType kitType) {
        if (kitType.equals(KitType.Iron)){
            if (!player.hasPermission("skypvp.kit.iron")){
                Main.getInstance().get(player).setKit("MEMBER");
                grantKit(player,KitType.MEMBER);
                return;
            }
        }
        if (kitType.equals(KitType.Diamond)){
            if (!player.hasPermission("skypvp.kit.diamond")){
                Main.getInstance().get(player).setKit("MEMBER");
                grantKit(player,KitType.MEMBER);
                return;
            }
        }
        if (kitType.equals(KitType.Mars)){
            if (!player.hasPermission("skypvp.kit.mars")){
                Main.getInstance().get(player).setKit("MEMBER");
                grantKit(player,KitType.MEMBER);
                return;
            }
        }
        if (kitType.equals(KitType.Super)){
            if (!player.hasPermission("skypvp.kit.super")){
                Main.getInstance().get(player).setKit("MEMBER");
                grantKit(player,KitType.MEMBER);
                return;
            }
        }
        player.getInventory().setArmorContents(kitType.getArmour());
        for (ItemStack itemStack : kitType.getTools()) {
            if (itemStack.getType().equals(Material.ARROW)){
                player.getInventory().setItem(8,itemStack);
                continue;
            }
            player.getInventory().addItem(itemStack);
        }
        Main.getInstance().get(player).setKit(kitType.name());
    }

    public ItemStack KitSelected(Player player, String permission, String name, Integer price, Material item, Color color){
        if (permission == null){
            if (Main.getInstance().get(player).getKit().equalsIgnoreCase(ChatColor.stripColor(name))){
                return new ItemBuilder(item,1,0).setDisplayname(name).setLore("",TextHelper.text("&7It shows you content"),TextHelper.text("&7of "+name+" &7kit"),"",TextHelper.text("&8▪ &c&lSELECTED"),TextHelper.text("&8▪ &6&lRight-Click &7Preview")).addHiddenEnchantment().addLeatherColor(color).build();
            }
            return new ItemBuilder(item,1,0).setDisplayname(name).setLore("",TextHelper.text("&7It shows you content"),TextHelper.text("&7of "+name+" &7kit"),"",TextHelper.text("&8▪ &a&lLeft-Click &7Select"),TextHelper.text("&8▪ &6&lRight-Click &7Preview")).addLeatherColor(color).build();
        }
        if (!player.hasPermission(permission)){
            if (price != null) {
                if (!Main.getInstance().get(player).getSettings().contains("Iron")) {
                    return new ItemBuilder(item,1,0).setDisplayname(name).setLore("",Main.getInstance().getItemStackAPI().LoreShop(2500),"",TextHelper.text("&8▪ &a&lLeft-Click &7Purchase"),TextHelper.text("&8▪ &6&lRight-Click &7Preview")).addLeatherColor(color).build();
                } if (Main.getInstance().get(player).getKit().equalsIgnoreCase(ChatColor.stripColor(name))) {
                    return new ItemBuilder(item,1,0).setDisplayname(name).setLore("",TextHelper.text("&7It shows you content"),TextHelper.text("&7of "+name+" &7kit"),"",TextHelper.text("&8▪ &c&lSELECTED"),TextHelper.text("&8▪ &6&lRight-Click &7Preview")).addLeatherColor(color).addHiddenEnchantment().build();

                }
                return new ItemBuilder(item,1,0).setDisplayname(name).setLore("",TextHelper.text("&7It shows you content"),TextHelper.text("&7of "+name+" &7kit"),"",TextHelper.text("&8▪ &a&lLeft-Click &7Select"),TextHelper.text("&8▪ &6&lRight-Click &7Preview")).addLeatherColor(color).build();

            }
            return new ItemBuilder(item,1,0).setDisplayname(name).setLore("",TextHelper.text("&7It shows you content"),TextHelper.text("&7of "+name+" &7kit"),"",TextHelper.text("&8▪ &c&lPREMIUM FEATURE"),TextHelper.text("&8▪ &6&lRight-Click &7Preview")).addLeatherColor(color).build();
        }
        if (Main.getInstance().get(player).getKit().equalsIgnoreCase(ChatColor.stripColor(name))){
            return new ItemBuilder(item,1,0).setDisplayname(name).setLore("",TextHelper.text("&7It shows you content"),TextHelper.text("&7of "+name+" &7kit"),"",TextHelper.text("&8▪ &c&lSELECTED"),TextHelper.text("&8▪ &6&lRight-Click &7Preview")).addHiddenEnchantment().addLeatherColor(color).build();
        }
        return new ItemBuilder(item,1,0).setDisplayname(name).setLore("",TextHelper.text("&7It shows you content"),TextHelper.text("&7of "+name+" &7kit"),"",TextHelper.text("&8▪ &a&lLeft-Click &7Select"),TextHelper.text("&8▪ &6&lRight-Click &7Preview")).addLeatherColor(color).build();

    }
}

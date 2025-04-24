package me.firas.skypvp.kits;

import me.firas.skypvp.Main;
import me.firas.skypvp.enums.KitType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class KitView {

    Inventory inventory = Bukkit.createInventory(null,36,"Kit Viewer (Click)");

    public KitView(Player player){
        if (Main.getInstance().get(player).getKit().contains("MEMBER")){
            int xd = 10;
            for (ItemStack item : KitType.MEMBER.getMemberKit()){
                inventory.setItem(xd,item);
                xd++;
            }
        }
        else if (Main.getInstance().get(player).getKit().contains("Iron")){
            int xd = 10;
            for (ItemStack item : KitType.Iron.getIronKit()){
                inventory.setItem(xd,item);
                xd++;
            }
        }
        else if (Main.getInstance().get(player).getKit().contains("Diamond")){
            int xd = 10;
            for (ItemStack item : KitType.Diamond.getDiamondKit()){
                inventory.setItem(xd,item);
                xd++;
            }
        }
        else if (Main.getInstance().get(player).getKit().contains("Mars")){
            int xd = 10;
            for (ItemStack item : KitType.Mars.getMarsKit()){
                inventory.setItem(xd,item);
                xd++;
            }
        }
        else if (Main.getInstance().get(player).getKit().contains("Super")){
            int xd = 10;
            for (ItemStack item : KitType.Super.getSuperKit()){
                inventory.setItem(xd,item);
                xd++;
            }
        } else {
            int xd = 10;
            for (ItemStack item : KitType.MEMBER.getMemberKit()){
                inventory.setItem(xd,item);
                xd++;
            }
        }
        inventory.setItem(31,Main.getInstance().getItemStackAPI().BackToMenu());
        player.openInventory(inventory);
    }
}

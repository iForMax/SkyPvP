package me.firas.skypvp.spectate.system;

import me.firas.skypvp.util.ItemBuilder;
import me.firas.skypvp.util.TextHelper;
import org.bukkit.ChatColor;
import org.bukkit.Material;

public abstract class PaginatedMenu extends Menu {

    //Keep track of what page the menu is on
    protected int page = 0;
    //28 is max items because with the border set below,
    //28 empty slots are remaining.
    protected int maxItemsPerPage = 27;
    //the index represents the index of the slot
    //that the loop is on
    protected int index = 0;


    public int getPage(){
        return page+1;
    }






    public PaginatedMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    //Set the border and menu buttons for the menu
    public void addMenuBorder(){
        inventory.setItem(30, new ItemBuilder(Material.ARROW,1,0).setDisplayname("&aPrevious Page").setLore(TextHelper.text("&7Page "+(getPage()-1))).build());
        inventory.setItem(31, makeItem(Material.BARRIER, ChatColor.RED.toString() + ChatColor.BOLD + "Close"));
        inventory.setItem(32, new ItemBuilder(Material.ARROW,1,0).setDisplayname("&aNext Page").setLore(TextHelper.text("&7Page "+(getPage()+1))).build());

    }

    public int getMaxItemsPerPage() {
        return maxItemsPerPage;
    }

}

package me.firas.skypvp.commands;

import me.firas.skypvp.Main;
import me.firas.skypvp.util.ItemBuilder;
import me.firas.skypvp.util.TextHelper;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class Region implements CommandExecutor {
    private final HashMap<Player, String> regionCreate = new HashMap<>();
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (!(commandSender instanceof Player)) return true;
        Player player = (Player) commandSender;
        if (!player.hasPermission("regions.use")){
            return true;
        }
        if (args.length != 1){
            player.sendMessage(TextHelper.format("&7/region <name>"));
            player.sendMessage(TextHelper.format("&7/region save"));
            return true;
        }
        if (args[0].equalsIgnoreCase("save")){
            if (Main.getInstance().getRegions().getPos1(regionCreate.get(player)) == null) {
                player.sendMessage(TextHelper.format("&cPos-1 not set!"));
                return true;
            }
            if (Main.getInstance().getRegions().getPos2(regionCreate.get(player)) == null) {
                player.sendMessage(TextHelper.format("&cPos-2 not set!"));
                return true;
            }
            Main.getInstance().getRegions().createRegion(regionCreate.get(player));
            player.sendMessage(TextHelper.format("&7Region &a"+regionCreate.get(player) +" &7saved"));
            player.getInventory().remove(Material.STICK);
            regionCreate.remove(player);
            return true;
        }
        if (regionCreate.containsKey(player)){
            regionCreate.remove(player);
            player.sendMessage(TextHelper.format("&7Left region creating mode!"));
            player.getInventory().remove(Material.STICK);
            return true;
        }
        String name = args[0];
        regionCreate.put(player, name);
        player.sendMessage(TextHelper.format("&7Entered region creating mode!"));
        player.sendMessage(TextHelper.format("&c/region&7 to leave"));
        player.getInventory().addItem(new ItemBuilder(Material.STICK, 1, 0).setDisplayname(TextHelper.format("&eWand &7["+name+"]")).build());
        return false;
    }
}

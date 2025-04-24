package me.firas.skypvp.commands;

import me.firas.skypvp.util.TextHelper;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

public class Invsee implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player player = (Player) sender;
        String name = player.getName();
        if (name.equals("NaifAlmalki") | name.equals("Webmasters") | name.equals("IchBinKlauss")| name.equals("Fahad_b")){
            if (args.length != 1){
                player.sendMessage(TextHelper.format("&cUsage: /invsee [Name]"));
                return true;
            }
            if (Bukkit.getPlayerExact(args[0]) == null){
                player.sendMessage(TextHelper.format("&7"+args[0]+" &cis not online!"));
                return true;
            }
            PlayerInventory inventory = Bukkit.getPlayerExact(args[0]).getInventory();
            player.openInventory(inventory);
            return true;
        }
        player.performCommand("/unknownCommand");

        return false;
    }
}

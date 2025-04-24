package me.firas.skypvp.commands;

import me.firas.skypvp.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Vote implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player player = (Player) sender;
        if (!player.hasPermission("skypvp.vote")){
            player.performCommand("/unknownCommand");
            return true;
        }
        Main.getInstance().getEvents().openEvent(player);
        return false;
    }
}

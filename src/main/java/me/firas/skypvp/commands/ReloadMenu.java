package me.firas.skypvp.commands;

import me.firas.skypvp.generators.menu.MenuManager;
import me.firas.skypvp.util.TextHelper;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReloadMenu implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player player = (Player) sender;
        if (player.hasPermission("reload.menu")){
            MenuManager.reloadStartUp();
            player.sendMessage(TextHelper.format("&aSuccessfully reload &bMenu&a!"));
        } else {
            player.performCommand("/unknownCommand");
            return true;
        }
        return false;
    }
}

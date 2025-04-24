package me.firas.skypvp.commands;

import me.firas.skypvp.Main;
import me.firas.skypvp.util.TextHelper;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetLocation implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (!(commandSender instanceof Player)) return true;
        Player p = (Player) commandSender;
        if (!p.hasPermission("skypvp.setlocation")) {
            p.sendMessage(TextHelper.noPerm("skypvp.setlocation"));
            return true;
        }
        if (args.length != 1) {
            p.sendMessage(TextHelper.text("&f/setlocation [name]"));
            return true;
        }
        String name = args[0];
        Main.getInstance().getLocationHandler().setLocation(name, p.getLocation());
        p.sendMessage(TextHelper.text("Location&6 " + args[0] + " &fhas been set!"));
        return false;
    }
}


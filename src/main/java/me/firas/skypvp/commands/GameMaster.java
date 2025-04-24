package me.firas.skypvp.commands;

import me.firas.skypvp.Main;
import me.firas.skypvp.util.TextHelper;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GameMaster implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player player = (Player) sender;
        if (Main.getInstance().GameMaster.contains(player)){
            Main.getInstance().GameMaster.remove(player);
            player.sendMessage(TextHelper.format("&cYou are no longer in &4Game Master"));
            return true;
        }
        Main.getInstance().GameMaster.add(player);
        player.sendMessage(TextHelper.format("&aYou are now in &4Game Master"));
        return false;
    }
}

package me.firas.skypvp.commands;

import me.firas.skypvp.Main;
import me.firas.skypvp.util.TextHelper;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class Stats implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {return true;}
        Player player = (Player) sender;
        if (args.length == 1){
            if (player.isOp()){
                if (Bukkit.getPlayer(args[0]) == null){
                    player.sendMessage(TextHelper.format("&7"+args[0]+" &cis not online!"));
                    return true;
                }
                Player player1 = Bukkit.getPlayer(args[0]);
                player.sendMessage(TextHelper.format("&cStats of "+player1.getDisplayName()));
                player.sendMessage(TextHelper.text(" &7Prestige &8» &a"+ Main.getInstance().get(player1).getPrestige()));
                player.sendMessage(TextHelper.text(" &7Points &8» &a"+ Main.getInstance().get(player1).getPoints()));

                player.sendMessage(TextHelper.text(" &7K/D &8» &a"+Main.getInstance().get(player1).kdr()));
                player.sendMessage(TextHelper.text(" &7Kills &8» &a"+Main.getInstance().get(player1).getKills()));
                player.sendMessage(TextHelper.text(" &7Killstreak &8» &a"+Main.getInstance().get(player1).getStreak()));
                player.sendMessage(TextHelper.text(" &7Death &8» &a"+Main.getInstance().get(player1).getDeaths()));
                player.sendMessage(TextHelper.text(" &7Coins &8» &a"+Main.getInstance().get(player1).getCoins()));
                player.sendMessage(TextHelper.text(" &8&m----------------------------"));
                return true;
            } else {
                player.sendMessage(TextHelper.format("&cStats of "+player.getDisplayName()));
                player.sendMessage(TextHelper.text(" &7Prestige &8» &a"+ Main.getInstance().get(player).getPrestige()));
                player.sendMessage(TextHelper.text(" &7Points &8» &a"+ Main.getInstance().get(player).getPoints()));

                player.sendMessage(TextHelper.text(" &7K/D &8» &a"+Main.getInstance().get(player).kdr()));
                player.sendMessage(TextHelper.text(" &7Kills &8» &a"+Main.getInstance().get(player).getKills()));
                player.sendMessage(TextHelper.text(" &7Killstreak &8» &a"+Main.getInstance().get(player).getStreak()));
                player.sendMessage(TextHelper.text(" &7Death &8» &a"+Main.getInstance().get(player).getDeaths()));
                player.sendMessage(TextHelper.text(" &7Coins &8» &a"+Main.getInstance().get(player).getCoins()));
                player.sendMessage(TextHelper.text(" &8&m----------------------------"));
            }
        }
        player.sendMessage(TextHelper.format("&cStats of "+player.getDisplayName()));
        player.sendMessage(TextHelper.text(" &7Prestige &8» &a"+ Main.getInstance().get(player).getPrestige()));
        player.sendMessage(TextHelper.text(" &7Points &8» &a"+ Main.getInstance().get(player).getPoints()));
        player.sendMessage(TextHelper.text(" &7K/D &8» &a"+Main.getInstance().get(player).kdr()));
        player.sendMessage(TextHelper.text(" &7Kills &8» &a"+Main.getInstance().get(player).getKills()));
        player.sendMessage(TextHelper.text(" &7Killstreak &8» &a"+Main.getInstance().get(player).getStreak()));
        player.sendMessage(TextHelper.text(" &7Death &8» &a"+Main.getInstance().get(player).getDeaths()));
        player.sendMessage(TextHelper.text(" &7Coins &8» &a"+Main.getInstance().get(player).getCoins()));
        player.sendMessage(TextHelper.text(" &8&m----------------------------"));

        return false;
    }
}

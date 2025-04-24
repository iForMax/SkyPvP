package me.firas.skypvp.commands;

import me.firas.skypvp.Main;
import me.firas.skypvp.util.TextHelper;
import me.firas.skypvp.util.Title;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Build implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player player = (Player) sender;
        if (!(player.hasPermission("esx.build"))){
            player.performCommand("/unknownCommand");
            return true;
        }
        if (args.length != 1) {
            if (Main.getInstance().build.contains(player)) {
                Main.getInstance().build.remove(player);
                leaveBuild(player);
                return true;
            }
            Main.getInstance().build.add(player);
            joinBuild(player);
            return true;
        }
        if (args[0].equals("list")){
            if (Main.getInstance().build.isEmpty()){
                player.sendMessage(TextHelper.format("&cThere is no one in Build mode!"));
                return true;
            }
            player.sendMessage(TextHelper.format("&8&m----------------------"));
            for (Player player1 : Main.getInstance().build){
                player.sendMessage(TextHelper.format("&7- &a"+player1.getDisplayName()));
            }
            player.sendMessage(TextHelper.format("&8&m----------------------"));
            return true;
        }
        Player argplayer = Bukkit.getPlayer(args[0]);
        if (argplayer == null){
            player.sendMessage(TextHelper.format("&c"+args[0]+" is not online"));
            return true;
        }
        if (Main.getInstance().build.contains(argplayer)){
            player.sendMessage(TextHelper.format("&e"+argplayer.getDisplayName()+" &cis no longer in Build mode"));
            leaveBuild(argplayer);
            Main.getInstance().build.remove(argplayer);
            return true;
        }
        player.sendMessage(TextHelper.format("&e"+argplayer.getDisplayName()+" &ais now in Build mode"));
        joinBuild(argplayer);
        Main.getInstance().build.add(argplayer);
        return false;
    }

    public void leaveBuild(Player player){
        player.sendMessage(TextHelper.format("&cYou are no longer in Build mode"));
        new Title("","&c&lYou are no longer in Build mode",0,30,20).send(player);
        player.setGameMode(GameMode.SURVIVAL);
        player.teleport(Main.getInstance().getLocationHandler().getLocation("spawn"));
        player.playSound(player.getLocation(), Sound.NOTE_BASS,1,1);

    }
    public void joinBuild(Player player){
        player.sendMessage(TextHelper.format("&aYou are now in Build mode"));
        player.playSound(player.getLocation(), Sound.NOTE_PLING,1,2);
        new Title("","&a&lYou are now in Build mode",0,30,20).send(player);
        player.setGameMode(GameMode.CREATIVE);
    }
}

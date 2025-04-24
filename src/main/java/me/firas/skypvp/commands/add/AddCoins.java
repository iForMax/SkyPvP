package me.firas.skypvp.commands.add;

import me.firas.skypvp.Main;
import me.firas.skypvp.settings.Settings;
import me.firas.skypvp.util.Cooldown;
import me.firas.skypvp.util.TextHelper;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class AddCoins implements CommandExecutor {
    private final Cooldown cooldown = Cooldown.builder().duration(3).name("addCL").cooldowns(new HashMap<>()).build();
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (!(commandSender instanceof Player)) return true;
        Player player = (Player) commandSender;
        if (cooldown.isOnCooldown(player.getUniqueId())) {
            player.sendMessage(TextHelper.text("&fPlease wait few seconds..."));
            return true;
        }
        cooldown.addCooldown(player.getUniqueId());
        if (!player.hasPermission("add.coins")){
            player.sendMessage(TextHelper.noPerm("add.coins"));
            return true;
        }
        if (args.length == 1){
            String amount = args[0];
            if (!isInt(amount)){
                player.sendMessage(TextHelper.text("&cThat's not a number!"));
                return true;
            }
            Main.getInstance().get(player).addStats("coins", Integer.parseInt(amount));
            player.sendMessage(TextHelper.format("&aAdded &7"+amount+"&a coins to &e"+player.getName()));
            return true;
        }
        if (args.length == 2){
            Player argplayer = Bukkit.getPlayer(args[0]);
            String amount = args[1];
            if (argplayer == null || !argplayer.isOnline()){
                player.sendMessage(TextHelper.format("&cPlayer not found"));
                return true;
            }
            if (!isInt(amount)){
                player.sendMessage(TextHelper.format("&cThat's not a number!"));
                return true;
            }
            Main.getInstance().get(argplayer).addStats("coins", Integer.parseInt(amount));
            player.sendMessage(TextHelper.format("&aAdded &7"+amount+"&a coins to &6"+argplayer.getName()));
            return true;
        }
        player.sendMessage(TextHelper.format("&c/addcoins <amount>"));
        player.sendMessage(TextHelper.format("&c/addcoins <player> <amount>"));
        return false;
    }
    private boolean isInt(String s)
    {
        try
        { int i = Integer.parseInt(s); return true; }

        catch(NumberFormatException er)
        { return false; }
    }
}

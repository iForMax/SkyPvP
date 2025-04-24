package me.firas.skypvp.commands.add;

import me.firas.skypvp.Main;
import me.firas.skypvp.quests.menus.Kills;
import me.firas.skypvp.util.Cooldown;
import me.firas.skypvp.util.TextHelper;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class AddAchievement implements CommandExecutor {
    private final Cooldown cooldown = Cooldown.builder().duration(3).name("addCL").cooldowns(new HashMap<>()).build();

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (!(commandSender instanceof Player)) return true;
        Player player = (Player) commandSender;

        if (!player.hasPermission("add.achievement")){
            player.sendMessage(TextHelper.noPerm("add.achievement"));
            return true;
        }
        if (args.length == 2){
            String amount = args[1];
            if (!args[0].equalsIgnoreCase("kills")){

            }
            if (!isInt(amount)){
                player.sendMessage(TextHelper.format("&cThat's not a number!"));
                return true;
            }
            Main.getInstance().get(player).addStats("Achiv"+args[0], Integer.parseInt(amount));
            Main.getInstance().getQuest().updateAchievementKills(player);
            return true;
        }
        player.sendMessage(TextHelper.format("&c/addachievement <achievement> <amount>"));
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

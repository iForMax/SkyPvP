package me.firas.skypvp.commands;

import me.firas.skypvp.Main;
import me.firas.skypvp.util.Cooldown;
import me.firas.skypvp.util.TextHelper;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class Scramble implements CommandExecutor {
    private final Cooldown cooldown = Cooldown.builder().duration(3).name("Scramble").cooldowns(new HashMap<>()).build();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player player = (Player) sender;
        if (cooldown.isOnCooldown(player.getUniqueId())){
            player.sendMessage(TextHelper.format("&fPlease wait few seconds..."));
            return true;
        }
        cooldown.addCooldown(player.getUniqueId());
        if (!player.hasPermission("skypvp.scramble")){
            player.sendMessage(TextHelper.format("&cYou don't have a permission."));
            return true;
        }
        if (Main.getInstance().get(player).getScramble().equals("true")){
            Main.getInstance().get(player).setScramble("false");
            player.sendMessage(TextHelper.format("&aEveryone can see your prestige."));

            return true;
        } else {
            Main.getInstance().get(player).setScramble("true");
            player.sendMessage(TextHelper.format("&eNo one can see your prestige."));
            return true;
        }
    }
}

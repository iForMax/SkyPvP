package me.firas.skypvp.commands;

import me.firas.skypvp.Main;
import me.firas.skypvp.enums.PrestigeType;
import me.firas.skypvp.util.Cooldown;
import me.firas.skypvp.util.TextHelper;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class Ranks implements CommandExecutor {
    private final Cooldown cooldown = Cooldown.builder().duration(3).name("Ranks").cooldowns(new HashMap<>()).build();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }
        Player player = (Player) sender;
        if (cooldown.isOnCooldown(player.getUniqueId())){
            player.sendMessage(TextHelper.format("&fPlease wait few seconds..."));
            return true;
        }
        cooldown.addCooldown(player.getUniqueId());
        String selected = "&7(&a✔&7)";
        player.sendMessage(TextHelper.format("&8&m-------------------------------"));
        player.sendMessage(TextHelper.text("&8&l▏ &fRanks are ordered by &aPoints"));
        for (PrestigeType rank : PrestigeType.values()) {
            int kdr = (int) rank.getPoints();
            if (Main.getInstance().get(player).getPrestige().equals(rank.getName())) {
                player.sendMessage(TextHelper.text("&8&l▏ "+rank.getName() + " &8» &f"+kdr + " "+selected));
                continue;
            }
                player.sendMessage(TextHelper.text("&8&l▏ "+rank.getName() + " &8» &f"+kdr+""));
        }

        return false;
    }
}

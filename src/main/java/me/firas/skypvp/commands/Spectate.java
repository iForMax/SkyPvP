package me.firas.skypvp.commands;

import dev.mqzen.boards.BoardManager;
import me.firas.skypvp.Main;
import me.firas.skypvp.regions.Region;
import me.firas.skypvp.scoreboard.Board;
import me.firas.skypvp.util.Cooldown;
import me.firas.skypvp.util.ItemBuilder;
import me.firas.skypvp.util.TextHelper;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.List;

import static me.firas.skypvp.spectate.SpectateMode.JoinSpectate;
import static me.firas.skypvp.spectate.SpectateMode.LeaveSpectate;

public class Spectate implements CommandExecutor {
    private final Cooldown cooldown = Cooldown.builder().duration(3).name("spectates").cooldowns(new HashMap<>()).build();




    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player player = (Player) sender;
        if (cooldown.isOnCooldown(player.getUniqueId())){
            player.sendMessage(TextHelper.format("&fPlease wait few seconds..."));
            return true;
        }
        cooldown.addCooldown(player.getUniqueId());
        if (Main.getInstance().build.contains(player)){
            player.sendMessage(TextHelper.format("&cLeave from Build mode first!"));
            return true;
        }
        if (Main.getInstance().spectate.contains(player)) {
            LeaveSpectate(player);
            return true;
        }
        for (Region region : Main.getInstance().regionMap.values()) {
            if (region.contains(player.getLocation())) {
                JoinSpectate(player);

                return true;
            }
        }
        player.sendMessage(TextHelper.format("&cYou may spectate at the spawn!"));

        return false;
    }


}

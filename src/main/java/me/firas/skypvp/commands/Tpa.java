package me.firas.skypvp.commands;

import com.comphenix.protocol.wrappers.EnumWrappers;
import me.firas.skypvp.Main;
import me.firas.skypvp.events.HorseModifier;
import me.firas.skypvp.util.TextHelper;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class Tpa implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player player = (Player) sender;
        ItemStack ph = new ItemStack(Material.SKULL_ITEM);
        SkullMeta phmeta = (SkullMeta) ph.getItemMeta();
        phmeta.setOwner(player.getName());
        ph.setItemMeta(phmeta);
        if (Main.getInstance().spectate.contains(player)){
            if (args.length != 1){
                player.sendMessage(TextHelper.format("&c/tpa [username]"));
                return true;
            }
            if (Bukkit.getPlayerExact(args[0]) == null | Main.getInstance().spectate.contains(Bukkit.getPlayerExact(args[0]))){
                player.sendMessage(TextHelper.format("&7"+args[0]+" &cwas not found!"));
                return true;
            }
            player.teleport(Bukkit.getPlayerExact(args[0]));
            return true;
        }
        player.sendMessage(TextHelper.format("&cYou need to enter Spectate mode first!"));
        return false;
    }
}

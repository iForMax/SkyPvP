package me.firas.skypvp.commands;

import me.firas.skypvp.Main;
import me.firas.skypvp.regions.Region;
import me.firas.skypvp.util.TextHelper;
import me.firas.skypvp.util.Title;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Hub implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player player = (Player) sender;
        if (Main.getInstance().HubCooldown.contains(player)){
            return true;
        }
        if (Main.getInstance().getCombatPlayer().isInCombat(player)){
            player.sendMessage(TextHelper.format("&cYou are in combat"));
            return true;
        }
        if (player.getGameMode().equals(GameMode.SPECTATOR)){
            return true;
        }

        Main.getInstance().HubCooldown.add(player);
        player.sendMessage(TextHelper.format("&aYou will be moved in &b5 &aseconds, stay still"));
        new BukkitRunnable() {
            int seconds = 6;
            int x = (int) player.getLocation().getX();
            int y = (int) player.getLocation().getY();
            int z = (int) player.getLocation().getZ();

            @Override
            public void run() {
                if (x != (int) player.getLocation().getX() || y != (int) player.getLocation().getY() || z != (int) player.getLocation().getZ()){
                    Main.getInstance().HubCooldown.remove(player);
                    new Title("&cCANCELED","&4You moved",0,30,0).send(player);
                    player.playSound(player.getLocation(), Sound.CLICK,1,1);
                    this.cancel();
                    return;
                }
                seconds--;
                if (!Main.getInstance().HubCooldown.contains(player)){
                    this.cancel();
                    return;
                }
                if (seconds <= 0){
                    Main.getInstance().HubCooldown.remove(player);
                    Main.getInstance().HubCanLeave.add(player.getUniqueId());
                    player.performCommand("hub");
                    Main.getInstance().HubCanLeave.remove(player.getUniqueId());
                    this.cancel();
                    return;
                }
                player.playSound(player.getLocation(), Sound.WOOD_CLICK,1,1);
                new Title("","Teleporting in &e"+seconds+" &fseconds...",0,30,0).send(player);
            }
        }.runTaskTimer(Main.getInstance(),0,20);
        return false;
    }
}

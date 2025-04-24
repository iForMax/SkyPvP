package me.firas.skypvp.commands;

import me.firas.skypvp.Main;
import me.firas.skypvp.util.TextHelper;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Settings implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player player = (Player) sender;
        if (args.length != 1){
            return true;
        }
        if (args[0].equalsIgnoreCase("safedropkit")){
            if (Main.getInstance().get(player).getSettings().contains("SafeDropKit")){
                Main.getInstance().get(player).removeSetting("SafeDropKit");
                player.sendMessage(TextHelper.format("&7You &aactivate &7Safe Drop Kit"));
                return true;
            } else {
                Main.getInstance().get(player).addSetting("SafeDropKit");
                player.sendMessage(TextHelper.format("&7You &cdeactivate &7Safe Drop Kit"));
            }
            return true;
        }
        if (args[0].equalsIgnoreCase("chat")){
            boolean is = Boolean.parseBoolean(Main.getInstance().get(player).getChat());
            if (is){
                Main.getInstance().get(player).setChat("false");
                player.sendMessage(TextHelper.format("&7You &cdeactivate &7Chat"));
                return true;
            } else {
                Main.getInstance().get(player).setChat("true");
                player.sendMessage(TextHelper.format("&7You &aactivate &7Chat"));

            }
            return true;
        }
        return false;
    }
}

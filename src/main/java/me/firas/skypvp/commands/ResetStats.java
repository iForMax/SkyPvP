package me.firas.skypvp.commands;

import me.firas.skypvp.Main;
import me.firas.skypvp.generators.menu.MenuManager;
import me.firas.skypvp.spectate.SpectateMode;
import me.firas.skypvp.util.FakePlayer;
import me.firas.skypvp.util.TextHelper;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ResetStats implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player player = (Player) sender;
        String name = player.getName();
        if (name.equals("NaifAlmalki") | name.equals("Webmasters") | name.equals("IchBinKlauss")| name.equals("Fahad_b")){
            if (args.length != 1){
                player.sendMessage(TextHelper.format("&cUsage: /resetstats [Username]"));
                return true;
            }
            Player arg;
            if (Bukkit.getPlayer(args[0]) == null){
                FakePlayer fakePlayer = new FakePlayer(Bukkit.getOfflinePlayer(args[0]));
                arg = fakePlayer.getPlayer();
            } else {
                arg = Bukkit.getPlayerExact(args[0]);
            }
            Main.getInstance().get(arg).load();
            Main.getInstance().get(arg).setPoints(50);
            Main.getInstance().get(arg).setCoins(0);
            Main.getInstance().get(arg).setKills(0);
            Main.getInstance().get(arg).setDeaths(0);
            Main.getInstance().get(arg).save();
            player.sendMessage(TextHelper.format("&aSuccessfully reset stats of &e"+arg.getName()));
            return true;
        }
        player.performCommand("/unknownCommand");

        return false;
    }
}

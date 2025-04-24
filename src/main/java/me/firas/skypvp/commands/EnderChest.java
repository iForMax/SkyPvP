package me.firas.skypvp.commands;

import me.firas.skypvp.Main;
import me.firas.skypvp.util.FakePlayer;
import me.firas.skypvp.util.TextHelper;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.io.IOException;
import java.sql.ResultSet;

public class EnderChest implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player player = (Player) sender;
        String name = player.getName();
        if (name.equals("NaifAlmalki") | name.equals("Webmasters") | name.equals("IchBinKlauss")| name.equals("Fahad_b")){
            if (args.length != 2){
                player.sendMessage(TextHelper.format("&7/ec [Name] [Number]"));
                return true;
            }
            Player arg;
            if (Bukkit.getPlayer(args[0]) == null){
                FakePlayer fakePlayer = new FakePlayer(Bukkit.getOfflinePlayer(args[0]));
                arg = fakePlayer.getPlayer();
            } else {
                arg = Bukkit.getPlayer(args[0]);
            }
            try {
                if (args[1].equals("1")){
                    if (Main.getInstance().get(arg).getEnderchest1().contains("None")){
                        player.sendMessage(TextHelper.format("&6"+arg.getName()+" &7has never open "+args[1]));
                        return true;
                    }
                    Inventory inventory = Main.getInstance().getEnderchests().fromBase64s(Main.getInstance().get(arg).getEnderchest1(),arg.getName(),1);
                    player.openInventory(inventory);
                } else if (args[1].equals("2")){
                    if (Main.getInstance().get(arg).getEnderchest2().contains("None")){
                        player.sendMessage(TextHelper.format("&6"+arg.getName()+" &7has never open "+args[1]));
                        return true;
                    }
                    Inventory inventory = Main.getInstance().getEnderchests().fromBase64s(Main.getInstance().get(arg).getEnderchest2(),arg.getName(),2);
                    player.openInventory(inventory);
                } else if (args[1].equals("3")){
                    if (Main.getInstance().get(arg).getEnderchest3().contains("None")){
                        player.sendMessage(TextHelper.format("&6"+arg.getName()+" &7has never open "+args[1]));
                        return true;
                    }
                    Inventory inventory = Main.getInstance().getEnderchests().fromBase64s(Main.getInstance().get(arg).getEnderchest3(),arg.getName(),3);
                    player.openInventory(inventory);
                } else if (args[1].equals("4")){
                    if (Main.getInstance().get(arg).getEnderchest4().contains("None")){
                        player.sendMessage(TextHelper.format("&6"+arg.getName()+" &7has never open "+args[1]));
                        return true;
                    }
                    Inventory inventory = Main.getInstance().getEnderchests().fromBase64s(Main.getInstance().get(arg).getEnderchest4(),arg.getName(),4);
                    player.openInventory(inventory);
                } else if (args[1].equals("5")){
                    if (Main.getInstance().get(arg).getEnderchest5().contains("None")){
                        player.sendMessage(TextHelper.format("&6"+arg.getName()+"&7 has never open "+args[1]));
                        return true;
                    }
                    Inventory inventory = Main.getInstance().getEnderchests().fromBase64s(Main.getInstance().get(arg).getEnderchest5(),arg.getName(),5);
                    player.openInventory(inventory);
                } else if (args[1].equals("6")){
                    if (Main.getInstance().get(arg).getEnderchest6().contains("None")){
                        player.sendMessage(TextHelper.format("&6"+arg.getName()+" &7has never open "+args[1]));
                        return true;
                    }
                    Inventory inventory = Main.getInstance().getEnderchests().fromBase64s(Main.getInstance().get(arg).getEnderchest6(),arg.getName(),6);
                    player.openInventory(inventory);
                } else if (args[1].equals("7")){
                    if (Main.getInstance().get(arg).getEnderchest7().contains("None")){
                        player.sendMessage(TextHelper.format("&6"+arg.getName()+" &7has never open "+args[1]));
                        return true;
                    }
                    Inventory inventory = Main.getInstance().getEnderchests().fromBase64s(Main.getInstance().get(arg).getEnderchest7(),arg.getName(),7);
                    player.openInventory(inventory);
                } else if (args[1].equals("8")){
                    if (Main.getInstance().get(arg).getEnderchest8().contains("None")){
                        player.sendMessage(TextHelper.format("&6"+arg.getName()+" &7has never open "+args[1]));
                        return true;
                    }
                    Inventory inventory = Main.getInstance().getEnderchests().fromBase64s(Main.getInstance().get(arg).getEnderchest8(),arg.getName(),8);
                    player.openInventory(inventory);
                } else if (args[1].equals("9")){
                    if (Main.getInstance().get(arg).getEnderchest9().contains("None")){
                        player.sendMessage(TextHelper.format("&6"+arg.getName()+" &7has never open "+args[1]));
                        return true;
                    }
                    Inventory inventory = Main.getInstance().getEnderchests().fromBase64s(Main.getInstance().get(arg).getEnderchest9(),arg.getName(),9);
                    player.openInventory(inventory);
                }


                return true;
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        player.performCommand("/unknownCommand");
        return false;
    }

}

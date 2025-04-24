package me.firas.skypvp.commands;

import com.mojang.authlib.GameProfile;
import me.firas.skypvp.Main;
import me.firas.skypvp.generators.GeneratorMenu;
import me.firas.skypvp.generators.GeneratorType;
import me.firas.skypvp.util.ItemBuilder;
import me.firas.skypvp.util.TextHelper;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.*;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.util.StringUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Generators implements TabExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player player = (Player) sender;
        if (!player.isOp()) return true;
        if (args.length == 0){
            new GeneratorMenu(player);
            return true;
        }
        if (args[0].equalsIgnoreCase("EnderChest")){
            Main.getInstance().getGeneratorsHandler().setValue("Size of EnderChest",(Main.getInstance().getGeneratorsHandler().getValue("Size of EnderChest")+1));
            Main.getInstance().getGeneratorsHandler().addLocation("enderchest",player.getLocation().add(0,-1,0), GeneratorType.EnderChest);
            player.sendMessage(TextHelper.format("&fSuccessfully added &eEnder Chest&f! restart or reload to take effect"));
            return true;
        }
        if (args[0].equalsIgnoreCase("Menu")){
            Main.getInstance().getGeneratorsHandler().setValue("Size of Menu",(Main.getInstance().getGeneratorsHandler().getValue("Size of Menu")+1));
            Main.getInstance().getGeneratorsHandler().addLocation("menu",player.getLocation().add(0,-1,0), GeneratorType.Menu);
            player.sendMessage(TextHelper.format("&fSuccessfully added &eMenu&f! restart or reload to take effect"));

            return true;
        }
        if (args[0].equalsIgnoreCase("Iron")){
            Main.getInstance().getGeneratorsHandler().setValue("Size of Iron",(Main.getInstance().getGeneratorsHandler().getValue("Size of Iron")+1));
            Main.getInstance().getGeneratorsHandler().addLocation("iron",player.getLocation().add(0,1.5,0), GeneratorType.Iron);
            player.sendMessage(TextHelper.format("&fSuccessfully added &eIron&f! restart or reload to take effect"));

            return true;
        }
        if (args[0].equalsIgnoreCase("Emerald")){
            Main.getInstance().getGeneratorsHandler().setValue("Size of Emerald",(Main.getInstance().getGeneratorsHandler().getValue("Size of Emerald")+1));
            Main.getInstance().getGeneratorsHandler().addLocation("emerald",player.getLocation().add(0,1.5,0), GeneratorType.Emerald);
            player.sendMessage(TextHelper.format("&fSuccessfully added &eEmerald&f! restart or reload to take effect"));

            return true;
        }
        if (args[0].equalsIgnoreCase("Gold")){
            Main.getInstance().getGeneratorsHandler().setValue("Size of Gold",(Main.getInstance().getGeneratorsHandler().getValue("Size of Gold")+1));
            Main.getInstance().getGeneratorsHandler().addLocation("gold",player.getLocation().add(0,1.5,0), GeneratorType.Gold);
            player.sendMessage(TextHelper.format("&fSuccessfully added &eGold&f! restart or reload to take effect"));

            return true;
        }
        if (args[0].equalsIgnoreCase("xp")){
            Main.getInstance().getGeneratorsHandler().setValue("Size of XP",(Main.getInstance().getGeneratorsHandler().getValue("Size of XP")+1));
            Main.getInstance().getGeneratorsHandler().addLocation("xp",player.getLocation().add(0,1.5,0), GeneratorType.XP);
            player.sendMessage(TextHelper.format("&fSuccessfully added &eXP&f! restart or reload to take effect"));

            return true;
        }
        if (args[0].equalsIgnoreCase("koth")){
            if (args.length == 1){
                return true;
            }
            String type = args[1];
            if (type.equalsIgnoreCase("Arena") | type.equalsIgnoreCase("Aliens") | type.equalsIgnoreCase("Pyramid")){
                Main.getInstance().getGeneratorsHandler().addLocation("koth-"+type,player.getLocation().add(0,1.5,0));
                player.sendMessage(TextHelper.format("&fSuccessfully added &eKoTH "+type+"&f! restart or reload to take effect"));
            } else {
                player.sendMessage(TextHelper.format("&cThis koth was not found!"));
            }

            return true;
        }



        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command , String alias, String[] args){
        if (!sender.isOp()) return null;
        if (args.length == 0){
            return null;
        }
        List<String> completions = new ArrayList<>();
        List<String> commands = new ArrayList<>();
        if (args.length == 1) {
            commands.add("xp");
            commands.add("enderchest");
            commands.add("menu");
            commands.add("koth");
            commands.add("iron");
            commands.add("gold");
            commands.add("emerald");
            StringUtil.copyPartialMatches(args[0], commands, completions);
        }
        if (args.length == 2){
            if (args[0].equalsIgnoreCase("koth")){
                commands.add("aliens");
                commands.add("pyramid");
                commands.add("arena");
                StringUtil.copyPartialMatches(args[1], commands, completions);
            }
        }
        Collections.sort(completions);
        return completions;
    }

}

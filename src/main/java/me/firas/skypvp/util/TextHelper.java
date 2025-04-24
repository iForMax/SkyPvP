package me.firas.skypvp.util;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.entity.Player;

import java.util.Objects;

public class TextHelper {
    public static String format(String text){
        return ChatColor.translateAlternateColorCodes('&',
                    "&8&l▏ &b&lSky&f&lPvP &8▏ &f"+text);
    }
    public static String text(String text){
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public static String noPerm(String permission){
        return text("&cMissing permission ("+permission+")");
    }
    public static void rawText(Player player, String msg, String... raws){
        String message = (ChatColor.translateAlternateColorCodes('&',
                "&8&l▏ &b&lSky&f&lPvP &8▏ &f"+msg));
        ComponentBuilder cb = new ComponentBuilder(message);
        StringBuilder strings = null;
        for (String line : raws) {
            if (strings == null){
                strings = new StringBuilder(ChatColor.translateAlternateColorCodes('&', line));
                continue;
            }
            strings.append("\n");
            strings.append(ChatColor.translateAlternateColorCodes('&', line));
        }
        cb.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(Objects.requireNonNull(strings).toString()).create()));
        player.spigot().sendMessage(cb.create());
    }
    public static void rawCMD(Player player,String msg,String command, String... raws){
        String message = (ChatColor.translateAlternateColorCodes('&',
                "&8&l▏ &b&lSky&f&lPvP &8▏ &f"+msg));
        ComponentBuilder cb = new ComponentBuilder(message);
        StringBuilder strings = null;
        for (String line : raws) {
            if (strings == null){
                strings = new StringBuilder(ChatColor.translateAlternateColorCodes('&', line));
                continue;
            }
            strings.append("\n");
            strings.append(ChatColor.translateAlternateColorCodes('&', line));
        }
        cb.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(Objects.requireNonNull(strings).toString()).create()));
        cb.event(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/"+command));
        player.spigot().sendMessage(cb.create());
    }

}

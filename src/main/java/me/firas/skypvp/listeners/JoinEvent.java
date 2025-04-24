package me.firas.skypvp.listeners;

import dev.mqzen.boards.BoardManager;
import me.firas.skypvp.Main;
import me.firas.skypvp.enums.KitType;
import me.firas.skypvp.scoreboard.Board;
import me.firas.skypvp.util.TextHelper;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.lang.reflect.Field;

public class JoinEvent implements Listener {
    @EventHandler
    public void GlobalJoinEvent(PlayerJoinEvent e){
        Player player = e.getPlayer();
        if (player.getGameMode().equals(GameMode.SPECTATOR)){
            Main.getInstance().getKit().grantKit(e.getPlayer(), KitType.valueOf(Main.getInstance().get(e.getPlayer()).getKit()));
            player.teleport(Main.getInstance().getLocationHandler().getLocation("spawn"));
        }
        if (player.getLevel() >= 10000){
            player.setLevel(10);
        }
        try {
            Field underlyingEntityField = CraftEntity.class.getDeclaredField("entity");
            underlyingEntityField.setAccessible(true);
            Object underlyingPlayerObj = underlyingEntityField.get(e.getPlayer());
            if (underlyingPlayerObj instanceof EntityPlayer) {
                EntityPlayer underlyingPlayer = (EntityPlayer) underlyingPlayerObj;
                underlyingPlayer.invulnerableTicks = 1;
            }
        } catch (Exception es) {
            Bukkit.getConsoleSender().sendMessage(("LoginInvulnerabilityFix exception: " + es.getMessage()));
            es.printStackTrace();
        }

        if (player.isDead()){
            player.setHealth(player.getMaxHealth());
        }
        if (!Main.getInstance().get(player).getSettings().contains("FJFP")){
            Main.getInstance().get(player).addSetting("FJFP");
            Main.getInstance().get(player).setPoints(50);
        }

        BoardManager.getInstance().setupNewBoard(e.getPlayer(), new Board());
        e.setJoinMessage(null);
        Main.getInstance().updateTime(player);
        boolean chats = Boolean.parseBoolean(Main.getInstance().get(player).getChat());
        for (Player spec : Main.getInstance().spectate){
            player.hidePlayer(spec);
        }
        if (!chats){
            player.sendMessage(TextHelper.format("&cYou are currently disabled the chat!"));
            TextHelper.rawCMD(player,"&a&lClick here &7to activate the chat","settings chat","&aClick here to activate","&athe chat"," ","&7You can change this option","&7from the settings");
        }
        if (!player.hasPlayedBefore()){
            Main.getInstance().getKit().grantKit(e.getPlayer(), KitType.valueOf(Main.getInstance().get(e.getPlayer()).getKit()));
            player.teleport(Main.getInstance().getLocationHandler().getLocation("spawn"));
        }
    }
}

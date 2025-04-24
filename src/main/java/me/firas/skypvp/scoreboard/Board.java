package me.firas.skypvp.scoreboard;

import dev.mqzen.boards.animation.HighlightingAnimation;
import dev.mqzen.boards.animation.core.Animation;
import dev.mqzen.boards.base.BoardAdapter;
import dev.mqzen.boards.base.Title;
import dev.mqzen.boards.body.Body;
import me.firas.skypvp.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.text.SimpleDateFormat;
import java.util.Date;

import static dev.mqzen.boards.body.Body.empty;
import static me.firas.skypvp.combat.CombatLogger.TimeRemaining;

public class Board implements BoardAdapter {
    SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, hh:mm");
    @Override
    public @NonNull Title title(Player player) {
        if (Main.getInstance().getScoreboardHandler().getManager().getBoolean("Scoreboard.HighlightingAnimation"))
            return Title.builder()
                    .withText("Hello")
                    .withAnimation(HighlightingAnimation.of(Main.getInstance().getScoreboardHandler().getString("Scoreboard.Title"), Main.getInstance().getScoreboardHandler().getString("Scoreboard.HighlightingColor1"), Main.getInstance().getScoreboardHandler().getString("Scoreboard.HighlightingColor2"))).build();
        else
            return Title.builder()
                    .withText(Main.getInstance().getScoreboardHandler().getString("Scoreboard.Title")).build();
    }
    @Override
    public @NonNull Body getBody(Player player) {
        Body body = empty();
        Date date = new Date();
        for (String line : Main.getInstance().getScoreboardHandler().getManager().getStringList("Scoreboard.lines")) {
            body.addNewLine(ChatColor.translateAlternateColorCodes('&', line)
                    .replace("<date>", dateFormat.format(date))
                    .replace("<prestige>",Main.getInstance().get(player).getPrestige())
                    .replace("<coins>",Main.getInstance().get(player).getCoins()+"")
                    .replace("<deaths>",Main.getInstance().get(player).getDeaths()+"")
                    .replace("<kills>", Main.getInstance().get(player).getKills()+"")
                    .replace("<kdr>",Main.getInstance().get(player).kdr()+"")
                    .replace("<streak>",Main.getInstance().get(player).getStreak()+"")
                    .replace("<event>",Main.getInstance().getEvents().getEvent())
                    .replace("<timeevent>",Main.getInstance().getEvents().getTime())
                    .replace("<combat>",TimeRemaining(player))
                    .replace("<points>",Main.getInstance().get(player).getPoints()+"")

            );
        }
        return body;
    }
}

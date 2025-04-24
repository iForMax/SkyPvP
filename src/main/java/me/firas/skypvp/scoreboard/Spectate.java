package me.firas.skypvp.scoreboard;

import dev.mqzen.boards.animation.HighlightingAnimation;
import dev.mqzen.boards.base.BoardAdapter;
import dev.mqzen.boards.base.Title;
import dev.mqzen.boards.body.Body;
import me.firas.skypvp.Main;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.text.SimpleDateFormat;
import java.util.Date;

import static dev.mqzen.boards.body.Body.empty;
import static me.firas.skypvp.combat.CombatLogger.TimeRemaining;

public class Spectate implements BoardAdapter {
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
        body.addNewLine("");
        body.addNewLine("&6⧖ &f"+dateFormat.format(date));
        body.addNewLine("");
        body.addNewLine("&7You are now a");
        body.addNewLine("&7spectator");
        body.addNewLine("");
        body.addNewLine("&f/spec &7to leave");
        body.addNewLine("");
        body.addNewLine("&aplay.marsmc.net");
        return body;
    }
}

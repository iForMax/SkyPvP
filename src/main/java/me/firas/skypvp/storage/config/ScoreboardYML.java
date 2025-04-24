package me.firas.skypvp.storage.config;

import me.firas.skypvp.Main;
import me.firas.skypvp.util.YMLFile;

import java.util.ArrayList;
import java.util.List;

public class ScoreboardYML extends YMLFile {
    public ScoreboardYML() {
        super("scoreboard.yml", Main.getInstance().getDataFolder().getPath());
        createNew();
    }

    private List<String> lines = new ArrayList<>(15);

    @Override
    public void setDefaults() {
        setString("Scoreboard.Title", "PvP");
        setObject("Scoreboard.HighlightingAnimation", true);
        setString("Scoreboard.HighlightingColor1", "&e");
        setString("Scoreboard.HighlightingColor2", "&6");
        if (lines.isEmpty()) {
            lines.add("&7<date>");
            lines.add("");
            lines.add("&fKills: &e<kills>");
            lines.add("&fDeaths: &e<deaths>");
            lines.add("&fStreak: &e<streak>");
            lines.add("&fPoints: &e<points>");
            lines.add("&fCoins: &e<coins>");
            lines.add("&fPrestige: &e<prestige>");
            lines.add("");
            lines.add("&aForMax.tk");
            setStringList("Scoreboard.lines", lines);
            return;
        }
        setString("Scoreboard.lines", "Empty");
    }
}

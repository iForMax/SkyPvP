package me.firas.skypvp.enums;

import lombok.Getter;

public enum PrestigeType {
    Bronze("&8Bronze", 0),
    Veteran("&2Veteran", 100),
    Warrior("&7Warrior", 500),
    Crown("&bCrown", 1000),
    Ace("&5Ace", 1500),
    Master("&3Master", 2500),
    Boss("&6Boss", 5000),
    Legendary("&c&lLegendary", 7500),
    General("&4&lGeneral", 10000);




    @Getter
    private final double points;
    @Getter
    private final String name;
    PrestigeType(String name, double points) {
        this.points = points;
        this.name = name;
    }
    public static PrestigeType getPrestigeType(double kdr) {
        PrestigeType[] values = PrestigeType.values();
        for (int i = values.length - 1; i >= 0; i--) {
            if (kdr >= values[i].points) {
                return values[i];
            }
        }
        return Bronze;
    }
}

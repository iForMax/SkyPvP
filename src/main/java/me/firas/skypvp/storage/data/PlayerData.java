package me.firas.skypvp.storage.data;


import lombok.Data;
import me.firas.skypvp.Main;
import me.firas.skypvp.enums.PrestigeType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.UUID;
@Data
public class PlayerData {

    private final String ign, table = "SkyPvP";
    private UUID uuid;
    private int AchivKills = 0,AchivTime = 0,AchivBow = 0,AchivDeath = 0,AchivPickup = 0, kills = 0, deaths = 0, streak = 0, points=50, coins=0;
    private String Time="Dawn",Chat="true",Enderchest1="None",Enderchest2="None",Enderchest3="None",Enderchest4="None",Enderchest5="None",Enderchest6="None",Enderchest7="None",Enderchest8="None",Enderchest9="None",trail="None",kit="MEMBER",killEffect="None",killMessage="None",
            settings="PLAYER",scramble="false";

    public PlayerData(UUID uuid, String ign){
        this.uuid = uuid;
        this.ign = ign;
    }
    public PlayerData load() {
        try {
            try(final Connection connection = Main.getInstance().getDataManager().getConnection() ;
                final PreparedStatement preparedStatement = connection.prepareStatement(handleReplace("select * from {table} where uuid='{uuid}'"))) {
                try (final ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        this.kills = resultSet.getInt("kills");
                        this.deaths = resultSet.getInt("deaths");
                        this.streak = resultSet.getInt("streak");
                        this.points = resultSet.getInt("points");
                        this.AchivKills = resultSet.getInt("AchivKills");
                        this.AchivBow = resultSet.getInt("AchivBow");
                        this.AchivTime = resultSet.getInt("AchivTime");
                        this.AchivDeath = resultSet.getInt("AchivDeath");
                        this.AchivPickup = resultSet.getInt("AchivPickup");
                        this.scramble = resultSet.getString("scramble");
                        this.settings = resultSet.getString("settings");
                        this.kit = resultSet.getString("kit");
                        this.trail = resultSet.getString("trail");
                        this.coins = resultSet.getInt("coins");
                        this.killEffect = resultSet.getString("killEffect");
                        this.killMessage = resultSet.getString("killMessage");
                        this.Enderchest1 = resultSet.getString("Enderchest1");
                        this.Enderchest2 = resultSet.getString("Enderchest2");
                        this.Enderchest3 = resultSet.getString("Enderchest3");
                        this.Enderchest4 = resultSet.getString("Enderchest4");
                        this.Enderchest5 = resultSet.getString("Enderchest5");
                        this.Enderchest6 = resultSet.getString("Enderchest6");
                        this.Enderchest7 = resultSet.getString("Enderchest7");
                        this.Enderchest8 = resultSet.getString("Enderchest8");
                        this.Enderchest9 = resultSet.getString("Enderchest9");
                        this.Time = resultSet.getString("Time");
                        this.Chat = resultSet.getString("Chat");
                    }
                    return this;
                } catch (Exception e) {
                    System.out.println("MySQL error while loading[" + this.uuid + "]: " + e.getMessage());
                    return null;
                }
            } catch (Exception e) {
                System.out.println("MySQL error while loading[" + this.uuid + "]: " + e.getMessage());
                return null;
            }
        } catch (Exception e) {
            System.out.println("MySQL error while loading[" + this.uuid + "]: " + e.getMessage());
            return null;
        }
    }

    public void save() {
        try {
            try(final Connection connection = Main.getInstance().getDataManager().getConnection(); final PreparedStatement preparedStatement = connection.prepareStatement(handleReplace("select * from {table} where uuid='{uuid}'"))){
                try(final ResultSet resultSet = preparedStatement.executeQuery()){
                    try(PreparedStatement ps = connection.prepareStatement(handleReplace(resultSet.next() ? "update {table} set ign='{ign}', Time='{Time}',Chat='{Chat}', Enderchest1='{Enderchest1}', Enderchest2='{Enderchest2}', Enderchest3='{Enderchest3}', Enderchest4='{Enderchest4}', Enderchest5='{Enderchest5}', Enderchest6='{Enderchest6}', Enderchest7='{Enderchest7}', Enderchest8='{Enderchest8}', Enderchest9='{Enderchest9}', trail='{trail}', kit='{kit}',killEffect='{killEffect}',killMessage='{killMessage}', settings='{settings}',scramble='{scramble}', AchivKills='{AchivKills}', AchivDeath='{AchivDeath}', AchivTime='{AchivTime}', AchivBow='{AchivBow}', AchivPickup='{AchivPickup}', points='{points}', kills='{kills}', coins='{coins}', deaths='{deaths}', streak='{streak}' where uuid='{uuid}'" : "insert into {table} (uuid, ign, Time,Chat, Enderchest1 ,Enderchest2 ,Enderchest3 ,Enderchest4 ,Enderchest5 ,Enderchest6,Enderchest7,Enderchest8,Enderchest9,trail, kit, killEffect, killMessage, settings,scramble,AchivKills,AchivDeath,AchivTime,AchivBow,AchivPickup, points, kills, coins, deaths, streak) values ('{uuid}', '{ign}', '{Time}', '{Chat}', '{Enderchest1}', '{Enderchest2}', '{Enderchest3}', '{Enderchest4}', '{Enderchest5}', '{Enderchest6}', '{Enderchest7}', '{Enderchest8}', '{Enderchest9}', '{trail}', '{kit}', '{killEffect}', '{killMessage}', '{settings}','{scramble}',{AchivKills},{AchivDeath},{AchivTime},{AchivBow},{AchivPickup}, {points}, {kills}, {coins}, {deaths}, {streak})"))){
                        ps.executeUpdate();
                    } catch (Exception e) {
                        System.out.println("&cMySQL error while saving [1]&8[&4" + this.uuid + "&8]: &7" + e.getMessage());
                    }
                } catch (Exception e) {
                    System.out.println("&cMySQL error while saving [2]&8[&4" + this.uuid + "&8]: &7" + e.getMessage());
                }
            } catch (Exception e) {
                System.out.println("&cMySQL error while saving [3]&8[&4" + this.uuid + "&8]: &7" + e.getMessage());
            }
        } catch (Exception e) {
            System.out.println("&cMySQL error while saving [4]&8[&4" + this.uuid + "&8]: &7" + e.getMessage());
        }
    }
    private String handleReplace(String s) {
        return s.replace("{table}", this.table).replace("{Chat}",Chat+"").replace("{Time}",Time+"").replace("{Enderchest1}",Enderchest1+"").replace("{Enderchest2}",Enderchest2+"").replace("{Enderchest3}",Enderchest3+"").replace("{Enderchest4}",Enderchest4+"").replace("{Enderchest5}",Enderchest5+"").replace("{Enderchest6}",Enderchest6+"").replace("{Enderchest7}",Enderchest7+"").replace("{Enderchest8}",Enderchest8+"").replace("{Enderchest9}",Enderchest9+"").replace("{trail}", trail+"").replace("{kit}", kit+"").replace("{killEffect}", killEffect+"").replace("{killMessage}", killMessage+"").replace("{scramble}",scramble+"").replace("{settings}", settings+"").replace("{AchivKills}",AchivKills+"").replace("{AchivDeath}",AchivDeath+"").replace("{AchivTime}",AchivTime+"").replace("{AchivBow}",AchivBow+"").replace("{AchivPickup}",AchivPickup+"").replace("{points}", points+"").replace("{coins}", coins+"").replace("{streak}", ""+streak).replace("{deaths}", ""+deaths).replace("{kills}", ""+kills).replace("{uuid}", this.uuid+"").replace("{ign}", this.ign);
    }


    public void addStats(String stat, int amount){
        switch (stat){
            case "AchivKills":
                AchivKills=AchivKills+amount;
                return;
            case "AchivDeath":
                AchivDeath=AchivDeath+amount;
                return;
            case "AchivTime":
                AchivTime=AchivTime+amount;
                return;
            case "AchivBow":
                AchivBow=AchivBow+amount;
                return;
            case "AchivPickup":
                AchivPickup=AchivPickup+amount;
                return;

            case "kills":
                kills=kills+amount;
                return;
            case "deaths":
                deaths=deaths+amount;
                return;
            case "streak":
                streak=streak+amount;
                return;
            case "points":
                points=points+amount;
                return;
            case "coins":
                coins=coins+amount;
                break;


        }
    }
    public int getStats(String stat){
        switch (stat){
            case "AchivKills":
                return AchivKills;
            case "AchivDeath":
                return AchivDeath;
            case "AchivTime":
                return AchivTime;
            case "AchivBow":
                return AchivBow;
            case "AchivPickup":
                return AchivPickup;
        }
        return 0;
    }
    public String getEnderChest(int number){
        switch (number){
            case 1:
                return Enderchest1;
            case 2:
                return Enderchest2;
            case 3:
                return Enderchest3;
            case 4:
                return Enderchest4;
            case 5:
                return Enderchest5;
            case 6:
                return Enderchest6;
            case 7:
                return Enderchest7;
            case 8:
                return Enderchest8;
            case 9:
                return Enderchest9;

        }
        return null;
    }

    public void setEnderChest(int number,String data){
        switch (number){
            case 1:
                Enderchest1 = data;
                break;
            case 2:
                Enderchest2 = data;
                break;
            case 3:
                Enderchest3 = data;
                break;
            case 4:
                Enderchest4 = data;
                break;
            case 5:
                Enderchest5 = data;
                break;
            case 6:
                Enderchest6 = data;
                break;
            case 7:
                Enderchest7 = data;
                break;
            case 8:
                Enderchest8 = data;
                break;
            case 9:
                Enderchest9 = data;

        }
    }

    public void addSetting(String setting) {
        String[] settingsArray = settings.split(" ");
        boolean settingExists = false;
        for (String s : settingsArray) {
            if (s.split(":")[0].equals(setting.split(":")[0])) {
                settingExists = true;
                break;
            }
        }
        if (!settingExists) {
            if (settings.isEmpty()) {
                settings = setting;
            } else {
                settings = settings + " " + setting;
            }
        }
    }
    public void removeSetting(String settingKey) {
        String[] settingsArray = settings.split(" ");
        StringBuilder newSettings = new StringBuilder();

        for (String s : settingsArray) {
            if (!s.split(":")[0].equals(settingKey)) {
                if (newSettings.length() > 0) {
                    newSettings.append(" ");
                }
                newSettings.append(s);
            }
        }

        settings = newSettings.toString();
    }

    public String kdrs() {
        if (kills == 0) {
            if (deaths == 0)
                return 0.0D+"";
            return -deaths+"";
        }
        if (deaths == 0) {
            if (kills == 0)
                return 0.0D+"";
            return kills+"";
        }
        double kds = (double) kills / (double) deaths;
        DecimalFormat df = new DecimalFormat("0.00");
        String kdrs = df.format(kds);
        return kdrs;
    }
    public double kdr(){
        double kds = Double.parseDouble(kdrs());
        if (kds < 0.00){
            return 0.00D;
        }
        return Double.parseDouble(kdrs());
    }

    public String getPrestige() {
        PrestigeType prestigeType = PrestigeType.getPrestigeType(points);
        return prestigeType.getName();
    }
}

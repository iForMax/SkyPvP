package me.firas.skypvp.util;

import com.mojang.authlib.GameProfile;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import net.minecraft.server.v1_8_R3.PlayerInteractManager;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.entity.Player;

public class FakePlayer {
    private Player target;
    public FakePlayer(OfflinePlayer player) {
        if(Bukkit.getPlayer(player.getUniqueId()) != null) {
            target = Bukkit.getPlayer(player.getUniqueId());
        }
        MinecraftServer minecraftserver = MinecraftServer.getServer();
        GameProfile gameprofile = new GameProfile(player.getUniqueId(), player.getName());
        EntityPlayer entity = new EntityPlayer(minecraftserver,
                ((CraftWorld) Bukkit.getWorlds().get(0)).getHandle(),
                gameprofile,
                new PlayerInteractManager(((CraftWorld) Bukkit.getWorlds().get(0)).getHandle()));

        final Player target = entity == null ? null : entity.getBukkitEntity();
        if (target != null) {
            target.loadData();
            this.target = target;
        }
    }

    public Player getPlayer() {
        return target;
    }

    public void savePlayer() {
        if(target != null) {
            target.saveData();

        }
    }
}

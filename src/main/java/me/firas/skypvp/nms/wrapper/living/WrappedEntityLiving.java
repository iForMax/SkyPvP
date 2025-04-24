package me.firas.skypvp.nms.wrapper.living;

import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.Vector3F;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import me.firas.skypvp.nms.EIDGen;
import me.firas.skypvp.nms.UtilMath;
import me.firas.skypvp.nms.VersionChecker;
import me.firas.skypvp.nms.wrapper.packet.*;

import net.minecraft.server.v1_8_R3.DataWatcher;
import net.minecraft.server.v1_8_R3.EntityHorse;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public abstract class WrappedEntityLiving {
    private WrapperPlayServerSpawnEntityLiving spawnPacket;

    private WrapperPlayServerEntityDestroy destroyPacket;

    private WrapperPlayServerEntityMetadata metaPacket;

    private WrapperPlayServerEntityTeleport teleportPacket;

    private WrapperPlayServerEntityHeadRotation yawPacket;


    public WrappedDataWatcher dataWatcher;

    protected Map<EnumWrappers.ItemSlot, WrapperPlayServerEntityEquipment> equipments = new HashMap<>();

    private Location location;


    private int typeID;

    private int id;
    private boolean isDespawned;


    public int getTypeID(){
        return this.spawnPacket.getTypeID();
    }

    public WrappedEntityLiving(int typeID) {
        this.id = EIDGen.generateEID();
        this.typeID = typeID;

        this.spawnPacket = new WrapperPlayServerSpawnEntityLiving();
        this.spawnPacket.setEntityID(this.id);
        this.spawnPacket.setType(typeID);
        if (this.location != null){
            this.spawnPacket.setPitch(location.getPitch());
            this.spawnPacket.setHeadPitch(location.getPitch());
            this.spawnPacket.setYaw(location.getYaw());
            this.spawnPacket.setX(location.getX());
            this.spawnPacket.setY(location.getY());
            this.spawnPacket.setZ(location.getZ());
        }

        this.yawPacket = new WrapperPlayServerEntityHeadRotation();
        this.yawPacket.setEntityID(this.id);
        if (this.location != null)
            this.yawPacket.setHeadYaw(UtilMath.toPackedByte(location.getYaw()));
        this.destroyPacket = new WrapperPlayServerEntityDestroy();
        this.destroyPacket.setEntityIds(new int[] { this.id });
        this.dataWatcher = new WrappedDataWatcher();
        this.metaPacket = new WrapperPlayServerEntityMetadata();
        this.metaPacket.setEntityID(this.id);
        this.teleportPacket = new WrapperPlayServerEntityTeleport();
        byte b;
        int i;
        EnumWrappers.ItemSlot[] arrayOfItemSlot;
        for (i = (arrayOfItemSlot = EnumWrappers.ItemSlot.values()).length, b = 0; b < i; ) {
            EnumWrappers.ItemSlot itemSlot = arrayOfItemSlot[b];
            WrapperPlayServerEntityEquipment equip = new WrapperPlayServerEntityEquipment();
            equip.setEntityID(this.id);
            this.equipments.put(itemSlot, equip);
            b++;
        }
    }

    public void teleport(Location newLocation,Player player) {
        this.teleportPacket.setEntityID(this.id);
        this.teleportPacket.setX(newLocation.getX());
        this.teleportPacket.setY(newLocation.getY());
        this.teleportPacket.setZ(newLocation.getZ());
        this.teleportPacket.setYaw(newLocation.getYaw());
        this.teleportPacket.setPitch(newLocation.getPitch());
        this.teleportPacket.sendPacket(player);
        this.location = newLocation;
        this.spawnPacket.setPitch(this.location.getPitch());
        this.spawnPacket.setHeadPitch(this.location.getPitch());
        this.spawnPacket.setYaw(this.location.getYaw());
        this.spawnPacket.setX(this.location.getX());
        this.spawnPacket.setY(this.location.getY());
        this.spawnPacket.setZ(this.location.getZ());
        this.yawPacket.setHeadYaw(UtilMath.toPackedByte(this.location.getYaw()));
    }
    public UUID getUUID(){
        return this.spawnPacket.getUniqueId();
    }


    public void fakeTeleport(Location l,Player player) {
        this.teleportPacket.setEntityID(this.id);
        this.teleportPacket.setX(l.getX());
        this.teleportPacket.setY(l.getY());
        this.teleportPacket.setZ(l.getZ());
        this.teleportPacket.setYaw(l.getYaw());
        this.teleportPacket.setPitch(l.getPitch());
        this.teleportPacket.sendPacket(player);

    }

    public void updateYaw(Player player) {
        this.yawPacket.sendPacket(player);
    }

    public void spawnClient(Player client) {
        if (isDespawned) return;
        this.spawnPacket.sendPacket(client);
        if (this.typeID != 0) {
            this.equip(EnumWrappers.ItemSlot.HEAD,this.getEquip(EnumWrappers.ItemSlot.HEAD),client);
            this.equip(EnumWrappers.ItemSlot.CHEST,this.getEquip(EnumWrappers.ItemSlot.CHEST),client);
            this.equip(EnumWrappers.ItemSlot.FEET,this.getEquip(EnumWrappers.ItemSlot.FEET),client);
            this.equip(EnumWrappers.ItemSlot.LEGS,this.getEquip(EnumWrappers.ItemSlot.LEGS),client);
            this.equip(EnumWrappers.ItemSlot.MAINHAND,this.getEquip(EnumWrappers.ItemSlot.MAINHAND),client);
            this.sendUpdatedmetadata(client);
            this.yawPacket.sendPacket(client);
        }
    }



    public void setCustomName(String name) {
        if (VersionChecker.isHigherOrEqualThan(VersionChecker.v1_13_R1)) {
            this.dataWatcher.setObject(new WrappedDataWatcher.WrappedDataWatcherObject(2,
                            WrappedDataWatcher.Registry.getChatComponentSerializer(true)),
                    Optional.ofNullable(WrappedChatComponent.fromChatMessage(name)[0].getHandle()));
        } else {
            setDataWatcherObject(String.class, 2, name);
        }
    }
    public String getCustomName() {
        return (String)  getDataWatcherObject(2);
    }


    public void setCustomNameVisible(boolean visible) {
        if (VersionChecker.isLowerOrEqualThan(VersionChecker.v1_8_R3)) {
            setDataWatcherObject(Byte.class, 3, Byte.valueOf((byte)(visible ? 1 : 0)));
        } else {
            setDataWatcherObject(Boolean.class, 3, Boolean.valueOf(visible));
        }
    }


    public void despawn(Player player) {
        this.destroyPacket.sendPacket(player);
    }
    public void despawn(){
        isDespawned=true;
    }
    public boolean isDespawned(){
        return isDespawned;
    }


    public void equip(EnumWrappers.ItemSlot slot, ItemStack item) {
        WrapperPlayServerEntityEquipment equipPacket = this.equipments.get(slot);
        equipPacket.setItem(item);
        if (slot == EnumWrappers.ItemSlot.HEAD) {
            equipPacket.setSlot(4);
        } else if (slot == EnumWrappers.ItemSlot.CHEST){
            equipPacket.setSlot(3);
        } else if (slot == EnumWrappers.ItemSlot.LEGS){
            equipPacket.setSlot(2);
        } else if (slot == EnumWrappers.ItemSlot.FEET) {
            equipPacket.setSlot(1);
        } else {
            equipPacket.setSlot(0);

        }
    }


    public void equip(EnumWrappers.ItemSlot slot, ItemStack item,Player player) {
        WrapperPlayServerEntityEquipment equipPacket = this.equipments.get(slot);
        equipPacket.setItem(item);
        equipPacket.setSlot(slot);
        equipPacket.sendPacket(player);
    }
    public ItemStack getEquip(EnumWrappers.ItemSlot slot) {
        WrapperPlayServerEntityEquipment equipPacket = this.equipments.get(slot);
        return equipPacket.getItem();
    }

    public void setDataWatcherObject(Class<?> type, int objectIndex, Object object) {
        this.dataWatcher.setObject(objectIndex, object);

    }
    public void setDataWatcherObject(int objectIndex, Object object) {
            this.dataWatcher.setObject(objectIndex, object);
    }
    public Object getDataWatcherObject(int objectIndex) {
        return this.dataWatcher.getObject(objectIndex);

    }

    public void sendUpdatedmetadata(Player player) {
        this.metaPacket.setMetadata(this.dataWatcher.getWatchableObjects());
        this.metaPacket.sendPacket(player);
    }
    public void updateMetadata(){
        this.metaPacket.setMetadata(this.dataWatcher.getWatchableObjects());
    }

    public WrappedDataWatcher getDataWatcher() {
        return this.dataWatcher;
    }

    public void setLocation(Location location) {
        this.spawnPacket.setPitch(location.getPitch());
        this.spawnPacket.setHeadPitch(location.getPitch());
        this.spawnPacket.setYaw(location.getYaw());
        this.spawnPacket.setX(location.getX());
        this.spawnPacket.setY(location.getY());
        this.spawnPacket.setZ(location.getZ());
        this.location = location;
        this.yawPacket.setHeadYaw(UtilMath.toPackedByte(location.getYaw()));
    }
    public void setInvisible(boolean invisible) {
        setDataWatcherObject(Byte.class, 0, Byte.valueOf((byte) (invisible ? 32 : 0)));
    }


    public int getId() {
        return this.id;
    }

    public Location getLocation() {
        return this.location;
    }
}


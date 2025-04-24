package me.firas.skypvp.nms.wrapper.packet;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import me.firas.skypvp.nms.AbstractPacket;
import me.firas.skypvp.nms.VersionChecker;

import org.bukkit.World;
import org.bukkit.entity.Entity;

public class WrapperPlayServerEntityTeleport extends AbstractPacket {
	public static final PacketType TYPE = PacketType.Play.Server.ENTITY_TELEPORT;

	public WrapperPlayServerEntityTeleport() {
		super(new PacketContainer(TYPE), TYPE);
		this.handle.getModifier().writeDefaults();
	}

	public WrapperPlayServerEntityTeleport(PacketContainer packet) {
		super(packet, TYPE);
	}

	public int getEntityID() {
		return ((Integer)this.handle.getIntegers().read(0)).intValue();
	}

	public void setEntityID(int value) {
		this.handle.getIntegers().write(0, Integer.valueOf(value));
	}

	public Entity getEntity(World world) {
		return (Entity)this.handle.getEntityModifier(world).read(0);
	}

	public Entity getEntity(PacketEvent event) {
		return getEntity(event.getPlayer().getWorld());
	}

	public void setX(double value) {
		if (VersionChecker.isLowerOrEqualThan(VersionChecker.v1_8_R3)) {
			this.handle.getIntegers().write(1, Integer.valueOf((int)Math.floor(value * 32.0D)));
		} else {
			this.handle.getDoubles().write(0, Double.valueOf(value));
		}
	}

	public void setY(double value) {
		if (VersionChecker.isLowerOrEqualThan(VersionChecker.v1_8_R3)) {
			this.handle.getIntegers().write(2, Integer.valueOf((int)Math.floor(value * 32.0D)));
		} else {
			this.handle.getDoubles().write(1, Double.valueOf(value));
		}
	}

	public void setZ(double value) {
		if (VersionChecker.isLowerOrEqualThan(VersionChecker.v1_8_R3)) {
			this.handle.getIntegers().write(3, Integer.valueOf((int)Math.floor(value * 32.0D)));
		} else {
			this.handle.getDoubles().write(2, Double.valueOf(value));
		}
	}

	public double getX() {
		return ((Double)this.handle.getDoubles().read(0)).doubleValue();
	}

	public double getY() {
		return ((Double)this.handle.getDoubles().read(1)).doubleValue();
	}

	public double getZ() {
		return ((Double)this.handle.getDoubles().read(2)).doubleValue();
	}

	public float getYaw() {
		return ((Byte)this.handle.getBytes().read(0)).byteValue() * 360.0F / 256.0F;
	}

	public void setYaw(float value) {
		this.handle.getBytes().write(0, Byte.valueOf((byte)(int)(value * 256.0F / 360.0F)));
	}

	public float getPitch() {
		return ((Byte)this.handle.getBytes().read(1)).byteValue() * 360.0F / 256.0F;
	}

	public void setPitch(float value) {
		this.handle.getBytes().write(1, Byte.valueOf((byte)(int)(value * 256.0F / 360.0F)));
	}

	public boolean getOnGround() {
		return ((Boolean)this.handle.getBooleans().read(0)).booleanValue();
	}

	public void setOnGround(boolean value) {
		this.handle.getBooleans().write(0, Boolean.valueOf(value));
	}
}


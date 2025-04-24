package me.firas.skypvp.nms.wrapper.packet;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.injector.PacketConstructor;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import me.firas.skypvp.nms.AbstractPacket;
import me.firas.skypvp.nms.VersionChecker;
import org.bukkit.World;
import org.bukkit.entity.Entity;

import java.util.UUID;

public class WrapperPlayServerSpawnEntityLiving extends AbstractPacket {
	public static final PacketType TYPE = PacketType.Play.Server.SPAWN_ENTITY_LIVING;

	private static PacketConstructor entityConstructor;

	public WrapperPlayServerSpawnEntityLiving() {
		super(new PacketContainer(TYPE), TYPE);
		this.handle.getModifier().writeDefaults();
	}

	public WrapperPlayServerSpawnEntityLiving(PacketContainer packet) {
		super(packet, TYPE);
	}

	public WrapperPlayServerSpawnEntityLiving(Entity entity) {
		super(fromEntity(entity), TYPE);
	}

	private static PacketContainer fromEntity(Entity entity) {
		if (entityConstructor == null)
			entityConstructor =
					ProtocolLibrary.getProtocolManager()
							.createPacketConstructor(TYPE, entity);
		return entityConstructor.createPacket(entity);
	}

	public int getEntityID() {
		return ((Integer)this.handle.getIntegers().read(0)).intValue();
	}

	public Entity getEntity(World world) {
		return (Entity)this.handle.getEntityModifier(world).read(0);
	}

	public Entity getEntity(PacketEvent event) {
		return getEntity(event.getPlayer().getWorld());
	}

	public UUID getUniqueId() {
		return (UUID)this.handle.getUUIDs().read(0);
	}

	public void setUniqueId(UUID value) {
		if (VersionChecker.isLowerOrEqualThan(VersionChecker.v1_8_R3))
			this.handle.getUUIDs().write(0, value);
	}

	public void setEntityID(int value) {
		this.handle.getIntegers().write(0, Integer.valueOf(value));
	}

	public int getTypeID() {
		return ((Integer)this.handle.getIntegers().read(1)).intValue();
	}

	public void setType(int typeID) {
		this.handle.getIntegers().write(1, Integer.valueOf(typeID));
	}

	public double getX() {
		return ((Double)this.handle.getDoubles().read(0)).doubleValue();
	}

	public void setX(double value) {
		if (VersionChecker.isLowerOrEqualThan(VersionChecker.v1_8_R3)) {
			this.handle.getIntegers().write(2, Integer.valueOf((int)Math.floor(value * 32.0D)));
		} else {
			this.handle.getDoubles().write(0, Double.valueOf(value));
		}
	}

	public double getY() {
		return ((Double)this.handle.getDoubles().read(1)).doubleValue();
	}

	public void setY(double value) {
		if (VersionChecker.isLowerOrEqualThan(VersionChecker.v1_8_R3)) {
			this.handle.getIntegers().write(3, Integer.valueOf((int)Math.floor(value * 32.0D)));
		} else {
			this.handle.getDoubles().write(1, Double.valueOf(value));
		}
	}

	public double getZ() {
		return ((Double)this.handle.getDoubles().read(2)).doubleValue();
	}

	public void setZ(double value) {
		if (VersionChecker.isLowerOrEqualThan(VersionChecker.v1_8_R3)) {
			this.handle.getIntegers().write(4, Integer.valueOf((int)Math.floor(value * 32.0D)));
		} else {
			this.handle.getDoubles().write(2, Double.valueOf(value));
		}
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

	public float getHeadPitch() {
		return ((Byte)this.handle.getBytes().read(2)).byteValue() * 360.0F / 256.0F;
	}

	public void setHeadPitch(float value) {
		if (VersionChecker.isLowerOrEqualThan(VersionChecker.v1_8_R3)) {
			this.handle.getBytes().write(1, Byte.valueOf((byte)(int)(value * 256.0F / 360.0F)));
		} else {
			this.handle.getBytes().write(2, Byte.valueOf((byte)(int)(value * 256.0F / 360.0F)));
		}
	}

	public WrappedDataWatcher getMetadata() {
		return (WrappedDataWatcher)this.handle.getDataWatcherModifier().read(0);
	}

	public void setMetadata(WrappedDataWatcher value) {
		this.handle.getDataWatcherModifier().write(0, value);
	}
}

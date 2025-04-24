package me.firas.skypvp.nms.wrapper.packet;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import me.firas.skypvp.nms.AbstractPacket;

public class WrapperPlayServerEntityDestroy extends AbstractPacket {
	public static final PacketType TYPE = PacketType.Play.Server.ENTITY_DESTROY;

	public WrapperPlayServerEntityDestroy() {
		super(new PacketContainer(TYPE), TYPE);
		this.handle.getModifier().writeDefaults();
	}

	public WrapperPlayServerEntityDestroy(PacketContainer packet) {
		super(packet, TYPE);
	}

	public int getCount() {
		return ((int[])this.handle.getIntegerArrays().read(0)).length;
	}

	public int[] getEntityIDs() {
		return (int[])this.handle.getIntegerArrays().read(0);
	}

	public void setEntityIds(int[] value) {
		this.handle.getIntegerArrays().write(0, value);
	}
}
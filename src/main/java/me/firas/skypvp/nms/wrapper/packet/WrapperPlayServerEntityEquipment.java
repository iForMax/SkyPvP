package me.firas.skypvp.nms.wrapper.packet;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.EnumWrappers;
import me.firas.skypvp.nms.AbstractPacket;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

public class WrapperPlayServerEntityEquipment extends AbstractPacket {
	public static final PacketType TYPE = PacketType.Play.Server.ENTITY_EQUIPMENT;

	public WrapperPlayServerEntityEquipment() {
		super(new PacketContainer(TYPE), TYPE);
		this.handle.getModifier().writeDefaults();
	}

	public WrapperPlayServerEntityEquipment(PacketContainer packet) {
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

	public EnumWrappers.ItemSlot getSlot() {
		return (EnumWrappers.ItemSlot)this.handle.getItemSlots().read(0);
	}

	public void setSlot(EnumWrappers.ItemSlot value) {
		this.handle.getItemSlots().write(0, value);
	}

	public void setSlot(int value) {
		this.handle.getIntegers().write(1, Integer.valueOf(value));
	}

	public ItemStack getItem() {
		return (ItemStack)this.handle.getItemModifier().read(0);
	}

	public void setItem(ItemStack value) {
		this.handle.getItemModifier().write(0, value);
	}
}

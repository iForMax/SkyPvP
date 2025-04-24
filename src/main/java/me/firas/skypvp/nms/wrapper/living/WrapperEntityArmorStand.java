package me.firas.skypvp.nms.wrapper.living;

import com.comphenix.protocol.wrappers.Vector3F;
import me.firas.skypvp.nms.VersionChecker;

public class WrapperEntityArmorStand extends WrappedEntityLiving {

    private boolean small = false;

    private boolean noBasePlate = true;

    private boolean marker = false;

    private boolean arms = false;

    private static int armorIndex = VersionChecker.isLowerOrEqualThan(VersionChecker.v1_9_R2) ? 10 : 11;


    private static byte markerMask = (byte)(VersionChecker.isLowerOrEqualThan(VersionChecker.v1_8_R2) ? 22 : 16);

    private static byte armsIndex = (byte) 4;

    private static int id = 1;

    static {
        if (VersionChecker.isLowerOrEqualThan(VersionChecker.v1_12_R1))
            id = 30;
    }

    public WrapperEntityArmorStand() {
        super(id);
    }

    public void setIver(float x, float y, float z) {
        setDataWatcherObject(Vector3F.getMinecraftClass(),14,new Vector3F(x, y, z));
    }


    public void setArms(boolean arms){
        this.arms = arms;
        setDataWatcherObject(Byte.class,armorIndex,Byte.valueOf((byte)((this.small ? 1 : 0) | (this.noBasePlate ? 8 : 0) | (this.marker ? markerMask : 0) | (arms ? armsIndex : 0))));
    }


    public void setMarker(boolean marker) {
        this.marker = marker;
        setDataWatcherObject(Byte.class, armorIndex, Byte.valueOf((byte)((this.small ? 1 : 0) | (this.noBasePlate ? 8 : 0) | (marker ? markerMask : 0) | (this.arms ? armsIndex : 0))));
    }



}

package me.firas.skypvp.regions;

import lombok.Data;
import org.bukkit.Location;
@Data
public class Region {
        private final String name;
        private Location pos1;
        private Location pos2;
        private boolean pvp;
        public Region(String name) {
            this.name = name;
        }
        public boolean contains(Location location) {
            if (!location.getWorld().equals(pos1.getWorld())) {
                return false;
            }

            double x = location.getX();
            double y = location.getY();
            double z = location.getZ();

            double x1 = pos1.getX();
            double y1 = pos1.getY();
            double z1 = pos1.getZ();

            double x2 = pos2.getX();
            double y2 = pos2.getY();
            double z2 = pos2.getZ();

            double maxX = Math.max(x1, x2);
            double maxY = Math.max(y1, y2);
            double maxZ = Math.max(z1, z2);

            double minX = Math.min(x1, x2);
            double minY = Math.min(y1, y2);
            double minZ = Math.min(z1, z2);

            return (x >= minX && x <= maxX) &&
                    (y >= minY && y <= maxY) &&
                    (z >= minZ && z <= maxZ);
        }
}

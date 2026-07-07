package tech.ccat.cheattest.item;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.util.Vector;
import tech.ccat.cheattest.config.ConfigManager;

public class ObsidianCorroderItem extends AbstractItem {
    public static final String TYPE = "OBSIDIAN_CORRODER";
    private static final int RADIUS = 5;
    private static final int RADIUS_SQUARED = RADIUS * RADIUS;

    public ObsidianCorroderItem() {
        super(TYPE, Material.STICK);
    }

    @Override
    public boolean isEnabled(ConfigManager config) {
        return config.isMythicEnable() && config.isMythicObsidianCorroder();
    }

    @Override
    public void onUse(ItemUseContext context) {
        Block target = findFirstBlockedBlock(context.getPlayer().getEyeLocation(), 64);
        if (target != null && target.getType() == Material.OBSIDIAN) {
            corrodeObsidianSphere(target);
        }
    }

    private void corrodeObsidianSphere(Block center) {
        for (int x = -RADIUS; x <= RADIUS; x++) {
            for (int y = -RADIUS; y <= RADIUS; y++) {
                for (int z = -RADIUS; z <= RADIUS; z++) {
                    if (x * x + y * y + z * z > RADIUS_SQUARED) {
                        continue;
                    }

                    Block block = center.getRelative(x, y, z);
                    if (block.getType() == Material.OBSIDIAN) {
                        block.setType(Material.SAND);
                    }
                }
            }
        }
    }

    private Block findFirstBlockedBlock(Location eyeLocation, int maxDistance) {
        Vector direction = eyeLocation.getDirection().normalize();
        for (double distance = 0.5; distance <= maxDistance; distance += 0.5) {
            Block block = eyeLocation.clone().add(direction.clone().multiply(distance)).getBlock();
            if (!block.isEmpty()) {
                return block;
            }
        }
        return null;
    }
}

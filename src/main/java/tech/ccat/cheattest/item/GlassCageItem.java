package tech.ccat.cheattest.item;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import tech.ccat.cheattest.config.ConfigManager;

public class GlassCageItem extends AbstractItem {
    public static final String TYPE = "GLASS_CAGE";

    public GlassCageItem() {
        super(TYPE, Material.STICK);
    }

    @Override
    public boolean isEnabled(ConfigManager config) {
        return config.isMythicEnable() && config.isMythicGlassCage();
    }

    @Override
    public void onUse(ItemUseContext context) {
        Entity target = findTargetEntity(context.getPlayer(), 64);
        if (target == null) {
            return;
        }

        createCage(target.getLocation());
    }

    private void createCage(Location center) {
        int centerX = center.getBlockX();
        int baseY = center.getBlockY();
        int centerZ = center.getBlockZ();

        for (int x = -2; x <= 2; x++) {
            for (int y = 0; y <= 2; y++) {
                for (int z = -2; z <= 2; z++) {
                    boolean shell = Math.abs(x) == 2 || Math.abs(z) == 2 || y == 0 || y == 2;
                    if (!shell) {
                        continue;
                    }

                    Block block = center.getWorld().getBlockAt(centerX + x, baseY + y, centerZ + z);
                    if (block.getType() != Material.BEDROCK) {
                        block.setType(Material.GLASS);
                    }
                }
            }
        }
    }
}

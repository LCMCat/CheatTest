package tech.ccat.cheattest.item;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import tech.ccat.cheattest.config.ConfigManager;

public class DrinkerItem extends AbstractItem {
    public static final String TYPE = "DRINKER";

    public DrinkerItem() {
        super(TYPE, Material.BUCKET);
    }

    @Override
    public boolean isEnabled(ConfigManager config) {
        return config.isMythicEnable() && config.isMythicDrinker();
    }

    @Override
    public void onUse(ItemUseContext context) {
        Location targetLocation = findTargetBlock(context.getPlayer(), 100, true);
        clearNearbyFluid(targetLocation, context.getPlayer().isSneaking() ? 10 : 5);
    }

    private void clearNearbyFluid(Location targetLocation, int range) {
        if (targetLocation == null) {
            return;
        }

        for (int x = -range; x <= range; x++) {
            for (int y = -range; y <= range; y++) {
                for (int z = -range; z <= range; z++) {
                    Block block = targetLocation.clone().add(x, y, z).getBlock();
                    if (block.isLiquid()) {
                        block.setType(Material.AIR);
                    }
                }
            }
        }
    }
}

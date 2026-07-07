package tech.ccat.cheattest.item;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.util.Vector;
import tech.ccat.cheattest.config.ConfigManager;

public class ObsidianWallItem extends AbstractItem {
    public static final String TYPE = "OBSIDIAN_WALL";

    private static final int[][] DIRECTIONS = {
            {0, 1},
            {-1, 1},
            {-1, 0},
            {-1, -1},
            {0, -1},
            {1, -1},
            {1, 0},
            {1, 1}
    };

    public ObsidianWallItem() {
        super(TYPE, Material.STICK);
    }

    @Override
    public boolean isEnabled(ConfigManager config) {
        return config.isMythicEnable() && config.isMythicObsidianWall();
    }

    @Override
    public void onUse(ItemUseContext context) {
        Location playerLocation = context.getPlayer().getLocation();
        Vector eyeDirection = context.getPlayer().getEyeLocation().getDirection();
        if (eyeDirection.getY() >= 0.75) {
            createHorizontalWall(playerLocation, playerLocation.getBlockY() + 2);
            return;
        }
        if (eyeDirection.getY() <= -0.75) {
            createHorizontalWall(playerLocation, playerLocation.getBlockY() - 1);
            return;
        }

        int[] direction = nearestDirection(eyeDirection);
        int directionX = direction[0];
        int directionZ = direction[1];
        int perpendicularX = -directionZ;
        int perpendicularZ = directionX;

        int centerX = playerLocation.getBlockX() + directionX * 3;
        int baseY = playerLocation.getBlockY();
        int centerZ = playerLocation.getBlockZ() + directionZ * 3;

        for (int width = -2; width <= 2; width++) {
            for (int height = 0; height <= 4; height++) {
                Block block = playerLocation.getWorld().getBlockAt(
                        centerX + perpendicularX * width,
                        baseY + height,
                        centerZ + perpendicularZ * width
                );
                if (block.getType() != Material.BEDROCK) {
                    block.setType(Material.OBSIDIAN);
                }
            }
        }
    }

    private void createHorizontalWall(Location playerLocation, int centerY) {
        int centerX = playerLocation.getBlockX();
        int centerZ = playerLocation.getBlockZ();

        for (int x = -2; x <= 2; x++) {
            for (int z = -2; z <= 2; z++) {
                Block block = playerLocation.getWorld().getBlockAt(centerX + x, centerY, centerZ + z);
                if (block.getType() != Material.BEDROCK) {
                    block.setType(Material.OBSIDIAN);
                }
            }
        }
    }

    private int[] nearestDirection(Vector direction) {
        Vector horizontal = direction.clone();
        horizontal.setY(0);
        if (horizontal.lengthSquared() == 0) {
            return new int[]{0, 1};
        }
        horizontal.normalize();

        int[] nearest = DIRECTIONS[0];
        double bestDot = -Double.MAX_VALUE;
        for (int[] candidate : DIRECTIONS) {
            Vector candidateVector = new Vector(candidate[0], 0, candidate[1]).normalize();
            double dot = horizontal.dot(candidateVector);
            if (dot > bestDot) {
                bestDot = dot;
                nearest = candidate;
            }
        }
        return nearest;
    }
}

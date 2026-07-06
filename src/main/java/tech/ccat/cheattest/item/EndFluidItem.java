package tech.ccat.cheattest.item;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import tech.ccat.cheattest.config.ConfigManager;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

public class EndFluidItem extends AbstractItem {
    public static final String TYPE = "ENDFLUID";

    private static final BlockFace[] FACES = {
            BlockFace.UP,
            BlockFace.DOWN,
            BlockFace.NORTH,
            BlockFace.SOUTH,
            BlockFace.WEST,
            BlockFace.EAST
    };

    private final ConfigManager config;

    public EndFluidItem(ConfigManager config) {
        super(TYPE, Material.BUCKET);
        this.config = config;
    }

    @Override
    public boolean isEnabled(ConfigManager config) {
        return config.isMythicEnable() && config.isMythicEndFluidEnable();
    }

    @Override
    public void onUse(ItemUseContext context) {
        Location targetLocation = findTargetBlock(context.getPlayer(), 100, true);
        if (targetLocation != null) {
            clearConnectedFluids(targetLocation.getBlock(), config.getMythicEndFluidMax());
        }
    }

    private void clearConnectedFluids(Block start, int maxBlocks) {
        if (start == null || !start.isLiquid() || maxBlocks <= 0) {
            return;
        }

        Queue<Block> queue = new ArrayDeque<>();
        Set<Long> visited = new HashSet<>();

        queue.add(start);
        visited.add(blockKey(start));

        int cleared = 0;
        while (!queue.isEmpty() && cleared < maxBlocks) {
            Block block = queue.poll();
            if (!block.isLiquid()) {
                continue;
            }

            block.setType(Material.AIR);
            cleared++;

            for (BlockFace face : FACES) {
                Block next = block.getRelative(face);
                long key = blockKey(next);
                if (!visited.contains(key) && next.isLiquid()) {
                    visited.add(key);
                    queue.add(next);
                }
            }
        }
    }

    private long blockKey(Block block) {
        return (((long) block.getX() & 0x3FFFFFFL) << 38)
                | (((long) block.getZ() & 0x3FFFFFFL) << 12)
                | ((long) block.getY() & 0xFFFL);
    }
}

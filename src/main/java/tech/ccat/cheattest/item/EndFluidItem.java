package tech.ccat.cheattest.item;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import tech.ccat.cheattest.config.ConfigManager;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class EndFluidItem extends AbstractItem {
    public static final String TYPE = "END_FLUID";

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
        use(context.getPlayer());
    }

    public void use(Player player) {
        clearConnectedFluids(findTargetFluid(player, 100), config.getMythicEndFluidMax());
    }

    private Block findTargetFluid(Player player, int maxDistance) {
        Location eyeLocation = player.getEyeLocation();
        Vector direction = eyeLocation.getDirection().normalize();

        for (double distance = 0.0; distance <= maxDistance; distance += 0.25) {
            Block block = eyeLocation.clone().add(direction.clone().multiply(distance)).getBlock();
            if (block.isLiquid()) {
                return block;
            }
            if (!block.isEmpty()) {
                return findFluidStart(block);
            }
        }
        return null;
    }

    private Block findFluidStart(Block target) {
        if (target == null) {
            return null;
        }
        if (target.isLiquid()) {
            return target;
        }

        for (BlockFace face : FACES) {
            Block relative = target.getRelative(face);
            if (relative.isLiquid()) {
                return relative;
            }
        }
        return null;
    }

    private void clearConnectedFluids(Block start, int maxBlocks) {
        if (start == null || !start.isLiquid() || maxBlocks <= 0) {
            return;
        }

        Queue<Block> queue = new ArrayDeque<>();
        Set<Long> visited = new HashSet<>();
        List<Block> fluids = new ArrayList<>();

        queue.add(start);
        visited.add(blockKey(start));

        while (!queue.isEmpty() && fluids.size() < maxBlocks) {
            Block block = queue.poll();
            if (!block.isLiquid()) {
                continue;
            }

            fluids.add(block);

            for (BlockFace face : FACES) {
                Block next = block.getRelative(face);
                long key = blockKey(next);
                if (!visited.contains(key) && next.isLiquid()) {
                    visited.add(key);
                    queue.add(next);
                }
            }
        }

        for (Block block : fluids) {
            block.setType(Material.AIR, false);
        }
    }

    private long blockKey(Block block) {
        return (((long) block.getX() & 0x3FFFFFFL) << 38)
                | (((long) block.getZ() & 0x3FFFFFFL) << 12)
                | ((long) block.getY() & 0xFFFL);
    }
}

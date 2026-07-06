package tech.ccat.cheattest.modules;

import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import tech.ccat.cheattest.Main;

//广度优先搜索...信竞生泪目
//Stackoverflow.. java似乎不是那么好用
@Deprecated(since = "StackOverflow")
public class BucketOfEndFluid extends CModule {
    private Location startLocation;
    boolean isStarted;

    int Max = config.getMythicEndFluidMax();

        private final boolean[] visited;
    private final BlockFace[] faces = {BlockFace.UP, BlockFace.DOWN, BlockFace.NORTH, BlockFace.SOUTH, BlockFace.WEST, BlockFace.EAST};

    protected BucketOfEndFluid(Main INSTANCE) {
        super(INSTANCE);
        isStarted = false;
        this.visited = new boolean[Max * Max * Max];
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (!config.isMythicEnable() || !config.isMythicDrinker())
            return;
        Player player = event.getPlayer();
        if (IsItemExist(player.getInventory().getItemInMainHand())) {

            NBTItem nbtItem = new NBTItem(player.getInventory().getItemInMainHand());
            if (nbtItem.hasNBTData() && nbtItem.getString("ITEM_TYPE").equals("ENDFLUID")) {
                startLocation = getPlayerLookingAt(player, 100);
                if (startLocation != null && !isStarted) {
                    isStarted = true;
                    clearConnectedFluids(startLocation.getBlock());
                    isStarted = false;
                }
            }

        }

        if (IsItemExist(player.getInventory().getItemInOffHand())) {

            NBTItem nbtItem1 = new NBTItem(player.getInventory().getItemInOffHand());
            if (nbtItem1.hasNBTData() && nbtItem1.getString("ITEM_TYPE").equals("ENDFLUID")) {
                startLocation = getPlayerLookingAt(player, 100);
                if (startLocation != null && !isStarted) {
                    isStarted = true;
                    clearConnectedFluids(startLocation.getBlock());
                    isStarted = false;
                }
            }

        }
    }

    private boolean IsItemExist(ItemStack item) {
        return item != null && item.getType() != Material.AIR && item.getAmount() > 0;
    }

    public static Location getPlayerLookingAt(Player player, int maxDistance) {
        Location eyeLocation = player.getEyeLocation();
        Vector direction = eyeLocation.getDirection().normalize();

        for (int i = 0; i < maxDistance; i++) {
            Location loc = eyeLocation.clone().add(direction.clone().multiply(i));
            Block block = loc.getBlock();
            if (!block.isEmpty()) {
                return block.getLocation();
            }
        }
        return null;
    }

    private void clearConnectedFluids(Block block) {
        if (block.isLiquid()) {
            int index = getBlockIndex(block);
            if (!visited[index]) {
                visited[index] = true;
            block.setType(Material.AIR);
            for (BlockFace face : faces) {
                Block neighbor = block.getRelative(face);
                if (neighbor.isLiquid()) {
                    clearConnectedFluids(neighbor); // 递归清除相邻的流体
                }
                }
            }
        }
    }

    private int getBlockIndex(Block block) {
        int x = block.getX();
        int y = block.getY();
        int z = block.getZ();
        return x * Max * Max + z * Max + y;
    }
}

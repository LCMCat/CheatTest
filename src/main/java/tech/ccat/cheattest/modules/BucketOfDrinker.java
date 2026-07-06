package tech.ccat.cheattest.modules;

import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import tech.ccat.cheattest.Main;

import java.util.List;

public class BucketOfDrinker extends CModule{
    protected BucketOfDrinker(Main INSTANCE) {
        super(INSTANCE);
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerInteract(PlayerInteractEvent event){
        if(!config.isMythicEnable() || !config.isMythicDrinker())
            return;
        Player player = event.getPlayer();
        if(IsItemExist(player.getInventory().getItemInMainHand())){

            NBTItem nbtItem = new NBTItem(player.getInventory().getItemInMainHand());
            if(nbtItem.hasNBTData() && nbtItem.getString("ITEM_TYPE").equals("DRINKER")){
                if(player.isSneaking()) clearNearbyFluid(getPlayerLookingAt(player, 100), 10);
                else clearNearbyFluid(getPlayerLookingAt(player, 100), 5);
            }

        }

        if(IsItemExist(player.getInventory().getItemInOffHand())){

            NBTItem nbtItem1 = new NBTItem(player.getInventory().getItemInOffHand());
            if(nbtItem1.hasNBTData() && nbtItem1.getString("ITEM_TYPE").equals("DRINKER")){
                if(player.isSneaking()) clearNearbyFluid(getPlayerLookingAt(player, 100), 10);
                else clearNearbyFluid(getPlayerLookingAt(player, 100), 5);
            }

        }
    }

    private boolean IsItemExist(ItemStack item){
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

    public void clearNearbyFluid(Location targetLocation, int range) {
        if(targetLocation == null)
            return;
        for (int x = -range; x <= range; x++) {
            for (int y = -range; y <= range; y++) {
                for (int z = -range; z <= range; z++) {
                    Location loc = targetLocation.clone().add(x, y, z);
                    Block block = loc.getBlock();
                    if (block.isLiquid()) {
                        block.setType(Material.AIR);
                    }
                }
            }
        }
    }

}

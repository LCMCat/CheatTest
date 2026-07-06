package tech.ccat.cheattest.modules;

import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import tech.ccat.cheattest.Main;

import java.util.List;

public class StickOfDestruction extends CModule{
    protected StickOfDestruction(Main INSTANCE) {
        super(INSTANCE);
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerInteract(PlayerInteractEvent event){
        if(!config.isMythicEnable() || !config.isMythicDestruction())
            return;
        Player player = event.getPlayer();
        World world = player.getWorld();
        if(IsItemExist(player.getInventory().getItemInMainHand())){

            NBTItem nbtItem = new NBTItem(player.getInventory().getItemInMainHand());
            if(nbtItem.hasNBTData() && nbtItem.getString("ITEM_TYPE").equals("DESTRUCTION")){
                Location target = getPlayerLookingAt(player, 100);
                if(target!=null){
                    world.spawnEntity(target, EntityType.LIGHTNING);
                    if(player.isSneaking()) world.createExplosion(target, 10);
                    else world.createExplosion(target, 5);
                }
            }

        }

        if(IsItemExist(player.getInventory().getItemInOffHand())){

            NBTItem nbtItem1 = new NBTItem(player.getInventory().getItemInOffHand());
            if(nbtItem1.hasNBTData() && nbtItem1.getString("ITEM_TYPE").equals("DESTRUCTION")){
                Location target = getPlayerLookingAt(player, 100);
                if(target!=null)
                    player.sendMessage(target.toString());
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
            List<Entity> nearbyEntities = (List<Entity>) loc.getWorld().getNearbyEntities(loc, 1, 1, 1);
            for (Entity entity : nearbyEntities) {
                if (entity.getLocation().distanceSquared(loc) < 1 && !entity.getName().equals(player.getName()) && !(entity instanceof Item)) {
                    return entity.getLocation();
                }
            }
            Block block = loc.getBlock();
            if (!block.isEmpty() && !block.isLiquid()) {
                return block.getLocation();
            }
        }
        return null;
    }
}

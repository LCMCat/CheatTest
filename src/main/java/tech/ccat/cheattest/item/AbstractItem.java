package tech.ccat.cheattest.item;

import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import tech.ccat.cheattest.config.ConfigManager;

public abstract class AbstractItem {
    public static final String ITEM_TYPE_KEY = "ITEM_TYPE";

    private final String type;
    private final Material material;

    protected AbstractItem(String type, Material material) {
        this.type = type;
        this.material = material;
    }

    public String getType() {
        return type;
    }

    public ItemStack createItemStack() {
        ItemStack itemStack = new ItemStack(material, 1);
        NBTItem nbtItem = new NBTItem(itemStack);
        nbtItem.setString(ITEM_TYPE_KEY, type);
        return nbtItem.getItem();
    }

    public boolean matches(ItemStack itemStack) {
        if (!isUsableItem(itemStack)) {
            return false;
        }
        NBTItem nbtItem = new NBTItem(itemStack);
        return nbtItem.hasNBTData() && type.equalsIgnoreCase(nbtItem.getString(ITEM_TYPE_KEY));
    }

    public abstract boolean isEnabled(ConfigManager config);

    public abstract void onUse(ItemUseContext context);

    protected boolean isUsableItem(ItemStack itemStack) {
        return itemStack != null && itemStack.getType() != Material.AIR && itemStack.getAmount() > 0;
    }

    protected Location findTargetBlock(Player player, int maxDistance, boolean allowLiquid) {
        Location eyeLocation = player.getEyeLocation();
        Vector direction = eyeLocation.getDirection().normalize();

        for (int i = 0; i < maxDistance; i++) {
            Location location = eyeLocation.clone().add(direction.clone().multiply(i));
            Block block = location.getBlock();
            if (!block.isEmpty() && (allowLiquid || !block.isLiquid())) {
                return block.getLocation();
            }
        }
        return null;
    }

    protected Location findTargetEntityOrBlock(Player player, int maxDistance) {
        Location eyeLocation = player.getEyeLocation();
        Vector direction = eyeLocation.getDirection().normalize();

        for (int i = 0; i < maxDistance; i++) {
            Location location = eyeLocation.clone().add(direction.clone().multiply(i));
            for (Entity entity : location.getWorld().getNearbyEntities(location, 1, 1, 1)) {
                if (entity instanceof Item || entity.getName().equals(player.getName())) {
                    continue;
                }
                if (entity.getLocation().distanceSquared(location) < 1) {
                    return entity.getLocation();
                }
            }

            Block block = location.getBlock();
            if (!block.isEmpty() && !block.isLiquid()) {
                return block.getLocation();
            }
        }
        return null;
    }
}

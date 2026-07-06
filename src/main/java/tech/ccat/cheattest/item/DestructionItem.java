package tech.ccat.cheattest.item;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import tech.ccat.cheattest.config.ConfigManager;

public class DestructionItem extends AbstractItem {
    public static final String TYPE = "DESTRUCTION";

    public DestructionItem() {
        super(TYPE, Material.STICK);
    }

    @Override
    public boolean isEnabled(ConfigManager config) {
        return config.isMythicEnable() && config.isMythicDestruction();
    }

    @Override
    public void onUse(ItemUseContext context) {
        Location target = findTargetEntityOrBlock(context.getPlayer(), 100);
        if (target == null) {
            return;
        }

        if (context.getHandSlot() == HandSlot.OFF_HAND) {
            context.getPlayer().sendMessage(target.toString());
            return;
        }

        World world = context.getPlayer().getWorld();
        world.spawnEntity(target, EntityType.LIGHTNING);
        world.createExplosion(target, context.getPlayer().isSneaking() ? 10 : 5);
    }
}

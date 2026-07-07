package tech.ccat.cheattest.item;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;
import tech.ccat.cheattest.config.ConfigManager;

public class PullWebItem extends AbstractItem {
    public static final String TYPE = "PULL_WEB";

    public PullWebItem() {
        super(TYPE, Material.WEB);
    }

    @Override
    public boolean isEnabled(ConfigManager config) {
        return config.isMythicEnable() && config.isMythicPullWeb();
    }

    @Override
    public void onUse(ItemUseContext context) {
        Entity target = findTargetEntity(context.getPlayer(), 64);
        if (target == null) {
            return;
        }

        Vector pull = context.getPlayer().getLocation().toVector().subtract(target.getLocation().toVector());
        if (pull.lengthSquared() == 0) {
            return;
        }

        target.setVelocity(pull.normalize().multiply(2.2));
    }
}

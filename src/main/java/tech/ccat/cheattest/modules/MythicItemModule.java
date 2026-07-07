package tech.ccat.cheattest.modules;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import tech.ccat.cheattest.Main;
import tech.ccat.cheattest.item.AbstractItem;
import tech.ccat.cheattest.item.EndFluidItem;
import tech.ccat.cheattest.item.HandSlot;
import tech.ccat.cheattest.item.ItemUseContext;

import java.util.Optional;

public class MythicItemModule extends CModule {
    protected MythicItemModule(Main INSTANCE) {
        super(INSTANCE);
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (!config.isMythicEnable()) {
            return;
        }

        EquipmentSlot hand = event.getHand();
        if (hand == EquipmentSlot.OFF_HAND) {
            useItem(event, event.getPlayer().getInventory().getItemInOffHand(), HandSlot.OFF_HAND);
            return;
        }

        useItem(event, event.getPlayer().getInventory().getItemInMainHand(), HandSlot.MAIN_HAND);
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerBucketFill(PlayerBucketFillEvent event) {
        if (!config.isMythicEnable()) {
            return;
        }

        useBucketItem(event, event.getPlayer().getInventory().getItemInMainHand());
        useBucketItem(event, event.getPlayer().getInventory().getItemInOffHand());
    }

    private void useItem(PlayerInteractEvent event, ItemStack itemStack, HandSlot handSlot) {
        Optional<AbstractItem> item = INSTANCE.getItemRegistry().findMatching(itemStack)
                .filter(mythicItem -> mythicItem.isEnabled(config));
        item.ifPresent(mythicItem -> {
            event.setCancelled(true);
            mythicItem.onUse(new ItemUseContext(INSTANCE, event, itemStack, handSlot));
        });
    }

    private void useBucketItem(PlayerBucketFillEvent event, ItemStack itemStack) {
        Optional<AbstractItem> item = INSTANCE.getItemRegistry().findMatching(itemStack)
                .filter(mythicItem -> mythicItem.isEnabled(config))
                .filter(mythicItem -> mythicItem instanceof EndFluidItem);
        item.ifPresent(mythicItem -> {
            event.setCancelled(true);
            ((EndFluidItem) mythicItem).use(event.getPlayer());
        });
    }
}

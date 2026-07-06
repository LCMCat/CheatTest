package tech.ccat.cheattest.item;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import tech.ccat.cheattest.Main;

public class ItemUseContext {
    private final Main plugin;
    private final PlayerInteractEvent event;
    private final Player player;
    private final ItemStack itemStack;
    private final HandSlot handSlot;

    public ItemUseContext(Main plugin, PlayerInteractEvent event, ItemStack itemStack, HandSlot handSlot) {
        this.plugin = plugin;
        this.event = event;
        this.player = event.getPlayer();
        this.itemStack = itemStack;
        this.handSlot = handSlot;
    }

    public Main getPlugin() {
        return plugin;
    }

    public PlayerInteractEvent getEvent() {
        return event;
    }

    public Player getPlayer() {
        return player;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public HandSlot getHandSlot() {
        return handSlot;
    }
}

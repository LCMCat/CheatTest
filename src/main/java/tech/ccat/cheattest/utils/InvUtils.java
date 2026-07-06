package tech.ccat.cheattest.utils;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class InvUtils {
    public static boolean addToFirstEmptySlot(Player player, ItemStack itemStack) {
        int slot = player.getInventory().firstEmpty();
        if (slot == -1) {
            return false;
        }

        player.getInventory().setItem(slot, itemStack);
        return true;
    }
}

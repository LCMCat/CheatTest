package tech.ccat.cheattest.utils;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.Map;
import java.util.stream.Stream;

public class InvUtils {
    public static int FindAirSlot(Player player){
        for(int i=0; i<=35; i++){
            if(player.getInventory().getItem(i) == null)
                return i;
        }
        return -1;
    }
    public static boolean SpawnItem(Player player, ItemStack itemStack){
        int i = FindAirSlot(player);
        if(i != -1){
            player.getInventory().setItem(i, itemStack);
            return true;
        }else{
            return false;
        }
    }

    public static boolean isLegalItem(ItemStack itemStack){
        if(itemStack == null)
            return true;
        //多堆叠物品
        if(itemStack.getAmount() > itemStack.getType().getMaxStackSize()) {
            return false;
        }
        Map<Enchantment, Integer> enchantments = itemStack.getEnchantments();

        //非法附魔物品
        for (Enchantment enchant: enchantments.keySet()) {
            if(enchantments.get(enchant) > enchant.getMaxLevel()){
                return false;
            }
        }

        return true;
    }
}

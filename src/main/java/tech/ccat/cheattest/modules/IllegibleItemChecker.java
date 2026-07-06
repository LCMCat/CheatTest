package tech.ccat.cheattest.modules;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import tech.ccat.cheattest.Main;

import java.util.List;
import java.util.Map;

public class IllegibleItemChecker extends CModule{

    int IICCooldown;
    List<Material> bannedItem = List.of(Material.BARRIER, Material.MONSTER_EGG, Material.MONSTER_EGGS, Material.COMMAND
            , Material.COMMAND_CHAIN, Material.COMMAND_REPEATING, Material.COMMAND_MINECART, Material.DEAD_BUSH);

    protected IllegibleItemChecker(Main INSTANCE) {
        super(INSTANCE);
        IICCooldown = config.getIICCooldown();
        if(config.isIICEnable()) INSTANCE.getServer().getScheduler().runTaskTimerAsynchronously(INSTANCE, this::IllegibleItemScheduler, 0 , IICCooldown);
    }


    private void IllegibleItemScheduler(){
        INSTANCE.getServer().getOnlinePlayers().forEach((player) -> {
            PlayerInventory inventory = player.getInventory();
            for(int i=0; i<=35; i++){
                ItemStack itemStack = inventory.getItem(i);
                if(!checker(itemStack)) {
                    player.sendMessage("§7你背包里的一件神秘物品已被清除!");
                    itemStack.setAmount(0);
                }
            }
        });
    }

    private boolean checker(ItemStack itemStack){
        if(itemStack == null)
            return true;
        //多堆叠物品
        if(itemStack.getAmount() > itemStack.getType().getMaxStackSize()) {
            return false;
        }

        //非法附魔物品
        Map<Enchantment, Integer> enchantments = itemStack.getEnchantments();
        for (Enchantment enchant: enchantments.keySet()) {
            if(enchantments.get(enchant) > enchant.getMaxLevel()){
                itemStack.addEnchantment(enchant, enchant.getMaxLevel());
            }
        }

        //禁止物品
        if(bannedItem.contains(itemStack.getType())){
            return false;
        }

        return true;
    }
}

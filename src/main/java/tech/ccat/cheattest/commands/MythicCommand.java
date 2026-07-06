package tech.ccat.cheattest.commands;

import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import tech.ccat.cheattest.Main;
import tech.ccat.cheattest.utils.InvUtils;

public class MythicCommand extends CCommand{
    protected MythicCommand(Main INSTANCE) {
        super(INSTANCE);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if(!commandSender.hasPermission("cheattest.mythic")){
            commandSender.sendMessage("§c你没有这个权限!");
            return true;
        }
        if(!(commandSender instanceof Player player)){
            commandSender.sendMessage("§4该命令只能玩家执行!");
            return true;
        }
        if(!config.isMythicCommand()){
            commandSender.sendMessage("§c该功能已被禁用!");
            return true;
        }
        if(args.length < 1){
            player.sendMessage("§c请输入参数!");
            return true;
        }
        ItemStack itemStack = new ItemStack(Material.STICK, 1);
        NBTItem nbt = new NBTItem(itemStack);
        nbt.setString("ITEM_TYPE", args[0]);
        itemStack = nbt.getItem();
        InvUtils.SpawnItem(player, itemStack);
        commandSender.sendMessage("§7你获取了一件神秘物品.");

        return true;
    }
}

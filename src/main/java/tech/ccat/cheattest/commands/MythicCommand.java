package tech.ccat.cheattest.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tech.ccat.cheattest.Main;
import tech.ccat.cheattest.item.AbstractItem;
import tech.ccat.cheattest.utils.InvUtils;

import java.util.Optional;

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
        Optional<AbstractItem> item = INSTANCE.getItemRegistry().findByType(args[0]);
        if (item.isEmpty()) {
            player.sendMessage("§c未知的神秘物品类型!");
            return true;
        }
        if (!InvUtils.addToFirstEmptySlot(player, item.get().createItemStack())) {
            player.sendMessage("§c背包已满!");
            return true;
        }
        commandSender.sendMessage("§7你获取了一件神秘物品.");

        return true;
    }
}

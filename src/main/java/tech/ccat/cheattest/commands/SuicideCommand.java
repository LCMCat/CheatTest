package tech.ccat.cheattest.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import tech.ccat.cheattest.Main;
import tech.ccat.cheattest.player.CPlayer;

public class SuicideCommand extends CCommand{


    protected SuicideCommand(Main INSTANCE) {
        super(INSTANCE);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if(!commandSender.hasPermission("cheattest.suicide")){
            commandSender.sendMessage("§c你没有这个权限!");
            return true;
        }
        if(!(commandSender instanceof Player player)){
            commandSender.sendMessage("§4该命令只能玩家执行!");
            return true;
        }
        if(!config.isSuicideEnable()){
            commandSender.sendMessage("§c该功能已被禁用!");
            return true;
        }
        player.sendMessage("§3再见了, 残酷的世界...");
        player.setHealth(0);
        return true;
    }


}

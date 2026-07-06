package tech.ccat.cheattest.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import tech.ccat.cheattest.Main;
import tech.ccat.cheattest.player.CPlayer;

public class DupeCommand extends CCommand{


    private Long dupeDelay;

    protected DupeCommand(Main INSTANCE) {
        super(INSTANCE);
        config = INSTANCE.getConfigManager();
        dupeDelay = config.getDupeDelay();
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if(!commandSender.hasPermission("cheattest.dupe")){
            commandSender.sendMessage("§c你没有这个权限!");
            return true;
        }
        if(!(commandSender instanceof Player player)){
            commandSender.sendMessage("§4该命令只能玩家执行!");
            return true;
        }
        if(!config.isDupeEnable()){
            commandSender.sendMessage("§c该功能已被禁用!");
            return true;
        }
        CPlayer cPlayer = INSTANCE.getPlayerManager().getCPlayer(player);
        Long timePassed = System.currentTimeMillis() - cPlayer.getLastDupeMs();
        if(timePassed < dupeDelay){
            commandSender.sendMessage("§c请等待 " + (dupeDelay - timePassed)/1000 + " 秒!");
            return true;
        }
        ItemStack itemStack = player.getInventory().getItemInMainHand();
        itemStack.setAmount(64);
        cPlayer.updateDupeTimer();
        return true;
    }


}

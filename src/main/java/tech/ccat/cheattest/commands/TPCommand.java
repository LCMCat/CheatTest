package tech.ccat.cheattest.commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tech.ccat.cheattest.Main;
import tech.ccat.cheattest.player.CPlayer;

public class TPCommand extends CCommand{


    private final long TPDelay;
    private final long TPMaxX;
    private final long TPMaxY;
    private final long TPMaxZ;


    protected TPCommand(Main INSTANCE) {
        super(INSTANCE);
        TPDelay = config.getTPDelay();
        TPMaxX = config.getTPMaxX();
        TPMaxY = config.getTPMaxY();
        TPMaxZ = config.getTPMaxZ();
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if(!commandSender.hasPermission("cheattest.tp")){
            commandSender.sendMessage("§c你没有这个权限!");
            return true;
        }
        if(!(commandSender instanceof Player player)){
            commandSender.sendMessage("§4该命令只能玩家执行!");
            return true;
        }
        if(!config.isTPEnable()){
            commandSender.sendMessage("§c该功能已被禁用!");
            return true;
        }
        CPlayer cPlayer = INSTANCE.getPlayerManager().getCPlayer(player);
        long timePassed = System.currentTimeMillis() - cPlayer.getLastTPMs();
        if(timePassed < TPDelay){
            commandSender.sendMessage("§c请等待 " + (TPDelay - timePassed)/1000 + " 秒!");
            return true;
        }
        Location termination = passArgs(player, args);
        if(termination != null) {
            player.teleport(termination);
            cPlayer.updateTPTimer();
        }
        return true;
    }

    private Location passArgs(Player player, String[] args){
        if(args.length < 3){
            player.sendMessage("§c缺少参数! 正确用法: //tp <x> <y> <z>");
            return null;
        }

        double x,y,z;

        try{
            x = Double.parseDouble(args[0]);
            y = Double.parseDouble(args[1]);
            z = Double.parseDouble(args[2]);
        }catch (NumberFormatException ignored){
            player.sendMessage("§c非法的参数! 正确用法: //tp <x> <y> <z>");
            return null;
        }

        if(Math.abs(x) > TPMaxX || Math.abs(y) > TPMaxY || Math.abs(z) > TPMaxZ){
            player.sendMessage("§c过大的参数绝对值! 请修改参数.");
            return null;
        }

        return new Location(player.getWorld(), x, y, z);
    }

}

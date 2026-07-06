package tech.ccat.cheattest.modules;

import org.bukkit.GameMode;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import tech.ccat.cheattest.Main;

import java.util.List;

public class AntiIllegibleOP extends CModule{
    List<String> AntiIOPLegibleOP;
    boolean AntiIOPGamemode;
    boolean AntiIOPPunish;

    protected AntiIllegibleOP(Main INSTANCE) {
        super(INSTANCE);
        AntiIOPLegibleOP = config.getAntiIOPLegibleOP();
        AntiIOPGamemode = config.isAntiIOPGamemode();
        AntiIOPPunish = config.isAntiIOPPunish();
        if(config.isAntiIOPEnable()) INSTANCE.getServer().getScheduler().runTaskTimerAsynchronously(INSTANCE, this::runnable, 0, 20);
    }

    private void punish(Player player){
        if(!AntiIOPPunish)
            return;
        player.setVelocity(new Vector(0, 5, 0));
        new BukkitRunnable(){

            public void run() {
                player.getWorld().spawnEntity(player.getLocation(), EntityType.LIGHTNING);
                player.getWorld().createExplosion(player.getLocation(), 5, false);
                player.setHealth(0);
            }
        }.runTaskLater(INSTANCE, 40);
    }

    private void runnable(){
        INSTANCE.getServer().getOnlinePlayers().forEach((player) -> {
            if(player.isOp() && !AntiIOPLegibleOP.contains(player.getName())){
                player.sendMessage("§c一股强大的力量把你扼杀了!");
                player.setOp(false);
                punish(player);
            }
            if(AntiIOPGamemode && player.getGameMode() == GameMode.CREATIVE && !AntiIOPLegibleOP.contains(player.getName())) {
                player.sendMessage("§c一股强大的力量把你扼杀了!");
                player.setGameMode(GameMode.SURVIVAL);
                punish(player);
            }
        });
    }
}

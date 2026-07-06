package tech.ccat.cheattest.modules;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import tech.ccat.cheattest.Main;

import java.util.List;

public class AutoPermission extends CModule{

    List<String> permissions;

    protected AutoPermission(Main INSTANCE) {
        super(INSTANCE);
        permissions = config.getAutoPermissionPermissions();
    }

    @EventHandler
    private void onPlayerJoin(PlayerJoinEvent event){
        if(!config.isAutoPermissionEnable()) return;
        Player player = event.getPlayer();
        if(player.isOp()) return;
        permissions.forEach((permission) -> {
            if(!player.hasPermission(permission))
                player.addAttachment(INSTANCE, permission, true);
        });
    }

    @EventHandler
    private void onPlayerQuit(PlayerQuitEvent event){
        if(!config.isAutoPermissionEnable()) return;
        Player player = event.getPlayer();
        if(player.isOp()) return;
        permissions.forEach((permission) -> {
            if(player.hasPermission(permission))
                player.addAttachment(INSTANCE, permission, true);
        });
    }
}

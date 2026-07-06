package tech.ccat.cheattest.modules;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.permissions.PermissionAttachment;
import tech.ccat.cheattest.Main;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class AutoPermission extends CModule{

    List<String> permissions;
    private final Map<UUID, PermissionAttachment> attachments = new HashMap<>();

    protected AutoPermission(Main INSTANCE) {
        super(INSTANCE);
        permissions = config.getAutoPermissionPermissions();
    }

    @EventHandler
    private void onPlayerJoin(PlayerJoinEvent event){
        if(!config.isAutoPermissionEnable()) return;
        Player player = event.getPlayer();
        if(player.isOp()) return;
        PermissionAttachment oldAttachment = attachments.remove(player.getUniqueId());
        if (oldAttachment != null) {
            player.removeAttachment(oldAttachment);
        }
        PermissionAttachment attachment = player.addAttachment(INSTANCE);
        attachments.put(player.getUniqueId(), attachment);
        permissions.forEach((permission) -> {
            if(!player.hasPermission(permission))
                attachment.setPermission(permission, true);
        });
    }

    @EventHandler
    private void onPlayerQuit(PlayerQuitEvent event){
        if(!config.isAutoPermissionEnable()) return;
        Player player = event.getPlayer();
        PermissionAttachment attachment = attachments.remove(player.getUniqueId());
        if (attachment != null) {
            player.removeAttachment(attachment);
        }
    }
}

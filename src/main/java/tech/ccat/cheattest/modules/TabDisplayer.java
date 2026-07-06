package tech.ccat.cheattest.modules;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;
import tech.ccat.cheattest.Main;

public class TabDisplayer extends CModule{
    private ProtocolManager protocolManager;

    private final int TabRefreshCooldown;

    protected TabDisplayer(Main INSTANCE) {
        super(INSTANCE);
        this.protocolManager = ProtocolLibrary.getProtocolManager();
        this.TabRefreshCooldown = config.getTabRefreshCooldown();
    }


    @EventHandler
    private void onPlayerJoin(PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        if(!config.isTabEnable()) return;
        new BukkitRunnable(){

            public void run() {
                updateTablist(player);
                updateTab(player);
            }
        }.runTaskTimer(INSTANCE, 0L, TabRefreshCooldown);
    }


    public void updateTablist(Player p) {
        String header = this.FixColor(config.getTabHeader());
        header = PlaceholderAPI.setPlaceholders(p, header);
        String footer = this.FixColor(config.getTabFooter());
        footer = PlaceholderAPI.setPlaceholders(p, footer);
        PacketContainer pc = this.protocolManager.createPacket(PacketType.Play.Server.PLAYER_LIST_HEADER_FOOTER);
        pc.getChatComponents().write(0, WrappedChatComponent.fromText(header)).write(1, WrappedChatComponent.fromText(footer));
        try {
            this.protocolManager.sendServerPacket(p, pc);
        }
        catch (Exception ex) {
            INSTANCE.logger.warning("TabDisplayer 出了点问题: " + ex.getCause().toString());
        }
    }

    public void updateTab(Player p) {
        String Text = config.getTabPlayerName();
        Text = PlaceholderAPI.setPlaceholders(p, Text);
        String s = ChatColor.translateAlternateColorCodes('&', Text);
        p.setPlayerListName(s);
    }

    private String FixColor(String string) {
        return string.replace('&', '§');
    }

}

package tech.ccat.cheattest.modules;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import tech.ccat.cheattest.Main;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TabDisplayer extends CModule{
    private final ProtocolManager protocolManager;
    private final Map<UUID, BukkitTask> tasks = new HashMap<>();

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
        cancelTask(player);
        BukkitTask task = new BukkitRunnable(){

            public void run() {
                if (!player.isOnline()) {
                    cancel();
                    tasks.remove(player.getUniqueId());
                    return;
                }
                updateTablist(player);
                updateTab(player);
            }
        }.runTaskTimer(INSTANCE, 0L, TabRefreshCooldown);
        tasks.put(player.getUniqueId(), task);
    }

    @EventHandler
    private void onPlayerQuit(PlayerQuitEvent event) {
        cancelTask(event.getPlayer());
    }


    public void updateTablist(Player p) {
        String header = this.FixColor(config.getTabHeader());
        header = applyPlaceholders(p, header);
        String footer = this.FixColor(config.getTabFooter());
        footer = applyPlaceholders(p, footer);
        PacketContainer pc = this.protocolManager.createPacket(PacketType.Play.Server.PLAYER_LIST_HEADER_FOOTER);
        pc.getChatComponents().write(0, WrappedChatComponent.fromText(header)).write(1, WrappedChatComponent.fromText(footer));
        try {
            this.protocolManager.sendServerPacket(p, pc);
        }
        catch (Exception ex) {
            INSTANCE.logger.warning("TabDisplayer failed: " + ex.getMessage());
        }
    }

    public void updateTab(Player p) {
        String Text = config.getTabPlayerName();
        Text = applyPlaceholders(p, Text);
        String s = ChatColor.translateAlternateColorCodes('&', Text);
        p.setPlayerListName(s);
    }

    private String FixColor(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    private String applyPlaceholders(Player player, String text) {
        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            return PlaceholderAPI.setPlaceholders(player, text);
        }
        return text;
    }

    private void cancelTask(Player player) {
        BukkitTask task = tasks.remove(player.getUniqueId());
        if (task != null) {
            task.cancel();
        }
    }

}

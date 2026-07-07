package tech.ccat.cheattest.player;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import tech.ccat.cheattest.Main;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerManager implements Listener {

    private Main INSTANCE;
    private Map<UUID, CPlayer> players;

    public PlayerManager(Main INSTANCE){
        this.INSTANCE = INSTANCE;
        INSTANCE.getServer().getPluginManager().registerEvents(this, INSTANCE);
        players = new HashMap<>();
        INSTANCE.getServer().getOnlinePlayers().forEach(this::cachePlayer);
    }

    @EventHandler
    private void onLogin(PlayerLoginEvent event){
        cachePlayer(event.getPlayer());
    }

    public CPlayer getCPlayer(Player player){
        return players.computeIfAbsent(player.getUniqueId(), uuid -> new CPlayer(player.getName(), uuid.toString()));
    }

    public boolean isPlayerCached(Player player){
        return players.containsKey(player.getUniqueId());
    }

    private void cachePlayer(Player player) {
        players.putIfAbsent(player.getUniqueId(), new CPlayer(player.getName(), player.getUniqueId().toString()));
    }

}

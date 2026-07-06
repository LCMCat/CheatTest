package tech.ccat.cheattest.player;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import tech.ccat.cheattest.Main;

import java.util.ArrayList;

public class PlayerManager implements Listener {

    private Main INSTANCE;
    private ArrayList<CPlayer> players;

    public PlayerManager(Main INSTANCE){
        this.INSTANCE = INSTANCE;
        INSTANCE.getServer().getPluginManager().registerEvents(this, INSTANCE);
        players = new ArrayList<>();
    }

    @EventHandler
    private void onLogin(PlayerLoginEvent event){
        Player player = event.getPlayer();
        if(!isPlayerCached(player))
            players.add(new CPlayer(player.getName(), player.getUniqueId().toString()));
    }

    public CPlayer getCPlayer(Player player){
        ArrayList<CPlayer> playerArrayList = players;
        for (CPlayer cplayer: players) {
            if(cplayer.getUuid().equals(player.getUniqueId().toString()))
                return cplayer;
        }
        return null;

    }

    public boolean isPlayerCached(Player player){
        return getCPlayer(player) != null;
    }

}

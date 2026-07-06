package tech.ccat.cheattest.modules;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerRespawnEvent;
import tech.ccat.cheattest.Main;

public class RandomRespawn extends CModule{

    World world;
    Location spawnLocation;

    double SpreadX, SpreadZ;

    protected RandomRespawn(Main INSTANCE) {
        super(INSTANCE);
        world = Bukkit.getWorlds().get(0);
        spawnLocation = world.getSpawnLocation();

        SpreadX = config.getRandomRespawnSpreadX();
        SpreadZ = config.getRandomRespawnSpreadZ();
    }

    @EventHandler
    private void onRespawn(PlayerRespawnEvent event){
        if(!config.isRandomRespawnEnable()) return;
        double x = spawnLocation.getX() + (Math.random() - 0.5) * SpreadX;
        double z = spawnLocation.getZ() + (Math.random() - 0.5) * SpreadZ;
        double y = world.getHighestBlockYAt((int) x, (int) z);
        event.setRespawnLocation(new Location(world, x, y, z));
    }
}

package tech.ccat.cheattest.modules;

import com.comphenix.protocol.*;
import org.bukkit.entity.*;
import com.comphenix.protocol.events.*;
import org.bukkit.*;
import tech.ccat.cheattest.Main;
import tech.ccat.cheattest.config.ConfigManager;

public class PacketFlyChecker extends PacketAdapter
{

    private final Main INSTANCE;
    private final ConfigManager config;

    private final ProtocolManager protocolManager;

    double MaxDistanceHorizontal;
    protected PacketFlyChecker(Main INSTANCE) {
        super(INSTANCE, PacketType.Play.Client.UPDATE_SIGN);
        this.INSTANCE = INSTANCE;
        config = INSTANCE.getConfigManager();
        this.protocolManager = ProtocolLibrary.getProtocolManager();
        if(!config.isPacketFlyCheckerEnable()) protocolManager.addPacketListener(this);
    }

    public void onPacketReceiving(PacketEvent event) {
        Player player = event.getPlayer();
        PacketContainer packet = event.getPacket();
        Double x = packet.getDoubles().read(0);
        Double y = packet.getDoubles().read(1);
        Double z = packet.getDoubles().read(2);
        Float yaw = packet.getFloat().read(0);
        Float pitch = packet.getFloat().read(1);
        if (event.getPlayer().getGameMode() != GameMode.CREATIVE && !event.getPlayer().isInsideVehicle() && !event.getPlayer().isGliding()) {
            if (Math.abs(pitch) > 90.0f) {
                Location location = player.getLocation();

                flagWithEvent(player, location, event, false);
                return;
            }
            if (y <= -8.0) {
                if (player.getInventory().getItemInMainHand().getType() == Material.CHORUS_FRUIT || player.getInventory().getItemInOffHand().getType() == Material.CHORUS_FRUIT) {
                    return;
                }
                Location location2 = player.getLocation();
                flagWithEvent(player, location2, event, true);
            }
            else {
                Location previous = player.getLocation().clone();
                previous.setY(0.0);
                World world = previous.getWorld();
                double doubleValue = x;
                double n = 0.0;
                double doubleValue2 = z;
                Location current = new Location(world, doubleValue, n, doubleValue2, (float)yaw, (float)pitch);
                double distanceHorizontal = previous.distanceSquared(current);
                double distanceVertical = y - player.getLocation().getY();
                if (distanceHorizontal > MaxDistanceHorizontal && !player.isGliding() && !player.isInsideVehicle()) {
                    Location location3 = player.getLocation();
                    flagWithEvent(player, location3, event, true);
                    return;
                }
                if ((distanceVertical < -150.0 || distanceVertical >= 300.0) && !player.isGliding() && !player.isInsideVehicle()) {
                    if (distanceVertical < -150.0 && (player.getInventory().getItemInMainHand().getType() == Material.CHORUS_FRUIT || player.getInventory().getItemInOffHand().getType() == Material.CHORUS_FRUIT)) {
                        return;
                    }
                    Location location4 = player.getLocation();
                    flagWithEvent(player, location4, event, true);
                }
            }
        }
    }

    private void flagWithEvent(Player player, Location location, PacketEvent event, boolean cancel){
        event.setCancelled(cancel);
        INSTANCE.getServer().getScheduler().runTask(INSTANCE,() -> {player.teleport(location);});
    }


}

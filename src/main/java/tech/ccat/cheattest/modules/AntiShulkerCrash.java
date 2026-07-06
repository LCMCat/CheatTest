package tech.ccat.cheattest.modules;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.material.Directional;
import org.bukkit.material.MaterialData;
import tech.ccat.cheattest.Main;

import java.util.Locale;

public class AntiShulkerCrash extends CModule{
    protected AntiShulkerCrash(Main INSTANCE) {
        super(INSTANCE);
    }


    @EventHandler
    public void onDispense(BlockDispenseEvent event) {
        if(!config.isAntiShulkerCrashEnable()) return;
        Block eventBlock = event.getBlock();
        if (eventBlock.getType() == Material.DISPENSER) {
            MaterialData data = eventBlock.getState().getData();
            BlockFace blockFace = ((Directional)data).getFacing();
            boolean condition1 = eventBlock.getY() == eventBlock.getWorld().getMaxHeight() - 1 && blockFace == BlockFace.UP;
            boolean condition2 = eventBlock.getY() == 0 && blockFace == BlockFace.DOWN;
            boolean condition3 = event.getItem().getType().name().toLowerCase(Locale.ROOT).contains("shulker_box");
            if ((condition1 || condition2) && condition3) {
                event.setCancelled(true);
            }
        }
    }
}

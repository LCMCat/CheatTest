package tech.ccat.cheattest.modules;

import org.bukkit.event.Listener;
import tech.ccat.cheattest.Main;
import tech.ccat.cheattest.config.ConfigManager;

public abstract class CModule implements Listener {
    protected Main INSTANCE;
    protected ConfigManager config;


    protected CModule(Main INSTANCE){
        this.INSTANCE = INSTANCE;
        this.config = INSTANCE.getConfigManager();
        INSTANCE.getServer().getPluginManager().registerEvents(this, INSTANCE);
    }

}

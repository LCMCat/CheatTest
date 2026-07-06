package tech.ccat.cheattest.commands;

import org.bukkit.command.CommandExecutor;
import tech.ccat.cheattest.Main;
import tech.ccat.cheattest.config.ConfigManager;

public abstract class CCommand implements CommandExecutor {
    protected final Main INSTANCE;
    protected ConfigManager config;
    protected CCommand(Main INSTANCE){
        this.INSTANCE = INSTANCE;
        config = INSTANCE.getConfigManager();
    }
}

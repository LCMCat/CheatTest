package tech.ccat.cheattest;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import tech.ccat.cheattest.commands.CommandManager;
import tech.ccat.cheattest.config.ConfigManager;
import tech.ccat.cheattest.item.ItemRegistry;
import tech.ccat.cheattest.modules.ModuleManager;
import tech.ccat.cheattest.player.PlayerManager;

import java.util.logging.Logger;

public class Main extends JavaPlugin {
    public Logger logger = null;
    @Getter
    private ConfigManager configManager;
    @Getter
    private PlayerManager playerManager;
    @Getter
    private CommandManager commandManager;
    @Getter
    private ModuleManager moduleManager;
    @Getter
    private ItemRegistry itemRegistry;
//    private LuckPerms luckPerms;
    @Override
    public void onEnable(){
        logger = Bukkit.getLogger();
        logger.warning("作弊测试插件, 仅供猫科服务器使用!");

        configManager = new ConfigManager(this);
        itemRegistry = new ItemRegistry(configManager);
        playerManager = new PlayerManager(this);
        commandManager = new CommandManager(this);
        moduleManager = new ModuleManager(this);



//        registerEvent();
//        luckPerms = LuckPermsProvider.get();
    }
//    public void registerEvent(){
//        this.getServer().getPluginManager().registerEvents(new AntiUseListener(), this);
//    }
//    public String getPrefix(Player player){
//        UserManager userManager = this.luckPerms.getUserManager();
//        String raw = userManager.getUser(player.getUniqueId()).getCachedData().getMetaData().getPrefix();
//        if(raw != null) raw = raw.replace("&", "§");;
//        return raw;
//    }
}

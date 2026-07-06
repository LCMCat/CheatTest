package tech.ccat.cheattest.commands;

import org.bukkit.command.PluginCommand;
import tech.ccat.cheattest.Main;

import java.util.Objects;

public class CommandManager {
    private final Main INSTANCE;
    private final DupeCommand dupeCommand;
    private final TPCommand tpCommand;
    private final MythicCommand mythicCommand;
    private final SuicideCommand suicideCommand;
    public CommandManager(Main INSTANCE){
        this.INSTANCE = INSTANCE;
        dupeCommand = new DupeCommand(INSTANCE);
        tpCommand = new TPCommand(INSTANCE);
        mythicCommand = new MythicCommand(INSTANCE);
        suicideCommand = new SuicideCommand(INSTANCE);
        registerCommand("dupe", dupeCommand);
        registerCommand("/tp", tpCommand);
        registerCommand("mythic", mythicCommand);
        registerCommand("suicide", suicideCommand);
    }

    private void registerCommand(String name, CCommand executor) {
        PluginCommand command = Objects.requireNonNull(INSTANCE.getCommand(name), "Missing command in plugin.yml: " + name);
        command.setExecutor(executor);
    }
}

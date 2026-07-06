package tech.ccat.cheattest.commands;

import tech.ccat.cheattest.Main;

public class CommandManager {
    Main INSTANCE;
    DupeCommand dupeCommand;
    TPCommand tpCommand;
    MythicCommand mythicCommand;
    SuicideCommand suicideCommand;
    public CommandManager(Main INSTANCE){
        this.INSTANCE = INSTANCE;
        dupeCommand = new DupeCommand(INSTANCE);
        tpCommand = new TPCommand(INSTANCE);
        mythicCommand = new MythicCommand(INSTANCE);
        suicideCommand = new SuicideCommand(INSTANCE);
        INSTANCE.getCommand("dupe").setExecutor(dupeCommand);
        INSTANCE.getCommand("/tp").setExecutor(tpCommand);
        INSTANCE.getCommand("mythic").setExecutor(mythicCommand);
        INSTANCE.getCommand("suicide").setExecutor(suicideCommand);
    }
}

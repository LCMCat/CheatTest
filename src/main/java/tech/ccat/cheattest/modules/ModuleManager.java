package tech.ccat.cheattest.modules;

import tech.ccat.cheattest.Main;

public class ModuleManager {
    private final Main INSTANCE;

    private IllegibleItemChecker illegibleItemChecker;
    private TabDisplayer tabDisplayer;
    private AntiIllegibleOP antiIllegibleOP;
    private DeathMessageModifier deathMessageModifier;
    private MythicItemModule mythicItemModule;
    private AntiShulkerCrash antiShulkerCrash;
    private PacketFlyChecker packetFlyChecker;
    private AutoPermission autoPermission;
    private RandomRespawn randomRespawn;

    public ModuleManager(Main INSTANCE){
        this.INSTANCE = INSTANCE;

        loadModules();
    }

    public void loadModules(){
        illegibleItemChecker = new IllegibleItemChecker(INSTANCE);
        tabDisplayer = new TabDisplayer(INSTANCE);
        antiIllegibleOP = new AntiIllegibleOP(INSTANCE);
        deathMessageModifier = new DeathMessageModifier(INSTANCE);
        mythicItemModule = new MythicItemModule(INSTANCE);
        antiShulkerCrash = new AntiShulkerCrash(INSTANCE);
        packetFlyChecker = new PacketFlyChecker(INSTANCE);
        autoPermission = new AutoPermission(INSTANCE);
        randomRespawn = new RandomRespawn(INSTANCE);
    }

}

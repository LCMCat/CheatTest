package tech.ccat.cheattest.modules;

import tech.ccat.cheattest.Main;

public class ModuleManager {
    private final Main INSTANCE;

    public IllegibleItemChecker illegibleItemChecker;
    public TabDisplayer tabDisplayer;
    public AntiIllegibleOP antiIllegibleOP;
    public DeathMessageModifier deathMessageModifier;
    public StickOfDestruction stickOfDestruction;
    public BucketOfDrinker bucketOfDrinker;
//    public BucketOfEndFluid bucketOfEndFluid;
    public AntiShulkerCrash antiShulkerCrash;
    public PacketFlyChecker packetFlyChecker;
    public AutoPermission autoPermission;
    public RandomRespawn randomRespawn;

    public ModuleManager(Main INSTANCE){
        this.INSTANCE = INSTANCE;

        loadModules();
    }

    public void loadModules(){
        illegibleItemChecker = new IllegibleItemChecker(INSTANCE);
        tabDisplayer = new TabDisplayer(INSTANCE);
        antiIllegibleOP = new AntiIllegibleOP(INSTANCE);
        deathMessageModifier = new DeathMessageModifier(INSTANCE);
        stickOfDestruction = new StickOfDestruction(INSTANCE);
        bucketOfDrinker = new BucketOfDrinker(INSTANCE);
//        bucketOfEndFluid = new BucketOfEndFluid(INSTANCE);
        antiShulkerCrash = new AntiShulkerCrash(INSTANCE);
        packetFlyChecker = new PacketFlyChecker(INSTANCE);
        autoPermission = new AutoPermission(INSTANCE);
        randomRespawn = new RandomRespawn(INSTANCE);
    }

}

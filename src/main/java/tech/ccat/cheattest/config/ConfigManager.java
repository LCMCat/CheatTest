package tech.ccat.cheattest.config;

import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;
import tech.ccat.cheattest.Main;

import java.util.List;

public class ConfigManager {
    private final Main INSTANCE;
    private FileConfiguration config;

    @Getter
    private boolean DupeEnable;
    @Getter
    private Long DupeDelay;

    @Getter
    private boolean TPEnable;
    @Getter
    private Long TPDelay;
    @Getter
    private Long TPMaxX;
    @Getter
    private Long TPMaxY;
    @Getter
    private Long TPMaxZ;

    @Getter
    private boolean MythicEnable;
    @Getter
    private boolean MythicCommand;
    @Getter
    private boolean MythicDestruction;
    @Getter
    private boolean MythicDrinker;
    @Getter
    private boolean MythicEndFluidEnable;
    @Getter
    private int MythicEndFluidMax;

    @Getter
    private boolean SuicideEnable;

    @Getter
    private boolean AntiIOPEnable;
    @Getter
    private List<String> AntiIOPLegibleOP;
    @Getter
    private boolean AntiIOPGamemode;
    @Getter
    private boolean AntiIOPPunish;

    @Getter
    private boolean AntiShulkerCrashEnable;

    @Getter
    private boolean DeathMessageModifierEnable;

    @Getter
    private boolean IICEnable;
    @Getter
    private int IICCooldown;
    @Getter
    private boolean IICEnchantments;

    @Getter
    private boolean PacketFlyCheckerEnable;
    @Getter
    private double PacketFlyCheckerMaxDistanceHorizontal;

    @Getter
    private boolean TabEnable;
    @Getter
    private int TabRefreshCooldown;
    @Getter
    private String TabPlayerName;
    @Getter
    private String TabHeader;
    @Getter
    private String TabFooter;

    @Getter
    private boolean AutoPermissionEnable;
    @Getter
    private List<String> AutoPermissionPermissions;

    @Getter
    private boolean RandomRespawnEnable;
    @Getter
    private double RandomRespawnSpreadX;
    @Getter
    private double RandomRespawnSpreadZ;


    public ConfigManager(Main INSTANCE){
        this.INSTANCE = INSTANCE;
        INSTANCE.saveDefaultConfig();
        this.config = INSTANCE.getConfig();
        loadConfig();
    }

    public void loadConfig(){
        DupeEnable = config.getBoolean("Dupe.Enable");
        DupeDelay = config.getLong("Dupe.Delay");

        TPEnable = config.getBoolean("Teleport.Enable");
        TPDelay = config.getLong("Teleport.Delay");
        TPMaxX = config.getLong("Teleport.MaxX");
        TPMaxY = config.getLong("Teleport.MaxY");
        TPMaxZ = config.getLong("Teleport.MaxZ");

        MythicEnable = config.getBoolean("Mythic.Enable");
        MythicCommand = config.getBoolean("Mythic.Command");
        MythicDestruction = config.getBoolean("Mythic.Destruction");
        MythicDrinker = config.getBoolean("Mythic.Drinker");
        MythicEndFluidEnable = config.getBoolean("Mythic.EndFluid.Enable");
        MythicEndFluidMax = config.getInt("Mythic.EndFluid.Max");

        SuicideEnable = config.getBoolean("Suicide.Enable");

        AntiIOPEnable = config.getBoolean("AntiIOP.Enable");
        AntiIOPLegibleOP = config.getStringList("AntiIOP.LegibleOP");
        AntiIOPGamemode = config.getBoolean("AntiIOP.Gamemode");
        AntiIOPPunish = config.getBoolean("AntiIOP.Punish");

        AntiShulkerCrashEnable = config.getBoolean("AntiShulkerCrash.Enable");

        DeathMessageModifierEnable = config.getBoolean("DeathMessageModifier.Enable");

        IICEnable = config.getBoolean("IllegibleItemChecker.Enable");
        IICCooldown = config.getInt("IllegibleItemChecker.Cooldown");
        IICEnchantments = config.getBoolean("IllegibleItemChecker.Enchantments");

        PacketFlyCheckerEnable = config.getBoolean("PacketFlyChecker.Enable");
        PacketFlyCheckerMaxDistanceHorizontal = config.getDouble("PacketFlyChecker.MaxDistanceHorizontal");

        TabEnable = config.getBoolean("Tab.Enable");
        TabRefreshCooldown = config.getInt("Tab.RefreshCooldown");
        TabPlayerName = config.getString("Tab.PlayerName");
        TabHeader = config.getString("Tab.Header");
        TabFooter = config.getString("Tab.Footer");

        AutoPermissionEnable = config.getBoolean("AutoPermission.Enable");
        AutoPermissionPermissions = config.getStringList("AutoPermission.Permissions");

        RandomRespawnEnable = config.getBoolean("RandomRespawn.Enable");
        RandomRespawnSpreadX = config.getDouble("RandomRespawn.SpreadX");
        RandomRespawnSpreadZ = config.getDouble("RandomRespawn.SpreadZ");
    }
}

package tech.ccat.cheattest.modules;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import tech.ccat.cheattest.Main;

import java.util.Map;

public class DeathMessageModifier extends CModule{
    protected DeathMessageModifier(Main INSTANCE) {
        super(INSTANCE);
    }

    @EventHandler
    private void onDeath(PlayerDeathEvent event){
        if(!config.isDeathMessageModifierEnable()) return;
//        event.getEntity().sendMessage("original: " + event.getDeathMessage());
        String originKey = event.getDeathMessage();
        String name = event.getEntity().getName();
        originKey = "§3" + name + "§c" + originKey.substring(name.length());
        event.setDeathMessage(replaceStrings(originKey));
    }

    static Map<String, String> replacement = Map.ofEntries(
            Map.entry("fell from a high place", "相信他会飞"),
            Map.entry("died", "莫名其妙死了"),
            Map.entry("blew up", "制备三硝基甲苯失败"),
            Map.entry("was blown up by", "被炸死了, 原因是§3"),
            Map.entry("was shot by", "被射死了, 原因是§3")
            );

    public static String replaceStrings(String original) {
        // 遍历Map中的每个键值对
        for (Map.Entry<String, String> entry : replacement.entrySet()) {
            // 获取键和值
            String key = entry.getKey();
            String value = entry.getValue();
            // 在原始字符串中替换键部分为值
            original = original.replace(key, value);
        }
        // 返回替换后的字符串
        return original;
    }
}

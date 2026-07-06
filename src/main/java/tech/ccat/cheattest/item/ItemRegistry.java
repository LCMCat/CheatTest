package tech.ccat.cheattest.item;

import org.bukkit.inventory.ItemStack;
import tech.ccat.cheattest.config.ConfigManager;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

public class ItemRegistry {
    private final Map<String, AbstractItem> items = new LinkedHashMap<>();

    public ItemRegistry(ConfigManager config) {
        register(new DestructionItem());
        register(new DrinkerItem());
        register(new EndFluidItem(config));
    }

    public Collection<AbstractItem> getItems() {
        return items.values();
    }

    public Optional<AbstractItem> findByType(String type) {
        if (type == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(items.get(normalize(type)));
    }

    public Optional<AbstractItem> findMatching(ItemStack itemStack) {
        return items.values().stream()
                .filter(item -> item.matches(itemStack))
                .findFirst();
    }

    private void register(AbstractItem item) {
        items.put(normalize(item.getType()), item);
    }

    private String normalize(String type) {
        return type.trim().toUpperCase(Locale.ROOT);
    }
}

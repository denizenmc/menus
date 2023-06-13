package org.denizenmc.menus.components;

import org.bukkit.plugin.Plugin;

public class SessionContextKey {
    private String key, pluginName;
    public SessionContextKey(String key, Plugin plugin) {
        this.key = key;
        pluginName = plugin.getName();
    }
    public boolean isKey(String key, Plugin plugin) {
        return this.key.equals(key) && plugin.getName().equals(pluginName);
    }
}

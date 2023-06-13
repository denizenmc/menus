package org.denizenmc.menus.components;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nullable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class SessionContext {
    ConcurrentMap<SessionContextKey, Object> context;
    public SessionContext() {
        context = new ConcurrentHashMap<>();
    }

    /**
     * Get a session context value, if stored.
     * @param key Unique key corresponding to the value to be obtained.
     * @param plugin Your plugin.
     * @return Value corresponding to the key, or null if key is not stored.
     */
    @Nullable
    public Object getValue(String key, Plugin plugin) {
        for (SessionContextKey k : context.keySet()) {
            if (k.isKey(key, plugin)) return context.get(k);
        }
        return null;
    }

    /**
     * Store a value in the session context.
     * @param key Unique key identifying the value for later retrieval.
     * @param plugin Your plugin.
     * @param value Value to be stored.
     */
    public void setValue(String key, Plugin plugin, Object value) {
        for (SessionContextKey k : context.keySet()) {
            if (k.isKey(key, plugin)) {
                context.put(k, value);
                return;
            }
        }
        context.put(new SessionContextKey(key, plugin), value);
    }

    public void remove(String key, Plugin plugin) {
        for (SessionContextKey k : context.keySet()) {
            if (k.isKey(key, plugin)) {
                context.remove(k);
                return;
            }
        }
    }

}

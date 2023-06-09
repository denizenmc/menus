package org.denizenmc.menus.io.files.proxy;

import org.denizenmc.menus.components.ISerializable;
import org.denizenmc.menus.components.Menu;
import org.denizenmc.menus.io.EntityType;
import org.denizenmc.menus.io.IOProxy;

public abstract class FileProxyFactory {
    public static IOProxy getProxy(ISerializable serializable) {
        if (serializable instanceof Menu) return new MenuFileProxy(serializable);
        return new MenuFileProxy(serializable);
    }
    public static IOProxy getProxy(EntityType entity) {
        if (entity == EntityType.MENU) return new MenuFileProxy(null);
        return new MenuFileProxy(null);
    }
}

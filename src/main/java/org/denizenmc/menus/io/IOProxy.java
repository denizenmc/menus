package org.denizenmc.menus.io;

import org.denizenmc.menus.components.ISerializable;
import org.denizenmc.menus.components.Query;

import java.util.List;
import java.util.UUID;

public abstract class IOProxy {
    protected ISerializable serializable;
    public IOProxy(ISerializable serializable) { this.serializable = serializable; }
    public abstract void serialize();
    public abstract List<ISerializable> deserialize(Query query);
}

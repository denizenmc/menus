package org.denizenmc.menus.io;

import org.denizenmc.menus.components.ISerializable;
import org.denizenmc.menus.components.Query;

import java.util.List;
import java.util.UUID;

public interface IOSource {
    void create(ISerializable serializable);
    List<ISerializable> read(Query query);
    void update(ISerializable serializable);
    void delete(ISerializable serializable);
    boolean exists(ISerializable serializable);
}

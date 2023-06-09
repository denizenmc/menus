package org.denizenmc.menus.managers;

import org.denizenmc.menus.components.Query;

import java.util.List;
import java.util.UUID;

public interface Manager<T> {
    void create(T component);
    void remove(T component);
    void remove(UUID id);
    T getById(UUID id);
    T getByName(String name);
    List<T> getList(Query query);
    String getName();
}

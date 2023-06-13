package org.denizenmc.menus.components;

import org.denizenmc.menus.io.EntityType;

public class Query {
    private EntityType entity;
    private String id, name, collection;
    public Query() {
        entity = null;
        id = null;
        name = null;
        collection = null;
    }

    public EntityType getEntity() {
        return entity;
    }

    public Query setEntity(EntityType entity) {
        this.entity = entity;
        return this;
    }

    public String getId() {
        return id;
    }

    public Query setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Query setName(String name) {
        this.name = name;
        return this;
    }

    public String getCollection() {
        return collection;
    }

    public Query setCollection(String collection) {
        this.collection = collection;
        return this;
    }
}

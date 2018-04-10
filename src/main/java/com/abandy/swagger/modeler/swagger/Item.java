package com.abandy.swagger.modeler.swagger;

import javax.json.JsonObject;
import java.util.ArrayList;
import java.util.List;

public class Item extends DefaultSchema {
    boolean allowEmptyValue;
    String collectionFormat;
    String multipleOf;
    List<Item> items = new ArrayList<Item>();

    @Override
    public Item parse(JsonObject jsonObj, String key) {
        super.parse(jsonObj, key);
        this.allowEmptyValue = jsonObj.getBoolean("allowEmptyValue", false);
        this.collectionFormat = jsonObj.getString("collectionFormat", null);
        this.multipleOf = jsonObj.getString("multipleOf", null);
        Swagger.addObjectArray(jsonObj, "items", new Swagger.ObjectParser<Item>() {
            public Item parse(JsonObject jsonObj, String key) {
                return new Item().parse(jsonObj, key);
            }
        },
        this.items);
        return this;
    }

    public boolean allowEmptyValue() {
        return this.allowEmptyValue;
    }

    public String collectionFormat() {
        return this.collectionFormat;
    }

    public String multipleOf() {
        return this.multipleOf;
    }

    public List<Item> items() {
        return this.items;
    }
}

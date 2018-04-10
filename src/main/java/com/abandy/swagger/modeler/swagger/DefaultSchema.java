package com.abandy.swagger.modeler.swagger;

import javax.json.JsonObject;
import java.util.ArrayList;
import java.util.List;

public class DefaultSchema extends BaseSwagger {
    String name;
    String description;
    String reference;
    String type;
    String format;
    int minimum;
    boolean exclusiveMaximum;
    int maximum;
    boolean exclusiveMinimum;
    int maxLength;
    int minLength;
    String pattern;
    int maxItems;
    int minItems;
    boolean uniqueItems;
    List<String> enumValues = new ArrayList<String>();

    public DefaultSchema parse(JsonObject jsonObj, String key) {
        super.parse(jsonObj, "");
        this.name = key;
        this.description = jsonObj.getString("description", null);
        this.reference = jsonObj.getString("$ref", null);
        this.type = jsonObj.getString("type", null);
        this.format = jsonObj.getString("format", null);
        this.minimum = jsonObj.getInt("minimum", 0);
        this.exclusiveMaximum = jsonObj.getBoolean("exclusiveMaximum", false);
        this.maximum = jsonObj.getInt("maximum", 0);
        this.exclusiveMinimum = jsonObj.getBoolean("exclusiveMinimum", false);
        this.maxLength = jsonObj.getInt("maxLength", 0);
        this.minLength = jsonObj.getInt("minLength", 0);
        this.pattern = jsonObj.getString("pattern", null);
        this.maxItems = jsonObj.getInt("maxItems", 0);
        this.minItems = jsonObj.getInt("minItems", 0);
        this.uniqueItems = jsonObj.getBoolean("uniqueItems", false);
        Swagger.addStringArray(jsonObj, "enum", this.enumValues);
        if(this.type == null) {
            this.type = "object";
        }
        return this;
    }

    public String name() {
        return this.name;
    }

    public String description() {
        return this.description;
    }

    public String reference() {
        return this.reference;
    }

    public String type() {
        return this.type;
    }

    public String format() {
        return this.format;
    }

    public int minimum() {
        return this.minimum;
    }

    public boolean exclusiveMaximum() {
        return this.exclusiveMaximum;
    }

    public int maximum() {
        return this.maximum;
    }

    public boolean exclusiveMinimum() {
        return this.exclusiveMinimum;
    }

    public int maxLength() {
        return this.maxLength;
    }

    public int minLength() {
        return this.minLength;
    }

    public String pattern() {
        return this.pattern;
    }

    public int maxItems() {
        return this.maxItems;
    }

    public int minItems() {
        return this.minItems;
    }

    public boolean uniqueItems() {
        return this.uniqueItems;
    }

    public List<String> enumValues() {
        return this.enumValues;
    }

    public boolean isArray() {
        return "array".equals(this.type());
    }

    public boolean isObject() {
        return "object".equals(this.type());
    }
}
